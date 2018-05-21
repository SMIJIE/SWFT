package ua.training.controller.commands.action.add;

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

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddNewDishTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private User user;

    private String path;
    private String name;
    private String weight;
    private String calories;
    private String proteins;
    private String fats;
    private String carbohydrates;
    private String category;

    @Before
    public void setUp() {
        name = "Scramble2";
        weight = "300";
        calories = "150";
        proteins = "1.5";
        fats = "2.5";
        carbohydrates = "0.5";
        category = "HOT";
    }

    @After
    public void tearDown() {
        path = null;
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
        AddNewDish addNewDish = new AddNewDish();

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.REQUEST_USER)).thenReturn(user);
        when(request.getParameter(Attributes.REQUEST_NAME)).thenReturn(name);
        when(request.getParameter(Attributes.REQUEST_WEIGHT)).thenReturn(weight);
        when(request.getParameter(Attributes.REQUEST_CALORIES)).thenReturn(calories);
        when(request.getParameter(Attributes.REQUEST_PROTEINS)).thenReturn(proteins);
        when(request.getParameter(Attributes.REQUEST_FATS)).thenReturn(fats);
        when(request.getParameter(Attributes.REQUEST_CARBOHYDRATES)).thenReturn(carbohydrates);
        when(request.getParameter(Attributes.REQUEST_CATEGORY)).thenReturn(category);

        path = addNewDish.execute(request);

        assertEquals(Pages.MENU_USERS_EDIT_REDIRECT_WITH_ERROR, path);
    }
}