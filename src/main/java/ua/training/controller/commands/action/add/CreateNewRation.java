package ua.training.controller.commands.action.add;

import lombok.extern.log4j.Log4j2;
import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.exception.DataHttpException;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.RationComposition;
import ua.training.model.entity.enums.FoodIntake;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
@Log4j2
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
                log.error(e.getMessage());
                return Pages.DAY_RATION;
            }

            dayRationSql = DAY_RATION_SERVICE_IMP.checkDayRationByDateAndUserId(dayRationHttp.getDate(),
                    dayRationHttp.getUser().getId());

            if (!dayRationSql.isPresent()) {
                DAY_RATION_SERVICE_IMP.insertNewDayRation(dayRationHttp);
                dayRationHttp.setCompositions(new ArrayList<>());
                dayRationSql = Optional.of(dayRationHttp);
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

                DISH_SERVICE_IMP.getDishById(id).ifPresent(dish -> {

                    Optional<RationComposition> rcSql = RATION_COMPOSITION_SERVICE_IMP.getCompositionByRationDishFoodIntake(
                            dayRation.getId(), foodIntake, id);

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
}
