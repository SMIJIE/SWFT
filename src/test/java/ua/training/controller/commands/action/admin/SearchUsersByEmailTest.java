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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchUsersByEmailTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private User user;

    private String path;
    private String email;

    @Before
    public void setUp() {
        email = "Zakusylo@gmail.com";
    }

    @After
    public void tearDown() {
        path = null;
        email = null;
    }

    @Test
    public void execute() {
        SearchUsersByEmail searchUsersByEmail = new SearchUsersByEmail();
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.REQUEST_USER)).thenReturn(user);
        when(request.getParameter(Attributes.REQUEST_EMAIL)).thenReturn(email);
        when(user.getEmail()).thenReturn(email);

        path = searchUsersByEmail.execute(request);
        assertEquals(Pages.SHOW_USERS_AFTER_UPDATE_OR_SEARCH_REDIRECT, path);
    }
}