package br.com.voffice.jwp2018.tf01.oscar.controllers;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EntityExtractor<E> implements Iterable<FieldExtractor<?>> {

	private final E entity;
	private final Map<String,FieldExtractor<?>> extractorsMapping = new LinkedHashMap<>();
	public final FieldExtractor<Long> id;
	public final FieldExtractor<String> title;
	public final FieldExtractor<LocalDate> releasedDate;
	public final FieldExtractor<Double> budget;
	public final FieldExtractor<String> poster;

	@SuppressWarnings("unchecked")
	public EntityExtractor(E entity, List<FieldExtractor<?>> extractors) {
		super();
		this.entity = entity;
		for (FieldExtractor<?> e: extractors) {
			extractorsMapping.put(e.getFieldName(), e);
		}
		this.id = (FieldExtractor<Long>) extractors.stream().filter(e -> "id".equals(e.getFieldName())).findFirst().orElse(null);
		this.title = (FieldExtractor<String>) extractors.stream().filter(e -> "title".equals(e.getFieldName())).findFirst().orElse(null);
		this.releasedDate = (FieldExtractor<LocalDate>) extractors.stream().filter(e -> "releasedDate".equals(e.getFieldName())).findFirst().orElse(null);
		this.budget = (FieldExtractor<Double>) extractors.stream().filter(e -> "budget".equals(e.getFieldName())).findFirst().orElse(null);
		this.poster = (FieldExtractor<String>) extractors.stream().filter(e -> "poster".equals(e.getFieldName())).findFirst().orElse(null);
	}

	public E getEntity() {
		return entity;
	}

	public FieldExtractor<?> getExtractor(String fieldName) {
		return extractorsMapping.get(fieldName);
	}

	@Override
	public Iterator<FieldExtractor<?>> iterator() {
		return extractorsMapping.values().stream().iterator();
	}



}
