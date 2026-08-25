package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseApiTest;
public class BookingApiTest extends BaseApiTest {

    @Test
    public void getAllBookings() {

        Response response = RestAssured
                .given()
                .baseUri("https://restful-booker.herokuapp.com")
                .when()
                .get("/booking");

        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200);
    }
    @Test
    public void createBooking() {

        String requestBody = """
            {
              "firstname": "An",
              "lastname": "Nguyen",
              "totalprice": 150,
              "depositpaid": true,
              "bookingdates": {
                "checkin": "2026-09-01",
                "checkout": "2026-09-05"
              },
              "additionalneeds": "Breakfast"
            }
            """;

        Response response = RestAssured
                .given()
                .baseUri("https://restful-booker.herokuapp.com")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/booking");

        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200);
    }
    @Test
    public void getBookingById() {

        String requestBody = """
            {
              "firstname": "Mai",
              "lastname": "Tran",
              "totalprice": 200,
              "depositpaid": true,
              "bookingdates": {
                "checkin": "2026-09-10",
                "checkout": "2026-09-15"
              },
              "additionalneeds": "Lunch"
            }
            """;

        Response createResponse = RestAssured
                .given()
                .baseUri("https://restful-booker.herokuapp.com")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/booking");

        Assert.assertEquals(createResponse.getStatusCode(), 200);

        int bookingId = createResponse
                .jsonPath()
                .getInt("bookingid");

        System.out.println("Booking ID vừa tạo: " + bookingId);

        Response getResponse = RestAssured
                .given()
                .baseUri("https://restful-booker.herokuapp.com")
                .when()
                .get("/booking/" + bookingId);

        System.out.println(getResponse.asPrettyString());

        Assert.assertEquals(getResponse.getStatusCode(), 200);
        Assert.assertEquals(
                getResponse.jsonPath().getString("firstname"),
                "Mai"
        );
        Assert.assertEquals(
                getResponse.jsonPath().getString("lastname"),
                "Tran"
        );
    }
    @Test
    public void updateBooking() {

        String createBody = """
            {
              "firstname": "Lan",
              "lastname": "Pham",
              "totalprice": 300,
              "depositpaid": true,
              "bookingdates": {
                "checkin": "2026-10-01",
                "checkout": "2026-10-05"
              },
              "additionalneeds": "Breakfast"
            }
            """;

        Response createResponse = RestAssured
                .given()
                .baseUri("https://restful-booker.herokuapp.com")
                .header("Content-Type", "application/json")
                .body(createBody)
                .when()
                .post("/booking");

        Assert.assertEquals(createResponse.getStatusCode(), 200);

        int bookingId = createResponse
                .jsonPath()
                .getInt("bookingid");

        System.out.println("Booking ID cần update: " + bookingId);

        String updateBody = """
            {
              "firstname": "Lan Updated",
              "lastname": "Pham",
              "totalprice": 500,
              "depositpaid": true,
              "bookingdates": {
                "checkin": "2026-10-02",
                "checkout": "2026-10-07"
              },
              "additionalneeds": "Dinner"
            }
            """;

        Response updateResponse = RestAssured
                .given()
                .baseUri("https://restful-booker.herokuapp.com")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .auth()
                .preemptive()
                .basic("admin", "password123")
                .body(updateBody)
                .when()
                .put("/booking/" + bookingId);

        System.out.println(updateResponse.asPrettyString());

        Assert.assertEquals(updateResponse.getStatusCode(), 200);
        Assert.assertEquals(
                updateResponse.jsonPath().getString("firstname"),
                "Lan Updated"
        );
        Assert.assertEquals(
                updateResponse.jsonPath().getInt("totalprice"),
                500
        );
    }
    @Test
    public void deleteBooking() {

        String createBody = """
            {
              "firstname": "Hoa",
              "lastname": "Le",
              "totalprice": 250,
              "depositpaid": true,
              "bookingdates": {
                "checkin": "2026-11-01",
                "checkout": "2026-11-03"
              },
              "additionalneeds": "Breakfast"
            }
            """;

        Response createResponse = RestAssured
                .given()
                .baseUri("https://restful-booker.herokuapp.com")
                .header("Content-Type", "application/json")
                .body(createBody)
                .when()
                .post("/booking");

        Assert.assertEquals(createResponse.getStatusCode(), 200);

        int bookingId = createResponse
                .jsonPath()
                .getInt("bookingid");

        System.out.println("Booking ID cần xóa: " + bookingId);

        Response deleteResponse = RestAssured
                .given()
                .baseUri("https://restful-booker.herokuapp.com")
                .auth()
                .preemptive()
                .basic("admin", "password123")
                .when()
                .delete("/booking/" + bookingId);

        Assert.assertEquals(deleteResponse.getStatusCode(), 201);

        Response getAfterDelete = RestAssured
                .given()
                .baseUri("https://restful-booker.herokuapp.com")
                .when()
                .get("/booking/" + bookingId);

        System.out.println("Status sau khi xóa: "
                + getAfterDelete.getStatusCode());

        Assert.assertEquals(
                getAfterDelete.getStatusCode(),
                404
        );
    }
}