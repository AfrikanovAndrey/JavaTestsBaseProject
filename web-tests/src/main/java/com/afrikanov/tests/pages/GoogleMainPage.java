package com.afrikanov.tests.pages;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;

public class GoogleMainPage {

    private final static String PAGE_URL = "http://google.com";

    public GoogleMainPage open(){
        Selenide.open(PAGE_URL);
        return this;
    }

    public GoogleResultsPage search(String text){
        $(By.name("q")).setValue("Selenide");
        $(By.name("q")).sendKeys(Keys.ENTER);
        return new GoogleResultsPage();
    }
}
