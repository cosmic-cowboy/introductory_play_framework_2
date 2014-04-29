package utils;

import org.junit.Test;
import static org.fest.assertions.Assertions.assertThat;

public class PageUtilTest {

	/**
	 * ページ数が 0 のとき
	 * @throws Exception
	 */
	@Test
	public void ページ数が0() throws Exception {
		assertThat(PageUtil.rightPage(0)).isEqualTo(0);
	}
	
	/**
	 * ページ数が -1 のとき
	 * @throws Exception
	 */
	@Test
	public void ページ数がマイナス1() throws Exception {
		assertThat(PageUtil.rightPage(-1)).isEqualTo(0);
	}

	/**
	 * ページ数が 1 のとき
	 * @throws Exception
	 */
	@Test
	public void ページ数が1() throws Exception {
		assertThat(PageUtil.rightPage(1)).isEqualTo(0);
	}

	/**
	 * ページ数が 2 のとき
	 * @throws Exception
	 */
	@Test
	public void ページ数が2() throws Exception {
		assertThat(PageUtil.rightPage(2)).isEqualTo(1);
	}

	/**
	 * ページ数が 10 のとき
	 * @throws Exception
	 */
	@Test
	public void ページ数が10() throws Exception {
		assertThat(PageUtil.rightPage(10)).isEqualTo(9);
	}

	/**
	 * ページ数が null のとき
	 * @throws Exception
	 */
	@Test
	public void ページ数がnull() throws Exception {
		assertThat(PageUtil.rightPage(null)).isEqualTo(0);
	}

}
