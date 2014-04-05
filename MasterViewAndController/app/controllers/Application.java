package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
    	// Controllerだけでページを作成する
    	// okメソッドは文字列をResultインスタンにして返却する
        return ok("This is test");
    }

}
