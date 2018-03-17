package br.com.voffice.jwp2018.tf01.oscar.controllers;

import static br.com.voffice.jwp2018.tf01.oscar.controllers.MoviesControllerFunctions.toSingleMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.voffice.jwp2018.tf01.oscar.commons.CollectionFunctions;
import br.com.voffice.jwp2018.tf01.oscar.domain.Movie;
import br.com.voffice.jwp2018.tf01.oscar.services.MovieService;

@WebServlet("/api/servlets/movies")
public class MoviesController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final MovieService service = new MovieService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String[]> parameterMap = req.getParameterMap();
		Map<String, String> parameters = parameterMap.entrySet().stream().map(toSingleMapper)
				.collect(CollectionFunctions.toMapCollector);
		String key = MoviesControllerFunctions.keyFromParameters.apply(parameters);
		if (key == null) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		EntityExtractor<Movie> movieExtractor = MoviesControllerFunctions.extractMovieGetPost.apply(req);
		if (MoviesControllerFunctions.respondRequired.apply(resp).apply(movieExtractor.getExtractor("title")))
			return;
		if (MoviesControllerFunctions.respondRequired.apply(resp).apply(movieExtractor.getExtractor("releasedDate")))
			return;
		for (FieldExtractor<?> fe : movieExtractor) {
			if (fe.wasAccepted())
				continue;
			MoviesControllerFunctions.respondInvalid.apply(resp).apply(fe);
			return;
		}
		boolean created = service.create(movieExtractor.getEntity());
		int status = (created) ? HttpServletResponse.SC_CREATED : HttpServletResponse.SC_CONFLICT;
		resp.setStatus(status);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		List<Movie> movies = service.findAll();
		try {
			resp.getWriter().write(MoviesControllerFunctions.toJSON(movies));
		} catch (IOException ioe) {
			this.log(ioe.getMessage());
		}
	}

}
