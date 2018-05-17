package ua.training.controller.commands.direction;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: This class directs users to general list of dish
 *
 * @author Zakusylo Pavlo
 */
public class Menu implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute(Attributes.PAGE_NAME, Attributes.PAGE_MENU);
        return Pages.MENU;
    }
}
