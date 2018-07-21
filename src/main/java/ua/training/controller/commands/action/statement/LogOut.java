package ua.training.controller.commands.action.statement;

import lombok.extern.log4j.Log4j2;
import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: This class log out user
 *
 * @author Zakusylo Pavlo
 */
@Log4j2
public class LogOut implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Attributes.REQUEST_USER);

        CommandsUtil.deleteUsersFromContext(request, user.getEmail());

        request.getSession().setAttribute(Attributes.REQUEST_USER, null);
        request.getSession().setAttribute(Attributes.PAGE_NAME, Attributes.PAGE_DEMONSTRATION);
        log.info(Mess.LOG_USER_LOGGED_OUT + "[" + user.getEmail() + "]");

        return Pages.SIGN_OR_REGISTER_REDIRECT;
    }
}
