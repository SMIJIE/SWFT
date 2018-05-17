package ua.training.model.dao.mapper;

import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.constant.RegexExpress;
import ua.training.controller.commands.exception.DataHttpException;
import ua.training.controller.commands.exception.DataSqlException;
import ua.training.model.entity.Dish;
import ua.training.model.entity.enums.FoodCategory;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import static java.util.Objects.isNull;

public class DishMapper implements ObjectMapper<Dish> {
    /**
     * @param rs ResultSet
     * @return new Dish
     */
    @Override
    public Dish extractFromResultSet(ResultSet rs) {
        Integer id;
        FoodCategory category;
        String name;
        Integer weight;
        Integer calories;
        Integer proteins;
        Integer fats;
        Integer carbohydrates;
        Boolean generalFood;

        try {
            id = rs.getInt(Attributes.SQL_DISH_ID);
            category = FoodCategory.valueOf(rs.getString(Attributes.REQUEST_CATEGORY));
            name = rs.getString(Attributes.REQUEST_NAME);
            weight = rs.getInt(Attributes.REQUEST_WEIGHT);
            calories = rs.getInt(Attributes.REQUEST_CALORIES);
            proteins = rs.getInt(Attributes.REQUEST_PROTEINS);
            fats = rs.getInt(Attributes.REQUEST_FATS);
            carbohydrates = rs.getInt(Attributes.REQUEST_CARBOHYDRATES);
            generalFood = rs.getBoolean(Attributes.REQUEST_GENERAL_FOOD);

        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DISH_RS_NOT_EXTRACT);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }

        return Dish.builder()
                .id(id)
                .foodCategory(category)
                .name(name)
                .weight(weight)
                .calories(calories)
                .proteins(proteins)
                .fats(fats)
                .carbohydrates(carbohydrates)
                .generalFood(generalFood)
                .build();
    }

    /**
     * @param req HttpServletRequest
     * @return new Dish
     */
    @Override
    public Dish extractFromHttpServletRequest(HttpServletRequest req) throws DataHttpException {
        FoodCategory category;
        String name;
        Double weight;
        Double calories;
        Double proteins;
        Double fats;
        Double carbohydrates;

        name = req.getParameter(Attributes.REQUEST_NAME);
        weight = Double.valueOf(req.getParameter(Attributes.REQUEST_WEIGHT));
        calories = Double.valueOf(req.getParameter(Attributes.REQUEST_CALORIES));
        proteins = Double.valueOf(req.getParameter(Attributes.REQUEST_PROTEINS));
        fats = Double.valueOf(req.getParameter(Attributes.REQUEST_FATS));
        carbohydrates = Double.valueOf(req.getParameter(Attributes.REQUEST_CARBOHYDRATES));

        Dish dish = Dish.builder()
                .name(name)
                .weight((int) (weight * 1000))
                .calories((int) (calories * 1000))
                .proteins((int) (proteins * 1000))
                .fats((int) (fats * 1000))
                .carbohydrates((int) (carbohydrates * 1000))
                .build();

        Optional<String> tempCategory = Optional.ofNullable(req.getParameter(Attributes.REQUEST_CATEGORY));
        if (tempCategory.isPresent() && !isNullOrEmpty(tempCategory.get())) {
            category = FoodCategory.valueOf(req.getParameter(Attributes.REQUEST_CATEGORY));
            dish.setFoodCategory(category);
        }

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

    /**
     * @param cache Map<Integer, Dish>
     * @param dish  Dish
     * @return new Dish or exist Dish
     */
    @Override
    public Dish makeUnique(Map<Integer, Dish> cache, Dish dish) {
        cache.putIfAbsent(dish.getId(), dish);
        return cache.get(dish.getId());
    }
}
