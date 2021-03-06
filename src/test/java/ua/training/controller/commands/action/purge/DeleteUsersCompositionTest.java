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
public class DeleteUsersCompositionTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private User user;
    private String idRC;

    @Before
    public void setUp() {
        idRC = "1,Test,2,11,22";
    }

    @After
    public void tearDown() {
        idRC = null;
    }

    @Test(expected = DataSqlException.class)
    public void execute() {
        DeleteUsersComposition deleteUsersComposition = new DeleteUsersComposition();

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.REQUEST_USER)).thenReturn(user);
        when(request.getParameter(Attributes.REQUEST_ARR_COMPOSITION)).thenReturn(idRC);

        deleteUsersComposition.execute(request);
    }
}