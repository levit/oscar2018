package br.com.voffice.jwp2018.tf01.oscar.controllers;

import static br.com.voffice.jwp2018.tf01.oscar.controllers.MoviesControllerFunctions.toMapCollector;
import static br.com.voffice.jwp2018.tf01.oscar.controllers.MoviesControllerFunctions.toMovie;
import static br.com.voffice.jwp2018.tf01.oscar.controllers.MoviesControllerFunctions.toSingleMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import br.com.voffice.jwp2018.tf01.oscar.domain.Movie;
import br.com.voffice.jwp2018.tf01.oscar.services.MovieService;

@WebServlet("/api/servlets/movies")
public class MoviesController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final MovieService service = new MovieService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String[]> parameterMap = req.getParameterMap();
		Map<String,String> parameters =
		parameterMap.entrySet().stream().map(toSingleMapper).collect(toMapCollector);
		String key = MoviesControllerFunctions.keyFromParameters.apply(parameters);
		if (key == null) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		EntityExtractor<Movie> movieExtractor = MoviesControllerFunctions.extractMovieGetPost.apply(req);
		if (MoviesControllerFunctions.respondRequired.apply(resp).apply(movieExtractor.title)) return;
		if (MoviesControllerFunctions.respondRequired.apply(resp).apply(movieExtractor.releasedDate)) return;

		boolean created = service.create(toMovie.apply(parameters));
		int status = (created)? HttpServletResponse.SC_CREATED: HttpServletResponse.SC_CONFLICT;
		resp.setStatus(status);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		List<Movie> movies = service.findAll();
		resp.getWriter().write(MoviesControllerFunctions.toJSON(movies));
	}


}

