package controllers;

import play.libs.F.Option;
import play.mvc.Controller;
import play.mvc.Result;
import utils.OptionUtil;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
    	// セッションを追加する
    	// アクセスしてきたユーザにセッションを返すため
    	session("sessionText", "セッションに格納しました");
    	// セッションから値の取得
    	Option<String> ops = OptionUtil.applyWithString(session("sessionText"));
        return ok(index.render(ops.getOrElse("セッションがない")));
    }

}
