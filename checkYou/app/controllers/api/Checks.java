package controllers.api;

import java.util.ArrayList;
import java.util.List;

import models.entity.Check;
import models.response.Check.CheckResponse;
import models.response.Check.ChecksDefaultResponse;
import models.response.Check.ChecksPagingResponse;
import models.setting.CheckYouStatusSetting;
import play.libs.F.*;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.ConfigUtil;

/**
 * API用Controller
 */
public class Checks extends Controller {

    /**
     * APIで指定されたIDの情報をJSON形式で返却
     * @param id
     * @return
     */
    public static Result check(Long id){
    	// 返却用レスポンス
    	ChecksDefaultResponse result = new ChecksDefaultResponse();
    	// リクエストのIDに紐づくレコードの取得
    	Option<Check> checkOps = new Check(id).unique();

    	// 正常系
    	// 取得したレコードの値をレスポンスにマッピング(result.response)
    	if(checkOps.isDefined()){
    		CheckYouStatusSetting status = CheckYouStatusSetting.OK;
    		result.setCode(status.getCode());
    		result.setStatus(status.getMessage());
    		result.setResult(result.response(checkOps.get()).getOrElse(new CheckResponse()));
    		return ok(Json.toJson(result));
    	}
    	
    	// 異常系
		return badRequest(Json.toJson(
				result.badRequest(ConfigUtil.get("checkYou.setting.message.error.noResult").getOrElse(""))
		));
    	
    }

    /**
     * APIで指定されたページの情報をJSON形式で返却
     * @param page
     * @return
     */
    public static Result getList(Integer page){
    	// 返却用レスポンス
    	ChecksPagingResponse result = new ChecksPagingResponse();
    	// ページ情報に紐づくレコードの取得
    	// 取得したレコードの値をレスポンスにマッピング
    	// Option型のmapメソッドは、OptionがSome型の場合のみ適用する関数を渡せるメソッド
    	// Javaでは関数を引数に渡せないので、applyメソッドをOverrideしたFunction型のインスタンスを渡す（ここではnew CheckToCheckResponse()がそれ）
    	// mapメソッドはFunction<T,A>型を受け取る
    	// Function型はPlayで用意された無名クラス用クラス
    	Option<List<CheckResponse>> list = new Check().entitiesList(page).map(new CheckToCheckResponse());
    	// 総レコード数の取得
    	Integer maxPage = new Check().entitiesMaxPage(0);

    	// 正常系
        if(list.isDefined()) {
            CheckYouStatusSetting status = CheckYouStatusSetting.OK;
            result.setCode(status.getCode());
            result.setStatus (status.getMessage());
            result.setMaxPage(maxPage);
            result.setResults(list.get());
            return ok(Json.toJson(result));
        }
    	// 異常系
        return badRequest(Json.toJson(
            result.badRequest(ConfigUtil.get("checkYou.setting.message.error.noResultList").getOrElse(""))));
    }

    
    /**
     * ヘルパーメソッド
     */
    
    /**
     * 取得したレコードの値をレスポンスにマッピングするためのクラス
     * Function型はPlayで用意された無名クラス用クラス
     * 入力される型と返却される型を指定できる。
     *
     */
    private static class CheckToCheckResponse implements Function<List<Check>, List<CheckResponse>> {
    	 @Override
         public List<CheckResponse> apply(List<Check> checkList) throws Throwable {
    		// 返却用レスポンス
    		 List<CheckResponse> result = new ArrayList<CheckResponse>();
             CheckResponse checksPostResponse;
             // 取得したレコードとレスポンスのマッピング
             for(Check check : checkList) {
                 checksPostResponse = new CheckResponse(
                     check.getId(),
                     check.getName(),
                     check.getResult(),
                     check.getCreated(),
                     check.getModified());
                 result.add(checksPostResponse);
             }
             return result;
         }
    }
}
