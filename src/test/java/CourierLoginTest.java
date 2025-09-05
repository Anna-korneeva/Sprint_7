import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import model.CourierCreateRequest;
import model.CourierLoginRequest;
import model.CourierLoginResponse;
import org.apache.http.params.CoreConnectionPNames;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import steps.CourierSteps;

import static constants.TestData.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.CourierSteps.*;

public class CourierLoginTest extends BaseAPITest {

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверяем, что курьер может авторизоваться с набором валидных данных")

    public void loginCourierSuccess() {
        CourierCreateRequest courier = new CourierCreateRequest(login, password, firstname);
        createCourier(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        CourierLoginRequest courierLoginRequest = new CourierLoginRequest(login, password);
        loginCourier(courierLoginRequest)
                .then()
                .statusCode(200)
                .body("id", notNullValue());

        String courierId = CourierSteps.getCourierId(courierLoginRequest);
        System.out.println("ID курьера: " + courierId);
        CourierLoginResponse loginResponse = new CourierLoginResponse(courierId);


        courierId = loginCourier(courierLoginRequest)
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .extract()
                .jsonPath()
                .getString("id");
        deleteCourier(courierId)
                .then()
                .statusCode(200)
                .body("ok", equalTo(true));
    }
    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверяем, что при попытке авторизоваться без поля login, тест может провалиться")
    public void LoginCourierWithoutLogin() {

        CourierCreateRequest courier = new CourierCreateRequest(login, password, firstname);
        createCourier(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        CourierLoginRequest courierNoLogin = new CourierLoginRequest(null, password);
        loginCourier(courierNoLogin)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

        CourierLoginRequest courierLoginRequest = new CourierLoginRequest(login, password);
        loginCourier(courierLoginRequest)
                .then()
                .statusCode(200)
                .body("id", notNullValue());

        String courierId = CourierSteps.getCourierId(courierLoginRequest);
        System.out.println("ID курьера: " + courierId);
        CourierLoginResponse loginResponse = new CourierLoginResponse(courierId);


        courierId = loginCourier(courierLoginRequest)
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .extract()
                .jsonPath()
                .getString("id");
        deleteCourier(courierId)
                .then()
                .statusCode(200)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверяем, что при попытке авторизоваться без поля password, тест может провалиться")
    public void LoginCourierWithoutPassword() {

        CourierCreateRequest courier = new CourierCreateRequest(login, password, firstname);
        createCourier(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        CourierLoginRequest courierNoPassword = new CourierLoginRequest(login, null);
        loginCourier(courierNoPassword)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

        CourierLoginRequest courierLoginRequest = new CourierLoginRequest(login, password);
        loginCourier(courierLoginRequest)
                .then()
                .statusCode(200)
                .body("id", notNullValue());

        String courierId = CourierSteps.getCourierId(courierLoginRequest);
        System.out.println("ID курьера: " + courierId);
        CourierLoginResponse loginResponse = new CourierLoginResponse(courierId);


        courierId = loginCourier(courierLoginRequest)
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .extract()
                .jsonPath()
                .getString("id");
        deleteCourier(courierId)
                .then()
                .statusCode(200)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Авторизация курьера с невалидными данными")
    @Description("Проверяем, что при попытке авторизоваться с невалидными данныими, тест может провалиться")
    public void LoginCourierWithInvalidData() {

        CourierLoginRequest courierLoginRequest = new CourierLoginRequest(WRONGLOGIN, WRONGPASSWORD);
        loginCourier(courierLoginRequest)
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

}
