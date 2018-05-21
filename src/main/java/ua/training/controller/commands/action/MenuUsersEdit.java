package ua.training.controller.commands.action;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.entity.Dish;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Description: This class directs users to their menu edit page
 *
 * @author Zakusylo Pavlo
 */
public class MenuUsersEdit implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Attributes.REQUEST_USER);

        List<Dish> usersDish = DISH_SERVICE_IMP.getLimitDishesByUserId(user.getId(), 5, 0);
        CommandsUtil.sortListByAnnotationFields(usersDish);

        Integer forPage = DISH_SERVICE_IMP.counterDishByUserId(user.getId());

        request.getSession().setAttribute(Attributes.PAGE_NAME, Attributes.PAGE_MENU_EDIT);
        request.getSession().setAttribute(Attributes.REQUEST_NUMBER_PAGE, 1);
        request.getSession().setAttribute(Attributes.REQUEST_NUMBER_USER_DISH, forPage);
        request.getSession().setAttribute(Attributes.REQUEST_USERS_DISHES, usersDish);
        request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_DATA, Attributes.PAGE_USER_NON_ERROR_DATA);

        return Pages.MENU_USERS_EDIT;
    }
}
