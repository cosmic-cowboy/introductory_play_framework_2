package utils;

import play.cache.Cache;

public class AppCache{

	private final static Integer defaultSec = 3600;
	
	public static Object get(String key){
		Object result = Cache.get(key);
		return result;
	}

	public static Object get(String userid, String userkey){
		Object result = Cache.get(String.format("{{%s}}{{%s}}", userid, userkey));
		return result;
	}

	public static Object set(String userid, String userkey, Object o){
		Cache.set(String.format("{{%s}}{{%s}}", userid, userkey), o, defaultSec);
		return o;
	}

	public static Object set(String userid, String userkey, Object o, Integer i ){
		Cache.set(String.format("{{%s}}{{%s}}", userid, userkey), o, i);
		return o;
	}

}
