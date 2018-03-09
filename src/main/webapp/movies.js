var init = () => {
	var tblBody = document.getElementById("tblBody");
	tblBody.innerHTML = "";
	var shapeOfWater = {
		title: "The Shape of Water",
		releasedDate: "2017-12-22",
		budget: "19.400.000",
		poster: "https://drraa3ej68s2c.cloudfront.net/wp-content/uploads/2017/12/12163133/87954c22e6e3783117f13feadf7e9681f463b7011a91c7af2ebd1a962d20aa53-195x195.jpg",
		category: "Best Picture",
		status: "yes"
	};
	tblBody.innerHTML +=
	"<tr>"+
	"<td>"+shapeOfWater.title+"</td>"+
	"<td>"+shapeOfWater.releasedDate+"</td>"+
	"<td>"+shapeOfWater.budget+"</td>"+
	"<td><img"+
	"	src=\""+shapeOfWater.poster+"\""+
	"	width=\"30px\"></td>"+
	"<td>"+shapeOfWater.category+"</td>"+
	"<td>"+shapeOfWater.status+"</td>"+
	"</tr>";
};
addEventListener("load", init());