package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {

    	// okメソッドは文字列をResultインスタンにして返却する
    	// index.scala.htmlをレンダリングしている
        return ok(index.render("これはテンプレートのテストです"));
    }


    public static Result redirect() {

    	// トップページにリダイレクト
    	// アドレスを静的に記述している
        return redirect("/");
    }

}
