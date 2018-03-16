package br.com.voffice.jwp2018.tf01.oscar.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
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

@WebServlet("/api/servlets/movies/oscar/nominees")
public class OscarNomineesController extends HttpServlet {

	private static final String CATEGORY_BEST_PICTURE = "Best Picture";
	private static final long serialVersionUID = 1L;
	private static final MovieService service = new MovieService();
	private static final Movie call = new Movie("Call Me By Your Name", LocalDate.of(2017, 1, 1), 0d, "https://drraa3ej68s2c.cloudfront.net/wp-content/uploads/2018/01/24093817/198d42addf5e5e45e6b9d4228ec47239fc247db5df56e44eb8bdbc1fd42b1d97-1024x1024.jpg", 74, CATEGORY_BEST_PICTURE, false);
	private static final Movie darkest = new Movie("Darkest Hour", LocalDate.of(2017, 9, 1), 114_845_157d, "https://image.tmdb.org/t/p/w1280/qtG4Gf3GE7UEdN6ixJXEKwZUSA4.jpg", 72, CATEGORY_BEST_PICTURE, false);
	private static final Movie dunkirk = new Movie("Dunkirk", LocalDate.of(2017, 7, 19), 100_000_000d, "https://image.tmdb.org/t/p/w1280/ebSnODDg9lbsMIaWg2uAbjn7TO5.jpg", 74, CATEGORY_BEST_PICTURE, false);
	private static final Movie getOut = new Movie("Get Out", LocalDate.of(2017, 2,24), 5_000_000d, "https://images-na.ssl-images-amazon.com/images/M/MV5BMjUxMDQwNjcyNl5BMl5BanBnXkFtZTgwNzcwMzc0MTI@._V1_UX182_CR0,0,182,268_AL_.jpg", 74, CATEGORY_BEST_PICTURE, false);
	private static final Movie ladyBird = new Movie("Lady Bird", LocalDate.of(2017, 12, 1), 10_000_000d, "https://images-na.ssl-images-amazon.com/images/M/MV5BODhkZGE0NDQtZDc0Zi00YmQ4LWJiNmUtYTY1OGM1ODRmNGVkXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL_.jpg", 76, CATEGORY_BEST_PICTURE, false);
	private static final Movie phantom = new Movie("Phantom Thread", LocalDate.of(2017, 12, 11), 35_000_000d, "https://image.tmdb.org/t/p/w1280/yZ8j8xKk2xQ1d88hB8YG6LZfRQj.jpg", 73, CATEGORY_BEST_PICTURE, false);
	private static final Movie post = new Movie("The Post", LocalDate.of(2017, 12, 14), 50_000_000d, "https://image.tmdb.org/t/p/w1280/qyRwj5VvuTRdJ76o2grP93grNxt.jpg", 70, CATEGORY_BEST_PICTURE, false);
	private static final Movie shapeOfWater = new Movie("Shape of Water", LocalDate.of(2017, 12, 22), 19_400_000d, "https://drraa3ej68s2c.cloudfront.net/wp-content/uploads/2017/12/12163133/87954c22e6e3783117f13feadf7e9681f463b7011a91c7af2ebd1a962d20aa53-195x195.jpg", 74, CATEGORY_BEST_PICTURE, true);
	private static final Movie threeBillboards = new Movie("Three Billboards Outside Ebbing, Missouri", LocalDate.of(2017,12,01), 15_000_000d, "https://images-na.ssl-images-amazon.com/images/M/MV5BZTZjYzU2NTktNTdmNi00OTM0LTg5MDgtNGFjOGMzNjY0MDk5XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL_.jpg", 82, CATEGORY_BEST_PICTURE,false);
	private static final List<Movie> nominees = Arrays.asList(call,darkest,dunkirk,getOut,ladyBird,phantom,post,shapeOfWater,threeBillboards);
	private static final ObjectMapper mapper = new ObjectMapper()
			   .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
			   .registerModule(new ParameterNamesModule())
			   .registerModule(new Jdk8Module())
			   .registerModule(new JavaTimeModule());

	public static final Function<List<Movie>,String> writeValueAsString = movies -> {
		try {
			return mapper.writeValueAsString(movies);
		} catch (final Exception e) {
			Logger.getAnonymousLogger().severe(e::getMessage);
			return null;
		}

	};

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		nominees.stream().forEach(service::create);
		resp.setContentType("application/json");
		resp.getWriter().write(writeValueAsString.apply(service.findAll()));
	}
}

