package controllers;

import static play.data.Form.form;
import play.data.Form;
import play.libs.F.Option;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import utils.OptionUtil;
import views.html.*;

public class Application extends Controller {

    // 様々な入力形式の入力フォームに対応する入出力インタフェース
	public static class SampleForm {
		public String sessionInput;
		public String cookieInput;
	}
	
    public static Result index() {
    	Form<SampleForm> f = form(SampleForm.class);
    	// セッションから値の取得
    	Option<String> sessionOpt = OptionUtil.applyWithString(session("sessionText"));

    	// Cookieの取得
    	Option<Http.Cookie> cookieOpt = OptionUtil.apply(request().cookie("myName"));
    	Option<String> cookieNameOpt, cookieValueOpt;
    	if(cookieOpt.isDefined()){
    		cookieNameOpt  = OptionUtil.applyWithString(cookieOpt.get().name());
    		cookieValueOpt = OptionUtil.applyWithString(cookieOpt.get().value());
    	} else {
    		cookieNameOpt = OptionUtil.applyWithString("");
    		cookieValueOpt = OptionUtil.applyWithString("");
    	}
        return ok(index.render(
        		sessionOpt.getOrElse("セッションがない"), 
        		cookieNameOpt.getOrElse("独自実装のcookieが設定されていない"), 
        		cookieValueOpt.getOrElse("独自実装のcookieが設定されていない"),
        		f));
    }

    public static Result send(){

    	// 入力値の取得
    	// アクセスしてきたユーザにセッションを返すため
    	Form<SampleForm> f = new Form(SampleForm.class).bindFromRequest();
    	// バリデーション
    	if(!f.hasErrors()){
	    	Option<String> sessionInputOpt = OptionUtil.applyWithString(f.get().sessionInput);
	    	Option<String> cookieInputOpt = OptionUtil.applyWithString(f.get().cookieInput);
	
	    	// こんなことは普通しませんが...
	    	// セッションに追加する
	    	session("sessionText", sessionInputOpt.getOrElse("未入力"));
	    	// Cookieへ値を設定
	    	String cookieInput = cookieInputOpt.getOrElse("non");
	    	if(cookieInput.equals("discardCookie")){
		    	response().discardCookie("myName");	    			    		
	    	} else {
		    	response().setCookie("myName", cookieInput, 30);	    		
	    	}
	    	
	    	return redirect(controllers.routes.Application.index());
    	} else {
    		return badRequest(index.render("エラー","エラー","エラー", f));
    	}
    }

}
