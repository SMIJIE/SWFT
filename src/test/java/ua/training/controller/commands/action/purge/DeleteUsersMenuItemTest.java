package ua.training.controller.commands.action.purge;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.constant.Attributes;
import ua.training.controller.commands.exception.DataSqlException;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteUsersMenuItemTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private User user;
    private String idDishes;

    @Before
    public void setUp() {
        idDishes = "1,Test,2,11,22";
    }

    @After
    public void tearDown() {
        idDishes = null;
    }

    @Test(expected = DataSqlException.class)
    public void execute() {
        DeleteUsersMenuItem deleteUsersMenuItem = new DeleteUsersMenuItem();
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.REQUEST_USER)).thenReturn(user);
        when(request.getParameter(Attributes.REQUEST_ARR_DISH)).thenReturn(idDishes);

        deleteUsersMenuItem.execute(request);
    }
}