package kllngii.r6h.DB;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import kllngii.r6h.spieler.Spieler;

public class DBWorker extends Application{
	public List<Spieler> getDBNames(List<Spieler> team) {
		//TODO Bessere Lösung finden
		for(Spieler sp : team) {
			TextInputDialog dialog = new TextInputDialog("IngameName");
			dialog.setTitle("DB-Name");
			dialog.setHeaderText(sp.getName() + "'s Ingame Name");
			dialog.setContentText("Ingamename von " + sp.getName() + " angeben:");
				
			Optional<String> result = dialog.showAndWait();
			result.ifPresent(name -> sp.setDbname(name));
			System.out.println("Der DB-Name von " + sp.getName() + " ist jetzt " + sp.getDbname());
		}
		return team;
	}
	public List<DBValues> getTeam(List<Spieler> team) {
		List<DBValues> processedTeam = new ArrayList<>();
		DBRequest request = new DBRequest();
		for(Spieler sp : team) {
			 processedTeam.add(request.getPlayerData(sp.getDbname()));
		}
		return processedTeam;
	}
	public static void main(String[] args) {
		launch(args);
	}
	@SuppressWarnings("unused")
	@Override
	public void start(Stage primaryStage) throws Exception {
		List<Spieler> str = new ArrayList<>();
		Spieler sp = new Spieler("Kllngii");
		str.add(sp);
		str = getDBNames(str);
		getTeam(str);
	}
}
