package controllers;

import play.mvc.Call;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * 
 *
 */
public class Apps extends Controller {
	
    /**
     * @param call ルーティングの返り値（routes.Checks.index()）
     * @param flashKey
     * @param flashMessage
     * @return
     */
    public static Result fail(Call call, String flashKey, String flashMessage) {
    	// ctx()：HTTPリクエストのコンテキスト
    	// Flashメッセージ
    	ctx().flash().put(flashKey, flashMessage);
        // Callへリダイレクト
    	return redirect(call);
    }
}
