package com.afrikanov.tests.countries;

import io.restassured.RestAssured;

public class BaseRestCountriesTest {
    
    static {
        RestAssured.baseURI = "https://restcountries.eu";
    }
}
