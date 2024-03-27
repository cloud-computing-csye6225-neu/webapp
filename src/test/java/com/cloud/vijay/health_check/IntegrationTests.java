package com.cloud.vijay.health_check;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesRegex;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTests {

    private static final String UUID_REGEX = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$";
    private static final String userName = System.getenv("USER_NAME");
    private static final String password = System.getenv("PASSWORD");
    private static final String firstName = System.getenv("FIRST_NAME");
    private static final String lastName = System.getenv("LAST_NAME");
    private static final String updatedFirstName = System.getenv("UPDATED_FIRST_NAME");
    private static final String updatedLastName = System.getenv("UPDATED_LAST_NAME");
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
                        "    \"first_name\": \"" + firstName + "\",\n" +
                        "    \"last_name\": \"" + lastName + "\",\n" +
                        "    \"password\": \"" + password + "\",\n" +
                        "    \"username\": \"" + userName + "\"\n" +
                        "}")
                .when()
                .post("/v1/user")
                .then()
                .statusCode(201);

        // Retrieve the user and validate existence
        given()
                .auth().preemptive().basic(userName, password)
                .header("IsIntegrationTest", "true")
                .when()
                .get("/v1/user/self")
                .then()
                .statusCode(200)
                .body("id", matchesRegex(UUID_REGEX))
                .body("first_name", equalTo(firstName))
                .body("last_name", equalTo(lastName))
                .body("username", equalTo(userName));
    }

    @Test
    @Order(2)
    public void testUpdateUser() {
        // Update the account
        given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic(userName, password)
                .header("IsIntegrationTest", "true")
                .body("{\n" +
                        "    \"first_name\": \"" + updatedFirstName + "\",\n" +
                        "    \"last_name\": \"" + updatedLastName + "\"\n" +
                        "}")
                .when()
                .put("/v1/user/self")
                .then()
                .statusCode(204);

        // Retrieve the user and validate the update
        given()
                .when()
                .auth().preemptive().basic(userName, password)
                .header("IsIntegrationTest", "true")
                .get("/v1/user/self")
                .then()
                .statusCode(200)
                .body("id", matchesRegex(UUID_REGEX))
                .body("first_name", equalTo(updatedFirstName))
                .body("last_name", equalTo(updatedLastName))
                .body("username", equalTo(userName));
    }
}
