var LABEL_CREATE = "Create";
var LABEL_UPDATE = "Update";

var defineKey = (m) =>  m.title+m.releasedDate;
var formatKey = (m) =>  m.title+" ("+m.releasedDate.split("-")[0]+")";

var showInfo = (m) =>  messageArea.innerHTML = m;
var showError = (m) =>  messageArea.innerHTML = "<strong>Error</strong>: <output>"+m+"<output>";

var getMovieFromForm = () => {
	 var movie = {};
	 movie.title = fieldTitle.value;
	 movie.releasedDate = fieldReleasedDate.value;
	 movie.budget = fieldBudget.value;
	 movie.poster = fieldPoster.value;
	 movie.rating = fieldRating.value;
	 movie.category = fieldCategory.value;
	 movie.result = fieldResultWinner.checked?"true":"false";
	 return movie;
};

var setMovieToForm = (movie) => {
	 fieldTitle.value = movie.title;
	 fieldReleasedDate.value = movie.releasedDate;
	 fieldBudget.value = movie.budget;
	 fieldPoster.value = movie.poster;
	 fieldRating.value = movie.rating;
	 fieldCategory.value = movie.category;
	 delete fieldResultWinner.checked;
	 delete fieldResultNominee.checked;
	 if (movie.result == true) fieldResultWinner.checked = "checked";
	 if (movie.result == false) fieldResultNominee.checked = "checked";
};

var clearForm = () => {
	 fieldTitle.value = "";
	 fieldReleasedDate.value = "";
	 fieldBudget.value = "0.00";
	 fieldPoster.value = "";
	 fieldRating.value = "0";
	 fieldCategory.value = "";
	 fieldResultWinner.removeAttribute("checked");
	 fieldResultNominee.removeAttribute("checked");
	 fieldTitle.removeAttribute("disabled");
	 fieldReleasedDate.removeAttribute("disabled");
	 btnSave.value = LABEL_CREATE;
};

var encodeParams = function (params) {
    return Object.keys(params).map(function(key) {
        return [key, params[key]].join("=");
    }).join("&");
};

var addToTable = (m) => {
	var key = defineKey(m);
	tblBody.innerHTML +=
		"<tr>"+
		"<td>"+m.title+"</td>"+
		"<td>"+m.releasedDate+"</td>"+
		"<td>"+m.budget+"</td>"+
		"<td><a href='"+m.poster+"' target='_blank'><img"+
		"	src=\""+m.poster+"\""+
		"	width=\"30px\"></a></td>"+
		"<td>"+m.rating+"</td>"+
		"<td>"+m.category+"</td>"+
		"<td>"+m.result+"</td>"+
		"<td>"+
		"<button onclick=\"deleteMovie('"+key+"')\"> delete </button>"+
		"<button onclick=\"editMovie('"+key+"')\"> edit </button>"+
		"</td>"+
		"</tr>";
}

var editMovie = (key) => {
	btnSave.value = LABEL_UPDATE;
	getMovie(key);
	fieldTitle.disabled = "disabled";
	fieldReleasedDate.disabled = "disabled";
};

var createMovie = () => {
	if (	btnSave.value == LABEL_CREATE){
		var movie = getMovieFromForm();
		movie = encodeParams(movie);
		xhrPost(moviesUri,movie,
				(e) => {
					 if (isSuccess(e)) {
					  showInfo("Movie created!");
					  loadMovies();
					  clearForm();
					 }
				},
			e => console.log(e));
	}
};

var updateMovie = () => {
	if (btnSave.value == LABEL_UPDATE){
		var movie = getMovieFromForm();
		var key = defineKey(movie);
		movie = encodeParams(movie);
		xhrPut(moviesKeyUri+"/"+key,movie,
				(e) => {
					  showInfo("Movie updated!");
					  loadMovies();
					  clearForm();
				},
				e => console.log(e));
	}
};

var saveMovie = () => {
	if (btnSave.value == LABEL_CREATE){
		createMovie();
	}
	if (btnSave.value == LABEL_UPDATE){
		updateMovie();
	}
};

var deleteMovie = (key) => {
	xhrDelete(moviesKeyUri+"/"+key,"",
			(e) => {
				    showInfo("Movie removed!");
					loadMovies();
					clearForm();
			},
			e => console.log(e));
}

var loadMovies = () => {
	 var onLoad = (e) => {
		var response = e.target.responseText;
		tblBody.innerHTML = "";
		if (response){
			var movies = JSON.parse(response);
			movies.forEach((m) => addToTable(m));
		}
	};
	var onError = e => console.log(e);
	xhrGet(moviesUri, "", onLoad, onError);
};

var getMovie = (key) => {
	console.log(key);
	 var onLoad = e => {
		var response = e.target.responseText;
		var isSuccess = e.target.status >= 200 && e.target.status < 400;
		if (isSuccess){
			var movie = JSON.parse(response);
			setMovieToForm(movie);
			btnSave.value=LABEL_UPDATE;
		} else {
			if (e.target.status == 404){
				showError("Movie "+key+" not found");

			} else {
				showError("unknown error ("+e.target.statusText+").");
			}
		}
	};
	xhrGet(moviesKeyUri+"/"+key, "", onLoad, onErrorDefault);
};

var loadBestPicture = () => {
	xhrGet(moviesBestPictureUri, "", (e) => {
		loadMovies();
	},
	(e) => console.log(e));
};

var init = () => {
	loadMovies();
	btnSave.addEventListener("click", saveMovie);
	btnClear.addEventListener("click", clearForm);
	btnBestPicture.addEventListener("click", loadBestPicture);
	btnSave.value=LABEL_CREATE;
};

addEventListener("load", init());