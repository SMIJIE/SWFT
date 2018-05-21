package ua.training.controller.commands.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.action.ChangeLanguage;
import ua.training.controller.commands.action.HomePage;
import ua.training.controller.commands.action.MenuUsersEdit;
import ua.training.controller.commands.action.UserDayRation;
import ua.training.controller.commands.action.add.AddNewDish;
import ua.training.controller.commands.action.add.CreateNewRation;
import ua.training.controller.commands.action.admin.*;
import ua.training.controller.commands.action.pages.ListDishPage;
import ua.training.controller.commands.action.pages.ListHomePage;
import ua.training.controller.commands.action.pages.ListUserDayRation;
import ua.training.controller.commands.action.purge.DeleteUsersComposition;
import ua.training.controller.commands.action.purge.DeleteUsersMenuItem;
import ua.training.controller.commands.action.statement.LogIn;
import ua.training.controller.commands.action.statement.LogOut;
import ua.training.controller.commands.action.statement.RegisterNewUser;
import ua.training.controller.commands.action.update.UpdateUsersComposition;
import ua.training.controller.commands.action.update.UpdateUsersDish;
import ua.training.controller.commands.action.update.UpdateUsersParameters;
import ua.training.controller.commands.direction.*;
import ua.training.controller.commands.exception.DataSqlException;
import ua.training.model.dao.utility.DishComparator;
import ua.training.model.dao.utility.SortAnnotation;
import ua.training.model.entity.Dish;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Description: This util class for commands
 *
 * @author Zakusylo Pavlo
 */
public class CommandsUtil {
    /**
     * Logger for CommandsUtil classes
     *
     * @see LogManager
     */
    private static Logger logger = LogManager.getLogger(CommandsUtil.class);
    private static List<Dish> list;

    /**
     * Initialize all commands
     *
     * @return commandMap  Map<String, Command>
     */
    public static Map<String, Command> commandMapInitialize() {
        Map<String, Command> commandMap = new HashMap<>();

        commandMap.put(Attributes.COMMAND_SIGN_OR_REGISTER, new SignInOrRegister());
        commandMap.put(Attributes.COMMAND_SIGN_OR_REGISTER_WITH_ERROR, new SignInOrRegisterWithError());
        commandMap.put(Attributes.COMMAND_LOG_IN, new LogIn());
        commandMap.put(Attributes.COMMAND_REGISTER_NEW_USER, new RegisterNewUser());
        commandMap.put(Attributes.COMMAND_HOME_PAGE, new HomePage());
        commandMap.put(Attributes.COMMAND_LOG_OUT, new LogOut());
        commandMap.put(Attributes.COMMAND_CHANGE_LANGUAGE, new ChangeLanguage());
        commandMap.put(Attributes.COMMAND_USER_SETTINGS, new UsersSettings());
        commandMap.put(Attributes.COMMAND_USER_SETTINGS_WITH_ERROR, new UsersSettingsWithError());
        commandMap.put(Attributes.COMMAND_USER_UPDATE_PARAMETERS, new UpdateUsersParameters());
        commandMap.put(Attributes.COMMAND_MENU, new Menu());
        commandMap.put(Attributes.COMMAND_MENU_GENERAL_EDIT, new MenuGeneralEdit());
        commandMap.put(Attributes.COMMAND_MENU_GENERAL_DELETE, new DeleteGeneralMenuItem());
        commandMap.put(Attributes.COMMAND_MENU_GENERAL_UPDATE, new UpdateGeneralDish());
        commandMap.put(Attributes.COMMAND_MENU_USERS_EDIT, new MenuUsersEdit());
        commandMap.put(Attributes.COMMAND_MENU_USERS_EDIT_AFTER_UPDATE, new MenuUsersEditAfterUpdate());
        commandMap.put(Attributes.COMMAND_MENU_ADD_DISH, new AddNewDish());
        commandMap.put(Attributes.COMMAND_MENU_LIST_DISH_PAGE, new ListDishPage());
        commandMap.put(Attributes.COMMAND_MENU_USERS_DELETE, new DeleteUsersMenuItem());
        commandMap.put(Attributes.COMMAND_MENU_USERS_UPDATE, new UpdateUsersDish());
        commandMap.put(Attributes.COMMAND_SHOW_USERS, new ShowUsers());
        commandMap.put(Attributes.COMMAND_DELETE_USERS, new DeleteUsers());
        commandMap.put(Attributes.COMMAND_MENU_LIST_USERS_PAGE, new ListUsersPage());
        commandMap.put(Attributes.COMMAND_MENU_USERS_EDIT_WITH_ERROR, new MenuUsersEditWithError());
        commandMap.put(Attributes.COMMAND_MENU_GENERAL_EDIT_WITH_ERROR, new MenuGeneralEditWithError());
        commandMap.put(Attributes.COMMAND_USER_UPDATE_BY_ADMIN, new UpdateUsersByAdmin());
        commandMap.put(Attributes.COMMAND_SHOW_USERS_UPDATE_SEARCH_BY_ADMIN, new ShowUsersAfterUpdateOrSearch());
        commandMap.put(Attributes.COMMAND_SHOW_USERS_BY_EMAIL_BY_ADMIN, new SearchUsersByEmail());
        commandMap.put(Attributes.COMMAND_DAY_RATION, new UserDayRation());
        commandMap.put(Attributes.COMMAND_CREATE_DAY_RATION, new CreateNewRation());
        commandMap.put(Attributes.COMMAND_LIST_HOME_PAGE, new ListHomePage());
        commandMap.put(Attributes.COMMAND_LIST_USER_DAY_RATION, new ListUserDayRation());
        commandMap.put(Attributes.COMMAND_DELETE_USER_COMPOSITION, new DeleteUsersComposition());
        commandMap.put(Attributes.COMMAND_UPDATE_USER_COMPOSITION, new UpdateUsersComposition());

        return commandMap;
    }

