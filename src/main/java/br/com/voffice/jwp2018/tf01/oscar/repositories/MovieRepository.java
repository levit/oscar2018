package br.com.voffice.jwp2018.tf01.oscar.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import br.com.voffice.jwp2018.tf01.oscar.domain.Movie;

public class MovieRepository {

	public static final Logger logger = Logger.getLogger(MovieRepository.class.getName());
    private static final Map<String,Movie> REPO = new HashMap<>();

	public boolean create(Movie movie) {
		boolean canCreate = ! REPO.containsKey(movie.getKey());
		if (canCreate) {
			REPO.put(movie.getKey(), movie);
		}
		return canCreate;
	}

	public boolean update(Movie movie) {
		boolean canUpdate =  REPO.containsKey(movie.getKey());
		if (canUpdate) {
			REPO.put(movie.getKey(), movie);
		}
		return canUpdate;
	}

	public boolean remove(String key) {
		boolean canRemove =  REPO.containsKey(key);
		if (canRemove) {
			REPO.remove(key);
		}
		return canRemove;
	}

	public List<Movie> findAll(){
		List<Movie> result = new ArrayList<Movie>(REPO.values());
		return result;
	}

	public Movie findByKey(String key) {
		return REPO.getOrDefault(key, null);
	}

	public boolean containsKey(String key) {
		return REPO.containsKey(key);
	}

}
