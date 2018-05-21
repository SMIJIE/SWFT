package ua.training.controller.commands.action.admin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.Roles;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateUsersByAdminTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private User user;

    private String path;
    private String email;
    private String password;
    private String roles;

    @Before
    public void setUp() {
        email = "Pavlo@mail.ua";
        password = "";
        roles = Roles.USER.toString();
    }

    @After
    public void tearDown() {
        path = null;
        email = null;
        password = null;
        roles = null;
    }

    @Test
    public void execute() {
        UpdateUsersByAdmin updateUsersByAdmin = new UpdateUsersByAdmin();
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.REQUEST_USER)).thenReturn(user);
        when(request.getParameter(Attributes.REQUEST_EMAIL_USERS)).thenReturn(email);
        when(request.getParameter(Attributes.REQUEST_PASSWORD)).thenReturn(password);
        when(request.getParameter(Attributes.REQUEST_USER_ROLE)).thenReturn(roles);

        path = updateUsersByAdmin.execute(request);
        assertEquals(Pages.SHOW_USERS_AFTER_UPDATE_OR_SEARCH_REDIRECT, path);
    }
}