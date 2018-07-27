package ua.training.controller.commands.action.update;

import lombok.extern.log4j.Log4j2;
import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.entity.Dish;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * Description: This class update users dish
 *
 * @author Zakusylo Pavlo
 */
@Log4j2
public class UpdateUsersDish implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Attributes.REQUEST_USER);
        String numDish = request.getParameter(Attributes.REQUEST_NUMBER_DISH);
        Integer numPage = (Integer) request.getSession().getAttribute(Attributes.REQUEST_NUMBER_PAGE);

        Optional<Dish> dishHttp = CommandsUtil.extractDishFromHTTP(request);
        if (!dishHttp.isPresent()) {
            return Pages.MENU_USERS_EDIT_REDIRECT_WITH_ERROR;
        }

        DISH_SERVICE_IMP.getDishById(Integer.valueOf(numDish))
                .ifPresent(d -> updateDishParameters(dishHttp.get(), d));

        List<Dish> listUsers = DISH_SERVICE_IMP.getLimitDishesByUserId(user.getId(), 5, 5 * (numPage - 1));
        CommandsUtil.sortListByAnnotationFields(listUsers);

        Integer forPage = DISH_SERVICE_IMP.counterDishByUserId(user.getId());

        request.getSession().setAttribute(Attributes.REQUEST_NUMBER_USER_DISH, forPage);
        request.getSession().setAttribute(Attributes.REQUEST_USERS_DISHES, listUsers);

        return Pages.MENU_USERS_EDIT_AFTER_UPDATE_REDIRECT;
    }

    private void updateDishParameters(Dish dishHttp, Dish currentDish) {
        currentDish.setWeight(dishHttp.getWeight());
        currentDish.setCalories(dishHttp.getCalories());
        currentDish.setProteins(dishHttp.getProteins());
        currentDish.setFats(dishHttp.getFats());
        currentDish.setCarbohydrates(dishHttp.getCarbohydrates());

        DISH_SERVICE_IMP.updateDishParameters(currentDish);
    }
}
