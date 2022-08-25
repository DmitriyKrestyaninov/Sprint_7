package org.sprint;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {
    private static final String COURIER_PATH = "/api/v1/courier";
    private static final String LOGIN_PATH = "/api/v1/courier/login";

    @Step("Create new courier {courier}")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    public CourierClient() {
    }

    @Step("Login courier {credentials}")
    public ValidatableResponse loginCourier(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(LOGIN_PATH)
                .then();
    }

    public ValidatableResponse deleteCourier(int id) {
        return given()
                .spec(getBaseSpec())
                .param("id", Integer.toString(id))
                .when()
                .delete(COURIER_PATH)
                .then();
    }
}

