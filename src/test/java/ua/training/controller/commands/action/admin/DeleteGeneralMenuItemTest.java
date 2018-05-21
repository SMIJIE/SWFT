package ua.training.controller.commands.action.admin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.constant.Attributes;
import ua.training.controller.commands.exception.DataSqlException;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteGeneralMenuItemTest {
    @Mock
    private HttpServletRequest request;

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
        DeleteGeneralMenuItem deleteGeneralMenuItem = new DeleteGeneralMenuItem();

        when(request.getParameter(Attributes.REQUEST_ARR_DISH)).thenReturn(idDishes);

        deleteGeneralMenuItem.execute(request);
    }
}