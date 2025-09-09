import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

import io.restassured.response.Response;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.OrderSteps.orderList;

public class OrderGetListTest extends BaseAPITest {

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Получение списка заказов, проверка наличия списка")
    public void orderGetList() {
        Response response = orderList();
        response.then()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
