package com.api.springmongo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class SpringMongoApplicationTests {

	@Test
	void testSys() {
		//1
		System.out.println("Hello World");

		//2
		for (int i = 1; i <= 10; i++) {
			if (i % 3 == 0 && i % 5 == 0) {
				System.out.println("FizzBuzz");
			} else if (i % 3 == 0) {
				System.out.println("Fizz");
			} else if (i % 5 == 0) {
				System.out.println("Buzz");
			} else {
				System.out.println(i);
			}
		}

		//3
		// Example array of integers
		int[] numbers = {1, 2, 3, 4, 5};
		int sum = 0;

		for (int number : numbers) {
			sum += number;
		}

		System.out.println("The sum of all elements in the array is: " + sum);

		//5
		List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

		List<Integer> squaredEvens = integers.stream()
				.filter(i -> i % 2 == 0)
				.map(i -> i * i)
				.collect(Collectors.toList());

		System.out.println(squaredEvens);



	}

}
