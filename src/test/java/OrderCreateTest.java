import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import model.OrderCreateRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import steps.OrderSteps;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.OrderSteps.orderCreate;

@RunWith(Parameterized.class)
public class OrderCreateTest extends BaseAPITest {

    private List<String> color;

    public OrderCreateTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет самоката - {0}")
    public static Collection<Object[]> dataGen() {
        return Arrays.asList(new Object[][] {
                {Arrays.asList("BLACK", "GREY")},
                {Arrays.asList("BLACK")},
                {Arrays.asList("GREY")},
                {Arrays.asList()}
        });
    }
    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа с самокатами разных цветов через параметризованный тест")
    public void createOrderWithDifferentColors()  {

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(color);
        orderCreate(orderCreateRequest)
                .then()
                .statusCode(201)
                .body("track", notNullValue());

    }
}
