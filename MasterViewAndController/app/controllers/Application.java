package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {

    	// okメソッドは文字列をResultインスタンにして返却する
    	// HTMLで出力するにはasメソッドを使って、形式を明確にする
    	// 記述形式はHTMLのContent-typeを設定する
        return ok("<html><body><h1>Hello!</h1><p>This is Test.</p></body></html>")
        		.as("text/html");
    }

}
