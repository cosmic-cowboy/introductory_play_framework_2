package models;

import javax.persistence.*;

import play.data.validation.Constraints.*;
import play.db.ebean.Model;

@Entity
public class Member extends Model{
	
	private static final long serialVersionUID = 7540569181170577956L;

	@Id
	public Long id;
	
	// 必須項目
	@Required(message="必須項目です")
	public String name;
	
	@Email(message="メールアドレスを記入してください")
	public String mail;
	
	public String tel;

	// Finderクラスは保管するオブジェクトを総称型で指定できる
	// ここでは<Long, Member>
	// これはプライマリキーのクラスとエンティティのクラス
	// 必要に応じてメソッド呼び出しでエンティティを取得
	public static Finder<Long, Member> find = 
			new Finder<Long, Member>(Long.class, Member.class);
	
	@Override
	public String toString(){
		return ("[id: " + id + ", name: " + name + ", mail: " + mail
				+ ", tel: " + tel + "]" );
	}
	
	// 名前からメンバーを探す
	public static Member findByName(String input){
		return Member.find.where().eq("name", input).findList().get(0);
	}
}
