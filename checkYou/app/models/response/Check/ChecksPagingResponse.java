package models.response.Check;

import java.util.List;

import models.entity.Check;
import models.services.api.Check.CheckResponseService;
import play.libs.F.Option;

/**
 * 診断結果のパーマリンクAPI用レスポンス
 *
 */
public class ChecksPagingResponse {
	
	private Integer code;
	private String status;
	private String message;
	private Integer maxPage;

    List<CheckResponse> results;

	public ChecksPagingResponse(){
		
	};

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

	public Integer getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(Integer maxPage) {
		this.maxPage = maxPage;
	}
	

	public List<CheckResponse> getResults() {
		return results;
	}

	public void setResults(List<CheckResponse> results) {
		this.results = results;
	}
}
