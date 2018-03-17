		function getJson() {
			return r6helperdata;
			
//			let url = "https://www.dropbox.com/s/kvdxxjv92u9v3d0/r6helper.json?dl=1";
//			let url = "file:////Users/lasse_kelling/Dropbox/R6/r6helper.json";
//			let json = {};
//			
//			fetch(url)
//				.then(res => json = res.json())
//				.catch(err => { throw err });
			
			
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
			parseJSON();
			
			// Wieder aktivieren, wenn das JSON direkt vom Server geholt werden kann:
			// setTimeout(parseJSON, 1000);
		}
		function parseJSON() {
			let json = getJson();
			var op1na, op1pri, op1sek, op1life, op1gadgets;
			var op2na, op2pri, op2sek, op2life, op2gadgets;
			
			for (let i = 0; i <= 4; i++) {
				zeigeGegner(i, json);
			}
		}
		
		function zeigeGegner(i, json) {
			let row = document.getElementById("op" + (i+1));
			let none = "KEINE";
			
			if (json.gegnerteam.length >= i) {
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

		}