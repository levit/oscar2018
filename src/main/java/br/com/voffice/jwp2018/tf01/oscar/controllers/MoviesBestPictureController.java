package br.com.voffice.jwp2018.tf01.oscar.controllers;

import java.io.IOException;
import java.time.LocalDate;
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

@WebServlet("/api/servlets/movies/bestPicture")
public class MoviesBestPictureController extends HttpServlet {

	private static final String CATEGORY_BEST_PICTURE = "Best Picture";
	private static final long serialVersionUID = 1L;
	private static final MovieService service = new MovieService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Movie threeBillboards = new Movie("Three Billboards Outside Ebbing, Missouri", LocalDate.of(2017,12,01), 15_000_000d, "https://images-na.ssl-images-amazon.com/images/M/MV5BZTZjYzU2NTktNTdmNi00OTM0LTg5MDgtNGFjOGMzNjY0MDk5XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL_.jpg", 82, CATEGORY_BEST_PICTURE,false);
		Movie ladyBird = new Movie("Lady Bird", LocalDate.of(2017, 12, 1), 10_000_000d, "https://images-na.ssl-images-amazon.com/images/M/MV5BODhkZGE0NDQtZDc0Zi00YmQ4LWJiNmUtYTY1OGM1ODRmNGVkXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL_.jpg", 76, CATEGORY_BEST_PICTURE, false);
		Movie getOut = new Movie("Get Out", LocalDate.of(2017, 2,24), 5_000_000d, "https://images-na.ssl-images-amazon.com/images/M/MV5BMjUxMDQwNjcyNl5BMl5BanBnXkFtZTgwNzcwMzc0MTI@._V1_UX182_CR0,0,182,268_AL_.jpg", 74, CATEGORY_BEST_PICTURE, false);
		Movie shapeOfWater = new Movie("Shape of Water", LocalDate.of(2017, 12, 22), 19_400_000d, "https://drraa3ej68s2c.cloudfront.net/wp-content/uploads/2017/12/12163133/87954c22e6e3783117f13feadf7e9681f463b7011a91c7af2ebd1a962d20aa53-195x195.jpg", 74, CATEGORY_BEST_PICTURE, true);
		List<Movie> nominees = Arrays.asList(threeBillboards, ladyBird, getOut, shapeOfWater);

		for (Movie nominee: nominees) {
			service.create(nominee);
		}
		List<Movie> movies = service.findAll();
		resp.setContentType("application/json");
		ObjectMapper mapper = new ObjectMapper()
				   .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
				   .registerModule(new ParameterNamesModule())
				   .registerModule(new Jdk8Module())
				   .registerModule(new JavaTimeModule());
		try {
			String json = mapper.writeValueAsString(movies);
			resp.getWriter().write(json);
		} catch (Exception e) {
			Logger.getAnonymousLogger().severe(e::getMessage);
		}
	}
}

