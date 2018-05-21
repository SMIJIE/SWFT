package ua.training.controller.commands.action.statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.constant.Attributes;
import ua.training.constant.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterNewUserTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    private String path;
    private String name;
    private String dob;
    private String emailWrong;
    private String emailAlreadyRegistered;
    private String password;
    private String height;
    private String weight;
    private String weightDesired;
    private String lifestyle;

    @Before
    public void setUp() {
        name = "Pavlo";
        dob = "2018-05-15";
        emailWrong = "Zakusylogmail";
        emailAlreadyRegistered = "Zakusylo@gmail";
        password = "qwerty";
        height = "180";
        weight = "90";
        weightDesired = "80";
        lifestyle = "1.7";
    }

    @After
    public void tearDown() {
        name = null;
        dob = null;
        emailWrong = null;
        emailAlreadyRegistered = null;
        password = null;
        height = null;
        weight = null;
        weightDesired = null;
        lifestyle = null;
        path = null;
    }

    @Test
    public void execute() {
        RegisterNewUser newUser = new RegisterNewUser();

        when(request.getParameter(Attributes.REQUEST_NAME)).thenReturn(name);
        when(request.getParameter(Attributes.REQUEST_DATE_OF_BIRTHDAY)).thenReturn(dob);
        when(request.getParameter(Attributes.REQUEST_EMAIL)).thenReturn(emailWrong);
        when(request.getParameter(Attributes.REQUEST_PASSWORD)).thenReturn(password);
        when(request.getParameter(Attributes.REQUEST_HEIGHT)).thenReturn(height);
        when(request.getParameter(Attributes.REQUEST_WEIGHT)).thenReturn(weight);
        when(request.getParameter(Attributes.REQUEST_WEIGHT_DESIRED)).thenReturn(weightDesired);
        when(request.getParameter(Attributes.REQUEST_LIFESTYLE)).thenReturn(lifestyle);
        when(request.getSession()).thenReturn(session);

        path = newUser.execute(request);
        assertEquals(Pages.SIGN_OR_REGISTER_WITH_ERROR, path);

        when(request.getParameter(Attributes.REQUEST_EMAIL)).thenReturn(emailAlreadyRegistered);
        path = newUser.execute(request);
        assertEquals(Pages.SIGN_OR_REGISTER_WITH_ERROR, path);
    }
}