package ua.training.controller.commands.action.statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.model.entity.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogOutTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private ServletContext context;
    @Mock
    private User user;
    private String path;
    private String email;
    private HashSet<String> allUsers;

    @Before
    public void setUp() {
        email = "Zakusylo@gmail.com";
        allUsers = new HashSet<>();
        allUsers.add("Zakusylo@gmail.com");
        allUsers.add("Sasha@gmail.com");
    }

    @After
    public void tearDown() {
        path = null;
        email = null;
        allUsers = null;
    }

    @Test
    public void execute() {
        LogOut logOut = new LogOut();

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.REQUEST_USER)).thenReturn(user);
        when(user.getEmail()).thenReturn(email);
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.REQUEST_USERS_ALL)).thenReturn(allUsers);

        assertEquals(2, allUsers.size());

        path = logOut.execute(request);

        assertEquals(1, allUsers.size());
        assertEquals(Pages.SIGN_OR_REGISTER_REDIRECT, path);
    }
}