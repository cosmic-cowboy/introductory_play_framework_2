package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

public class Login {

	@Required
	private String username;
	@Required
	private String password;
	
	// ユーザー確認（バリデーション）
	public String validate() throws NoSuchAlgorithmException{	
		if(authenticate(username, password) == null){
			return "Invalid user or password";
		}
		return null;
	}
	// ユーザー確認
	// ハッシュ化された格納されたパスワードに問い合わせ
	public static Admin authenticate(String username, String password) throws NoSuchAlgorithmException{
		Model.Finder<Long, Admin> find = 
				new Model.Finder<Long, Admin>(Long.class, Admin.class);
		String hashedPassword = "";
		if (password != null){
			hashedPassword = sha512(password);
		}
		return find.where()
				.eq("username", username)
				.eq("password", hashedPassword)
				.findUnique();
	}
	// ハッシュ化
	//
	public static String sha512(String message) throws NoSuchAlgorithmException{
		// 512ビット長のSHA形式
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		StringBuffer sb = new StringBuffer();
		md.update(message.getBytes());
		byte[] mb = md.digest();
		for(byte m : mb){
			String hex = String.format("%02x", m);
			sb.append(hex);
		}
		return sb.toString();
	}
	
	// Getter Setter
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
