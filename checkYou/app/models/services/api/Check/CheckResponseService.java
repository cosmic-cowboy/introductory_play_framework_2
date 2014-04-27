package models.services.api.Check;

import play.libs.F.*;
import utils.OptionUtil;
import models.entity.Check;
import models.response.Check.CheckResponse;
import models.response.Check.ChecksDefaultResponse;
import models.setting.CheckYouStatusSetting;

/**
 * API用のサービスクラス
 * 
 * レスポンスクラスから委譲されているユーティリティメソッドを実装している
 *
 */
public class CheckResponseService {

	/**
	 * ほぼファクトリメソッド
	 * チェーンメソッドっぽく書けるように
	 * @return
	 */
	public static CheckResponseService use(){
		return new CheckResponseService();
	}

	/**
	 * エラー時のJSON作成
	 * エラーコードとエラーステータスを返す
	 * @param message
	 * @return
	 */
	public ChecksDefaultResponse getBadRequest(String message){
		ChecksDefaultResponse result = new ChecksDefaultResponse();
		CheckYouStatusSetting status = CheckYouStatusSetting.NO_RESULT;
		result.setCode(status.getCode());
		result.setStatus(status.getMessage());
		result.setMessage(message);
		return result;	
	}

	/**
	 * 正常時のJSON作成
	 * @param response
	 * @return
	 */
	public Option<CheckResponse> getCheckResponse(Check response){
		Option<Check> checkops = OptionUtil.apply(response);
		if(checkops.isDefined()){
			CheckResponse checkResponse = new CheckResponse(
					checkops.get().getId(), 
					checkops.get().getName(), 
					checkops.get().getResult(), 
					checkops.get().getCreated(), 
					checkops.get().getModified()
			);
			
			return OptionUtil.apply(checkResponse);
		}
		return new None<CheckResponse>();
		
	}

}
