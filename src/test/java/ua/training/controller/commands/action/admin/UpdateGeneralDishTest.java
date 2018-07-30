package ua.training.controller.commands.action.admin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.model.entity.Dish;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateGeneralDishTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletContext servletContext;
    @Mock
    private HttpSession session;
    @Mock
    private Dish dish;
    private List<Dish> dishes;
    private String path;
    private String numDish;
    private String name;
    private String weight;
    private String calories;
    private String proteins;
    private String fats;
    private String carbohydrates;
    private String category;

    @Before
    public void setUp() {
        dishes = Arrays.asList(dish);
        name = "Scramble2";
        numDish = "1";
        weight = "300";
        calories = "150";
        proteins = "1.5";
        fats = "2.5";
        carbohydrates = "0.5";
        category = "HOT";
    }

    @After
    public void tearDown() {
        dishes = null;
        path = null;
        numDish = null;
        name = null;
        weight = null;
        calories = null;
        proteins = null;
        fats = null;
        carbohydrates = null;
        category = null;
    }

    @Test
    public void execute() {
        UpdateGeneralDish updateGeneralDish = new UpdateGeneralDish();

        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute(Attributes.REQUEST_GENERAL_DISHES)).thenReturn(dishes);

        when(request.getParameter(Attributes.REQUEST_NUMBER_DISH)).thenReturn(numDish);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(Attributes.REQUEST_NAME)).thenReturn(name);
        when(request.getParameter(Attributes.REQUEST_WEIGHT)).thenReturn(weight);
        when(request.getParameter(Attributes.REQUEST_CALORIES)).thenReturn(calories);
        when(request.getParameter(Attributes.REQUEST_PROTEINS)).thenReturn(proteins);
        when(request.getParameter(Attributes.REQUEST_FATS)).thenReturn(fats);
        when(request.getParameter(Attributes.REQUEST_CARBOHYDRATES)).thenReturn(carbohydrates);
        when(request.getParameter(Attributes.REQUEST_CATEGORY)).thenReturn(category);

        path = updateGeneralDish.execute(request);

        assertEquals(Pages.MENU_GENERAL_EDIT_WITH_ERROR_REDIRECT, path);
    }
}