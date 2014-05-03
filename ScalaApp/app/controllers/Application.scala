package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
/**
 * import文が「*」から「_」になっている
 * classがobjectになっている
 * Controllerはシングルトンクラスで
 * メソッドの宣言 def になっている
 */
object Application extends Controller {

  	/**
  	 * FormDataの定義
  	 * caseはクラスに必要な要素を自動生成する
  	 * case classだと用意されている引数をプロパティとして設定する
  	 */
  	case class FormData (input :String);
  	
  	// Formのコンパニオンメソッドを利用
  	// Scalaのカリー化を利用
  	// １番目の引数：フォームのデータを管理するクラス
  	// ２・３番目の引数：ファームデータを管理するクラスのメソッド
  	val form1 = Form(
  	    mapping("input" -> text)(FormData.apply)(FormData.unapply)
  	);
	
	
	/**
	 * Action
	 * メソッド名index
	 * Actionはオブジェクトでコンパニオンオブジェクトを呼び出している
	 * @return
	 */
	def index = Action {
		// OkメソッドはJavaと同様、Resultを返す
		Ok(views.html.index("フォームを送信", form1));
	}

	/**
	 * Action
	 * implicitは省略できるパラメータに用いる。
	 * implicitで省略されたパラメータ：request
	 * @return
	 */
	def send = Action { implicit request => 
		var resform = form1.bindFromRequest();
		var res = "you typed: " + resform.get.input;
		Ok(views.html.index(res, resform));
	}
	
}