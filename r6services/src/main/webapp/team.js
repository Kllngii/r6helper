		function init() {
			getAndParseJSON();
			setInterval(getAndParseJSON, 1000);
		}
		
		function getAndParseJSON() {
			let url = "/r6/service/data/all";
			
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
			
			for (let i = 0; i <= 4; i++) {
				zeigeTeam(i, json);
			}
		}
		
		function zeigeTeam(i, json) {
			let row = document.getElementById("spielerrepo" + (i+1));
			let none = "KEINE";
			
			if (json.spielerrepo == undefined || json.team.length == 0) {
				if (i == 0)
					row.querySelector(".name").innerHTML = "Daten enthalten kein Team!";
			}
			else if (json.spielerrepo.length > i) {
				let team = json.spielerrepo[i];
				row.querySelector(".name").innerHTML = team.name;
				if(team.deaths != undefined) {
					row.querySelector(".tode").innerHTML = team.tode;
				}
				if(team.kills != undefined) {
					row.querySelector(".kills").innerHTML = team.kills;
				}
				if(team.kd != undefined) {
					row.querySelector(".kd").innerHTML = team.kd;
				}
				if(team.ace != undefined) {
					row.querySelector(".ace").innerHTML = team.ace;
				}
				if(team.knife != undefined) {
					row.querySelector(".knife").innerHTML = team.knife;
				}
			}
			else {
				// Zeilen, die nicht von einem Gegner belegt sind, leeren
				row.querySelector(".name").innerHTML = " ";
				row.querySelector(".priwa").innerHTML = " ";
				row.querySelector(".sekwa").innerHTML = " ";
				row.querySelector(".life").innerHTML = " ";
				row.querySelector(".gadget").innerHTML = " ";
				row.querySelector(".faehig").innerHTML = " ";
			}

		}