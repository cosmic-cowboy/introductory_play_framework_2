package models.request.Check;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;

/**
 * ChecksControllerのPOSTメソッドに送られてくるResultメソッドで使われるフォームクラス
 *
 */
public class ResultPostRequest {
	
	/**
	 * Twitterと同じ形式
	 * 必須要素、数字、最大15文字
	 */
	@Required
	@Pattern("[\\w]+")
	@MaxLength(15)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
