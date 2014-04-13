package utils;

import play.db.ebean.Model.Finder;

/**
 * Play Frameworkの機能（play.db.ebean.Model.Finderクラス）をラッピングしたクラス
 * 検索を行う機能
 *
 */
public class ModelUtil {

	public static <T> Finder<Long, T> getFinder(Class<T> t) {
		return new Finder<Long, T>(Long.class, t);
	}

}
