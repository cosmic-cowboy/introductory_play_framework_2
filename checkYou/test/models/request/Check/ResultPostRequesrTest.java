package models.request.Check;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import play.data.Form;
import static play.data.Form.form;

import static org.fest.assertions.Assertions.assertThat;

public class ResultPostRequesrTest {

	@Test
	public void 正しいId形式(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "kara_d");
		Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
		assertThat(form.hasErrors()).isFalse();
	}


	@Test
	public void アンダーバーなし(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "karad");
		Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
		assertThat(form.hasErrors()).isFalse();
	}


	@Test
	public void 数字のみ(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "123");
		Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
		assertThat(form.hasErrors()).isFalse();
	}


	@Test
	public void 数値_英数_アンダーバー混合(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "kara_d_456");
		Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
		assertThat(form.hasErrors()).isFalse();
	}

	@Test
	public void 禁止文字を含む(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "kara_>d");
		Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
		assertThat(form.hasErrors()).isTrue();
	}

	@Test
	public void ひらがなを含む(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "からでぃ");
		Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
		assertThat(form.hasErrors()).isTrue();
	}

	@Test
	public void カタカナを含む(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "カラディ");
		Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
		assertThat(form.hasErrors()).isTrue();
	}

	@Test
	public void 全て大文字(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "KARA_D");
		Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
		assertThat(form.hasErrors()).isFalse();
	}

	@Test
	public void 全角英字(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "ｋａｒａｄ");
		Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
		assertThat(form.hasErrors()).isTrue();
	}

}
