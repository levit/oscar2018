package br.com.voffice.jwp2018.tf01.oscar.controllers;

import javax.servlet.http.HttpServletRequest;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import br.com.voffice.jwp2018.tf01.oscar.domain.Movie;

public class MoviesControllerFunctionsTest {

	private final MockHttpServletRequest request = new MockHttpServletRequest();

	@Test
	public void shouldRejectTitleNotPresent() {
		EntityExtractor<Movie> entityExtractor = MoviesControllerFunctions.extractMovieGetPost.apply(request);
		Assertions.assertThat(entityExtractor.getExtractor("title").isRequired()).isTrue();
		Assertions.assertThat(entityExtractor.getExtractor("title").wasAccepted()).isFalse();
	}

	@Test
	public void shouldRejectTitleEmpty() {
		request.setParameter("title", "");
		EntityExtractor<Movie> entityExtractor = MoviesControllerFunctions.extractMovieGetPost.apply(request);
		Assertions.assertThat(entityExtractor.getExtractor("title").isRequired()).isTrue();
		Assertions.assertThat(entityExtractor.getExtractor("title").wasAccepted()).isFalse();
	}

	@Test
	public void shouldAcceptTitleNotEmpty() {
		request.setParameter("title", "Ghost");
		EntityExtractor<Movie> entityExtractor = MoviesControllerFunctions.extractMovieGetPost.apply(request);
		Assertions.assertThat(entityExtractor.getExtractor("title").isRequired()).isTrue();
		Assertions.assertThat(entityExtractor.getExtractor("title").wasAccepted()).isTrue();
	}

	@Test
	public void shouldRejectReleasedDateNotPresent() {
		request.setParameter("title", "Ghost");
		EntityExtractor<Movie> entityExtractor = MoviesControllerFunctions.extractMovieGetPost.apply(request);
		Assertions.assertThat(entityExtractor.getExtractor("releasedDate").isRequired()).isTrue();
		Assertions.assertThat(entityExtractor.getExtractor("releasedDate").wasAccepted()).isFalse();
	}

	@Test
	public void shouldRejectReleasedDateEmpty() {
		request.setParameter("title", "Ghost");
		request.setParameter("releasedDate", "");
		EntityExtractor<Movie> entityExtractor = MoviesControllerFunctions.extractMovieGetPost.apply(request);
		Assertions.assertThat(entityExtractor.getExtractor("releasedDate").isRequired()).isTrue();
		Assertions.assertThat(entityExtractor.getExtractor("releasedDate").wasAccepted()).isFalse();
	}

	@Test
	public void shouldAcceptReleasedDateValid() {
		request.setParameter("title", "Ghost");
		request.setParameter("releasedDate", "2018-03-01");
		EntityExtractor<Movie> entityExtractor = MoviesControllerFunctions.extractMovieGetPost.apply(request);
		Assertions.assertThat(entityExtractor.getExtractor("releasedDate").isRequired()).isTrue();
		Assertions.assertThat(entityExtractor.getExtractor("releasedDate").wasAccepted()).isTrue();
	}


	@Test
	public void shouldRejectReleasedDateInvalid() {
		request.setParameter("title", "Ghost");
		request.setParameter("releasedDate", "dsfdfdf");
		EntityExtractor<Movie> entityExtractor = MoviesControllerFunctions.extractMovieGetPost.apply(request);
		Assertions.assertThat(entityExtractor.getExtractor("releasedDate").isRequired()).isTrue();
		Assertions.assertThat(entityExtractor.getExtractor("releasedDate").wasAccepted()).isFalse();
	}

	@Test
	public void shouldRejectBudgetInvalid() {
		request.setParameter("title", "Ghost");
		request.setParameter("releasedDate", "2018-01-01");
		request.setParameter("budget", "dfdfdfdf1");
		EntityExtractor<Movie> entityExtractor = MoviesControllerFunctions.extractMovieGetPost.apply(request);
		Assertions.assertThat(entityExtractor.getExtractor("budget").isRequired()).isFalse();
		Assertions.assertThat(entityExtractor.getExtractor("budget").wasSubmitted()).isTrue();
		Assertions.assertThat(entityExtractor.getExtractor("budget").wasConverted()).isFalse();
		Assertions.assertThat(entityExtractor.getExtractor("budget").wasAccepted()).isFalse();
	}

	@Test
	public void shouldAcceptBudgetEmpty() {
		request.setParameter("title", "Ghost");
		request.setParameter("releasedDate", "2018-01-01");
		request.setParameter("budget", "");
		EntityExtractor<Movie> entityExtractor = MoviesControllerFunctions.extractMovieGetPost.apply(request);
		Assertions.assertThat(entityExtractor.getExtractor("budget").isRequired()).isFalse();
		Assertions.assertThat(entityExtractor.getExtractor("budget").wasSubmitted()).isFalse();
		Assertions.assertThat(entityExtractor.getExtractor("budget").wasConverted()).isFalse();
		Assertions.assertThat(entityExtractor.getExtractor("budget").wasAccepted()).isTrue();
	}

}
