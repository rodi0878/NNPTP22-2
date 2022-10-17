/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.upce.fei.nnptp.zz.entity;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
    public void testToJson() {
        List<Password> passwords = new ArrayList<>();

        passwords.add(new Password(0, "test1",
                new HashMap<>(){
                    {
                        put("key1", new Parameter.TextParameter("val1"));
                        put("key2", new Parameter.PasswordParameter("val2"));
                        put("key3", new Parameter.DateTimeParameter(LocalDateTime.parse("2022-10-17T11:56:36.174509900")));
                    }
                }
                ));
        passwords.add(new Password(1, "test2"));
        passwords.add(new Password(2, "test3", new HashMap<>()));
        passwords.add(new Password(3, "test4", new HashMap<>(){
            {
                put("key1", new Parameter.TextParameter("val1"));
            }
        }));
        String contents = new JSON().toJson(passwords);
        System.out.println(contents);
        String expResult = "[\n" +
                "\t{\n" +
                "\t\t\"id\" : 0,\n" +
                "\t\t\"password\" : \"test1\",\n" +
                "\t\t\"parameters\" : {\n" +
                "\t\t\t\"key1\" : {\n" +
                "\t\t\t\t\"type\" : \"TextParameter\",\n" +
                "\t\t\t\t\"value\" : \"val1\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"key2\" : {\n" +
                "\t\t\t\t\"type\" : \"PasswordParameter\",\n" +
                "\t\t\t\t\"value\" : \"val2\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"key3\" : {\n" +
                "\t\t\t\t\"type\" : \"DateTimeParameter\",\n" +
                "\t\t\t\t\"value\" : \"2022-10-17T11:56:36.174509900\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\" : 1,\n" +
                "\t\t\"password\" : \"test2\",\n" +
                "\t\t\"parameters\" : {\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\" : 2,\n" +
                "\t\t\"password\" : \"test3\",\n" +
                "\t\t\"parameters\" : {\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\" : 3,\n" +
                "\t\t\"password\" : \"test4\",\n" +
                "\t\t\"parameters\" : {\n" +
                "\t\t\t\"key1\" : {\n" +
                "\t\t\t\t\"type\" : \"TextParameter\",\n" +
                "\t\t\t\t\"value\" : \"val1\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}\n" +
                "]";
        assertEquals(expResult, contents);
    }
}
