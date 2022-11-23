package cz.upce.fei.nnptp.entity;

import org.junit.jupiter.api.*;
import java.io.File;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordDatabaseTest {

    static File file;
    static String password;
    PasswordDatabase passwordDatabase;
    
    public PasswordDatabaseTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        file = new File("passwordDatabaseTestOutput.txt");
        password = "testPassword";
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        passwordDatabase = new PasswordDatabase(file, password);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void SaveLoadTest() {
        passwordDatabase.add(new Password(0, "sdfghjkl", new HashMap<>(){{
            put(ParameterType.TITLE, new Parameter.TextParameter("firstPassword"));
        }}));
        passwordDatabase.add(new Password(1, "ASDSAFafasdasdasdas", new HashMap<>(){{
            put(ParameterType.TITLE, new Parameter.TextParameter("secondPassword"));
        }}));
        passwordDatabase.add(new Password(2, "aaa-aaaa-", new HashMap<>(){{
            put(ParameterType.TITLE, new Parameter.TextParameter("thirdPassword"));
        }}));

        passwordDatabase.save();
        passwordDatabase = new PasswordDatabase(file, password);
        passwordDatabase.load();

        assertEquals("sdfghjkl", passwordDatabase.findEntryByTitle("firstPassword").getPassword());
        assertEquals("ASDSAFafasdasdasdas", passwordDatabase.findEntryByTitle("secondPassword").getPassword());
        assertEquals("aaa-aaaa-", passwordDatabase.findEntryByTitle("thirdPassword").getPassword());
    }
}
