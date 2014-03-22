package models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.*;

import com.avaje.ebean.annotation.CreatedTimestamp;

import play.db.ebean.Model;
import play.data.validation.*;
import play.data.validation.Constraints.Required;

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
	@Required
	public String name;

	public String mail;

	// 必須項目
	@Required
	public String message;

	// 作成日時設定
	@CreatedTimestamp	
	public Date postdate;
	
	// Finderクラスは保管するオブジェクトを総称型で指定できる
	// ここでは<Long, Message>
	// これはプライマリキーのクラスとエンティティのクラス
	// 必要に応じてメソッド呼び出しでエンティティを取得
	public static Finder<Long, Message> find = 
			new Finder<Long, Message>(Long.class, Message.class);
	
	@Override
	public String toString(){
		return ("[id: " + id + ", name: " + name + ", mail: " + mail
				+ ", message: " + message + ", date: " + postdate + "]" );
	}
}
