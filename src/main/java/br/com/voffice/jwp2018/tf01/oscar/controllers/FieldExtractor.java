package br.com.voffice.jwp2018.tf01.oscar.controllers;

import java.util.Optional;

public final class FieldExtractor<V> {

	private final String fieldName;
	private final Optional<String> submittedValue;
	private final Optional<V> convertedValue;
	private boolean required;

	FieldExtractor(String fieldName, String submittedValue, V convertedValue, boolean required) {
		this.fieldName = fieldName;
		this.submittedValue = Optional.ofNullable(submittedValue);
		this.convertedValue = Optional.ofNullable(convertedValue);
		this.required = required;
	}

	FieldExtractor(String fieldName, Optional<String> submittedValue, Optional<V> convertedValue, boolean required) {
		this.fieldName = fieldName;
		this.submittedValue = submittedValue;
		this.convertedValue = convertedValue;
		this.required = required;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Optional<V> getConvertedValue() {
		return convertedValue;
	}

	public Optional<String> getSubmittedValue() {
		return submittedValue;
	}

	public boolean isRequired() {
		return required;
	}

	public boolean wasSubmitted() {
		return getSubmittedValue().isPresent();
	}

	public boolean wasConverted() {
		return getConvertedValue().isPresent();
	}

	public boolean wasAccepted() {
		return wasSubmitted() && wasConverted();
	}

	public V getValue(V defaultValue) {
		return getConvertedValue().orElse(defaultValue);
	}

}
