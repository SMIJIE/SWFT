package ua.training.controller.commands.direction;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: This class directs users to the login/registration page
 *
 * @author Zakusylo Pavlo
 */
public class SignInOrRegister implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_EMAIL, Attributes.PAGE_USER_NON_EMAIL);
        return Pages.SIGN_OR_REGISTER;
    }
}
