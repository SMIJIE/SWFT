package ua.training.controller.commands.utility;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.action.UserDayRation;
import ua.training.controller.commands.action.add.CreateNewRation;
import ua.training.controller.commands.action.admin.DeleteUsers;
import ua.training.controller.commands.action.admin.ListUsersPage;
import ua.training.controller.commands.action.admin.SearchUsersByEmail;
import ua.training.controller.commands.action.admin.UpdateUsersByAdmin;
import ua.training.controller.commands.action.pages.ListUserDayRation;
import ua.training.controller.commands.action.purge.DeleteUsersComposition;
import ua.training.controller.commands.action.update.UpdateUsersComposition;
import ua.training.controller.commands.exception.DataSqlException;
import ua.training.model.dao.utility.DishComparator;
import ua.training.model.dao.utility.SortAnnotation;
import ua.training.model.entity.Dish;
import ua.training.model.entity.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * Description: This util class for commands
 *
 * @author Zakusylo Pavlo
 */
@Log4j2
public abstract class CommandsUtil implements Command {
    /**
     * Initialize all commands
     *
     * @return commandMap  Map<String, Command>
     */
    public static Map<String, Command> commandMapInitialize() {
        Map<String, Command> commandMap = new HashMap<>();

        commandMap.put(Attributes.COMMAND_DELETE_USERS, new DeleteUsers());
        commandMap.put(Attributes.COMMAND_MENU_LIST_USERS_PAGE, new ListUsersPage());
        commandMap.put(Attributes.COMMAND_USER_UPDATE_BY_ADMIN, new UpdateUsersByAdmin());
        commandMap.put(Attributes.COMMAND_SHOW_USERS_BY_EMAIL_BY_ADMIN, new SearchUsersByEmail());
        commandMap.put(Attributes.COMMAND_DAY_RATION, new UserDayRation());
        commandMap.put(Attributes.COMMAND_CREATE_DAY_RATION, new CreateNewRation());
        commandMap.put(Attributes.COMMAND_LIST_USER_DAY_RATION, new ListUserDayRation());
        commandMap.put(Attributes.COMMAND_DELETE_USER_COMPOSITION, new DeleteUsersComposition());
        commandMap.put(Attributes.COMMAND_UPDATE_USER_COMPOSITION, new UpdateUsersComposition());

        return commandMap;
    }

    /**
     * Open session for users and return page
     *
     * @param request            {@link HttpServletRequest}
     * @param user               {@link User}
     * @param modelAndView       {@link ModelAndView}
     * @param redirectAttributes {@link RedirectAttributes}
     */
    public static ModelAndView openUsersSession(HttpServletRequest request,
                                                User user,
                                                ModelAndView modelAndView,
                                                RedirectAttributes redirectAttributes) {

        CopyOnWriteArraySet<String> allUsers = (CopyOnWriteArraySet<String>) request.getServletContext()
                .getAttribute(Attributes.REQUEST_USERS_ALL);

        if (allUsers.contains(user.getEmail())) {
            log.warn(Mess.LOG_USER_DOUBLE_AUTH + " [" + user.getEmail() + "]");
            redirectAttributes.addFlashAttribute(Attributes.PAGE_USER_ERROR, Attributes.PAGE_USER_LOGGED);
        } else {
            allUsers.add(user.getEmail());
            request.getServletContext()
                    .setAttribute(Attributes.REQUEST_USERS_ALL, allUsers);

            request.getSession().setAttribute(Attributes.REQUEST_USER, user);
            log.info(Mess.LOG_USER_LOGGED + "[" + user.getEmail() + "]");

            modelAndView.setViewName(Pages.HOME_REDIRECT);
        }

        return modelAndView;
    }

    /**
     * Add general dishes to ServletContext after delete/update
     *
     * @param servletContext ServletContext
     */
    public static void addGeneralDishToContext(ServletContext servletContext) {
        List<Dish> generalList = DISH_SERVICE_IMP.getGeneralDishes();
        CommandsUtil.sortListByAnnotationFields(generalList);

        Map<String, List<Dish>> generalMap = new HashMap<>();
        generalList.forEach(dish -> {
            String category = dish
                    .getFoodCategory()
                    .toString()
                    .toLowerCase();
            generalMap.putIfAbsent(category, new ArrayList<>());
            generalMap.get(category).add(dish);
        });

        if (!generalList.isEmpty()) {
            servletContext.setAttribute(Attributes.REQUEST_GENERAL_DISHES, generalMap);
        }
    }

