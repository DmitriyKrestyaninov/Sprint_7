import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.sprint.*;

import static org.apache.http.HttpStatus.SC_CREATED;

@RunWith(Parameterized.class)
public class TestCreateOrder {
    private final int expectedCode;
    private final Order order;

    private OrderClient orderClient;
    private int trackCode;


    public TestCreateOrder(int expectedCode, Order order) {
        this.expectedCode = expectedCode;
        this.order = order;
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @After
    public void tearDown() {
        orderClient.cancelOrder(new OrderAccepted(trackCode));
    }

    @Parameterized.Parameters(name = "{index} acceptedCodeCreateOrderWithDifferentColors{0}={1}")
    public static Object[][] getDataForCreateOrders() {
        return new Object[][]{
                {SC_CREATED, OrderGenerator.getDefault()},
                {SC_CREATED, OrderGenerator.getBlackColor()},
                {SC_CREATED, OrderGenerator.getGreyColor()},
                {SC_CREATED, OrderGenerator.getBothColors()}
        };
    }

    @Test
    @DisplayName("Checking create order /api/v1/orders")
    @Description("Test checks to ability  create order of scooter with different variation of colors:" +
            "1 - without colors; 2 - with black color; 3 - with grey color; 4 - with both colors")
    public void testCheckCreateOrder() {
        ValidatableResponse response = orderClient.createOrder(order);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Status code is incorrect", expectedCode, statusCode);

        trackCode = response.extract().path("track");
        Assert.assertNotNull("Order not contains track", trackCode);
    }

}


