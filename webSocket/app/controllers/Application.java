package controllers;

import models.WebSocketActor;

import com.fasterxml.jackson.databind.JsonNode;

import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    /**
     * ユーザー名を受け取り、WebSocketコンテンツを表示
     * @param username
     * @return
     */
    public static Result draggable(String username){
    	// ユーザー名をセッションに保持
    	session("username", username);
    	// 画面を表示
    	return ok(draggable.render("WebSocket Sample", username));
    }
    
    /**
     * draggableアクションで表示されたページにあるJavaScriptからWebSocketプロトコルでアクセスされるため
     * ユーザーが直接利用するアクションではない。
     * Json以外にもString型やByte型で返却できる。
     * 仕様上はJson, String型はテキスト、Byte型はバイナリフレーム
     * @return WebSocket<JsonNode>
     * 
     */
    public static WebSocket<JsonNode> ws(){
    	// セッションのユーザー名を取得
    	final String username = session("username");
    	
    	return new WebSocket<JsonNode>(){
    		// 返却するオブジェクトの生成
    		// 引数のinが入力用チャネル、outが出力用チャネル
    		@Override
    		public void onReady(final WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out) {
    			try {
    				WebSocketActor.join(username, in, out);
    			} catch (Exception ex){
    				Logger.error("Can't connect WebSocket");
    				ex.printStackTrace();
    			}
    		};
    	};
    }
}
