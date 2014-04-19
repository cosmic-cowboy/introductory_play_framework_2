package utils;

import java.util.List;

import play.Play;
import play.libs.F.Option;

public class ConfigUtil {

	/**
	 * application.confのkeyをStringのListで返す
	 * 
	 * @param key
	 * @return
	 */
	public static Option<List<String>> getByList(String key){
		return OptionUtil.apply(Play.application().configuration().getStringList(key));
	}
	
	public static Option<String> get(String key){
		return OptionUtil.apply(Play.application().configuration().getString(key));
	}
}
