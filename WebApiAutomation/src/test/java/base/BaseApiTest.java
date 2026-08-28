package base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {

    protected String baseUrl = "https://restful-booker.herokuapp.com";

    @BeforeClass
    public void setupApi() {
        RestAssured.baseURI = baseUrl;
    }
}