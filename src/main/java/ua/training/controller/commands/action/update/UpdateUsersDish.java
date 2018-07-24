package ua.training.controller.commands.action.update;

import lombok.extern.log4j.Log4j2;
import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.exception.DataHttpException;
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

        Dish dishHttp;
        try {
            dishHttp = DISH_MAPPER.extractFromHttpServletRequest(request);
        } catch (DataHttpException e) {
            request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_DATA, Attributes.PAGE_USER_WRONG_DATA);
            log.error(e.getMessage());
            return Pages.MENU_USERS_EDIT_REDIRECT_WITH_ERROR;
        }

        Optional<Dish> dishSQL = DISH_SERVICE_IMP.getDishById(Integer.valueOf(numDish));
        if (dishSQL.isPresent()) {

            dishHttp.setId(dishSQL.get().getId());
            dishHttp.setFoodCategory(dishSQL.get().getFoodCategory());
            dishHttp.setName(dishSQL.get().getName());
            dishHttp.setUser(user);
            DISH_SERVICE_IMP.updateDishParameters(dishHttp);

            Integer numPage = (Integer) request.getSession().getAttribute(Attributes.REQUEST_NUMBER_PAGE);

            List<Dish> listUsers = DISH_SERVICE_IMP.getLimitDishesByUserId(user.getId(), 5, 5 * (numPage - 1));
            CommandsUtil.sortListByAnnotationFields(listUsers);

            Integer forPage = DISH_SERVICE_IMP.counterDishByUserId(user.getId());

            request.getSession().setAttribute(Attributes.REQUEST_NUMBER_USER_DISH, forPage);
            request.getSession().setAttribute(Attributes.REQUEST_USERS_DISHES, listUsers);
        }

        return Pages.MENU_USERS_EDIT_AFTER_UPDATE_REDIRECT;
    }
}
