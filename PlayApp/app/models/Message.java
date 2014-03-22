package models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.*;

import play.db.ebean.Model;
import play.data.validation.*;

/**
 * 
 * Messageモデルクラス
 * Modelクラスにはレコード作成・削除といっｔ編集機能がひととおり用意されている
 * 
 * アットEntity:EJBに用意されているJPAの機能
 * データベースに保管されるデータは「エンティティ（Entity）」として扱われる
 * 
 *
 */
@Entity
public class Message extends Model {

	private static final long serialVersionUID = 8504181707852558866L;

	// プライマリキー項目として扱われることを示す
	@Id
	public Long id;
	public String name;
	public String mail;
	public String message;
	public Date postdate;
	
}
