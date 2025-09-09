import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CourierCreateRequest;
import model.CourierLoginRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static constants.TestData.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.CourierSteps.*;

public class CourierLoginTest extends BaseAPITest {

    private boolean shouldCleanUp = false;

    @Before
    public void createCcourier() {
        CourierCreateRequest courier = new CourierCreateRequest(login, password, firstname);
        createCourier(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверяем, что курьер может авторизоваться с набором валидных данных")

    public void loginCourierSuccess() {

        CourierLoginRequest courierLoginRequest = new CourierLoginRequest(login, password);
        loginCourier(courierLoginRequest)
                .then()
                .statusCode(200)
                .body("id", notNullValue());

        shouldCleanUp = true;
    }
    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверяем, что при попытке авторизоваться без поля login, тест может провалиться")
    public void LoginCourierWithoutLogin() {

        CourierLoginRequest courierNoLogin = new CourierLoginRequest(null, password);
        loginCourier(courierNoLogin)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

        shouldCleanUp = true;
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверяем, что при попытке авторизоваться без поля password, тест может провалиться")
    public void LoginCourierWithoutPassword() {

        CourierLoginRequest courierNoPassword = new CourierLoginRequest(login, null);
        loginCourier(courierNoPassword)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

        shouldCleanUp = true;
    }

    @Test
    @DisplayName("Авторизация курьера с невалидным login")
    @Description("Проверяем, что при попытке авторизоваться с невалидными данныими, тест может провалиться")
    public void LoginCourierWithInvalidLogin() {

        CourierLoginRequest courierLoginRequest = new CourierLoginRequest(WRONGLOGIN, password);
        loginCourier(courierLoginRequest)
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

        shouldCleanUp = true;
    }

    @Test
    @DisplayName("Авторизация курьера с невалидным password")
    @Description("Проверяем, что при попытке авторизоваться с невалидными данныими, тест может провалиться")
    public void LoginCourierWithInvalidPassword() {

        CourierLoginRequest courierLoginRequest = new CourierLoginRequest(login, WRONGPASSWORD);
        loginCourier(courierLoginRequest)
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

        shouldCleanUp = true;
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
                shouldCleanUp = false; // Сбрасываем флаг
            }
        }
    }

}
