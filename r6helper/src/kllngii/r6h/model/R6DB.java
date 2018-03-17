package kllngii.r6h.model;

public class R6DB {
	
	static String url = "https://r6db.com/search/PS4/";
	
	public static String createUrl(String name) {
		if(name.trim() == ""){
			System.out.println("Fehler in R6DB.createUrl(name)! "+ url + name);
			return url;
		}
		else {
			return url+name+"/";
		}
	}
	
}
