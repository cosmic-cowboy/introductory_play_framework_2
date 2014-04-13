package utils;

import java.util.List;

import play.libs.F;
import scala.*;

/**
 * Option用ユーティリティクラス
 *
 */
public class OptionUtil {

	/**
	 * 値があるときはSome型に、なければNone型に
	 * @param value
	 * @return
	 */
	public static <A> F.Option<A> apply(A value){
		if(value != null){
			return F.Option.Some(value);
		} else {
			return F.Option.None();
		}
	}
	
	/**
	 * 値があるときはSome型に、なければNone型に
	 * nullチェックだけでなく、Listに値が格納されていることもチェック
	 * @param value
	 * @return
	 */
	public static <A> F.Option<List<A>> apply(List<A> value){
		if(value != null && value.size() != 0){
			return F.Option.Some(value);
		} else {
			return F.Option.None();
		}
	}
	
	/**
	 * 値があるときはSome型に、なければNone型に
	 * nullチェックだけでなく、空文字「""」かもチェック
	 * @param value
	 * @return
	 */
	public static <A> F.Option<String> applyWithString(String value){
		if(value != null && !value.equals("")){
			return F.Option.Some(value);
		} else {
			return F.Option.None();
		}
	}
	
	/**
	 * @return
	 */
	public static <T> F.None<T> none(){
		return new F.None<T>();
	}
	
	/**
	 * Play FrameworkのOptionをScalaのOptionに変換
	 * @param value
	 * @return
	 */
	public static <T> Option<T> asScala(F.Option<T> value){
		if(value.isDefined()){
			return Option$.MODULE$.apply(value.get());
		} else {
			return Option$.MODULE$.empty();
		}
	}
	
}
