package ua.training.controller.commands.direction;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: This class directs users to their menu edit page after update
 *
 * @author Zakusylo Pavlo
 */
public class MenuUsersEditAfterUpdate implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute(Attributes.PAGE_NAME, Attributes.PAGE_MENU_EDIT);
        request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_DATA, Attributes.PAGE_USER_NON_ERROR_DATA);
        return Pages.MENU_USERS_EDIT;
    }
}