    /**
     * Add users to ServletContextListener
     *
     * @param request HttpServletRequest
     * @param emails  String...
     */
    public static void addUsersToContext(HttpServletRequest request, String... emails) {
        CopyOnWriteArraySet<String> allUsers = (CopyOnWriteArraySet<String>) request.getServletContext().getAttribute(Attributes.REQUEST_USERS_ALL);
        allUsers.addAll(Arrays.asList(emails));
        request.getServletContext().setAttribute(Attributes.REQUEST_USERS_ALL, allUsers);
    }

    /**
     * Delete users from ServletContextListener
     *
     * @param request HttpServletRequest
     * @param emails  String...
     */
    public static void deleteUsersFromContext(HttpServletRequest request, String... emails) {
        CopyOnWriteArraySet<String> allUsers = (CopyOnWriteArraySet<String>) request.getServletContext().getAttribute(Attributes.REQUEST_USERS_ALL);
        Arrays.asList(emails).forEach(allUsers::remove);
        request.getServletContext().setAttribute(Attributes.REQUEST_USERS_ALL, allUsers);
    }

    /**
     * Merge users from Http Form to Session
     *
     * @param userHttp {@link User}
     * @param user     {@link User}
     */
    public static void mergeUserParameters(User userHttp, User user) {
        user.setName(userHttp.getName());
        user.setDob(userHttp.getDob());
        user.setEmail(userHttp.getEmail());
        user.setRole(userHttp.getRole());
        user.setHeight(userHttp.getHeight());
        user.setWeight(userHttp.getWeight());
        user.setWeightDesired(userHttp.getWeightDesired());
        user.setLifeStyleCoefficient(userHttp.getLifeStyleCoefficient());

        boolean flagPassword = userHttp.getPassword().isEmpty();
        user.setPassword(flagPassword ? user.getPassword() : userHttp.getPassword());
    }

    /**
     * Merge dish from Http Form to Session
     *
     * @param dishHttp {@link Dish}
     * @param dish     {@link Dish}
     */
    public static void mergeDishParameters(Dish dishHttp, Dish dish) {
        dish.setFoodCategory(dishHttp.getFoodCategory());
        dish.setWeight(dishHttp.getWeight());
        dish.setCalories(dishHttp.getCalories());
        dish.setProteins(dishHttp.getProteins());
        dish.setFats(dishHttp.getFats());
        dish.setCarbohydrates(dishHttp.getCarbohydrates());

        boolean flagName = dishHttp.getName().isEmpty();
        dish.setName(flagName ? dish.getName() : dishHttp.getName());
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
        } catch (ClassCastException | NumberFormatException e) {
            log.error(e.getMessage());
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }

        return intArr;
    }

    /**
     * Sort list of dish by annotation fields
     *
     * @param dishes List<T>
     */
    public static void sortListByAnnotationFields(List<Dish> dishes) {
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

        if (!fieldsForSort.isEmpty()) {
            dishes.sort(comparator);
        }
    }

    /**
     * Count pages from the total number and quantity per page
     *
     * @param numberItem   Integer
     * @param countPerPage Integer
     * @return maxPage Integer
     */
    public static Integer getCountPages(Integer numberItem, Integer countPerPage) {
        return (int) Math.round((numberItem / countPerPage) + ((numberItem % countPerPage) == 0 ? 0 : 0.5));
    }

    /**
     * Define min/max/current page for SQL statement
     *
     * @param currentNumber Integer
     * @param maxNumber     Integer
     * @return currentPage Integer
     */
    public static Integer getPageOrAmountForSQL(Integer currentNumber, Integer maxNumber) {
        return currentNumber <= 1 ? 1 : (currentNumber > maxNumber ? maxNumber : currentNumber);
    }
}
