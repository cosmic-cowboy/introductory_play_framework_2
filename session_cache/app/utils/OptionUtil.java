package utils;

import play.libs.F;

public class OptionUtil {

	public static F.Option<String> applyWithString(String value){
		if(value != null && !value.equals("")){
			return F.Option.Some(value);
		} else {
			return F.Option.None();
			
		}
	}
}
