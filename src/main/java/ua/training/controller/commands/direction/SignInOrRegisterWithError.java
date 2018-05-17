package ua.training.controller.commands.direction;

import ua.training.constant.Pages;
import ua.training.controller.commands.Command;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: This class directs users to the login/registration page with error
 *
 * @author Zakusylo Pavlo
 */
public class SignInOrRegisterWithError implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return Pages.SIGN_OR_REGISTER;
    }
}
