
		/**
		 * Nur zu Testzwecken
		 */
		function getJsonSynchron() {
//			return r6helperdata;
			
//			return {"gegnerteam": [
//		        {
//		            "primärwaffe": {
//		                "name": "SR-25",
//		                "typ": "DMR"
//		            },
//		            "name": "Blackbeard",
//		            "lifepoints": 100,
//		            "sekundärwaffe": {
//		                "name": "Desert Eagle",
//		                "typ": "PISTOLE"
//		            },
//		            "gadgets": ["Sprengladung"]
//		        },
//		        {
//		            "name": "Ash",
//		            "lifepoints": 100
//		        },
//		        {
//		            "name": "Lion",
//		            "lifepoints": 100
//		        },
//		        {
//		            "name": "Ying",
//		            "lifepoints": 100,
//		            "sekundärwaffe": {
//		                "name": "Q-929",
//		                "typ": "PISTOLE"
//		            }
//		        },
//		        {
//		            "name": "Rekrut Blau",
//		            "lifepoints": 100
//		        }
//		    ]};
		}
		
		function init() {
			//parseJSON(getJsonSynchron());
			
			getAndParseJSON();
			
			// Nur aktivieren, wenn das JSON direkt vom Server geholt werden kann:
			setInterval(getAndParseJSON, 1000);
		}
		
		function getAndParseJSON() {
//			let url = "https://www.dropbox.com/s/kvdxxjv92u9v3d0/r6helper.json?dl=1";
//			let url = "file:////Users/lasse_kelling/Dropbox/R6/r6helper.json";
//			let url = "http://192.168.2.10:8080/r6/service/data/all";
			let url = "/r6/service/data/all";
			
			fetch(url)
				.then(response => {
					console.log("Response ist da.")
					return response.json();
				})
				.then(json => {
					console.log("JSON wurde erfolgreich geholt.", json);
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
				zeigeGegner(i, json);
			}
		}
		
		function zeigeGegner(i, json) {
			let row = document.getElementById("op" + (i+1));
			let none = "KEINE";
			
			if (json.gegnerteam == undefined || json.gegnerteam.length == 0) {
				if (i == 0)
					row.querySelector(".name").innerHTML = "Daten enthalten kein Gegnerteam!";
			}
			else if (json.gegnerteam.length > i) {
				let gegner = json.gegnerteam[i];
				row.querySelector(".name").innerHTML = gegner.name;
				if(gegner.primärwaffe != undefined && gegner.primärwaffe.name != undefined) {
					row.querySelector(".priwa").innerHTML = gegner.primärwaffe.name;
				}
				if(gegner.sekundärwaffe != undefined && gegner.sekundärwaffe.name != undefined) {
					row.querySelector(".sekwa").innerHTML = gegner.sekundärwaffe.name;
				}
				if(gegner.lifepoints != undefined) {
					row.querySelector(".life").innerHTML = gegner.lifepoints.toString();
				}
				if(gegner.gadgets != undefined) {
					row.querySelector(".gadget").innerHTML = gegner.gadgets[0].toString();
					if(gegner.gadgets[1] != undefined) {
						row.querySelector(".gadget").innerHTML = gegner.gadgets[0].toString()+" "+gegner.gadgets[1].toString();
					}
				}
				row.querySelector(".faehig").innerHTML = none;
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