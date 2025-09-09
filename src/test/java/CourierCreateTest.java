import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CourierCreateRequest;
import model.CourierLoginRequest;
import org.junit.After;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.CourierSteps.*;

public class CourierCreateTest extends BaseAPITest {

    private boolean shouldCleanUp = false;

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Проверяем, что курьера можно создать с валидными данными")
    public void createNewCourier() {

        CourierCreateRequest courier = new CourierCreateRequest(login, password, firstname);
        createCourier(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        shouldCleanUp = true;

        }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверяем, что при создании дубликата курьера, тест может провалиться")
    public void createDuplicateCourier() {

        CourierCreateRequest courier = new CourierCreateRequest(login, password, firstname);

        createCourier(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        createCourier(courier)
                .then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        shouldCleanUp = true;
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверяем, что при попытке создать курьера без поля login, тест может провалиться")
    public void createCourierWithoutLogin() {

        CourierCreateRequest courier = new CourierCreateRequest(null, password, firstname);
        createCourier(courier)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверяем, что при попытке создать курьера без поля password, тест может провалиться")
    public void createCourierWithoutPassword() {

        CourierCreateRequest courier = new CourierCreateRequest(login, null, firstname);
        createCourier(courier)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @After
    public void cleanUp() {
        if (shouldCleanUp) {
            try {
                // Пытаемся получить ID курьера для удаления
                CourierLoginRequest courierLoginRequest = new CourierLoginRequest(login, password);
                String courierId = loginCourier(courierLoginRequest)
                        .then()
                        .statusCode(200)
                        .body("id", notNullValue())
                        .extract()
                        .jsonPath()
                        .getString("id");

                System.out.println("ID курьера для удаления: " + courierId);

                // Удаляем курьера
                deleteCourier(courierId)
                        .then()
                        .statusCode(200)
                        .body("ok", equalTo(true));

            } catch (Exception e) {
                System.out.println("Курьер не был создан или уже удален: " + login);
                // Игнорируем исключение, так как это ожидаемо
            } finally {
                shouldCleanUp = false;
            }
        }
    }
}
