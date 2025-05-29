package com.app.microservices.order;

import com.app.microservices.order.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
public class OrderServiceTests {

    @ServiceConnection
    static MySQLContainer mySQLContainer = new MySQLContainer(DockerImageName.parse("mysql:8.3.0"));

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        mySQLContainer.start();
    }

    @Test
    void shouldCreateOrder(){
        String requestBody = """
                {
                    "skuCode":"IPhone_16",
                    "price":120000,
                    "quantity":1
                }
                """;

        InventoryClientStub.stubInventoryCall("IPhone_16",1);

        var responseBodyString = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/order")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .body().asString();

        assertEquals(responseBodyString,"Order placed successfully");
    }
}
