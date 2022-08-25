import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sprint.Order;
import org.sprint.OrderAccepted;
import org.sprint.OrderClient;
import org.sprint.OrderGenerator;

import java.util.List;
import java.util.Objects;

import static org.apache.http.HttpStatus.SC_OK;

public class TestGetListOrder {
    private Order order;
    private OrderClient orderClient;
    private int trackCode;

    @Before
    public void setUp() {
        order = OrderGenerator.getDefault();
        orderClient = new OrderClient();
    }

    @After
    public void tearDown() {
        orderClient.cancelOrder(new OrderAccepted(trackCode));
    }

    @Test
    @DisplayName("Checking get list of orders  GET /api/v1/orders")
    @Description("Test checks getting  list orders: status code '200', body of response contains list of orders")
    public void testCheckGetListOrders() {
        ValidatableResponse responseCreateOrder = orderClient.createOrder(order);
        trackCode = responseCreateOrder.extract().path("track");

        ValidatableResponse response = orderClient.getListOrders();
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Request not accepted", SC_OK, statusCode);

        List<Objects> orders = response.extract().path("orders");
        Assert.assertNotNull("Orders  list is empty", orders);
    }
}
