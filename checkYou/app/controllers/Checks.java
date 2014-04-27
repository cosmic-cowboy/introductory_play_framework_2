package controllers;

import static play.data.Form.form;

import java.util.List;

import models.entity.Check;
import models.request.Check.ResultPostRequest;
import play.data.Form;
import play.libs.F.Option;
import play.mvc.Result;
import utils.ConfigUtil;
import views.html.check.*;

public class Checks extends Apps {

	private static final String FORM_KEY_NAME = "name";

    /**
     * トップページへのアクセス
     * タイトル・メッセージ・ID登録用フォームをセットする
     * @return Result
     */
    public static Result index() {
    	Form<ResultPostRequest> form = form(ResultPostRequest.class);
        return ok(views.html.check.index.render(
        		ConfigUtil.get("checkYou.setting.message.title").getOrElse(""), 
        		ConfigUtil.get("checkYou.setting.message.question").getOrElse(""), 
        		form));
    }


    /**
     * トップページの入力フォームから
     * 入力値のチェックを行い、エラーがあればトップページにエラーを出力
     * 診断ができれば、resultへ
     * 
     * @return Result
     */
    public static Result result() {
    	Form<ResultPostRequest> form = form(ResultPostRequest.class).bindFromRequest();
    	
    	// formの入力値チェック
    	// error.required
    	if(form.error(FORM_KEY_NAME) != null && form.error(FORM_KEY_NAME).message().contains("error.required")){
    		return validationErrorIndexResult("名前欄が空です",form);
    	}
    	// error.pattern
    	if(form.error(FORM_KEY_NAME) != null && form.error(FORM_KEY_NAME).message().contains("error.pattern")){
    		return validationErrorIndexResult("ID形式ではありません",form);
    	}
    	// error.maxLength
    	if(form.error(FORM_KEY_NAME) != null && form.error(FORM_KEY_NAME).message().contains("error.maxLength")){
    		return validationErrorIndexResult("文字数が15文字を超えています",form);
    	}
    	String name = form.data().get(FORM_KEY_NAME);
    	// 診断結果の取得
    	// 正常に取得できれば、その値、失敗したら、configの"checkYou.setting.message.failCheck"、それもなければ空を返却
    	Check checkOrigin = new Check(name, 
    			new Check(name).result().getOrElse(ConfigUtil.get("checkYou.setting.message.failCheck").getOrElse("")));
    	// 診断結果を登録
    	Option<Check> checkOps = checkOrigin.store();
    	// 成功したら、診断結果ページヘ
    	// 失敗したら診断エラーとしてトップページに戻り、エラーメッセージを表示する
    	return checkOps.isDefined() 
    			? ok(result.render(checkOps.get().getName() + ConfigUtil.get("checkYou.setting.message.resultTitle").getOrElse(""),
    					checkOps.get()))
    			: fail(routes.Checks.index(), "error", "診断エラーです");
    }


    public static Result resultId(Long id) {
    	Option<Check> check = new Check(id).unique();
    	return check.isDefined()
    			? ok(result.render(check.get().getName() + ConfigUtil.get("checkYou.setting.message.resultTitle").getOrElse(""),
    					check.get()))
    			: fail(routes.Checks.index(), "error", "診断エラーです");
    }


    public static Result recent(Integer page) {

    	Option<List<Check>> result = new Check().entitiesList(page);
    	Integer maxPage = new Check().entitiesMaxPage(1);
    	return result.isDefined()
    			? ok(recent.render(
    					ConfigUtil.get("checkYou.setting.message.recentTitle").getOrElse(""),
    					ConfigUtil.get("checkYou.setting.message.recentDescription").getOrElse(""),
    					result.get(), page, maxPage))
    			: ok(recentEmpty.render(
    					ConfigUtil.get("checkYou.setting.message.recentTitle").getOrElse(""),
    					ConfigUtil.get("checkYou.setting.message.recentDescriptionNone").getOrElse("")));
    }

    
    /**
     * ヘルパーメソッド
     */
    
    /**
     * バリデーションエラーメッセージを表示し、トップページへ戻る
     *
     * @param message
     * @return
     */
    private static Result validationErrorIndexResult(String message, Form<ResultPostRequest> form) {
    	// Flashメッセージ
        flash("error", message);
        // トップページへのリダイレクト
        return badRequest(views.html.check.index.render(
                ConfigUtil.get("checkyou.setting.message.title").getOrElse(""),
                ConfigUtil.get("checkyou.setting.message.question").getOrElse(""),
                form));
    }    
}
