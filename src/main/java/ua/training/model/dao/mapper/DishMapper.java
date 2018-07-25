package ua.training.model.dao.mapper;

import lombok.extern.log4j.Log4j2;
import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.constant.RegexExpress;
import ua.training.controller.commands.exception.DataHttpException;
import ua.training.model.entity.Dish;
import ua.training.model.entity.enums.FoodCategory;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import static java.util.Objects.isNull;

@Log4j2
public class DishMapper implements ObjectMapper<Dish> {
    /**
     * @param req HttpServletRequest
     * @return new Dish
     */
    @Override
    public Dish extractFromHttpServletRequest(HttpServletRequest req) throws DataHttpException {
        Double weight;
        Double calories;
        Double proteins;
        Double fats;
        Double carbohydrates;

        weight = Double.valueOf(req.getParameter(Attributes.REQUEST_WEIGHT));
        calories = Double.valueOf(req.getParameter(Attributes.REQUEST_CALORIES));
        proteins = Double.valueOf(req.getParameter(Attributes.REQUEST_PROTEINS));
        fats = Double.valueOf(req.getParameter(Attributes.REQUEST_FATS));
        carbohydrates = Double.valueOf(req.getParameter(Attributes.REQUEST_CARBOHYDRATES));


        Dish dish = Dish.builder()
                .weight((int) (weight * 1000))
                .calories((int) (calories * 1000))
                .proteins((int) (proteins * 1000))
                .fats((int) (fats * 1000))
                .carbohydrates((int) (carbohydrates * 1000))
                .build();

        checkByRegex(dish);

        return dish;
    }

    /**
     * Check dish by regex
     *
     * @param dish Dish
     * @throws DataHttpException
     */
    @Override
    public void checkByRegex(Dish dish) throws DataHttpException {
        boolean flag = true;

        if (!isNull(dish.getName())) {
            if (!(dish.getName().matches(RegexExpress.DISH_NAME_US)
                    || dish.getName().matches(RegexExpress.DISH_NAME_UA))) {
                flag = false;
            }
        } else if (((double) dish.getWeight() / 1000) < 50
                || ((double) dish.getWeight() / 1000) > 999) {
            flag = false;
        } else if (((double) dish.getCalories() / 1000) < 0.001
                || ((double) dish.getCalories() / 1000) > 1000) {
            flag = false;
        } else if (((double) dish.getProteins() / 1000) < 0.001
                || ((double) dish.getProteins() / 1000) > 1000) {
            flag = false;
        } else if (((double) dish.getFats() / 1000) < 0.001
                || ((double) dish.getFats() / 1000) > 1000) {
            flag = false;
        } else if (((double) dish.getCarbohydrates() / 1000) < 0.001
                || ((double) dish.getCarbohydrates() / 1000) > 1000) {
            flag = false;
        }

        if (!flag) {
            throw new DataHttpException(Mess.LOG_DISH_HTTP_NOT_EXTRACT);
        }
    }
}
