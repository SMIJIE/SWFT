package ua.training.controller.commands.action.pages;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ListHomePageTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private User user;

    private String path;

    @After
    public void tearDown() {
        path = null;
    }

    @Test
    public void execute() {
        ListHomePage listHomePage = new ListHomePage();

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.REQUEST_USER)).thenReturn(user);
        when(request.getParameter(Attributes.REQUEST_NUMBER_PAGE)).thenReturn("1");
        when(session.getAttribute(Attributes.REQUEST_NUMBER_PAGE)).thenReturn(2);
        when(user.getId()).thenReturn(2);

        path = listHomePage.execute(request);
        assertEquals(Pages.HOME, path);

        when(request.getParameter(Attributes.REQUEST_NUMBER_PAGE)).thenReturn(null);

        path = listHomePage.execute(request);
        assertEquals(Pages.HOME, path);
    }
}