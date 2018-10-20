package ua.training.controller.mapper;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import ua.training.controller.exception.DataHttpException;
import ua.training.model.entity.Dish;
import ua.training.model.entity.form.FormDish;

@Component
public class DishMapper implements ObjectMapper<Dish, FormDish> {
    /**
     * Extract entity 'Dish' from HTTP form
     *
     * @param formDish     {@link FormDish}
     * @param modelAndView {@link ModelAndView}
     * @return new {@link Dish}
     * @throws DataHttpException
     */
    @Override
    public Dish extractEntityFromHttpForm(FormDish formDish, ModelAndView modelAndView) throws DataHttpException {
        int weight;
        int calories;
        int proteins;
        int fats;
        int carbohydrates;

        weight = (int) (formDish.getWeight() * 1000);
        calories = (int) (formDish.getCalories() * 1000);
        proteins = (int) (formDish.getProteins() * 1000);
        fats = (int) (formDish.getFats() * 1000);
        carbohydrates = (int) (formDish.getCarbohydrates() * 1000);


        Dish dish = Dish.builder()
                .name(formDish.getName())
                .foodCategory(formDish.getFoodCategory())
                .weight(weight)
                .calories(calories)
                .proteins(proteins)
                .fats(fats)
                .carbohydrates(carbohydrates)
                .build();

        checkByRegex(dish, modelAndView);

        return dish;
    }

    /**
     * Check dish fields by regex
     *
     * @param dish         {@link Dish}
     * @param modelAndView {@link ModelAndView}
     * @throws DataHttpException
     */
    @Override
    public void checkByRegex(Dish dish, ModelAndView modelAndView) throws DataHttpException {
        boolean flag = true;

        if (!(dish.getName().matches(DISH_NAME_PATTERN))) {
            modelAndView.addObject(PAGE_USER_ERROR, DISH_VALID_NAME_WRONG);
            flag = false;
        } else if (dish.getWeight() < 50 * 1000 || dish.getWeight() > 999 * 1000) {
            modelAndView.addObject(PAGE_USER_ERROR, DISH_VALID_WEIGHT_SIZE);
            flag = false;
        } else if (dish.getCalories() < 0.001 * 1000 || dish.getCalories() > 1000 * 1000) {
            modelAndView.addObject(PAGE_USER_ERROR, DISH_VALID_CALORIES_SIZE);
            flag = false;
        } else if (dish.getProteins() < 0.001 * 1000 || dish.getProteins() > 1000 * 1000) {
            modelAndView.addObject(PAGE_USER_ERROR, DISH_VALID_PROTEINS_SIZE);
            flag = false;
        } else if (dish.getFats() < 0.001 * 1000 || dish.getFats() > 1000 * 1000) {
            modelAndView.addObject(PAGE_USER_ERROR, DISH_VALID_FATS_SIZE);
            flag = false;
        } else if (dish.getCarbohydrates() < 0.001 * 1000 || dish.getCarbohydrates() > 1000 * 1000) {
            modelAndView.addObject(PAGE_USER_ERROR, DISH_VALID_CARBOHYDRATES_SIZE);
            flag = false;
        }

        if (!flag) {
            throw new DataHttpException(LOG_DISH_HTTP_NOT_EXTRACT);
        }
    }
}
