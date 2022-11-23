package cz.upce.fei.nnptp.entity;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Roman
 */
public class JSONTest {

    List<Password> passwords = new ArrayList<>();
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
        passwords.add(new Password(0, "test1",
                new HashMap<>(){
                    {
                        put(ParameterType.TITLE, new Parameter.TextParameter("val1"));
                        put(ParameterType.DESCRIPTION, new Parameter.PasswordParameter("val2"));
                        put(ParameterType.DATE, new Parameter.DateTimeParameter(LocalDateTime.parse("2022-10-17T11:56:36.174509900")));
                    }
                }
        ));
        passwords.add(new Password(1, "test2"));
        passwords.add(new Password(2, "test3", new HashMap<>()));
        passwords.add(new Password(3, "test4", new HashMap<>(){
            {
                put(ParameterType.TITLE, new Parameter.TextParameter("val1"));
            }
        }));
    }

    @AfterEach
    public void tearDown() {
        passwords.clear();
    }

    /**
     * Test of toJson method, of class JSON.
     */
    @Test
    public void testToJson() {
        List<Password> password = new ArrayList<>();
        password.add(new Password(0, "sdfghjkl"));
        password.add(new Password(1, "ASDSAFafasdasdasdas"));
        password.add(new Password(2, "aaa-aaaa-"));
        
        String contents = JSON.toJson(password);
        System.out.println(contents);
        String expResult = "[{\"id\": 0,\"password\": \"sdfghjkl\",\"parameters\": {}},{\"id\": 1,\"password\": \"ASDSAFafasdasdasdas\",\"parameters\": {}},{\"id\": 2,\"password\": \"aaa-aaaa-\",\"parameters\": {}}]";
        
        assertEquals(expResult, contents);
    }
    @Test
    public void testFromJson() {

        LinkedList<Password> expectedResult = new LinkedList<>();
        HashMap<ParameterType, Parameter> parameters = new HashMap<>();

        parameters.put(
                ParameterType.TITLE,
                Parameter.getParameter(ParameterType.TITLE, "Bc.")
        );
        parameters.put(
                ParameterType.WEBSITE,
                Parameter.getParameter(ParameterType.WEBSITE, "google.com")
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

    /**
     * Notice! Have to be solved by unified approach for JSON formatting and type handling
     */
    @Test
    public void testBidirectionalJsonConversion() {
        List<Password> expectedResult = JSON.fromJson(JSON.toJson(passwords));
        assertArrayEquals(expectedResult.toArray(), passwords.toArray());
    }
}
