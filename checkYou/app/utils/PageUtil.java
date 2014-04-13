package utils;

import play.libs.F.Option;

public class PageUtil {

	public static Integer rightPage(Integer i) {
		
		Option<Integer> iOps = OptionUtil.apply(i);
		// Ebeanではページは0からはじまるので調整
		return (iOps.getOrElse(0) - 1 < 0) ? 0 : i - 1;
	}

}
