package kllngii.r6h.model;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.lang3.StringUtils;

public enum Channel {
	
	CHANNEL_STD("Standard Channel", 0, ""),
	CHANNEL_PRIVATE("Privater Channel", 1, "uc0vlQQoyoQO4HsNjL/75A==")
	;
	
	
	private final String name;
	private final int id;
	private final String hash;
	
	Channel(String name, int id, String hash) {
		this.name = name;
		this.id = id;
		this.hash = hash;
	}
	
	
	public String getName() {
		return name;
	}
	public int getId() {
		return id;
	}
	public String getHash() {
		return hash;
	}

	
	public boolean login(String pw) {
		if(this.equals(Channel.CHANNEL_STD))
			return true;
		if(StringUtils.isEmpty(pw))
			return false;
		if(getHash() == toPWHash(pw))
			return true;
		else
			return false;
	}
	
	@SuppressWarnings("hiding")
	private String toPWHash(String pw) {
		String password = null;
		try {
			byte[] b = new byte[16];
			KeySpec spec = new PBEKeySpec(pw.toCharArray(), b, 65536, 128);
			SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = f.generateSecret(spec).getEncoded();
			Base64.Encoder enc = Base64.getEncoder();
			password = enc.encodeToString(hash);
			return password;
		}
		catch(NoSuchAlgorithmException | InvalidKeySpecException e){
			e.printStackTrace();
			return password;
		}
	}
}
