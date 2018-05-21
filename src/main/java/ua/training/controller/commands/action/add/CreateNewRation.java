package ua.training.controller.commands.action.add;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.exception.DataHttpException;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.Dish;
import ua.training.model.entity.RationComposition;
import ua.training.model.entity.enums.FoodIntake;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

/**
 * Description: This class create user rations
 *
 * @author Zakusylo Pavlo
 */
public class CreateNewRation implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        Optional<DayRation> dayRationSql;

        String[] breakfast = request.getParameterValues(Attributes.REQUEST_SELECT_BREAKFAST);
        String[] dinner = request.getParameterValues(Attributes.REQUEST_SELECT_DINNER);
        String[] supper = request.getParameterValues(Attributes.REQUEST_SELECT_SUPPER);


        if (!(isNull(breakfast) && isNull(dinner) && isNull(supper))) {
            DayRation dayRationHttp;
            try {
                dayRationHttp = DAY_RATION.extractFromHttpServletRequest(request);
            } catch (DataHttpException e) {
                request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_DATA, Attributes.PAGE_USER_WRONG_DATA);
                LOGGER.error(e.getMessage());
                return Pages.DAY_RATION;
            }

            dayRationSql = DAY_RATION_SERVICE_IMP.checkDayRationByDateAndUserId(dayRationHttp.getDate(),
                    dayRationHttp.getUser().getId());

            if (!dayRationSql.isPresent()) {
                DAY_RATION_SERVICE_IMP.insertNewDayRation(dayRationHttp);
                dayRationSql = DAY_RATION_SERVICE_IMP.checkDayRationByDateAndUserId(dayRationHttp.getDate(),
                        dayRationHttp.getUser().getId());
            }

            createRationComposition(breakfast, dayRationSql.get(), FoodIntake.BREAKFAST);
            createRationComposition(dinner, dayRationSql.get(), FoodIntake.DINNER);
            createRationComposition(supper, dayRationSql.get(), FoodIntake.SUPPER);
        }

        return Pages.DAY_RATION_REDIRECT;
    }

    /**
     * Create ration composition from array of dishes
     *
     * @param idDishes   String[]
     * @param dayRation  DayRation
     * @param foodIntake FoodIntake
     */
    private void createRationComposition(String[] idDishes, DayRation dayRation, FoodIntake foodIntake) {
        if (!isNull(idDishes)) {
            Integer[] idForSupper = CommandsUtil.stringArrayToInteger(idDishes);

            Map<Integer, Long> countDishes = Arrays.stream(idForSupper)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            for (Integer id : countDishes.keySet()) {
                Optional<Dish> dish = DISH_SERVICE_IMP.getDishById(id);
                Optional<RationComposition> rationCompositionSql;

                if (dish.isPresent()) {
                    rationCompositionSql = RATION_COMPOSITION_SERVICE_IMP.getCompositionByRationDishFoodIntake(
                            dayRation.getId(), foodIntake, id);

                    if (rationCompositionSql.isPresent()) {
                        rationCompositionSql.get().setDayRation(dayRation);
                        rationCompositionSql.get().setDish(dish.get());
                        Integer sumNumber = rationCompositionSql.get().getNumberOfDish()
                                + Math.toIntExact(countDishes.get(dish.get().getId()));
                        rationCompositionSql.get().setNumberOfDish(sumNumber);
                        RATION_COMPOSITION_SERVICE_IMP.updateCompositionById(rationCompositionSql.get());
                    } else {
                        RationComposition rationComposition = new RationComposition();
                        rationComposition.setDayRation(dayRation);
                        rationComposition.setFoodIntake(foodIntake);
                        rationComposition.setDish(dish.get());
                        rationComposition.setNumberOfDish(Math.toIntExact(countDishes.get(dish.get().getId())));
                        rationComposition.setCaloriesOfDish(dish.get().getCalories());
                        RATION_COMPOSITION_SERVICE_IMP.insertNewDayRation(rationComposition);
                    }
                }
            }
        }
    }
}
