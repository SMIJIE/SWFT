package ua.training.controller.utility;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.training.constant.Attributes;
import ua.training.controller.controllers.GeneralController;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.Dish;
import ua.training.model.entity.RationComposition;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.FoodCategory;
import ua.training.model.entity.enums.FoodIntake;
import ua.training.model.service.implementation.DishServiceImp;
import ua.training.model.service.implementation.RationCompositionServiceImp;
import ua.training.model.utility.DishComparator;
import ua.training.model.utility.SortAnnotation;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * Description: This util class for commands
 *
 * @author Zakusylo Pavlo
 */
@Log4j2
public abstract class ControllerUtil implements GeneralController {
    @Autowired
    private static DishServiceImp DISH_SERVICE_IMP;
    @Autowired
    private static RationCompositionServiceImp RATION_COMPOSITION_SERVICE_IMP;

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
                .getAttribute(REQUEST_USERS_ALL);

        if (allUsers.contains(user.getEmail())) {
            log.warn(LOG_USER_DOUBLE_AUTH + " [" + user.getEmail() + "]");
            redirectAttributes.addFlashAttribute(PAGE_USER_ERROR, PAGE_USER_LOGGED);
        } else {
            allUsers.add(user.getEmail());
            request.getServletContext()
                    .setAttribute(REQUEST_USERS_ALL, allUsers);

            request.getSession().setAttribute(REQUEST_USER, user);
            log.info(LOG_USER_LOGGED + "[" + user.getEmail() + "]");

            modelAndView.setViewName(HOME_REDIRECT);
        }

        return modelAndView;
    }

    /**
     * Create ration composition from array of dishes
     *
     * @param idDishes   String[]
     * @param dayRation  DayRation
     * @param foodIntake FoodIntake
     */
    public static void createRationComposition(Integer[] idDishes, DayRation dayRation, FoodIntake foodIntake) {
        if (nonNull(idDishes)) {

            Map<Integer, Long> countDishes = Arrays.stream(idDishes)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            for (Integer id : countDishes.keySet()) {

                DISH_SERVICE_IMP.getDishById(id).ifPresent(dish -> {

                    Optional<RationComposition> rcSql = RATION_COMPOSITION_SERVICE_IMP
                            .getCompositionByRationDishFoodIntake(dayRation.getId(), foodIntake, id);

                    if (rcSql.isPresent()) {
                        rcSql.get().setDayRation(dayRation);
                        rcSql.get().setDish(dish);
                        Integer sumNumber = rcSql.get().getNumberOfDish()
                                + Math.toIntExact(countDishes.get(dish.getId()));
                        rcSql.get().setNumberOfDish(sumNumber);
                        RATION_COMPOSITION_SERVICE_IMP.updateCompositionById(rcSql.get());
                    } else {
                        RationComposition rationComposition = new RationComposition();
                        rationComposition.setDayRation(dayRation);
                        rationComposition.setFoodIntake(foodIntake);
                        rationComposition.setDish(dish);
                        rationComposition.setNumberOfDish(Math.toIntExact(countDishes.get(dish.getId())));
                        rationComposition.setCaloriesOfDish(dish.getCalories());
                        dayRation.getCompositions().add(rationComposition);
                        RATION_COMPOSITION_SERVICE_IMP.insertNewDayRation(rationComposition);
                    }
                });
            }
        }
    }

    /**
     * Get map of ration composition by food intake from list of ration composition
     *
     * @param rationCompositions {@link List}
     * @return rcMap {@link Map}
     */
    public static Map<String, List<RationComposition>> getDatRationByFoodIntake(List<RationComposition> rationCompositions) {
        Map<String, List<RationComposition>> rcMap = new LinkedHashMap<>();
        rcMap.put(FoodIntake.BREAKFAST.toString().toLowerCase(), new ArrayList<>());
        rcMap.put(FoodIntake.DINNER.toString().toLowerCase(), new ArrayList<>());
        rcMap.put(FoodIntake.SUPPER.toString().toLowerCase(), new ArrayList<>());

        rationCompositions.forEach(rc -> {
            String category = rc.getFoodIntake()
                    .toString()
                    .toLowerCase();
            rcMap.get(category).add(rc);
        });

        return rcMap;
    }

    /**
     * Get map of general dishes by food category from list of dishes
     *
     * @param dishesList {@link List}
     * @return dishes {@link Map}
     */
    public static Map<String, List<Dish>> addGeneralDishToContext(List<Dish> dishesList) {
        Map<String, List<Dish>> dishes = new LinkedHashMap<>();
        dishes.put(FoodCategory.LUNCHEON.toString().toLowerCase(), new ArrayList<>());
        dishes.put(FoodCategory.SOUP.toString().toLowerCase(), new ArrayList<>());
        dishes.put(FoodCategory.HOT.toString().toLowerCase(), new ArrayList<>());
        dishes.put(FoodCategory.DESSERT.toString().toLowerCase(), new ArrayList<>());

        dishesList.forEach(dish -> {
            String category = dish
                    .getFoodCategory()
                    .toString()
                    .toLowerCase();
            dishes.get(category).add(dish);
        });

        return dishes;
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
        request.getServletContext().setAttribute(REQUEST_USERS_ALL, allUsers);
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
        request.getServletContext().setAttribute(REQUEST_USERS_ALL, allUsers);
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
    public static Integer getMinMaxCurrentPage(Integer currentNumber, Integer maxNumber) {
        return currentNumber <= 1 ? 1 : (currentNumber > maxNumber ? maxNumber : currentNumber);
    }
}
