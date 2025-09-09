import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;

import static constants.TestData.BASE_URI;

public class BaseAPITest {

    protected String login;
    protected String password;
    protected String firstname;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;

        login = RandomStringUtils.randomAlphanumeric(2, 15);
        password = RandomStringUtils.randomAlphanumeric(7, 15);
        firstname = RandomStringUtils.randomAlphabetic(2, 18);

    }
}

