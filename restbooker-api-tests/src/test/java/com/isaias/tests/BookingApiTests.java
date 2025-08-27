package com.isaias.tests;

import com.isaias.framework.AuthClient;
import com.isaias.framework.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class BookingApiTests extends BaseTest {

    @Test
    void canCreateBooking_andReceiveBookingId() {   
        Map<String, Object> payload = new HashMap<>();
        payload.put("firstname", "Isaias");
        payload.put("lastname", "Iniguez");
        payload.put("totalprice", 123);
        payload.put("depositpaid", true);

        Map<String, String> dates = new HashMap<>();
        dates.put("checkin", "2025-01-01");
        dates.put("checkout", "2025-01-05");
        payload.put("bookingdates", dates);

        payload.put("additionalneeds", "Breakfast");

        Integer bookingId =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .header("User-Agent", "RestAssured")     
                        .header("Accept", "application/json")   
                        .body(payload)
                        .when()
                        .post("/booking")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("bookingid");

        assertThat(bookingId).isNotNull();
    }

    @Test
    void canListBookingIds() {
        List<Map<String, Object>> arr =
                given()
                        .when()
                        .get("/booking")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("$");

        assertThat(arr).isNotEmpty();
        assertThat(arr.get(0)).containsKey("bookingid");
    }

    @Test
    void canGetBookingById() {
        Integer bookingId =
                given()
                        .when()
                        .get("/booking")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("[0].bookingid");

        Map<String, Object> booking =
                given()
                        .when()
                        .get("/booking/{id}", bookingId)
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("$");

        assertThat(booking).containsKeys("firstname", "lastname", "bookingdates");
    }

    @Test
    void canUpdateExistingBooking_withToken() {
        // First create a booking to be sure it exists and is editable
        Map<String, Object> payload = new HashMap<>();
        payload.put("firstname", "Temp");
        payload.put("lastname", "User");
        payload.put("totalprice", 150);
        payload.put("depositpaid", true);
        Map<String, String> dates = new HashMap<>();
        dates.put("checkin", "2025-03-01");
        dates.put("checkout", "2025-03-10");
        payload.put("bookingdates", dates);
        payload.put("additionalneeds", "Dinner");

        Integer bookingId =
                given()
                        .body(payload)
                        .when()
                        .post("/booking")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("bookingid");

        String token = AuthClient.getToken();
        System.out.println("Token: " + token);

        Map<String, Object> update = new HashMap<>();
        update.put("firstname", "Updated");
        update.put("lastname", "Name");
        update.put("totalprice", 200);
        update.put("depositpaid", false);
        update.put("bookingdates", dates);
        update.put("additionalneeds", "Late checkout");

        given()
                .header("Cookie", "token=" + token)  
                .body(update)
                .when()
                .put("/booking/{id}", bookingId)
                .then()
                .statusCode(200);
    }

    @Test
    void canDeleteExistingBooking_withToken() {
        Integer bookingId =
                given().when().get("/booking").then().statusCode(200).extract().path("[0].bookingid");

        String token = AuthClient.getToken();

        given()
                .header("Cookie", "token=" + token)   
                .when()
                .delete("/booking/{id}", bookingId)
                .then()
                .statusCode(201);
    }
}
