/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.upce.fei.nnptp.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Objects;

/**
 *
 * @author Roman
 */
public class PasswordTest {

    Password password;

    public PasswordTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        password = new Password(1, "heslo1234", new HashMap<>() {
            {
                put("parameter1", new Parameter.TextParameter("value1"));
                put("parameter2", new Parameter.TextParameter("value2"));
            }
        });
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetId() {
        assertTrue(password.getId() == 1);
    }

    @Test
    public void testGetPassword() {
        assertTrue(password.getPassword().equals("heslo1234"));
    }

    @Test
    public void testGetParameter() {
        Parameter parameter1 = new Parameter.TextParameter("value1");
        Parameter parameter2 = new Parameter.TextParameter("value3");
        assertTrue(password.getParameter("parameter1").toString().equals(parameter1.toString()));
        assertFalse(password.getParameter("parameter2").toString().equals(parameter2.toString()));
    }

    @Test
    public void testGetParameters() {
        HashMap<String, Parameter> map1 = new HashMap<>() {
            {
                put("parameter1", new Parameter.TextParameter("value1"));
                put("parameter2", new Parameter.TextParameter("value2"));
            }
        };
        HashMap<String, Parameter> map2 = new HashMap<>() {
            {
                put("parameter1", new Parameter.TextParameter("value1"));
                put("parameter3", new Parameter.TextParameter("value2"));
            }
        };
        assertTrue(password.getParameters().equals(map1));
        assertFalse(password.getParameters().equals(map2));
    }

    @Test
    public void testEquals() {
        Password password1 = new Password(1, "heslo1234", new HashMap<>() {
            {
                put("parameter1", new Parameter.TextParameter("value1"));
                put("parameter2", new Parameter.TextParameter("value2"));
            }
        });
        Password password2 = new Password(1, "heslo123", new HashMap<>() {
            {
                put("parameter1", new Parameter.TextParameter("value1"));
                put("parameter2", new Parameter.TextParameter("value2"));
            }
        });
        Password password3 = new Password(1, "heslo1234", new HashMap<>() {
            {
                put("parameter1", new Parameter.TextParameter("value1"));
                put("parameter2", new Parameter.TextParameter("value3"));
            }
        });
        Password password4 = password;
        assertTrue(password.equals(password1));
        assertFalse(password.equals(password2));
        assertFalse(password.equals(password3));
        assertTrue(password.equals(password4));
    }

    @Test
    public void testHasParameter() {
        assertTrue(password.hasParameter("parameter1"));
        assertFalse(password.hasParameter("parameter3"));
    }

    @Test
    public void testHashCode() {
        int expectedCode = Objects.hash(password.getId(), password.getPassword(), password.getParameters().hashCode());
        assertTrue(password.hashCode() == expectedCode);
    }

    @Test
    public void testToString() {
        String expectedString = "Password{id=1, password=heslo1234, parameters=Parameter{key=parameter2, value=value2, type=TEXT},Parameter{key=parameter1, value=value1, type=TEXT}}";
        assertTrue(password.toString().equals(expectedString));
    }
}
