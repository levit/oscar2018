package br.com.voffice.jwp2018.tf01.oscar.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import br.com.voffice.jwp2018.tf01.oscar.domain.Movie;

public class MovieService {

	public static final Logger logger = Logger.getGlobal();
	public static final Map<String,Movie> repo = new HashMap<>();

	public boolean create(Movie movie) {
		boolean canCreate = ! repo.containsKey(movie.getKey());
		if (canCreate) {
			repo.put(movie.getKey(), movie);
		}
		logger.info("created= "+canCreate);
		return canCreate;
	}

	public boolean update(Movie movie) {
		boolean canUpdate =  repo.containsKey(movie.getKey());
		if (canUpdate) {
			repo.put(movie.getKey(), movie);
		}
		logger.info("updated= "+canUpdate);
		return canUpdate;
	}

	public boolean remove(String key) {
		boolean canRemove =  repo.containsKey(key);
		if (canRemove) {
			repo.remove(key);
		}
		logger.info("removed= "+canRemove);
		return canRemove;
	}

	public List<Movie> findAll(){
		List<Movie> result = new ArrayList<Movie>(repo.values());
		logger.info(" "+result.size()+" movies found");
		return result;
	}

	public Movie findByKey(String key) {
		logger.info(key +" exists ? "+(containsKey(key)));
		return repo.getOrDefault(key, null);
	}

	public boolean containsKey(String key) {
		return repo.containsKey(key);
	}
}
