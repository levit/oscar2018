package br.com.voffice.jwp2018.tf01.oscar.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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

@WebServlet("/api/servlets/movies/sortBy")
public class MoviesSortByController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final MovieService service = new MovieService();
	private static final ObjectMapper mapper = new ObjectMapper()
			   .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
			   .registerModule(new ParameterNamesModule())
			   .registerModule(new Jdk8Module())
			   .registerModule(new JavaTimeModule());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		String sortBy = null;
		sortBy = extractByField(req, "title", sortBy);
		sortBy = extractByField(req, "budget", sortBy);
		sortBy = extractByField(req, "rating", sortBy);
		List<Movie> movies = service.findAllSortBy(sortBy);
		resp.getWriter().write(toJSON(movies));
	}

	private static String extractByField(HttpServletRequest req, String field, String sortBy) {
		boolean hasField = req.getParameterMap().containsKey(field);
		String value = req.getParameter(field);
		hasField = hasField && Arrays.asList("asc","desc").contains(value);
		if (hasField) {
			sortBy = field+"="+value;
		}
		return sortBy;
	}
	private static String toJSON(List<Movie> movies) {
		try {
			return mapper.writeValueAsString(movies);
		} catch (Exception e) {
			Logger.getAnonymousLogger().severe(e::getMessage);
			return "[]";
		}
	}
}
