package com.afrikanov.tests.users;

import com.afrikanov.tests.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.afrikanov.tests.user.UserSubject.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assertions.assertAll;

public class UserTruthCustomSubjectTest {

	@Test
	@DisplayName("Check list contains")
	void checkListContains() {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		assertWithMessage("Check numbers in list")
				.that(numbers)
				.containsExactly(1, 2, 3, 4, 5, 6, 7, 9);
	}

	@Test
	@DisplayName("Check list order")
	void checkListOrder() {
		List<Integer> numbers = Arrays.asList(1, 2, 3);
		assertWithMessage("Check list order")
				.that(numbers)
				.containsExactly(3, 2, 1)
				.inOrder();
	}

	@Test
	void customSubjectCheck() {

		User user = new User();
		user.setName("Andrey");
		user.setLastName("Afrikanov");
		user.setAge(34);

//		assertWithMessage("Check user parameters")
//				.about(users())
//				.that(user)
//				.hasName("Andrey");

		assertAll(
				() -> assertThat(user).hasName("Andrey"),
				() -> assertThat(user).hasLastName("Ivanov"),
				() -> assertThat(user).hasAge(34)
		);


	}
}
