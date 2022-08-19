package org.sprint;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
    private static final String ORDER_PATH = "/api/v1/orders";
    private static final String CANCEL_ORDER_PATH = "/api/v1/orders/cancel";

    @Step("Create new order {order}")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Cancel create order {order}")
    public ValidatableResponse cancelOrder(OrderAccepted orderAccepted) {
        return given()
                .spec(getBaseSpec())
                .body(orderAccepted)
                .when()
                .put(CANCEL_ORDER_PATH)
                .then();
    }

    @Step("Get list order")
    public ValidatableResponse getListOrders() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }
}
