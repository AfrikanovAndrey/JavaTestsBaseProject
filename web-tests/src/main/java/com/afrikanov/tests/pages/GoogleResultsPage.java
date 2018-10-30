package com.afrikanov.tests.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$;

public class GoogleResultsPage {

    private ElementsCollection searchResults = $$("div.rc");

    public GoogleResultsPage checkResultsAtPage(int count) {
        searchResults.shouldHave(
                CollectionCondition
                        .size(count)
                        .because("Wrong search results on page")
        );
        return this;
    }

    public GoogleResultsPage checkResultWithText(String desiredResultText) {
        searchResults.filter(text(desiredResultText))
                .first()
                .should(exist.because("There is no desired search result"));
        return this;
    }
}
