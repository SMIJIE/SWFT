package ua.training.controller.commands.action;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.model.entity.Dish;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.FoodCategory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDayRationTest {
    @Mock
    private ServletContext context;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private User user;

    private Dish uDish;
    private Dish gDish;
    private List<Dish> generalDish;
    private ArrayList<Dish> userDish;
    private String path;

    @Before
    public void setUp() {
        uDish = new Dish();
        uDish.setFoodCategory(FoodCategory.LUNCHEON);
        uDish.setCalories(2000);
        gDish = new Dish();
        gDish.setFoodCategory(FoodCategory.HOT);
        gDish.setCalories(2000);
        generalDish = new ArrayList<>();
        userDish = new ArrayList<>();
    }

    @After
    public void tearDown() {
        uDish = null;
        gDish = null;
        generalDish = null;
        userDish = null;
        path = null;
    }

    @Test
    public void execute() {
        UserDayRation userDayRation = new UserDayRation();
        userDish.add(uDish);
        generalDish.add(gDish);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.REQUEST_USER)).thenReturn(user);
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.REQUEST_GENERAL_DISHES)).thenReturn(generalDish);

        when(user.getListDishes()).thenReturn(userDish);
        when(user.getId()).thenReturn(1);

        path = userDayRation.execute(request);

        assertEquals(Pages.DAY_RATION, path);
    }
}