var showInfo = (m) =>  console.log(m);
var showError = (m) =>  console.log(m);

var isSuccess = e => e.target.status >= 200 && e.target.status < 400;

var isError = e => !isSuccess(e) && e.target.status;

var isBadRequest = e => e.target.status == 400;

var onLoadDefault = e => {
	if (isSuccess(e)){
		showInfo("Data = ("+e.target.resultText+").");
	}
}

var onErrorDefault = e => {
	if (isError(e)){
		if (isBadRequest(e)){
			var response = e.target.responseText;
			var error = JSON.parse(response);
			showError(error.error_description);
		} else{
			showError("unhandled error ("+e.target.status+").");
		}
	}
}

var xhrMethod = (method, uri, params, onLoad, onError) => {
	var xhr = new XMLHttpRequest();
	xhr.onload = onLoad || onLoadDefault;
	xhr.onError = onError || onErrorDefault;
	xhr.onreadystatechange = onErrorDefault;
	xhr.open(method,uri,true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(params);
};

var xhrGet = (uri, params, onLoad, onError) => {
	var xhr = new XMLHttpRequest();
	xhr.onload = onLoad || onLoadDefault;
	xhr.onError = onError || onErrorDefault;
	if (params) uri += "?" + params;
	xhr.open("GET",uri, true);
	xhr.send();
};

var xhrPost = (uri, params, onLoad, onError) => {
	xhrMethod("POST", uri, params, onLoad, onError);
};

var xhrPut = (uri, params, onLoad, onError) => {
	xhrMethod("PUT", uri, params, onLoad, onError);
};

var xhrDelete = (uri, params, onLoad, onError) => {
	xhrMethod("DELETE", uri, params, onLoad, onError);
};

var encodeParams = (params) => {
    return Object.keys(params).map(function(key) {
        return [key, params[key]].join("=");
    }).join("&");
};