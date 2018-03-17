package br.com.voffice.jwp2018.tf01.oscar.controllers;

import java.io.IOException;

import javax.servlet.ServletException;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class MoviesControllerTest {

	private final MoviesController controller = new MoviesController();
	private final MockHttpServletRequest req = new MockHttpServletRequest();
	private final MockHttpServletResponse resp = new MockHttpServletResponse();


	@Test
	public void shouldReturnEmptyJSONArrayGivenEmptyMovies() throws ServletException, IOException {
		controller.doGet(req, resp);
		Assertions.assertThat(resp.getContentAsString()).isEqualTo("[]");
		}
}
