package controllers;

import java.util.Map;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

	
    public static Result index() {
    	// メソッド判定
    	String method = request().method();
    	
    	if("GET".equals(method)){
            return ok(index.render("please type:"));
    	} else {
    		// Http.Request > Http.RequestBody > Form情報
    		Map<String, String[]> form = request().body().asFormUrlEncoded();
    		// Form情報でname=inputの値を取得する
    		String [] input = form.get("input");
            return ok(index.render("posted:" + input[0]));
    	}
    }

}
