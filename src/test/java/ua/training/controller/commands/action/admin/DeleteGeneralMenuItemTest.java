package ua.training.controller.commands.action.admin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.constant.Attributes;
import ua.training.controller.commands.exception.DataSqlException;
import ua.training.model.entity.Dish;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteGeneralMenuItemTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletContext servletContext;
    @Mock
    private Dish dish;
    private List<Dish> dishes;
    private String idDishes;

    @Before
    public void setUp() {
        dishes = Arrays.asList(dish);
        idDishes = "1,Test,2,11,22";
    }

    @After
    public void tearDown() {
        idDishes = null;
    }

    @Test(expected = DataSqlException.class)
    public void execute() {
        DeleteGeneralMenuItem deleteGeneralMenuItem = new DeleteGeneralMenuItem();

        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute(Attributes.REQUEST_GENERAL_DISHES)).thenReturn(dishes);
        when(request.getParameter(Attributes.REQUEST_ARR_DISH)).thenReturn(idDishes);

        deleteGeneralMenuItem.execute(request);
    }
}