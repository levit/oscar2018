package br.com.voffice.jwp2018.tf01.oscar.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
@WebServlet("/api/servlets/movies/posters")
public class PostersController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String download = req.getParameter("download");
		if (download != null) {
			String path = getPosterPath();
			File folder = new File(path);
			File file = new File(folder, download);
			byte[] contents = Files.readAllBytes(file.toPath());
			ServletOutputStream outputStream = resp.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(outputStream, 4096);
			bos.write(contents);
		} else {
		List<String> posters = Files.list(Paths.get(getPosterPath())).filter(MoviesControllerFunctions.imageExtensionPredicate).map(p -> p.getFileName().toString()).sorted().collect(Collectors.toList());
		resp.getWriter().write(MoviesControllerFunctions.toJSON(posters));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Collection<Part> parts = req.getParts();
		parts.stream().forEach(p -> MoviesControllerFunctions.savePart(getPosterPath(), p));
		resp.setStatus(HttpServletResponse.SC_ACCEPTED);
		resp.sendRedirect(req.getRequestURI());
	}

	private final String getPosterPath() {
		return this.getServletContext().getInitParameter("poster_path");
	}

}
