package ua.training.controller.commands.action.add;

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
 * Description: This class add users dish
 *
 * @author Zakusylo Pavlo
 */
@Log4j2
public class AddNewDish implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Attributes.REQUEST_USER);

        Optional<Dish> dishHttp = CommandsUtil.extractDishFromHTTP(request);
        if (!dishHttp.isPresent()) {
            return Pages.MENU_USERS_EDIT_REDIRECT_WITH_ERROR;
        }

        dishHttp.get().setUser(user);
        dishHttp.get().setGeneralFood(false);
        DISH_SERVICE_IMP.insertNewDish(dishHttp.get());
        user.getListDishes().add(dishHttp.get());

        List<Dish> listUsers = DISH_SERVICE_IMP.getLimitDishesByUserId(user.getId(), 6, 0);
        CommandsUtil.sortListByAnnotationFields(listUsers);

        Integer forPage = DISH_SERVICE_IMP.counterDishByUserId(user.getId());

        request.getSession().setAttribute(Attributes.REQUEST_USERS_DISHES, listUsers);
        request.getSession().setAttribute(Attributes.REQUEST_NUMBER_USER_DISH, forPage);

        return Pages.MENU_USERS_EDIT_REDIRECT;
    }
}
