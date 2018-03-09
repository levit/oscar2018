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
	tblBody.innerHTML = "";
	var shapeOfWater = {
		title: "The Shape of Water",
		releasedDate: "2017-12-22",
		budget: "19.400.000",
		poster: "https://drraa3ej68s2c.cloudfront.net/wp-content/uploads/2017/12/12163133/87954c22e6e3783117f13feadf7e9681f463b7011a91c7af2ebd1a962d20aa53-195x195.jpg",
		category: "Best Picture",
		status: "yes"
	};
	var ladyBird = {
			title: "Lady Bird",
			releasedDate: "2017-12-01E",
			budget: "15.000.000",
			poster: "https://images-na.ssl-images-amazon.com/images/M/MV5BODhkZGE0NDQtZDc0Zi00YmQ4LWJiNmUtYTY1OGM1ODRmNGVkXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL_.jpg",
			category: "Best Picture",
			status: "no"
		};
	var getOut = {
			title: "Get Out",
			releasedDate: "2017-02-24",
			budget: "5.000.000",
			poster: "https://images-na.ssl-images-amazon.com/images/M/MV5BMjUxMDQwNjcyNl5BMl5BanBnXkFtZTgwNzcwMzc0MTI@._V1_UX182_CR0,0,182,268_AL_.jpg",
			category: "Best Picture",
			status: "no"
		};
	var threeBillboards = {
			title: "Three Billboards Outside Ebbing, Missouri",
			releasedDate: "2017-12-01",
			budget: "15.000.000",
			poster: "https://images-na.ssl-images-amazon.com/images/M/MV5BZTZjYzU2NTktNTdmNi00OTM0LTg5MDgtNGFjOGMzNjY0MDk5XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL_.jpg",
			category: "Best Picture",
			status: "no"
		};
	[shapeOfWater,ladyBird,getOut,threeBillboards].forEach((m) => addToTable(m));
};
addEventListener("load", init());