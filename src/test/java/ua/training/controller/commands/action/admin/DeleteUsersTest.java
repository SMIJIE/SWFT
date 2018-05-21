package ua.training.controller.commands.action.admin;

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
import java.util.HashSet;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteUsersTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletContext context;

    private String path;
    private String emailsUsers;
    private HashSet<String> allUsers;

    @Before
    public void setUp() {
        emailsUsers = "Test@gmail.com,Test2@gmail.com";
        allUsers = new HashSet<>();
        allUsers.add("Test@gmail.com");
        allUsers.add("Test2@gmail.com");
    }

    @After
    public void tearDown() {
        emailsUsers = null;
        allUsers = null;
        path = null;
    }

    @Test
    public void execute() {
        DeleteUsers deleteUsers = new DeleteUsers();

        when(request.getParameter(Attributes.REQUEST_ARR_EMAIL_USERS)).thenReturn(emailsUsers);
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.REQUEST_USERS_ALL)).thenReturn(allUsers);

        path = deleteUsers.execute(request);
        assertEquals(Pages.SHOW_USERS_REDIRECT, path);
    }
}