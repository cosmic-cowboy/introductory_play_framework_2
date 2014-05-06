package utils;

import play.Play;
import play.libs.F.Option;

public class ConfigUtil {

	public static Option<String> get(String key){
		return OptionUtil.apply(Play.application().configuration().getString(key));	
	}
}
