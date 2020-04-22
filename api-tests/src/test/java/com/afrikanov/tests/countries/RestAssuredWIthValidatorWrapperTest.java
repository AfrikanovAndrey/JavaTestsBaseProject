package com.afrikanov.tests.countries;

import com.site.tests.utils.junit.extensions.AllureRestAssuredExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.afrikanov.tests.country.CountrySteps.getCountriesByName;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

/*
 *  REST Countries API : https://restcountries.eu/rest/v2
 */

@ExtendWith(AllureRestAssuredExtension.class)
public class RestAssuredWIthValidatorWrapperTest extends BaseRestCountriesTest {
    
    static Stream<Arguments> countriesDataProvider() {
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
        getCountriesByName(country)
                .checkStatusCode(SC_OK)
                .checkCountriesCount(1)
                .checkCountryName(0, expectedOfficialName)
                .checkCountryCapitalName(0, expectedCapitalName);
    }
    
    @Test
    @DisplayName("Getting several countries by partially name")
    void checkGetMultipleCountryInfo() {
        getCountriesByName("uk")
                .checkStatusCode(SC_OK)
                .checkCountriesCount(4)
                .checkCountriesNames(
                        "Ukraine",
                        "Cook Islands",
                        "Korea (Democratic People's Republic of)",
                        "United Kingdom of Great Britain and Northern Ireland"
                );
    }
    
    @Test
    @DisplayName("Getting nonexistent country")
    void checkGettingNonexistentCountry() {
        getCountriesByName("zzz")
                .checkStatusCode(SC_NOT_FOUND);
    }
}
