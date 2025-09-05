package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderCreateRequest;

import static constants.TestData.ORDER_POST_CREATE;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Создание нового заказа")
    public static Response orderCreate(OrderCreateRequest orderCreateRequest) {
        return   given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(orderCreateRequest)
                .post(ORDER_POST_CREATE)
                .then()
                .extract().response();
    }
}
