package ua.training.controller.commands.action.statement;

import lombok.extern.log4j.Log4j2;
import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.exception.DataHttpException;
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

        User userHttp;
        try {
            userHttp = USER_MAPPER.extractFromHttpServletRequest(request);
        } catch (DataHttpException e) {
            request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_EMAIL, Attributes.PAGE_USER_WRONG_DATA);
            log.error(e.getMessage());
            return returnPage;
        }

        Optional<User> userSQL = USER_SERVICE_IMP.getOrCheckUserByEmail(userHttp.getEmail());

        if (!userSQL.isPresent()) {
            USER_SERVICE_IMP.registerNewUser(userHttp);
            userSQL = USER_SERVICE_IMP.getOrCheckUserByEmail(userHttp.getEmail());
            log.info(Mess.LOG_USER_REGISTERED + "[" + userSQL.get().getEmail() + "]");
            returnPage = CommandsUtil.openUsersSession(request, userSQL.get());
        } else {
            request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_EMAIL, Attributes.PAGE_USER_EXIST);
        }

        return returnPage;
    }
}
