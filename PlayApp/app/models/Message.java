package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.avaje.ebean.annotation.CreatedTimestamp;

import play.db.ebean.Model;
import play.data.validation.Constraints.*;
/**
 * 
 * Messageモデルクラス
 * Modelクラスにはレコード作成・削除といった編集機能がひととおり用意されている
 * 
 * アットEntity:EJBに用意されているJPAの機能
 * データベースに保管されるデータは「エンティティ（Entity）」として扱われる
 * 
 * [Evolution]という機能について
 * Ebeanがアプリケーション実行時にデータベーススキーマを監視し、
 * 最新の状態に更新するため、SQLクエリを自動生成する
 *
 */
@Entity
public class Message extends Model {

	private static final long serialVersionUID = 8504181707852558866L;

	// プライマリキー項目として扱われることを示す
	@Id
	public Long id;
	
	// 必須項目
	@Required(message="必須項目です")
	public String name;

	@Email(message="メールアドレスを記入してください")
	public String mail;

	// 必須項目
	@Required(message="必須項目です")
	public String message;

	// 作成日時設定
	@CreatedTimestamp	
	public Date postdate;
	
	// OneToOne で紐づくメンバー
	// cascadeはMessageが更新されたとき、関連するMemberを更新するかを示したもの
	// ALLで関連するすべて（ここではMember）を更新することになる
	// Messageテーブルにmember_idが追加される
	// mappedBy:関連するMemberのフィールド名(messages)に関連づけられている
	// mappedBYはどちらが主従関係を明確にする
	// そもそもManyToManyなんて使うべきではない。
	@ManyToMany(mappedBy="messages",cascade=CascadeType.ALL)
	public List<Member> members = new ArrayList<Member>();
	
	// Finderクラスは保管するオブジェクトを総称型で指定できる
	// ここでは<Long, Message>
	// これはプライマリキーのクラスとエンティティのクラス
	// 必要に応じてメソッド呼び出しでエンティティを取得
	public static Finder<Long, Message> find = 
			new Finder<Long, Message>(Long.class, Message.class);
	
	@Override
	public String toString(){

		String mem = "{id:";
		for(Member m : members){
			mem += " " + m.name;
		}
		mem += "}";
		return ("[id: " + id 
				+ ", member:" + mem
				+ ", message: " + message + ", date: " + postdate + "]" );
	}
	
	// 名前からメッセージを探す
	public static Message findByName(String input){
		return Message.find.where().eq("name", input).findList().get(0);
	}
//
//	// バリデーションクラス
//	// 「http://」ではじまっているかを判定
//	public static class IsUrl extends  play.data.validation.Constraints.Validator<String> {
//		// バリデーションチェック
//		@Override
//		public boolean isValid(String s){
//			return s.toLowerCase().startsWith("http://");
//		}
//		// バリデーションエラーメッセージ
//		@Override
//		public F.Tuple<String, Object[]> getErrorMessageKey(){
//			return new F.Tuple<String, Object[]>("error.invalid", new String[]{});
//		}
//	}

}
