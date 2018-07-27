package ua.training.controller.commands.action.update;

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
 * Description: This class change accounts users parameters
 *
 * @author Zakusylo Pavlo
 */
@Log4j2
public class UpdateUsersParameters implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Attributes.REQUEST_USER);
        String returnPage = Pages.USER_SETTINGS_REDIRECT;

        Optional<User> userHttp = CommandsUtil.extractUserFromHTTP(request);
        if (!userHttp.isPresent()) {
            request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_DATA, Attributes.PAGE_USER_WRONG_DATA);
            return Pages.USER_SETTINGS_REDIRECT_WITH_ERROR;
        }

        Optional<User> userSQL = USER_SERVICE_IMP.getOrCheckUserByEmail(userHttp.get().getEmail());
        if (!userSQL.isPresent() ||
                userSQL.get().getEmail().equalsIgnoreCase(user.getEmail())) {

            updateUserParameters(userHttp.get(), user);
            log.info(Mess.LOG_USER_UPDATE_PARAMETERS + "[" + user.getEmail() + "]");

            CommandsUtil.deleteUsersFromContext(request, user.getEmail());
            CommandsUtil.addUsersToContext(request, userHttp.get().getEmail());

            request.getSession().setAttribute(Attributes.REQUEST_USER, userHttp.get());
        } else {
            request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_DATA, Attributes.PAGE_USER_EXIST);
            returnPage = Pages.USER_SETTINGS_REDIRECT_WITH_ERROR;
        }

        return returnPage;
    }

    private void updateUserParameters(User userHttp, User userCurrent) {
        userCurrent.setName(userHttp.getName());
        userCurrent.setDob(userHttp.getDob());
        userCurrent.setEmail(userHttp.getEmail());
        userCurrent.setRole(userHttp.getRole());
        userCurrent.setHeight(userHttp.getHeight());
        userCurrent.setWeight(userHttp.getWeight());
        userCurrent.setWeightDesired(userHttp.getWeightDesired());
        userCurrent.setLifeStyleCoefficient(userHttp.getLifeStyleCoefficient());

        boolean flagPassword = userHttp.getPassword().isEmpty();
        userCurrent.setPassword(flagPassword ?
                userCurrent.getPassword() : userHttp.getPassword());

        USER_SERVICE_IMP.updateUserParameters(userCurrent);
    }
}
