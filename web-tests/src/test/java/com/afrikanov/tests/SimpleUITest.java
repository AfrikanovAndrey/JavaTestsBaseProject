package com.afrikanov.tests;

import com.afrikanov.tests.pages.GoogleMainPage;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Web tests")
public class SimpleUITest {

	@Test
	@DisplayName("Check google search result")
	public void googleSearchResults() {
		Configuration.holdBrowserOpen = true;

		new GoogleMainPage()
				.open()
				.search("Selenide")
				.checkResultsAtPage(10)
				.checkResultWithText("Selenide: лаконичные и стабильные UI тесты на Java");
	}
}
