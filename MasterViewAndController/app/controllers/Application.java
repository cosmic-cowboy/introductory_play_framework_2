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

}
