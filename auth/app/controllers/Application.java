package controllers;

import static play.data.Form.form;

import java.security.NoSuchAlgorithmException;

import models.Admin;
import models.Login;
import models.Secured;
import play.data.Form;
import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.RequireCSRFCheck;
import play.mvc.*;
import play.mvc.Security.Authenticated;
import util.LoginUtils;
import views.html.*;
public class Application extends Controller {

    /**
     * @return
     */
	@Authenticated(Secured.class)
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
    		return authenticated(loginForm.get().getUsername());
    	}
    }

    /**
     * Sessionを空にして、ログイン画面に遷移する
     * Authenticated：認証済でないユーザーはアクセス不可にするため
     * @return
     */
    @Authenticated(Secured.class)
    public static Result logout() {
    	session().clear();
        return redirect(routes.Application.login());
    }

    /**
     * ユーザ登録画面
     * @return
     */
    @AddCSRFToken
    public static Result register(){
    	return ok(register.render(form(Admin.class)));
    }

    /**
     * ユーザ登録処理
     * @return
     */
    @RequireCSRFCheck
    public static Result save()  throws NoSuchAlgorithmException{
    	Form<Admin> registerForm = form(Admin.class).bindFromRequest();
    	if(registerForm.hasErrors()){
    		return badRequest(register.render(registerForm));
    	} else {
    		Admin admin = registerForm.get();
    		admin.setPassword(LoginUtils.sha512(admin.getPassword()));
    		admin.save();
    		return authenticated(admin.getUsername());
    	}
    }


	private static Result authenticated(String username) {
		session("username", username);
		String returnUrl = ctx().session().get("returnUrl");
		if(returnUrl == null 
				|| returnUrl.equals("")
				|| returnUrl.equals(routes.Application.login().absoluteURL(request()))){
			returnUrl = routes.Application.index().url();
		}
		return redirect(returnUrl);
	}

}
