package utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import play.libs.F;
import static org.fest.assertions.Assertions.assertThat;

public class OptionUtilTest {

	@Test
	public void 有効値を入力(){
		String test = "abc";
		F.Option<String> strOps = OptionUtil.apply(test);
		assertThat(strOps.getClass()).isEqualTo(F.Some.class);
		assertThat(strOps.getClass()).isNotEqualTo(F.None.class);
	}
	
	@Test
	public void NULLを入力(){
		String test = null;
		F.Option<String> strOps = OptionUtil.apply(test);
		assertThat(strOps.getClass()).isNotEqualTo(F.Some.class);
		assertThat(strOps.getClass()).isEqualTo(F.None.class);
	}

	@Test
	public void 有効なListを入力(){
		List<String> testList = new ArrayList<String>();
		testList.add("a");
		testList.add("b");
		testList.add("c");
		F.Option<List<String>> strOps = OptionUtil.apply(testList);
		assertThat(strOps.getClass()).isEqualTo(F.Some.class);
		assertThat(strOps.getClass()).isNotEqualTo(F.None.class);
	}
	
	@Test
	public void 空のListを入力(){
		List<String> testList = new ArrayList<String>();
		F.Option<List<String>> strOps = OptionUtil.apply(testList);
		assertThat(strOps.getClass()).isNotEqualTo(F.Some.class);
		assertThat(strOps.getClass()).isEqualTo(F.None.class);
	}
	
	@Test
	public void 有効な文字列を入力(){
		String test = "abc";
		F.Option<String> strOps = OptionUtil.applyWithString(test);
		assertThat(strOps.getClass()).isEqualTo(F.Some.class);
		assertThat(strOps.getClass()).isNotEqualTo(F.None.class);
	}
	
	@Test
	public void 空文字を入力(){
		String test = "";
		F.Option<String> strOps = OptionUtil.applyWithString(test);
		assertThat(strOps.getClass()).isNotEqualTo(F.Some.class);
		assertThat(strOps.getClass()).isEqualTo(F.None.class);
	}
	
	@Test
	public void 有効値をScalaに変換(){
		String test = "abc";
		F.Option<String> strOps = OptionUtil.apply(test);
		scala.Option<String> strOptScala = OptionUtil.asScala(strOps);
		assertThat(strOps.getClass()).isEqualTo(F.Some.class);
		assertThat(strOptScala.getClass()).isEqualTo(scala.Some.class);
	}
	
	@Test
	public void 無効値をScalaに変換(){
		String test = null;
		F.Option<String> strOps = OptionUtil.apply(test);
		scala.Option<String> strOptScala = OptionUtil.asScala(strOps);
		assertThat(strOps.getClass()).isEqualTo(F.None.class);
		assertThat(strOptScala.getClass()).isEqualTo(scala.None$.class);
	}
	
}
