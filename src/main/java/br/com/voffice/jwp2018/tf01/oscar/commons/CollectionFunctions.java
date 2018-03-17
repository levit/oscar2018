package br.com.voffice.jwp2018.tf01.oscar.commons;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CollectionFunctions {

	private CollectionFunctions() {}

	public static final <T> List<T> paginate(List<T> list, int page, int size) {
		return list.stream().skip(size*(page - 1L)).limit(size).collect(Collectors.toList());
	}

	public static final Collector<Entry<String, String>, ?, Map<String, String>> toMapCollector = Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);

}
