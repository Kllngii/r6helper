package kllngii.r6s.stats;

import java.util.HashMap;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

@Singleton
@Lock(LockType.READ)
public class StatsVars {
	
	private boolean initalized = false;
	private String ubiSessionId = null;
	private String token = "";
	private String loginJSON = null;
	private HashMap<String, Long> last = new HashMap<>();
	
	public String getUbiSessionId() {
		return ubiSessionId;
	}
	public void setUbiSessionId(String ubiSessionId) {
		this.ubiSessionId = ubiSessionId;
	}
	public boolean isInitalized() {
		return initalized;
	}
	public void setInitalized(boolean initalized) {
		this.initalized = initalized;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLoginJSON() {
		return loginJSON;
	}
	public void setLoginJSON(String loginJSON) {
		this.loginJSON = loginJSON;
	}
	public HashMap<String, Long> getLast() {
		return last;
	}
	public void setLast(HashMap<String, Long> last) {
		this.last = last;
	}
	
	
}
