package br.com.voffice.jwp2018.tf01.oscar.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import br.com.voffice.jwp2018.tf01.oscar.domain.Movie;

public final class MoviesControllerFunctions {
	private MoviesControllerFunctions(){}



	public static final Function<Map<String,String>,String> keyFromParameters = parameters ->
	parameters.get("title")+parameters.get("releasedDate");

	public static final Function<Map.Entry<String,String[]>, Map.Entry<String,String>> toSingleMapper =
	 e -> new Map.Entry<String, String>() {

		@Override
		public String getKey() {
			return e.getKey();
		}

		@Override
		public String getValue() {
			return e.getValue()[0];
		}

		@Override
		public String setValue(String value) {
			return null;
		}
	};

	public static final Function<Map<String,Movie>,Function<HttpServletResponse,Function<String,Boolean>>> setStatusWhenKey = map -> resp -> key -> {
		boolean result = map.containsKey(key);
		if (result){
			resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return result;
	};

	public static final Function<String,String> getKey = u -> u.replaceAll("/oscar","").replaceAll("/api/servlets/movies/key", "").replaceAll("/", "");

	public static final Function<InputStream,Map<String,String>> getParameters = is -> {
		Scanner sc = new Scanner(is);
		Map<String,String> map = new HashMap<>();
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] parts = line.split("&");
			for (String part: parts) {
				if (part.split("=").length == 2) {
					String key = part.split("=")[0];
					String value = part.split("=")[1];
					map.put(key, value);
				} else {
					System.out.printf("%s not accepted %n", part);
				}
			}
		}
		sc.close();
		return map;
	};

	static final Function<String, Function<String, Function<Boolean, FieldExtractor<LocalDate>>>> localDateExtractor = n -> v -> r -> {
		String value = v != null && v.trim().length() > 0 ? v : null;
		Optional<String> submittedValue = Optional.ofNullable(value);
		Optional<LocalDate> convertedValue = Optional.empty();
		try {
			convertedValue = submittedValue.map(LocalDate::parse);
		} catch (DateTimeParseException e) {
			Logger.getGlobal().fine(e.getMessage());
		}
		return new FieldExtractor<LocalDate>(n, submittedValue, convertedValue, r);
	};

	static final Function<String, Function<String, Function<Boolean, FieldExtractor<Long>>>> longExtractor = n -> v -> r -> {
		String value = v != null && v.trim().length() > 0 ? v : null;
		Optional<String> submittedValue = Optional.ofNullable(value);
		Optional<Long> convertedValue = Optional.empty();
		try {
			convertedValue = submittedValue.map(Long::parseLong);
		} catch (NumberFormatException e) {
			Logger.getGlobal().fine(e.getMessage());
		}
		return new FieldExtractor<Long>(n, submittedValue, convertedValue, r);
	};

	static final Function<String, Function<String, Function<Boolean, FieldExtractor<Integer>>>> integerExtractor = n -> v -> r -> {
		String value = v != null && v.trim().length() > 0 ? v : null;
		Optional<String> submittedValue = Optional.ofNullable(value);
		Optional<Integer> convertedValue = Optional.empty();
		try {
			convertedValue = submittedValue.map(Integer::parseInt);
		} catch (NumberFormatException e) {
			Logger.getGlobal().fine(e.getMessage());
		}
		return new FieldExtractor<Integer>(n, submittedValue, convertedValue, r);
	};
	static final Function<String, Function<String, Function<Boolean, FieldExtractor<Double>>>> doubleExtractor = n -> v -> r -> {
		String value = v != null && v.trim().length() > 0 ? v : null;
		Optional<String> submittedValue = Optional.ofNullable(value);
		Optional<Double> convertedValue = Optional.empty();
		try {
			convertedValue = submittedValue.map(Double::parseDouble);
		} catch (NumberFormatException e) {
			Logger.getGlobal().fine(e.getMessage());
		}
		return new FieldExtractor<Double>(n, submittedValue, convertedValue, r);
	};

	static final Function<String, Function<String, Function<Boolean, FieldExtractor<String>>>> stringExtractor = n -> v -> r -> {
		String value = v != null && v.trim().length() > 0 ? v : null;
		Optional<String> submittedValue = Optional.ofNullable(value);
		Optional<String> convertedValue = submittedValue;
		return new FieldExtractor<String>(n, submittedValue, convertedValue, r);
	};


	static final Function<String, Function<String, Function<Boolean, FieldExtractor<Boolean>>>> booleanExtractor = n -> v -> r -> {
		String value = v != null && v.trim().length() > 0 ? v : null;
		Optional<String> submittedValue = Optional.ofNullable(value);
		Optional<Boolean> convertedValue = Optional.empty();
		try {
			convertedValue = submittedValue.map(Boolean::parseBoolean);
		} catch (NumberFormatException e) {
			Logger.getGlobal().fine(e.getMessage());
		}
		return new FieldExtractor<Boolean>(n, submittedValue, convertedValue, r);
	};

	static final Function<Map<String,String>, EntityExtractor<Movie>> extractMovie = parameters -> {
		FieldExtractor<String> title = stringExtractor.apply("title").apply(parameters.get("title")).apply(true);
		FieldExtractor<LocalDate> releasedDate = localDateExtractor.apply("releasedDate")
				.apply(parameters.get("releasedDate")).apply(true);
		FieldExtractor<Double> budget = doubleExtractor.apply("budget").apply(parameters.get("budget")).apply(false);
		FieldExtractor<String> poster = stringExtractor.apply("poster").apply(parameters.get("poster")).apply(false);
		FieldExtractor<Integer> rating = integerExtractor.apply("rating").apply(parameters.get("rating")).apply(false);
		FieldExtractor<String> category = stringExtractor.apply("category").apply(parameters.get("category")).apply(false);
		FieldExtractor<Boolean> result = booleanExtractor.apply("result").apply(parameters.get("result")).apply(false);
		List<FieldExtractor<?>> extractors = Arrays.asList(title, releasedDate, budget, poster, rating, category, result);
		Movie movie = new Movie(title.getValue(null), releasedDate.getValue(null),
				budget.getValue(0.0), poster.getValue(null), rating.getValue(0),category.getValue(null),result.getValue(false));
		return new EntityExtractor<Movie>(movie, extractors);
	};

	static final Function<HttpServletRequest, EntityExtractor<Movie>> extractMovieGetPost = (req) -> {
		Map<String,String[]> parameterValues = req.getParameterMap();
		Map<String,String> parameters = new HashMap<>();
		for (Map.Entry<String, String[]> pv: parameterValues.entrySet()) {
			String[] values = pv.getValue();
			if (values.length > 0) parameters.put(pv.getKey(), values[0]);
		}
		return extractMovie.apply(parameters);
	};

	static Function<HttpServletResponse, Function<FieldExtractor<?>, Boolean>> respondRequired = resp -> e -> {
		String name = e.getFieldName();
		boolean result = false;
		if ((e.isRequired()) && (!e.wasAccepted())) {
			result = true;
			try {
				resp.getWriter().write("{\"error\": \"parameter.required\", \"error_description\": \"Parameter " + name
						+ " is required\", \"error_source\": \"" + name + " parameter\", \"value\":\""+e.getSubmittedValue()+"\"}");
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			} catch (IOException ex) {
				throw new IllegalStateException(ex);
			}
		}
		return result;
	};


	static Function<HttpServletResponse, Function<FieldExtractor<?>, Boolean>> respondInvalid = resp -> e -> {
		String name = e.getFieldName();
		boolean result = false;
		if ((!e.isRequired()) && (!e.wasConverted())) {
			result = true;
			try {
				resp.getWriter().write("{\"error\": \"parameter.invalid\", \"error_description\": \"Parameter " + name
						+ " is invalid\", \"error_source\": \"" + name + " parameter\", \"value\":\""+e.getSubmittedValue()+"\"}");
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			} catch (IOException ex) {
				throw new IllegalStateException(ex);
			}
		}
		return result;
	};
	static final Function<String[], String> getFirst = ss -> ss.length > 0 ? ss[0] : null;

	static final Function<HttpServletRequest, EntityExtractor<Movie>> extractMoviePutDelete = (req) -> {
		try {
			Map<String, String> parameters = getParameters.apply(req.getInputStream());
			return extractMovie.apply(parameters);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	};

	public static final Function<Map<String,String>,Movie> toMovie = m -> {
		return new Movie(m.get("title"),
				LocalDate.parse(m.get("releasedDate")),
				Double.parseDouble(m.getOrDefault("budget","0")),
				m.get("poster"),
				Integer.parseInt(m.getOrDefault("rating","0")),
				m.get("category"),
				Boolean.parseBoolean(m.getOrDefault("result","false")));
	};

	public static final ObjectMapper mapper = new ObjectMapper()
			   .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
			   .registerModule(new ParameterNamesModule())
			   .registerModule(new Jdk8Module())
			   .registerModule(new JavaTimeModule());

	public static String toJSON(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			Logger.getAnonymousLogger().severe(e::getMessage);
			return "[]";
		}
	}


	public static final Predicate<? super Path> imageExtensionPredicate = p -> {
		List<String> extensions = Arrays.asList(".jpg",".gif","png");
		return extensions.stream().filter(e -> p.getFileName().toString().endsWith(e)).findFirst().isPresent();
	};

	public static final void savePart(String path, Part part) {
		String name = part.getSubmittedFileName();
		File folder = new File(path);
		File file = new File(folder, name);
		try (InputStream input = part.getInputStream()) {
		    Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

}
