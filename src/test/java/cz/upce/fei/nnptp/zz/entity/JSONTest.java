/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.upce.fei.nnptp.zz.entity;

import org.junit.jupiter.api.*;

import java.io.File;
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

        passwords.add(new Password(0, "test1", new HashMap<String, Parameter>(){{
            put("StringParam", new Parameter.TextParameter("Textovy parametr"));
            put("PassParam", new Parameter.PasswordParameter("Moje tajne heslo"));
        }}));
        passwords.add(new Password(1, "test2"));
        String contents = new JSON().toJson(passwords);

        String expResult = "[{id:0,password:\"test1\",parameters: {\"StringParam\":{type: \"TextParameter\", value: \"Textovy parametr\"},\"PassParam\":{type: \"PasswordParameter\", value: \"Moje tajne heslo\"},}},{id:1,password:\"test2\",parameters: {}}]";

        assertEquals(expResult, contents);
    }
}
