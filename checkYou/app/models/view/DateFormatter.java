package models.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import utils.ConfigUtil;

/**
 * 日付の変換を行うクラス
 * View用のロジックを記述する
 */
public class DateFormatter {
	
	private static final String defaultFormat = "yyyy年MM月dd日 HH時mm分ss秒";
	
	/**
	 * 日付の変換
	 * viewで使用する
	 * @param date
	 * @return
	 */
	public static String formatDefaultDate(Date date){
		String dateFormat = ConfigUtil
				.get("app.models.view.defaultFormat")
				.getOrElse(defaultFormat);
		
		return new SimpleDateFormat(dateFormat).format(date);
	}
}
