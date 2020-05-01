		function init() {
			getAndParseJSON();
			setCookie("last", "team", 20);
			setInterval(getAndParseJSON, 1000);
		}
		
		function getAndParseJSON() {
			let url = "/r6/service/data/all";
			
			fetch(url)
				.then(response => {
					console.log("Daten wurden erfolgreich geholt.");
					return response.json();
				})
				.catch(err => { 
					console.error("Fehler beim Holen des JSON: " + err);
					alert("Fehler beim Holen des JSON: 1" + err);
					throw err ;
				})
				.then(json => {
					console.log("JSON wurde ausgepackt.", json);
					zeigeTeam(json);
				})
				.catch(err => { 
					console.error("Fehler beim Verarbeiten des JSON: " + err);
					alert("Fehler beim Verarbeiten des JSON: " + err);
					throw err;
				});
		}
		function zeigeTeam(json) {
			document.getElementById("zeitstempel").innerHTML = new Date().toLocaleTimeString("de-DE");
			var save = $('#table .head').detach();
			$('#table').empty().append(save);
			for (let team of json.spielerrepo) {
				$('#table').append(`<tr>
						<td><p class="name" >0</p></td>
						<td><p class="kills">0</p></td>
						<td><p class="tode" >0</p></td>
						<td><p class="knife">0</p></td>
						<td><p class="ace">0</p></td>
						<td><p class="kd">0</p></td>
						<td><p class="headshot"></p></td>
					</tr>`);
				$('.name:last').text(team.name);
				$('.kills:last').text(team.kills);
				$('.tode:last').text(team.deaths);
				$('.knife:last').text(team.knife);
				$('.ace:last').text(team.ace);
				$('.kd:last').text(team.kd);
				$('.headshot:last').text(team.headshot);
			}
		}