package steps;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static constants.TestData.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import model.CourierCreateRequest;
import model.CourierLoginRequest;

public class CourierSteps {




    @Step("Создание нового курьера")
    public static Response createCourier(CourierCreateRequest courierCreateRequest) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierCreateRequest)
                .when()
                .post(COURIER_POST_CREATE)
                .then()
                .extract().response();
    }

    @Step("Авторизация курьера в системе")
    public static Response loginCourier(CourierLoginRequest courierLoginRequest) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierLoginRequest)
                .when()
                .post(COURIER_POST_LOGIN)
                .then()
                .extract().response();
    }
    @Step("Получение ID для удаления курьера")
    public static String getCourierId(CourierLoginRequest courierLoginRequest) {
        return loginCourier(courierLoginRequest)
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .extract()
                .jsonPath()
                .getString("id");
    }


    @Step("Удаление курьера по ID")
    public static Response deleteCourier(String courierId) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .delete(COURIER_DELETE, courierId)
                .then()
                .log().all()
                .extract().response();
    }

}

