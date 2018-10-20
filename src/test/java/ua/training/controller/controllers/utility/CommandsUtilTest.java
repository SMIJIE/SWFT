//package ua.training.controller.commands.utility;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import ua.training.constant.Attributes;
//import ua.training.constant.Pages;
//import ua.training.controller.commands.Command;
//import ua.training.controller.commands.action.HomePage;
//import ua.training.controller.commands.exception.DataSqlException;
//import ua.training.model.entity.Dish;
//import ua.training.model.entity.User;
//import ua.training.model.entity.enums.FoodCategory;
//
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.util.HashSet;
//import java.util.LinkedList;
//import java.util.Map;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class CommandsUtilTest {
//    @Mock
//    private HttpServletRequest request;
//    @Mock
//    private ServletContext context;
//    @Mock
//    private HttpSession session;
//    @Mock
//    private User user;
//    @Mock
//    private Dish dish;
//
//    private Map<String, Command> commandMap;
//    private HashSet<String> allUsers;
//    private Dish uDish;
//    private Dish gDish;
//    private LinkedList<Dish> generalDish;
//
//    @Before
//    public void setUp() {
//        commandMap = CommandsUtil.commandMapInitialize();
//        allUsers = new HashSet<>();
//        allUsers.add("Zakusylo@gmail.com");
//        allUsers.add("Sasha@gmail.com");
//        uDish = new Dish();
//        gDish = new Dish();
//        generalDish = new LinkedList<>();
//    }
//
//    @After
//    public void tearDown() {
//        commandMap = null;
//        allUsers = null;
//        gDish = null;
//        uDish = null;
//        generalDish = null;
//        commandMap = null;
//        allUsers = null;
//    }
//
//    @Test
//    public void commandMapInitialize() {
//        assertTrue(commandMap.get(Attributes.COMMAND_HOME_PAGE) instanceof HomePage);
//    }
//
//    @Test
//    public void openUsersSession() {
//        String returnPage;
//
//        when(request.getServletContext()).thenReturn(context);
//        when(context.getAttribute(Attributes.REQUEST_USERS_ALL)).thenReturn(allUsers);
//        when(user.getEmail()).thenReturn("Pavlo@mail.com");
//        when(request.getSession()).thenReturn(session);
//
//        returnPage = CommandsUtil.openUsersSession(request, user);
//        assertEquals(Pages.HOME_REDIRECT, returnPage);
//
//        when(user.getEmail()).thenReturn("Zakusylo@gmail.com");
//        returnPage = CommandsUtil.openUsersSession(request, user);
//        assertEquals(Pages.SIGN_OR_REGISTER_WITH_ERROR, returnPage);
//    }
//
//    @Test
//    public void addUsersToContext() {
//        allUsers = new HashSet<>();
//        assertTrue(allUsers.isEmpty());
//        when(request.getServletContext()).thenReturn(context);
//        when(context.getAttribute(Attributes.REQUEST_USERS_ALL)).thenReturn(allUsers);
//        CommandsUtil.addUsersToContext(request, "Zakusylo@gmail.com", "Sasha@gmail.com");
//        assertFalse(allUsers.isEmpty());
//
//    }
//
//    @Test
//    public void deleteUsersFromContext() {
//        assertFalse(allUsers.isEmpty());
//        when(request.getServletContext()).thenReturn(context);
//        when(context.getAttribute(Attributes.REQUEST_USERS_ALL)).thenReturn(allUsers);
//        CommandsUtil.deleteUsersFromContext(request, "Zakusylo@gmail.com", "Sasha@gmail.com");
//        assertTrue(allUsers.isEmpty());
//
//    }
//
//    @Test(expected = DataSqlException.class)
//    public void stringArrayToInteger() {
//        String[] arr = {"11", "22"};
//        Integer[] parseArr = CommandsUtil.stringArrayToInteger(arr);
//        assertEquals(2, parseArr.length);
//
//        arr = new String[]{"Zakusylo@gmail.com", "Sasha@gmail.com"};
//        CommandsUtil.stringArrayToInteger(arr);
//    }
//
//    @Test
//    public void sortListByAnnotationFields() {
//        uDish.setName("First");
//        uDish.setFoodCategory(FoodCategory.LUNCHEON);
//        uDish.setCalories(2000);
//        gDish.setName("Second");
//        gDish.setFoodCategory(FoodCategory.LUNCHEON);
//        gDish.setCalories(1000);
//
//        generalDish.add(uDish);
//        generalDish.add(gDish);
//
//        assertEquals("First", generalDish.getFirst().getName());
//
//        CommandsUtil.sortListByAnnotationFields(generalDish);
//
//        assertEquals("Second", generalDish.getFirst().getName());
//    }
//
//    @Test
//    public void updateDishParameters() {
//        Dish from = Dish.builder()
//                .foodCategory(FoodCategory.HOT)
//                .weight(1000)
//                .calories(1000)
//                .proteins(1000)
//                .fats(1000)
//                .carbohydrates(1000)
//                .build();
//
//        Dish to = new Dish();
//        CommandsUtil.updateDishParameters(from, to);
//
//        assertEquals(from.getCalories(), to.getCalories());
//    }
//
//    @Test
//    public void getCountPages() {
//        int count = CommandsUtil.getCountPages(3, 5);
//        assertEquals(1, count);
//
//        count = CommandsUtil.getCountPages(10, 5);
//        assertEquals(2, count);
//
//        count = CommandsUtil.getCountPages(12, 5);
//        assertEquals(3, count);
//    }
//
//    @Test
//    public void getPageOrAmountForSQL() {
//        int count = CommandsUtil.getPageOrAmountForSQL(0, 5);
//        assertEquals(1, count);
//
//        count = CommandsUtil.getPageOrAmountForSQL(3, 5);
//        assertEquals(3, count);
//
//        count = CommandsUtil.getPageOrAmountForSQL(12, 5);
//        assertEquals(5, count);
//    }
//}