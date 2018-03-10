package br.com.voffice.jwp2018.tf01.oscar.controllers;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Movie {
	private String title;
	private LocalDate releasedDate;
	private Double budget;
	private String poster;
	private String category;
	private boolean status;
}
