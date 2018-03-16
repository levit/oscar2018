package br.com.voffice.jwp2018.tf01.oscar.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet("/api/servlets/movies/posters")
public class PostersController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<String> posters = Files.list(Paths.get(getPosterPath())).filter(MoviesControllerFunctions.imageExtensionPredicate).map(p -> p.getFileName().toString()).sorted().collect(Collectors.toList());
		resp.getWriter().write(MoviesControllerFunctions.toJSON(posters));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getParts().stream().forEach(p -> MoviesControllerFunctions.savePart(getPosterPath(), p));
		resp.setStatus(HttpServletResponse.SC_ACCEPTED);
		resp.sendRedirect(req.getRequestURI());
	}

	private final String getPosterPath() {
		return this.getServletContext().getInitParameter("poster_path");
	}

}
