package com.afrikanov.tests;

import com.afrikanov.tests.pages.GoogleMainPage;
import com.afrikanov.tests.pages.GoogleResultsPage;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Web tests")
public class SimpleUITest {

    @Test
    @DisplayName("Check google search result")
    public void googleSearchResults() {
        Configuration.holdBrowserOpen = true;

        GoogleResultsPage resultsPage = new GoogleMainPage().open().search("Selenide");
        resultsPage
                .checkResultsAtPage(10)
                .checkResultWithText("Selenide: лаконичные и стабильные UI тесты на Java");
    }
}
