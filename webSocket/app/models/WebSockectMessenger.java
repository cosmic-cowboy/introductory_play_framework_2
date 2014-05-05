package models;

import java.util.Map;

import play.Logger;
import play.libs.Json;
import play.mvc.WebSocket;
import play.mvc.WebSocket.Out;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * WebSocketを使ってクライアントへメッセージを送信
 *
 */
public class WebSockectMessenger {

	/**
	 * クライアントへメッセージを送信する
	 * 
	 * @param username:送信元ユーザ
	 * @param x：X座標
	 * @param y：Y座標
	 * @param members:送信先一覧
	 */
	public static void notifyAll(String username, String x, String y,
			Map<String, Out<JsonNode>> members) {

		ObjectNode event = Json.newObject();
		event.put("username", username);
		event.put("x", x);
		event.put("y", y);
		Logger.info(event.toString());
		
		for(WebSocket.Out<JsonNode> channel : members.values()){ 
			// channelオブジェクトのwriteメソッドへ渡すことで送信が完了する
			channel.write(event);
		}
	}

}
