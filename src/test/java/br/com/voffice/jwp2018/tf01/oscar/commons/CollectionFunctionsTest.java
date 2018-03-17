package br.com.voffice.jwp2018.tf01.oscar.commons;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.assertj.core.api.Assertions;
import org.junit.Test;
public class CollectionFunctionsTest {

	private final List<Number> numbers = new ArrayList<>();

	@Test
	public void paginateShouldReturnEmptyGivenEmpty() {
		List<Number> result = CollectionFunctions.paginate(numbers,1,1);
		Assertions.assertThat(result).isEmpty();
	}

	@Test
	public void paginateShouldReturn1GivenPage1AndSize1() {
		numbers.add(1L);
		List<Number> result = CollectionFunctions.paginate(numbers,1,1);
		Assertions.assertThat(result).hasSize(1);
	}

	@Test
	public void paginateShouldReturn3GivenPage1AndSize3() {
		numbers.addAll(IntStream.rangeClosed(1, 10).mapToObj(n -> new Integer(n)).collect(Collectors.toList()));
		List<Number> result = CollectionFunctions.paginate(numbers,1,3);
		Assertions.assertThat(result).hasSize(3);
	}

	@Test
	public void paginateShouldReturn3GivenPage3AndSize3() {
		numbers.addAll(IntStream.rangeClosed(1, 10).mapToObj(n -> new Integer(n)).collect(Collectors.toList()));
		List<Number> result = CollectionFunctions.paginate(numbers,3,3);
		Assertions.assertThat(result).contains(7,8,9);
	}

	@Test
	public void paginateShouldReturn1GivenPage4AndSize3() {
		numbers.addAll(IntStream.rangeClosed(1, 10).mapToObj(n -> new Integer(n)).collect(Collectors.toList()));
		List<Number> result = CollectionFunctions.paginate(numbers,4,3);
		Assertions.assertThat(result).contains(10);
	}

}
