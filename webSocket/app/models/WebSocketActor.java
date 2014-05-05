package models;

import java.util.HashMap;
import java.util.Map;

import play.libs.Akka;
import play.libs.Json;
import play.mvc.WebSocket;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import static play.libs.F.*;
import static akka.pattern.Patterns.ask;
import static java.util.concurrent.TimeUnit.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import events.EventUtil;
import events.Message;
import events.WebSocketEvent;

/**
 * WebSocket接続、イベント周りを管理
 *
 */
public class WebSocketActor extends UntypedActor {

	// Actorのインスタンス
	private final static ActorRef ref = Akka.system().actorOf(new Props(WebSocketActor.class));
	
	// 送信先メンバー一覧（コンテンツの接続一覧）
	// ユーザ名をキーに持つ、出力チャネル
	Map<String, WebSocket.Out<JsonNode>> members = new HashMap<String, WebSocket.Out<JsonNode>>();
	
	/**
	 * メンバーに接続ユーザを追加する
	 * 
	 * @param username
	 * @param in
	 * @param out
	 * @throws Exception
	 */
	public static void join(final String username,
			WebSocket.In<JsonNode> in,
			WebSocket.Out<JsonNode> out) throws Exception {
		
		// JOINイベントを発生させ、接続一覧に追加
		Boolean result = (Boolean)Await.result(
				ask(ref, new Message(username, "", "", WebSocketEvent.JOIN, out), 1000), 
				Duration.create(1, SECONDS));
		
		// 接続に成功したら、
		if(result){
			// onMessageメソッドにコールバックを渡す
			in.onMessage(new Callback<JsonNode>() {

				@Override
				public void invoke(JsonNode event) throws Throwable {
					// 一方通行にActorにメッセージを投げるメソッド
					ref.tell(
						new Message(
							username, event.get("x").asText(), event.get("y").asText(), WebSocketEvent.MESSAGE, null
						), 
						ref
					);	
				}
			});
			
			// onCloseメソッドにコールバックを渡す
			in.onClose(new Callback0() {
				
				@Override
				public void invoke() throws Throwable {
					// 一方通行にActorにメッセージを投げるメソッド
					ref.tell(new Message(username, "", "", WebSocketEvent.QUIT, null), ref);
					
				}
			});
		} else {
			ObjectNode error = Json.newObject();
			error.put("error", result);
			out.write(error);
		}
	}

	/**
	 * メッセージを受け取り、実行する
	 * Actorへメッセージが渡されたタイミングで実行される
	 * メッセージの種類に合わせてアクションを決める
	 * @param message : Object
	 * @throws Exception
	 */
	@Override
	public void onReceive(Object message) throws Exception {
		
		Option<Message> event = EventUtil.getEvent(message);
		if(event.isDefined()){
			Message m = event.get();
			switch (m.getEventType()) {
			case JOIN:
				// ユーザを接続一覧に追加する
				members.put(m.getUsername(), m.getChannel());
				// 送信者に対して
				getSender().tell(true, ref);
				break;
			case MESSAGE:
				// WebSocket経由で接続一覧に送信
				WebSockectMessenger.notifyAll(m.getUsername(), m.getX(), m.getY(), members);
				break;
			case QUIT:
				// ユーザを接続一覧から削除
				members.remove(m.getUsername());
				break;

			default:
				unhandled(message);
				break;
			}
		}
	}

}
