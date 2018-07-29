package ua.training.controller.commands.direction;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: This class directs 'ADMIN' to general menu edit page with error
 *
 * @author Zakusylo Pavlo
 */
public class MenuGeneralEditWithError implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_DATA, Attributes.PAGE_USER_WRONG_DATA);
        return Pages.MENU_GENERAL_EDIT;
    }
}
