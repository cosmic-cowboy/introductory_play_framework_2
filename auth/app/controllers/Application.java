package controllers;

import static play.data.Form.form;

import models.Login;
import play.data.Form;
import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.RequireCSRFCheck;
import play.mvc.*;
import views.html.*;
public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    /**
     * ログイン画面を表示する
     * ログインフォームを設定するだけ
     * CookieにCSRFトークンを挿入する
     * @return
     */
    @AddCSRFToken
    public static Result login() {
        return ok(login.render(form(Login.class)));
    }

    /**
     * loginメソッド経由でアクセスされる
     * 認証が終わったら、目的にURLにリダイレクトする
     * RequireCSRFCheck：Session内にSCRFトークンが必要
     * @return
     */
    @RequireCSRFCheck
    public static Result authenticate() {
        // ログインフォームのバリデーション
    	Form<Login> loginForm = form(Login.class).bindFromRequest();
    	if(loginForm.hasErrors()){
    		return badRequest(login.render(loginForm));
    	} else {
    		session("username", loginForm.get().getUsername());
    		String returnUrl = ctx().session().get("returnUrl");
    		if(returnUrl == null 
    				|| returnUrl.equals("")
    				|| returnUrl.equals(routes.Application.login().absoluteURL(request()))){
    			returnUrl = routes.Application.index().url();
    		}
    		return redirect(returnUrl);
    	}
    }


    public static Result logout() {
        return TODO;
    }


}
