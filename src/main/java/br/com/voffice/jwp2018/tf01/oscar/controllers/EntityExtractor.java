package br.com.voffice.jwp2018.tf01.oscar.controllers;

import java.time.LocalDate;
import java.util.List;

public class EntityExtractor<E> {

	private final E entity;
	private final List<FieldExtractor<?>> extractors;
	public final FieldExtractor<Long> id;
	public final FieldExtractor<String> title;
	public final FieldExtractor<LocalDate> releasedDate;
	public final FieldExtractor<Double> budget;
	public final FieldExtractor<String> poster;

	@SuppressWarnings("unchecked")
	public EntityExtractor(E entity, List<FieldExtractor<?>> extractors) {
		super();
		this.entity = entity;
		this.extractors = extractors;
		this.id = (FieldExtractor<Long>) extractors.stream().filter(e -> "id".equals(e.getFieldName())).findFirst().orElse(null);
		this.title = (FieldExtractor<String>) extractors.stream().filter(e -> "title".equals(e.getFieldName())).findFirst().orElse(null);
		this.releasedDate = (FieldExtractor<LocalDate>) extractors.stream().filter(e -> "releasedDate".equals(e.getFieldName())).findFirst().orElse(null);
		this.budget = (FieldExtractor<Double>) extractors.stream().filter(e -> "budget".equals(e.getFieldName())).findFirst().orElse(null);
		this.poster = (FieldExtractor<String>) extractors.stream().filter(e -> "poster".equals(e.getFieldName())).findFirst().orElse(null);
	}

	public E getEntity() {
		return entity;
	}

	public List<FieldExtractor<?>> getExtractors() {
		return extractors;
	}

}
