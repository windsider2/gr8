import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BrewerySearchTest {
    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "https://api.openbrewerydb.org/v1/breweries/search";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void validateResponseSchema() {

        given()
                .queryParam("query", "san diego")
                .when()
                .get()
                .then()
                .statusCode(200)
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("brewery-schema.json")); // Path to your schema file
    }


    @Test(dataProvider = "searchQueries")
    public void validateSearchByQuery(String query, String expectedState, Matcher<?> matcher) {
        given()
                .queryParam("query", query)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("[0].state_province", equalTo(expectedState))
                .body("size()", matcher);
    }


    @Test(dataProvider = "paginationCases")
    public void validatePagination(Object perPage, int expectedStatusCode, int expectedSize) {
        Response response = given()
                .queryParam("query", "san diego")
                .queryParam("per_page", perPage)
                .when()
                .get();

        response.then()
                .statusCode(expectedStatusCode)
                .body("size()", equalTo(expectedSize)); // Verify the size of the result
    }

    @Test
    public void validateResponseFields() {

        given()
                .queryParam("query", "san diego")
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].brewery_type", notNullValue())
                .body("[0].city", notNullValue())
                .body("[0].state_province", notNullValue())
                .body("[0].postal_code", notNullValue());
    }

    @Test
    public void validateSearchSpecialBrand() {

        given()
                .queryParam("query", "mikkeller brewing san diego")
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("[0].name", containsStringIgnoringCase("Mikkeller Brewing San Diego"))
                .body("[0].city", equalTo("San Diego"));

    }

    @Test
    public void validateMissingQueryParameter() {

        given()
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("$", hasSize(0));
    }

    @DataProvider(name = "searchQueries")
    private Object[][] provideSearchQueries() {
        return new Object[][]{
                {"san diego", "California", greaterThan(0)},
                {"", null, equalTo(0)},
                {"@#$%^&*", null, equalTo(0)},
                {"1234567890", null, equalTo(0)},
                {"longquerywithnoresultsbecauseitdoesnotexist", null, equalTo(0)}
        };
    }

    @DataProvider(name = "paginationCases")
    private Object[][] providePaginationCases() {
        return new Object[][]{
                {3, 200, 3},
                {1, 200, 1},
                {92, 200, 91},
                {0, 200, 0},
                {-1, 200, 0},
                {Integer.MIN_VALUE, 400, 0},
                {Integer.MAX_VALUE, 400, 0},
                {null, 200, 91},
                {"abc", 400, 0}
        };
    }
}

