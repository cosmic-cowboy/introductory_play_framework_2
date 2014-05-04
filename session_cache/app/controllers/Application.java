package controllers;

import static play.data.Form.form;

import play.data.Form;
import play.libs.F.Option;
import play.mvc.Controller;
import play.mvc.Result;
import utils.OptionUtil;
import views.html.*;

public class Application extends Controller {

    // 様々な入力形式の入力フォームに対応する入出力インタフェース
	public static class SampleForm {
		public String input;
	}
	
    public static Result index() {
    	Form<SampleForm> f = form(SampleForm.class);
    	// セッションから値の取得
    	Option<String> sessionOpt = OptionUtil.applyWithString(session("sessionText"));
        return ok(index.render(sessionOpt.getOrElse("セッションがない"), f));
    }

    public static Result send(){

    	// 入力値の取得
    	// アクセスしてきたユーザにセッションを返すため
    	Form<SampleForm> f = new Form(SampleForm.class).bindFromRequest();
    	// バリデーション
    	if(!f.hasErrors()){
	    	Option<String> inputOpt = OptionUtil.applyWithString(f.get().input);
	
	    	// こんなことは普通しませんが...
	    	// セッションに追加する
	    	session("sessionText", inputOpt.getOrElse("未入力"));
	    	
	    	return redirect(controllers.routes.Application.index());
    	} else {
    		return badRequest(index.render("エラー", f));
    	}
    }

}
