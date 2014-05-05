package models;

import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.Context;

public class Secured extends Security.Authenticator{

	/* (非 Javadoc)
	 * @see play.mvc.Security.Authenticator#getUsername(play.mvc.Http.Context)
	 * ユーザー名を返す
	 * ログイン状態かどうかを把握する判断材料にする
	 */
	@Override
	public String getUsername(Context ctx) {
		return ctx.session().get("username");
	}
	
	/* (非 Javadoc)
	 * @see play.mvc.Security.Authenticator#onUnauthorized(play.mvc.Http.Context)
	 * ログインしていなかった場合のリダイレクト先を指定、ここではログイン画面
	 * ただし、指定したURLはセッションに保存
	 * ログイン後に遷移できるようにする
	 * 
	 */
	@Override
	public Result onUnauthorized(Context ctx) {
		String returnUrl = ctx.request().uri();
		if(returnUrl == null){
			returnUrl = "/";
		}
		ctx.session().put("returnUrl", returnUrl);
		return redirect(controllers.routes.Application.login());
	}
}
