package ua.training.model.dao.utility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class PasswordEncoderTest {
    private String password;
    private String returnPassword;

    @Before
    public void setUp() {
        password = "qwerty";
        returnPassword = "d8578edf8458ce06fbc5bb76a58c5ca4";
    }

    @After
    public void tearDown() {
        password = null;
        returnPassword = null;
    }

    @Test
    public void encodePassword() {
        password = PasswordEncoder.encodePassword(password);

        assertEquals(returnPassword, password);
    }
}