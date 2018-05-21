package ua.training.controller.commands.action.statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.constant.Attributes;
import ua.training.constant.Pages;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogInTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private ServletContext context;

    private String path;
    private String email;
    private String emailTest;
    private String password;
    private String passwordWrong;
    private HashSet<String> allUsers;

    @Before
    public void setUp() {
        email = "Pavlo@mail.ua";
        emailTest = "Test@gmail.com";
        password = "qwerty";
        passwordWrong = "qwertyWrong";
        allUsers = new HashSet<>();
    }

    @After
    public void tearDown() {
        path = null;
        email = null;
        emailTest = null;
        password = null;
        passwordWrong = null;
        allUsers = null;
    }

    @Test
    public void execute() {
        LogIn logIn = new LogIn();

        when(request.getParameter(Attributes.REQUEST_EMAIL)).thenReturn(email);
        when(request.getParameter(Attributes.REQUEST_PASSWORD)).thenReturn(password);

        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.REQUEST_USERS_ALL)).thenReturn(allUsers);
        when(request.getSession()).thenReturn(session);

        path = logIn.execute(request);
        assertEquals(Pages.HOME_REDIRECT, path);

        when(request.getParameter(Attributes.REQUEST_EMAIL)).thenReturn(emailTest);
        when(request.getParameter(Attributes.REQUEST_PASSWORD)).thenReturn(password);

        path = logIn.execute(request);
        assertEquals(Pages.SIGN_OR_REGISTER_WITH_ERROR, path);

        when(request.getParameter(Attributes.REQUEST_EMAIL)).thenReturn(email);
        when(request.getParameter(Attributes.REQUEST_PASSWORD)).thenReturn(passwordWrong);

        path = logIn.execute(request);
        assertEquals(Pages.SIGN_OR_REGISTER_WITH_ERROR, path);
    }
}