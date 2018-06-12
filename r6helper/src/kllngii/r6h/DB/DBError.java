package kllngii.r6h.DB;

public class DBError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6640803624078056215L;

	public DBError(String ex) {
		super(ex);
	}
	
	public DBError(String message, Throwable cause) {
        super(message, cause);
    }
	
	protected DBError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
}

	public DBError() {
		super("Fehler beim Verarbeiten der ID -> Eventuell existiert der Spieler nicht ");
	}
}
