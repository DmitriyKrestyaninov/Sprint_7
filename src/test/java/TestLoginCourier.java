import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sprint.Courier;
import org.sprint.CourierClient;
import org.sprint.CourierCredentials;
import org.sprint.CourierGenerator;

import static org.apache.http.HttpStatus.*;
import static org.sprint.TextOfMessagesOnRequest.*;

public class TestLoginCourier {

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
    @DisplayName("Checking login courier /api/v1/courier/login")
    @Description("Test checks ability to login courier: status  code  '200', body of response contains courier_id")
    public void testCheckLoginCourier() {
        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));
        int statusCode = loginResponse.extract().statusCode();
        Assert.assertEquals("Courier not login", SC_OK, statusCode);

        courierId = loginResponse.extract().path("id");
        Assert.assertNotEquals("Such courier not exists", 0, courierId);
    }

    @Test
    @DisplayName("Checking get error with not valid login")
    @Description("The test checks getting error an attempt log in with a non-existent login:" +
            "status code 404, message of response : 'Учетная запись не найдена'")
    public void testCheckGetErrorNotValidLogin() {
        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials
                .from(CourierGenerator.getNotExistsLogin()));
        int statusCode = loginResponse.extract().statusCode();
        Assert.assertEquals("Courier with incorrect login accepted", SC_NOT_FOUND, statusCode);

        String messageResponse = loginResponse.extract().path("message");
        Assert.assertEquals("Message on request with non-existent login not correct",
                MESS_LOG_WITH_INCORRECT_LOGIN, messageResponse);
    }

    @Test
    @DisplayName("Checking get error to login without login or password")
    @Description("The test checks getting error an attempt to log  without a username or password: status code '400'" +
            "message of response: 'Недостаточно данных для входа'")
    public void testCheckGetErrorWithoutLoginOrPassword() {
        ValidatableResponse responseWithoutLogin = courierClient.loginCourier(CourierCredentials
                .from(CourierGenerator.getWithoutLogin()));
        ValidatableResponse responseWithoutPassword = courierClient.loginCourier(CourierCredentials
                .from(CourierGenerator.getWithoutLogin()));

        int statusCodeWithoutLogin = responseWithoutLogin.extract().statusCode();
        Assert.assertEquals("Status code for login without login_name incorrect", SC_BAD_REQUEST, statusCodeWithoutLogin);

        String messageResponseWithoutLogin = responseWithoutLogin.extract().path("message");
        Assert.assertEquals(MESS_LOG_WITHOUT_LOGIN_PASSWORD, messageResponseWithoutLogin);

        int statusCodeWithoutPassword = responseWithoutPassword.extract().statusCode();
        Assert.assertEquals("Status code for login without password incorrect", SC_BAD_REQUEST, statusCodeWithoutPassword);

        String messageResponseWithoutPassword = responseWithoutLogin.extract().path("message");
        Assert.assertEquals(MESS_LOG_WITHOUT_LOGIN_PASSWORD, messageResponseWithoutPassword);
    }
}
