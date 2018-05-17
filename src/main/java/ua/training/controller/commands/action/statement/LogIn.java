package ua.training.controller.commands.action.statement;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.utils.CommandsUtil;
import ua.training.model.dao.utils.PasswordEncoder;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Description: This class authorizes users
 *
 * @author Zakusylo Pavlo
 */
public class LogIn implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter(Attributes.REQUEST_EMAIL).toLowerCase();
        String password = PasswordEncoder.encodePassword(request.getParameter(Attributes.REQUEST_PASSWORD));
        String returnPage = Pages.SIGN_OR_REGISTER_WITH_ERROR;

        Optional<User> userSQL = USER_SERVICE_IMP.getOrCheckUserByEmail(email);

        if (userSQL.isPresent() && userSQL.get().getPassword().equals(password)) {
            returnPage = CommandsUtil.openUsersSession(request, userSQL.get());
        } else if (userSQL.isPresent() && !userSQL.get().getPassword().equals(password)) {
            request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_EMAIL, Attributes.PAGE_USER_WRONG_PASSWORD);
        } else {
            request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_EMAIL, Attributes.PAGE_USER_NOT_EXIST);
        }

        return returnPage;
    }
}
