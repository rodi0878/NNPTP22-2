package cz.upce.fei.nnptp.zz.entity;

import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Roman
 */
public class JSONTest {

    public JSONTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of toJson method, of class JSON.
     */
    @Test
    public void testFromJson() {

        LinkedList<Password> expectedResult = new LinkedList<>();
        HashMap<Parameter.StandardizedParameter, Parameter> parameters = new HashMap<>();

        parameters.put(
                Parameter.StandardizedParameter.TITLE,
                Parameter.getParameter(Parameter.StandardizedParameter.TITLE, "Bc.")
        );
        parameters.put(
                Parameter.StandardizedParameter.WEBSITE,
                Parameter.getParameter(Parameter.StandardizedParameter.TITLE, "google.com")
        );

        Password password1 = new Password(100, "pass", parameters);
        Password password2 = new Password(101, "pass", parameters);

        expectedResult.add(password1);
        expectedResult.add(password2);

        String json = "[{\"id\":100,\"password\":\"pass\",\"parameters\":[{\"type\":\"title\",\"value\":\"Bc.\"},{\"type\":\"website\",\"value\":\"google.com\"}]},{\"id\":101,\"password\":\"pass\",\"parameters\":[{\"type\":\"title\",\"value\":\"Bc.\"},{\"type\":\"website\",\"value\":\"google.com\"}]}]";
        List<Password> actualResult = JSON.fromJson(json);

        String expectedResultString = "";
        String actualResultString = "";

        for (Password password : expectedResult) {
            expectedResultString += password.toString();
        }

        for (Password password : actualResult) {
            actualResultString += password.toString();
        }

        System.out.println(expectedResultString);
        System.out.println(actualResultString);

        assertEquals(expectedResultString, actualResultString);
    }
}
