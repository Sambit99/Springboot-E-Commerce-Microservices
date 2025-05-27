package com.app.microservices.inventory;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InventoryServiceTests {

    @ServiceConnection
    static MySQLContainer mySQLContainer = new MySQLContainer(DockerImageName.parse("mysql:8.3.0"));

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup(){
        RestAssured.baseURI="http://localhost";
        RestAssured.port=port;
    }

    static {
        mySQLContainer.start();
    }

    @Test
    void shouldReadInventory(){
        var response = RestAssured.given()
                .when()
                .get("/api/v1/inventory?skuCode=IPhone 15&quantity=10")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response().as(Boolean.class);

        assertTrue(response);

        var negativeResponse = RestAssured.given()
                .when()
                .get("/api/v1/inventory?skuCode=IPhone 15&quantity=101")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response().as(Boolean.class);

        assertFalse(negativeResponse);
    }
}
