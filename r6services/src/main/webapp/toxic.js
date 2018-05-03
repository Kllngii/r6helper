		function getJsonSynchron() {
			var json = {"toxicteam":[{"name":"<script>alert('Pwned!');</script>Feuriegel14", "grund":"BOSS"},{"name":"Kllngii", "grund":"Zu guter Spieler"},{"name":"Carsten", "grund":"Mega-Hacker"}]};
			return json;
		}
		function init() {
			parseJSON(getJsonSynchron());
			
//			getAndParseJSON();
			
			// Nur aktivieren, wenn das JSON direkt vom Server geholt werden kann:
//			setInterval(getAndParseJSON, 1000);
		}
		
		function getAndParseJSON() {
			let url = "file:////Users/lasse_kelling/Desktop/toxic.json";
			//FIXME Dafür sorgen, dass es den Pfad gibt 
//			let url = "/r6/service/toxic/all";
			
			fetch(url)
				.then(response => {
//					console.log("Response ist da.")
					return response.json();
				})
				.then(json => {
//					console.log("JSON wurde erfolgreich geholt.", json);
					parseJSON(json);
				})
				.catch(err => { 
					console.error("Fehler beim Holen des JSON: " + err);
					throw err ;
				});
		}
		
		function parseJSON(json) {
			document.getElementById("zeitstempel").innerHTML = new Date().toLocaleTimeString("de-DE");
			
			if (json.toxicteam == undefined || json.toxicteam.length == 0) {
				//FIXME Irgendwie Meldung anzeigen
//				if (i == 0)
//					row.querySelector(".name").innerHTML = "Daten enthalten keine Toxic Spieler!";
			}
			else {
				// Für jeden Toxicspieler ein HTML-Fragment a la 
//				<tr id="sp1" align="center">
//				    <td class="name">Xxxx</td>
//				    <td class="grund">Yyyy</td>
//			    </tr>
				for (let toxic of json.toxicteam) {
					$('#table').append(`<tr>
							<td class="name"></td>
							<td class="grund"></td>
						</tr>`);
					$('.name:last').text(toxic.name);
					$('.grund:last').text(toxic.grund);
				}
			}
		}
		
		function zeigeToxic(i, json) {
			let row = document.getElementById("sp" + (i+1));
			let none = "KEINE";
			if (json.toxicteam == undefined || json.toxicteam.length == 0) {
				if (i == 0)
					row.querySelector(".name").innerHTML = "Daten enthalten keine Toxic Spieler!";
			}
			else if (json.toxicteam.length > i) {
				let toxic = json.toxicteam[i];
				row.querySelector(".name").innerHTML = toxic.name;
				if(toxic.grund != undefined) {
					row.querySelector(".grund").innerHTML = toxic.grund;
				}
			}
			else {
				// Zeilen, die nicht von einem Spieler belegt sind, leeren
				row.querySelector(".name").innerHTML = " ";
				row.querySelector(".grund").innerHTML = " ";
			}

		}