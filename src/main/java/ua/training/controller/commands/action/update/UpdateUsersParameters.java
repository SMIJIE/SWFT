package ua.training.controller.commands.action.update;

import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.exception.DataHttpException;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Optional;

/**
 * Description: This class change accounts users parameters
 *
 * @author Zakusylo Pavlo
 */
public class UpdateUsersParameters implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Attributes.REQUEST_USER);
        String returnPage = Pages.USER_SETTINGS_REDIRECT;
        User userHttp;

        try {
            userHttp = USER_MAPPER.extractFromHttpServletRequest(request);
        } catch (DataHttpException e) {
            request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_DATA, Attributes.PAGE_USER_WRONG_DATA);
            LOGGER.error(e.getMessage());
            return Pages.USER_SETTINGS_REDIRECT_WITH_ERROR;
        }

        Optional<User> userSQL = USER_SERVICE_IMP.getOrCheckUserByEmail(userHttp.getEmail());

        if (!userSQL.isPresent() ||
                userSQL.get().getEmail().equalsIgnoreCase(user.getEmail())) {

            userHttp.setId(user.getId());
            boolean flagPassword = userHttp.getPassword().isEmpty();
            userHttp.setPassword(flagPassword ? user.getPassword() : userHttp.getPassword());

            USER_SERVICE_IMP.updateUserParameters(userHttp);
            LOGGER.info(Mess.LOG_USER_UPDATE_PARAMETERS + "[" + user.getEmail() + "]");

            HashSet<String> allUsers = (HashSet<String>) request.getServletContext().getAttribute(Attributes.REQUEST_USERS_ALL);
            allUsers.remove(user.getEmail());
            allUsers.add(userHttp.getEmail());

            request.getSession().setAttribute(Attributes.REQUEST_USER, userHttp);
            request.getServletContext().setAttribute(Attributes.REQUEST_USERS_ALL, allUsers);
        } else {
            request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_DATA, Attributes.PAGE_USER_EXIST);
            returnPage = Pages.USER_SETTINGS_REDIRECT_WITH_ERROR;
        }

        return returnPage;
    }
}
