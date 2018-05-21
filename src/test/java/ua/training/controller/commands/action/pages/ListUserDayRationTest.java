package ua.training.controller.commands.action.pages;

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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ListUserDayRationTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletContext context;
    @Mock
    private HttpSession session;
    @Mock
    private User user;

    private String path;
    private List<Dish> generalDish;
    private ArrayList<Dish> userDish;

    @Before
    public void setUp() {
        generalDish = new ArrayList<>();
        userDish = new ArrayList<>();
    }

    @After
    public void tearDown() {
        generalDish = null;
        user = null;
        path = null;
    }

    @Test
    public void execute() {
        ListUserDayRation listUserDayRation = new ListUserDayRation();
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.REQUEST_USER)).thenReturn(user);
        when(request.getParameter(Attributes.REQUEST_NUMBER_PAGE)).thenReturn("1");
        when(session.getAttribute(Attributes.REQUEST_NUMBER_PAGE)).thenReturn(2);
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.REQUEST_GENERAL_DISHES)).thenReturn(generalDish);
        when(user.getListDishes()).thenReturn(userDish);
        when(user.getId()).thenReturn(2);

        path = listUserDayRation.execute(request);
        assertEquals(Pages.DAY_RATION, path);

        when(request.getParameter(Attributes.REQUEST_NUMBER_PAGE)).thenReturn("");

        path = listUserDayRation.execute(request);
        assertEquals(Pages.DAY_RATION, path);
    }
}