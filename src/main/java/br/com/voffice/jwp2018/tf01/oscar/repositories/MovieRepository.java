package br.com.voffice.jwp2018.tf01.oscar.repositories;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import br.com.voffice.jwp2018.tf01.oscar.domain.Movie;

public class MovieRepository {

	public static final Logger logger = Logger.getLogger(MovieRepository.class.getName());
    private static final Map<String,Movie> REPO = new HashMap<>();
	private static final Comparator<Movie> byTitleAsc = Comparator.comparing(Movie::getTitle);
	private static final Comparator<Movie> byTitleDesc = byTitleAsc.reversed();
	private static final Comparator<Movie> byBudgetAsc = Comparator.comparing(Movie::getBudget);
	private static final Comparator<Movie> byBudgetDesc = byBudgetAsc.reversed();
	private static final Comparator<Movie> byRatingAsc = Comparator.comparing(Movie::getRating);
	private static final Comparator<Movie> byRatingDesc = byRatingAsc.reversed();

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

	public List<Movie> findByCategory(String category) {
		return REPO.values().stream().filter(m -> m.getCategory().equals(category)).collect(Collectors.toList());
	}

	public List<Movie> findAllSortBy(String sortBy) {
		if (sortBy == null) sortBy = "title=asc";
		String field = sortBy.split("=")[0];
		String ascending = sortBy.split("=")[1];
		Comparator<Movie> by = byTitleAsc;
		if ("title".equals(field)) {
			by = "desc".equals(ascending)? byTitleDesc: byTitleAsc;
		}
		if ("budget".equals(field)) {
			by = "desc".equals(ascending)? byBudgetDesc: byBudgetAsc;
		}
		if ("rating".equals(field)) {
			by = "desc".equals(ascending)? byRatingDesc: byRatingAsc;
		}
		return this.findAll().stream().sorted(by).collect(Collectors.toList());
	}

}
