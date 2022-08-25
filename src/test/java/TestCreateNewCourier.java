import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sprint.Courier;
import org.sprint.*;

import static org.apache.http.HttpStatus.*;
import static org.sprint.TextOfMessagesOnRequest.*;

public class TestCreateNewCourier {
    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courier = CourierGenerator.getDeffault();
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Checking create courier  /api/v1/courier")
    @Description("The test checks the creation of a courier: accepted code '201'," +
            " the body of the response when the courier is successfully created 'ok', " +
            "the ability to login in to verify the creation of the client")
    public void testCreateCourier() {
        ValidatableResponse response = courierClient.createCourier(courier);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Status code is incorrect", SC_CREATED, statusCode);

        boolean isCreated = response.extract().path("ok");
        Assert.assertTrue("Courier is not created", isCreated);

        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
        Assert.assertNotEquals("Such courier not exists", 0, courierId);
    }

    @Test
    @DisplayName("Checking the impossibility of creating two identical couriers")
    @Description("The test checks that it is impossible to create two couriers with the same login: " +
            "status code '409', message of response: 'Этот логин уже используется' ")
    public void testCheckCreateSameCourier() {
        courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");

        ValidatableResponse responseSameCourier = courierClient.createCourier(courier);
        int statusCode = responseSameCourier.extract().statusCode();
        Assert.assertEquals("The same courier is created", SC_CONFLICT, statusCode);

        String messageResponse = responseSameCourier.extract().path("message");
        Assert.assertEquals(MESS_REG_SAME_LOGIN, messageResponse);
    }

    @Test
    @DisplayName("Checking get error on attempt to create courier without  login or password")
    @Description("The test check that if you try to create a courier without a login or password, " +
            "an error with the status 400 and the body of the response will come in response:'Недостаточно данных для создания учетной записи'")
    public void testCheckGetErrorCreateCourierWithoutLoginOrPassword() {
        ValidatableResponse responseWithoutLogin = courierClient.createCourier(CourierGenerator.getWithoutLogin());
        ValidatableResponse responseWithoutPassword = courierClient.createCourier(CourierGenerator.getWithoutPassword());

        int statusCodeWithoutLogin = responseWithoutLogin.extract().statusCode();
        Assert.assertEquals("Status code for request without login incorrect", SC_BAD_REQUEST, statusCodeWithoutLogin);

        String messageResponseWithoutLogin = responseWithoutLogin.extract().path("message");
        Assert.assertEquals(MESS_REG_WITHOUT_LOGIN_PASSWORD, messageResponseWithoutLogin);

        int statusCodeWithoutPassword = responseWithoutPassword.extract().statusCode();
        Assert.assertEquals("Status code for request without passward incorrect", SC_BAD_REQUEST, statusCodeWithoutPassword);

        String messageResponseWithoutPassword = responseWithoutLogin.extract().path("message");
        Assert.assertEquals(MESS_REG_WITHOUT_LOGIN_PASSWORD, messageResponseWithoutPassword);
    }
}



