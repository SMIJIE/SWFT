package ua.training.controller.commands.action.statement;

import lombok.extern.log4j.Log4j2;
import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Description: This class register new user
 *
 * @author Zakusylo Pavlo
 */
@Log4j2
public class RegisterNewUser implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String returnPage = Pages.SIGN_OR_REGISTER_WITH_ERROR;

        Optional<User> userHttp = CommandsUtil.extractUserFromHTTP(request);
        if (!userHttp.isPresent()) {
            request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_EMAIL, Attributes.PAGE_USER_WRONG_DATA);
            return returnPage;
        }

        Optional<User> userSQL = USER_SERVICE_IMP.getOrCheckUserByEmail(userHttp.get().getEmail());

        if (!userSQL.isPresent()) {
            USER_SERVICE_IMP.registerNewUser(userHttp.get());
            log.info(Mess.LOG_USER_REGISTERED + "[" + userHttp.get().getEmail() + "]");
            returnPage = CommandsUtil.openUsersSession(request, userHttp.get());
        } else {
            request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_EMAIL, Attributes.PAGE_USER_EXIST);
        }

        return returnPage;
    }
}
