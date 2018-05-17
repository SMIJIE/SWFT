package ua.training.controller.commands.action.purge;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.utils.CommandsUtil;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: This class delete users dishes
 *
 * @author Zakusylo Pavlo
 */
public class DeleteUsersMenuItem implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Attributes.REQUEST_USER);

        String[] arrDish = request
                .getParameter(Attributes.REQUEST_ARR_DISH)
                .split(",");

        Integer[] idDishes = CommandsUtil.stringArrayToInteger(arrDish);

        DISH_SERVICE_IMP.deleteArrayDishesByIdAndUser(idDishes, user.getId());

        return Pages.MENU_USERS_EDIT_REDIRECT;
    }
}
