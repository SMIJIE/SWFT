package ua.training.controller.commands.action.admin;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Description: This class search for 'ADMIN' users by email
 *
 * @author Zakusylo Pavlo
 */
public class SearchUsersByEmail implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Attributes.REQUEST_USER);
        String email = request.getParameter(Attributes.REQUEST_EMAIL).toLowerCase();

        List<User> listUsers = new ArrayList<>();

        if (!user.getEmail().equalsIgnoreCase(email)) {
            Optional<User> userSQL = USER_SERVICE_IMP.getOrCheckUserByEmail(email);
            userSQL.ifPresent(listUsers::add);
        }

        listUsers.sort(Comparator.comparing(User::getEmail));

        Integer forPage = USER_SERVICE_IMP.countUsers(user.getId());

        request.getSession().setAttribute(Attributes.REQUEST_NUMBER_PAGE, 1);
        request.getSession().setAttribute(Attributes.REQUEST_LIST_USERS, listUsers);
        request.getSession().setAttribute(Attributes.REQUEST_NUMBER_USERS, forPage);

        return Pages.SHOW_USERS_AFTER_UPDATE_OR_SEARCH_REDIRECT;
    }
}
