package constants;

import org.apache.commons.lang3.RandomStringUtils;

public class TestData {
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";


    public static final String WRONGLOGIN = "koshechka" + System.currentTimeMillis();
    public static final String WRONGPASSWORD = "1234566";


    public static final String COURIER_POST_CREATE = "/api/v1/courier"; // Создание курьера
    public static final String COURIER_POST_LOGIN = "/api/v1/courier/login/"; // Авторизация курьера
    public static final String COURIER_DELETE = "/api/v1/courier/{id}"; // Удаление курьера
    public static final String ORDER_POST_CREATE = "/api/v1/orders"; // Создание заказа
    public static String ORDER_GET_LIST = "/api/v1/orders"; // Получение списка заказов
}

