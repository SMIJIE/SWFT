package ua.training.controller.commands.action.admin;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.model.dao.utility.PasswordEncoder;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.Roles;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;


/**
 * Description: This class update users parameters by 'ADMIN'
 *
 * @author Zakusylo Pavlo
 */
public class UpdateUsersByAdmin implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Attributes.REQUEST_USER);
        String emailUsers = request.getParameter(Attributes.REQUEST_EMAIL_USERS);
        String password = request.getParameter(Attributes.REQUEST_PASSWORD);
        String role = request.getParameter(Attributes.REQUEST_USER_ROLE);
        boolean flag = false;

        Optional<User> userSQL = USER_SERVICE_IMP.getOrCheckUserByEmail(emailUsers);
        if (userSQL.isPresent()) {
            if (!isNullOrEmpty(password)) {
                flag = true;
                userSQL.get()
                        .setPassword(PasswordEncoder.encodePassword(password));
            }

            if (!Roles.valueOf(role).equals(userSQL.get().getRole())) {
                flag = true;
                userSQL.get()
                        .setRole(Roles.valueOf(role));
            }
        }

        if (flag) {
            List<User> listUsers = (List<User>) request.getSession().getAttribute(Attributes.REQUEST_LIST_USERS);
            listUsers.removeIf(u -> u.getEmail().equalsIgnoreCase(emailUsers));
            USER_SERVICE_IMP.updateUserParameters(userSQL.get());
            listUsers.add(userSQL.get());
            request.getSession().setAttribute(Attributes.REQUEST_LIST_USERS, listUsers);
        }

        Integer forPage = USER_SERVICE_IMP.countUsers(user.getId());
        request.getSession().setAttribute(Attributes.REQUEST_NUMBER_USERS, forPage);

        return Pages.SHOW_USERS_AFTER_UPDATE_OR_SEARCH_REDIRECT;
    }
}
