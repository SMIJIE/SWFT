package ua.training.controller.commands.action.admin;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

/**
 * Description: This class directs 'ADMIN' to page with users list
 *
 * @author Zakusylo Pavlo
 */
public class ShowUsers implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Attributes.REQUEST_USER);

        List<User> listUsers = USER_SERVICE_IMP.getLimitUsersWithoutAdmin(user.getId(), 6, 0);
        listUsers.sort(Comparator.comparing(User::getEmail));

        Integer forPage = USER_SERVICE_IMP.countUsers(user.getId());

        request.getSession().setAttribute(Attributes.PAGE_NAME, Attributes.PAGE_USERS_LIST);
        request.getSession().setAttribute(Attributes.REQUEST_NUMBER_PAGE, 1);
        request.getSession().setAttribute(Attributes.REQUEST_NUMBER_USERS, forPage);
        request.getSession().setAttribute(Attributes.REQUEST_LIST_USERS, listUsers);

        return Pages.SHOW_USERS;
    }
}
