package com.cloud.vijay.health_check;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesRegex;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {

    private static final String UUID_REGEX = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$";

    @LocalServerPort
    private int serverPort;
    @PostConstruct
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = serverPort;
    }

    @Test
    @Order(1)
    public void testCreateAndRetrieveUser() {
        System.out.println("Test 1");
        // Create an account
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"first_name\": \"test\",\n" +
                        "    \"last_name\": \"test\",\n" +
                        "    \"password\":\"password\",\n" +
                        "    \"username\":\"test@gmail.com\"\n" +
                        "}")
                .when()
                .post("/v1/user")
                .then()
                .statusCode(201);

        // Retrieve the user and validate existence
        given()
                .auth().preemptive().basic("test@gmail.com", "password")
                .when()
                .get("/v1/user/self")
                .then()
                .statusCode(200)
                .body("id", matchesRegex(UUID_REGEX))
                .body("first_name", equalTo("test"))
                .body("last_name", equalTo("test"))
                .body("username", equalTo("test@gmail.com"));
    }

    @Test
    @Order(2)
    public void testUpdateUser() {
        // Update the account
        given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("test@gmail.com", "password")
                .body("{\n" +
                        "    \"first_name\": \"test_updated\",\n" +
                        "    \"last_name\": \"test_updated\",\n" +
                        "    \"password\":\"password\"\n" +
                        "}")
                .when()
                .put("/v1/user/self")
                .then()
                .statusCode(204);

        // Retrieve the user and validate the update
        given()
                .when()
                .auth().preemptive().basic("test@gmail.com", "password")
                .get("/v1/user/self")
                .then()
                .statusCode(200)
                .body("id", matchesRegex(UUID_REGEX))
                .body("first_name", equalTo("test_updated"))
                .body("last_name", equalTo("test_updated"))
                .body("username", equalTo("test@gmail.com"));
    }
}
