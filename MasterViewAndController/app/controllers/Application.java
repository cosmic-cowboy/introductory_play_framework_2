package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

	// routesで指定したindex/:msgにより、
	// index/パラメータという形でアクセスが可能に
	// パラメータが取得可能になる
	// 複数指定する場合はindex/:msg~:idのように
	public static Result index(String msg, int id ) {

        return ok(index.render("引数は：" + msg + ", " + id + "です"));
    }


//    public static Result redirect() {
//
//    	// トップページにリダイレクト
//    	// アドレスを静的に記述していると変更が面倒
//    	// return redirect("/");
//    	// 
//    	// リバースルーティングという手法を使う
//    	// これにより、アドレス変更の影響を受けない
//    	// 
//    	// routesファイルに記述されたControllerのルーティング情報は、
//    	// controllers.routesにまとめられる
//    	// controllers.routes.ApplicationはControllers.Applicationとは異なる
//    	// 「ReverseApplication」というクラスで、Application.index()に割り当てられたアドレスがわかる
//    	return redirect(controllers.routes.Application.index());
//    }

}
