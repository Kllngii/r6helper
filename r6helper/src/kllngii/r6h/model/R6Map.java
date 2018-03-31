package kllngii.r6h.model;

public enum R6Map {
////	Hauptspiel Maps:	////
	Bank("/#bank/1/all", "Bank"),
	Barlett("/#bartlett/1/all", "Barlett Universität"),
	Chalet("/#chalet/1/all", "Chalet"),
	Clubhaus("/#club/1/all", "Clubhaus"),
	CafeDosto("/#kafe/1/all", "Cafe Dostjewski"),
	Haus("/#house/1/all", "Haus"),
	Hereford("/#hereford/1/all", "Hereford Base"),
	Kanal("/#kanal/1/all", "Kanal"),
	Konsulat("/#consulate/1/all", "Konsulat"),
	Oregon("/#oregon/1/all", "Oregon"),
	Flugzeug("/#plane/2/all", "Flugzeug"),
	
////	DLC Maps:	////
//	Year 1
	Jacht("/#yacht/2/all", "Jacht"),
	Grenze("/#border/1/all", "Grenze"),
	Favela("/#favela/1/all", "Favela"),
	Wolkenkratzer("/#skyscraper/1/all", "Wolkenkratzer"),
//	Year 2
	Küste("/#coastline/1/all", "Küste"),
	Freizeitpark("/#themepark/1/all", "Freizeitpark"),
	Turm("/#Tower/1/all", "Turm"),
//  Year 3
	
	;
	
	private final String url;
	private final String name;
	
	private R6Map(String url, String name){
		this.url =url;
		this.name =name;
	}

	public String getName() {
		return name;
	}
	@Override
	public String toString() {
	    return name;
	}

	public String getUrl() {
		return url;
	}
}
