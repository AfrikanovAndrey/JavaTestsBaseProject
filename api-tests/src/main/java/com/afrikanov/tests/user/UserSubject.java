package com.afrikanov.tests.user;

import com.google.common.truth.FailureMetadata;
import com.google.common.truth.Subject;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nullable;

import static com.google.common.truth.Truth.assertAbout;

public class UserSubject extends Subject {

	private User actual;

	protected UserSubject(FailureMetadata metadata, @NullableDecl User actual) {
		super(metadata, actual);
		this.actual = actual;
	}

	public static Factory<UserSubject, User> users() {
		return UserSubject::new;
	}

	public static UserSubject assertThat(@Nullable User actual) {
		return assertAbout(users()).that(actual);
	}

	public void hasName(String name) {
		check("name").that(actual.getName()).isEqualTo(name);
	}

	public void hasLastName(String lastName) {
		check("last name").that(actual.getLastName()).isEqualTo(lastName);
	}

	public void hasAge(int age) {
		check("last name").that(actual.getAge()).isEqualTo(age);
	}
}
