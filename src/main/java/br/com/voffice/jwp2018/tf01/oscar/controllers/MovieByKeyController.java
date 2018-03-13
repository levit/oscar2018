package br.com.voffice.jwp2018.tf01.oscar.controllers;

import static br.com.voffice.jwp2018.tf01.oscar.controllers.MoviesControllerFunctions.getKey;
import static br.com.voffice.jwp2018.tf01.oscar.controllers.MoviesControllerFunctions.getParameters;

import java.io.IOException;
import java.net.URLDecoder;
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

@WebServlet("/api/servlets/movies/key/*")
public class MovieByKeyController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final MovieService service = new MovieService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String key = getKey.apply(req.getRequestURI());
		if (key == null || key.trim().length() == 0) {
			resp.getWriter().write("key is required");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		key = URLDecoder.decode(key, "UTF-8");
		if (service.containsKey(key)) {
			ObjectMapper mapper = new ObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
					.registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
					.registerModule(new JavaTimeModule());
			try {
				String json = mapper.writeValueAsString(service.findByKey(key));
				resp.getWriter().write(json);
			} catch (Exception e) {
				Logger.getAnonymousLogger().severe(e::getMessage);
			}
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String key = getKey.apply(req.getRequestURI());
		if (key == null || key.trim().length() == 0) {
			resp.getWriter().write("key is required");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		key = URLDecoder.decode(key, "UTF-8");
		Map<String, String> parameters = getParameters.apply(req.getInputStream());
		Movie movie = MoviesControllerFunctions.toMovie.apply(parameters);
		if (service.containsKey(key)) {
			service.update(movie);
			resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String key = getKey.apply(req.getRequestURI());
		if (key == null || key.trim().length() == 0) {
			resp.getWriter().write("key is required");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		key = URLDecoder.decode(key, "UTF-8");
		if (service.containsKey(key)) {
			service.remove(key);
			resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

}
