package models

import anorm._
import play.api.db._
import anorm.SqlParser._
import play.api.Play.current


/**
 * case classは引数に応じてそれらの値を保管するプロパティなどが自動生成される
 * id:Pk[Long]はIDを扱うための特別なオブジェクト（自動的にIDが割り振られるIDはこれを使う）
 * 
 */
case class AddressBook (id:Pk[Long], name:String, age:Int, tel:String, address:String){

	/**
	 * 連絡帳を保存する
	 * データベースに接続して、レコードを追加する
	 * DB.withConnection：DB接続
	 * DB.withConnectionの{}内：SQL実行
	 * SQLオブジェクトでSQL文を指定、実行する
	 */
	def add {
		DB.withConnection { implicit c =>
			val id: Int = SQL("insert into addressbook (name, age, tel, address) values ({name}, {age}, {tel}, {address})").
			on('name->this.name, 'age -> this.age, 'tel -> this.tel, 'address -> this.address).executeUpdate()
		}
	}
}

/**
 * コンパニオンオブジェクト
 *
 */
object AddressBook {
	
	/**
	 * 「パーサー(RowParser)」の定義
	 * SQLを実行して取得されたデータを決まった形式のオブジェクトにパースする
	 * 
	 */
	val data = {
		get[Pk[Long]]("id") ~
		get[String]("name") ~
		get[Int]("age") ~
		get[String]("tel") ~
		get[String]("address") map {
			case id ~name ~age ~ tel ~ address => AddressBook(id, name, age, tel, address)
		}
	}
	
	def all: List[AddressBook] = {
		DB.withConnection { implicit c =>
			val datas = SQL("select * from addressbook").as(AddressBook.data *)
			return datas
		}
	}

}