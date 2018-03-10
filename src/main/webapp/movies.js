var init = () => {
	var tblBody = document.getElementById("tblBody");
	var addToTable = (m) => {
		tblBody.innerHTML +=
			"<tr>"+
			"<td>"+m.title+"</td>"+
			"<td>"+m.releasedDate+"</td>"+
			"<td>"+m.budget+"</td>"+
			"<td><img"+
			"	src=\""+m.poster+"\""+
			"	width=\"30px\"></td>"+
			"<td>"+m.category+"</td>"+
			"<td>"+m.status+"</td>"+
			"</tr>";
	}
	var xhr = new XMLHttpRequest();
	xhr.onload = (e) => {
		console.log(e.target.responseText);
		var movies = JSON.parse(e.target.responseText);
		tblBody.innerHTML = "";
		movies.forEach((m) => addToTable(m));
	}
	xhr.open("GET","http://localhost:8080/oscar/api/movies", true);
	xhr.send();
};

addEventListener("load", init());