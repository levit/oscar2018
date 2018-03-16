package br.com.voffice.jwp2018.tf01.oscar.services;

import java.util.List;
import java.util.logging.Logger;

import br.com.voffice.jwp2018.tf01.oscar.domain.Movie;
import br.com.voffice.jwp2018.tf01.oscar.repositories.MovieRepository;

public class MovieService {

	public static final Logger logger = Logger.getLogger(MovieService.class.getName());
	private MovieRepository repository = new MovieRepository();

	public boolean create(Movie movie) {
		boolean wasCreated = repository.create(movie);
		logger.info(() -> movie.getKey()+" was created - " +wasCreated);
		return wasCreated;
	}

	public boolean update(Movie movie) {
		boolean wasUpdated = repository.update(movie);
		logger.info(() -> movie.getKey()+" was updated - " +wasUpdated);
		return wasUpdated;
	}

	public boolean remove(String key) {
		boolean wasRemoved = repository.remove(key);
		logger.info(() -> key+" was removed - " +wasRemoved);
		return wasRemoved;
	}

	public List<Movie> findAll(){
		List<Movie>  result = repository.findAll();
		logger.info(() -> " Movies were found - " + (result.size() > 0));
		return result;
	}

	public Movie findByKey(String key) {
		logger.info(() -> key +" exists ? "+(containsKey(key)));
		return repository.findByKey(key);
	}

	public boolean containsKey(String key) {
		return repository.containsKey(key);
	}

}
