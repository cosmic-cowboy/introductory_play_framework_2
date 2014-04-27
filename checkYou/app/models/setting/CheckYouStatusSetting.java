package models.setting;

/**
 * 
 * ステータスコードの管理
 *
 */
public enum CheckYouStatusSetting {

	OK         (20, "ok"),
	NO_RESULT  (50, "no result");

	private final Integer code;
	private final String  message;

	private CheckYouStatusSetting(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}
