package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderCreateRequest;

import static constants.TestData.*;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Создание нового заказа")
    public static Response orderCreate(OrderCreateRequest orderCreateRequest) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(orderCreateRequest)
                .post(ORDER_POST_CREATE)
                .then()
                .extract().response();
    }

    @Step("Отмена заказа")
    public static Response orderCancel(int track) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\"track\": \"" + track + "\"}")
                .put(ORDER_CANCEL)
                .then()
                .extract().response();
    }

    @Step("Получение списка заказов")
    public static Response orderList() {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .get(ORDER_GET_LIST)
                .then()
                .extract().response();

    }
}


