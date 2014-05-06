package controllers;


import java.util.concurrent.Callable;

import play.cache.Cache;
import play.data.Form;
import play.libs.F.Option;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import utils.ConfigUtil;
import utils.OptionUtil;
import views.html.*;

public class Application extends Controller {

    // 様々な入力形式の入力フォームに対応する入出力インタフェース
	public static class SampleForm {
		public String sessionInput;
		public String cookieInput;
		public boolean sendMail;
	}
	
    public static Result index() {

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
    	
    	Boolean sendMail = false;
    	// キャッシュの取得
    	try {
        	sendMail = Cache.getOrElse("sendMail", 
					new Callable<Boolean>(){
						@Override
						public Boolean call() throws Exception {
							return new Boolean(ConfigUtil.get("send.mail").getOrElse("false"));
						}    			
					}, 300 * 1000
		    	);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	// フォームの初期値設定
    	SampleForm sf = new SampleForm();
    	sf.sendMail = sendMail;
    	Form<SampleForm> form = new Form(SampleForm.class).fill(sf);

    	// Resultの返却
        return ok(index.render(
        		sessionOpt.getOrElse("セッションがない"), 
        		cookieNameOpt.getOrElse("独自実装のcookieが設定されていない"), 
        		cookieValueOpt.getOrElse("独自実装のcookieが設定されていない"),
        		sendMail,
        		form));
    }

    public static Result send(){

    	// 入力値の取得
    	// アクセスしてきたユーザにセッションを返すため
    	Form<SampleForm> f = new Form(SampleForm.class).bindFromRequest();
    	// バリデーション
    	if(!f.hasErrors()){
	    	Option<String> sessionInputOpt = OptionUtil.applyWithString(f.get().sessionInput);
	    	Option<String> cookieInputOpt = OptionUtil.applyWithString(f.get().cookieInput);
	    	boolean sendMail = f.get().sendMail;
	
	    	// こんなことは普通しませんが...
	    	// セッションに追加する
	    	session("sessionText", sessionInputOpt.getOrElse("未入力"));
	    	// Cookieへ値を設定
	    	String cookieInput = cookieInputOpt.getOrElse("non");
	    	if(cookieInput.equals("discardCookie")){
		    	response().discardCookie("myName");	    			    		
	    	} else {
		    	response().setCookie("myName", cookieInput, 30 * 1000);	    		
	    	}
	    	// キャッシュの値を期限付きで設定
	    	Cache.set("sendMail", sendMail, 30 * 1000);
	    	
	    	return redirect(controllers.routes.Application.index());
    	} else {
    		return badRequest(index.render("エラー","エラー","エラー",false, f));
    	}
    }

}
