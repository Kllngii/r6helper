package kllngii.r6h.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

public class FTPZugriff {
	
	/**
	 * @return Bei Fehler den <code>reply</code> Parameter, ansonsten null
	 * 
	 * @param server IP-Adresse des Servers
	 * @param login Gibt an, ob Login erforderlich
	 * @param username Benutzername auf dem Server
	 * @param password Password für Benutzer
	 * @param filename Name der Datei, die erstellt wird
	 * @param serverfilename Name der Date auf dem Server
	 * @param path Dateipfad auf dem Server
	 * 
	 * @author Lasse Kelling
	 */
	public void getFileFTP(URI server, boolean login, String username, String password, String filename, String serverfilename, URI path) {
		FTPClient client = new FTPClient();
		FTPClientConfig config = new FTPClientConfig();
		client.configure(config);
		
		try {
			client.connect(server.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(login) {
			try {
				client.login(username ,password);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		int reply = client.getReplyCode();
		
		if(FTPReply.isPositiveCompletion(reply))
			System.out.println("Login war erfolgreich!");
		
		else {
			System.err.println("Login gescheitert! Reply:" + reply);
			try {
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		File file = new File(filename);
		try {
			@SuppressWarnings("resource")
			FileOutputStream out = new FileOutputStream(file);
			client.changeWorkingDirectory(path.toString());
			client.retrieveFile(serverfilename, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			client.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * @return null oder <code>reply</code> Fehlercode
	 * 
	 * @param server IP-Adresse des Servers
	 * @param login Gibt an, ob Login erforderlich
	 * @param username Benutzername auf dem Server
	 * @param password Password für Benutzer
	 * @param filename Name der Datei auf Server
	 * 
	 * @author Lasse Kelling
	 */
	public void postFileFTP(URI server, boolean login, String username, String password, String filename) {
		FTPClient client = new FTPClient();
		
		try {
			client.connect(server.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(login) {
			try {
				client.login(username ,password);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		int reply = client.getReplyCode();
		
		if(FTPReply.isPositiveCompletion(reply))
			System.out.println("Login war erfolgreich!");
		
		else {
			System.err.println("Login gescheitert! Reply:" + reply);
			try {
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			@SuppressWarnings("resource")
			FileInputStream post = new FileInputStream(filename);
			client.storeFile(filename, post);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			client.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
