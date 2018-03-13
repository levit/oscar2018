package br.com.voffice.jwp2018.tf01.oscar.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Movie {
	private String title;
	private LocalDate releasedDate;
	private Double budget;
	private String poster;
	private Integer rating;
	private String category;
	private boolean result;
	public String getKey() {
		return this.getTitle()+this.getReleasedDate().format(DateTimeFormatter.ISO_DATE);
	}

}
