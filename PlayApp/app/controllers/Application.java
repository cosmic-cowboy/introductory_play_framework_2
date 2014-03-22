package controllers;

import play.*;
import play.mvc.*;
import views.html.*;
import play.data.*;
import static play.data.Form.*;

public class Application extends Controller {

	// Form用の内部クラス
	public static class SampleForm {
		public String message;
	}

    // ルートにアクセスした際のAction
    public static Result index() {
        return ok(index.render(
        	"何か書いて",
        	new Form(SampleForm.class)
        ));
    }

    // sendにアクセスした際のAction
    public static Result send() {
    	// フォームの情報を取得
    	// 送信されたフォームの値をバインドしたFormインスタンスを生成
    	Form<SampleForm> f = form(SampleForm.class).bindFromRequest();
    	// バリデーション
    	if(!f.hasErrors()){
    		// フォームの取り出し
    		SampleForm data = f.get();
    		String msg = "you typed: " + data.message;
    		// フォームの値をindexページに設定
	        return ok(index.render(msg, f));
    	} else {
    		// エラーが発生したことをクライアントに伝える
    		return badRequest(index.render("Error", form(SampleForm.class)));
    	}
    }

}
