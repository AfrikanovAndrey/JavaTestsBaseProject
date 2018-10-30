package com.afrikanov.tests;

import com.site.tests.utils.junit.extensions.AllureRestAssuredExtension;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

/*
 *  REST Countries API : https://restcountries.eu/rest/v2
 */

@ExtendWith(AllureRestAssuredExtension.class)
@DisplayName("API tests")
public class SimpleApiTest {

    static Stream<Arguments> countriesDataProvider(){
        return Stream.of(
                Arguments.of(
                        // Country name
                        "russia",
                        // Official name
                        "Russian Federation",
                        // Capital
                        "Moscow"
                ),
                Arguments.of(
                        // Country name
                        "canada",
                        // Official name
                        "Canada",
                        // Capital
                        "Ottawa"
                )
        );
    }

    @ParameterizedTest(name = "Getting info about country: {0}")
    @MethodSource("countriesDataProvider")
    void checkGetCountryInfo(
            String country,
            String expectedOfficialName,
            String expectedCapitalName
    ) {
        given()
                .pathParam("country", country)
        .when()
                .get("https://restcountries.eu/rest/v2/name/{country}")
        .then()
                .statusCode(200)
                .body("size()", equalTo(1))
                .body("name[0]", equalTo(expectedOfficialName))
                .body("capital[0]", equalTo(expectedCapitalName));
    }

    @Test
    @DisplayName("Getting several countries by partially name")
    void checkGetMultipleCountryInfo(){
        Response response =given()
                .pathParam("country", "uk")
                .when()
                .get("https://restcountries.eu/rest/v2/name/{country}")
                .then()
                .statusCode(200)
                .body("size()", equalTo(4))
                .extract()
                .response();

        assertThat(
                "Unexpected countries",
                response.path("collect {it.name}"),
                contains(
                        "Ukraine",
                        "Cook Islands",
                        "Korea (Democratic People's Republic of)",
                        "United Kingdom of Great Britain and Northern Ireland"
                )
        );
    }

    @Test
    @DisplayName("Getting nonexistent country")
    void checkGettingNonexistentCountry(){
        given()
                .pathParam("country", "zzz")
                .when()
                .get("https://restcountries.eu/rest/v2/name/{country}")
                .then()
                .statusCode(404);
    }
}