    /**
     * Open session for users and return page
     *
     * @param request HttpServletRequest
     * @param user    User
     * @return page String
     */
    public static String openUsersSession(HttpServletRequest request, User user) {

        HashSet<String> allUsers = (HashSet<String>) request.getServletContext().getAttribute(Attributes.REQUEST_USERS_ALL);

        if (allUsers.contains(user.getEmail())) {
            logger.warn(Mess.LOG_USER_DOUBLE_AUTH + " [" + user.getEmail() + "]");
            request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_EMAIL, Attributes.PAGE_USER_LOGGED);
            return Pages.SIGN_OR_REGISTER_WITH_ERROR;
        }

        request.getSession().setAttribute(Attributes.REQUEST_USER, user);
        request.getSession().setAttribute(Attributes.PAGE_NAME, Attributes.PAGE_GENERAL);

        allUsers.add(user.getEmail());
        request.getServletContext().setAttribute(Attributes.REQUEST_USERS_ALL, allUsers);
        logger.info(Mess.LOG_USER_LOGGED + "[" + user.getEmail() + "]");

        return Pages.HOME_REDIRECT;
    }

    /**
     * Delete users from ServletContextListener
     *
     * @param request HttpServletRequest
     * @param emails  String...
     */
    public static void deleteUsersFromContext(HttpServletRequest request, String... emails) {
        HashSet<String> allUsers = (HashSet<String>) request.getServletContext().getAttribute(Attributes.REQUEST_USERS_ALL);

        for (int i = 0; i < emails.length; i++) {
            allUsers.remove(emails[i]);
        }

        request.getServletContext().setAttribute(Attributes.REQUEST_USERS_ALL, allUsers);
    }

    /**
     * Convert String array to Integer array
     *
     * @param strArr String[]
     * @return intArr Integer[]
     */
    public static Integer[] stringArrayToInteger(String[] strArr) {
        Integer[] intArr;

        try {
            intArr = Arrays.stream(strArr)
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);
        } catch (NumberFormatException e) {
            logger.error(e.getMessage());
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }

        return intArr;
    }

    /**
     * Sort list of dish by annotation fields
     *
     * @param list List<T>
     */
    public static void sortListByAnnotationFields(List<Dish> list) {
        CommandsUtil.list = list;
        Class<?> cl = Dish.class;
        Field[] fields = cl.getDeclaredFields();
        ArrayList<Field> fieldsForSort = new ArrayList<>();
        Comparator<Dish> comparator = null;
        boolean flag = true;

        for (Field field : fields) {
            if (field.isAnnotationPresent(SortAnnotation.class)) {
                field.setAccessible(true);
                fieldsForSort.add(field);
            }
        }

        for (Field field : fieldsForSort) {
            if (flag) {
                comparator = new DishComparator(field);
                flag = false;
                continue;
            }
            comparator = comparator.thenComparing(new DishComparator(field));
        }

        list.sort(comparator);
    }
}
