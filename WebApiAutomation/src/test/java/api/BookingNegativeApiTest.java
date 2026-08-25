package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseApiTest;
public class BookingNegativeApiTest extends BaseApiTest {

    @Test
    public void getNonExistingBooking() {

        Response response = RestAssured
                .given()
                .when()
                .get("/booking/999999999");

        System.out.println("GET non-existing booking status: "
                + response.getStatusCode());

        Assert.assertEquals(
                response.getStatusCode(),
                404
        );
    }

    @Test
    public void deleteNonExistingBooking() {

        Response response = RestAssured
                .given()
                .auth()
                .preemptive()
                .basic("admin", "password123")
                .when()
                .delete("/booking/999999999");

        System.out.println("DELETE non-existing booking status: "
                + response.getStatusCode());

        Assert.assertTrue(
                response.getStatusCode() == 405
                        || response.getStatusCode() == 404
        );
    }
}