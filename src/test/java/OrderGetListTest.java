import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import org.junit.Test;

import static constants.TestData.ORDER_GET_LIST;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderGetListTest extends BaseAPITest {

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Получение списка заказов, проверка наличия списка")
    public void orderGetList() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .get(ORDER_GET_LIST)
                .then()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
