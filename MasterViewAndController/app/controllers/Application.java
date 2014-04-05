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
    	// アドレスを静的に記述していると変更が面倒
    	// return redirect("/");
    	// 
    	// リバースルーティングという手法を使う
    	// これにより、アドレス変更の影響を受けない
    	// 
    	// routesファイルに記述されたControllerのルーティング情報は、
    	// controllers.routesにまとめられる
    	// controllers.routes.ApplicationはControllers.Applicationとは異なる
    	// 「ReverseApplication」というクラスで、Application.index()に割り当てられたアドレスがわかる
    	return redirect(controllers.routes.Application.index());
    }

}
