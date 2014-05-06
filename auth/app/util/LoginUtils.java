package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginUtils {
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
}
