package models.response.Check;

import models.entity.Check;
import models.services.api.Check.CheckResponseService;
import play.libs.F.Option;

/**
 * 診断結果のパーマリンクAPI用レスポンス
 *
 */
public class ChecksDefaultResponse {
	
	private Integer code;
	private String status;
	private String message;
	private CheckResponse result;

	public ChecksDefaultResponse(){
		
	};
	
	/**
	 * 診断結果をresultに格納するユーティリティメソッド
	 * @param response
	 * @return
	 */
	public Option<CheckResponse> response(Check response){
		return CheckResponseService.use().getCheckResponse(response);
	}

	/**
	 * エラー時、その旨をresultに格納するユーティリティメソッド
	 * @param response
	 * @return
	 */
	public ChecksDefaultResponse badRequest(String message){
		return CheckResponseService.use().getBadRequest(message);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CheckResponse getResult() {
		return result;
	}

	public void setResult(CheckResponse result) {
		this.result = result;
	}
	

}
