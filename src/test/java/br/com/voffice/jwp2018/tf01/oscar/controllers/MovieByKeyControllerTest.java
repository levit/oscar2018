package br.com.voffice.jwp2018.tf01.oscar.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class MovieByKeyControllerTest {

	@Test
	public void doGet_sad_400_in_key_required() throws ServletException, IOException {
		MovieByKeyController controller = new MovieByKeyController();
		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse resp = new MockHttpServletResponse();
		controller.doGet(req, resp);
		Assertions.assertThat(resp.getContentAsString()).contains("key is required");
		Assertions.assertThat(resp.getStatus()).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);
	}

}
