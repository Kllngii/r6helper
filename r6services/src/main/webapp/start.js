function init() {
	
	let name = "last";
	let cookie;
	let url = "http://" + window.location.host + "/r6/";
	
	try {
		allowed = getCookie("allowed");
	}
	catch (e) {
		alert("Cookie Warnung!" + e);
	}
	if(allowed) {
		
	}
	else {
		alert("Diese Website nutzt Cookies!");
		setCookie("allowed", "true", 1000)
	}
	
	try {
		cookie = getCookie(name);
	}
	catch (e) {
		alert("Cookie Warnung!" + e)
	}
	//
	if(cookie == null)
		window.location.href = url + "index.html";
	else {
		if(cookie == "index") {
			window.location.href = url + "index.html";
		}
		if(cookie == "download") {
			window.location.href = url + "download.html";
		}
		if(cookie == "team") {
			window.location.href = url + "team.html";
		}
	}
}