package com.isaias.framework;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    protected static final String BASE_URI = "https://restful-booker.herokuapp.com";
    protected static RequestSpecification reqSpec;

    @BeforeAll
    static void globalSetup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("User-Agent", "RestAssured")
                .addHeader("Accept", "application/json")
                .build();
    }
}