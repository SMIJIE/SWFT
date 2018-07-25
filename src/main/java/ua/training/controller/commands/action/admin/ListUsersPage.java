package ua.training.controller.commands.action.admin;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;

/**
 * Description: This class flipping pages list of users
 *
 * @author Zakusylo Pavlo
 */
public class ListUsersPage implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Attributes.REQUEST_USER);
        String attrPage = request.getParameter(Attributes.REQUEST_NUMBER_PAGE);
        Integer sessionPage = (Integer) request.getSession().getAttribute(Attributes.REQUEST_NUMBER_PAGE);

        Integer numPage = isNullOrEmpty(attrPage) ? sessionPage : Integer.valueOf(attrPage);

        Integer numberUsers = USER_SERVICE_IMP.countUsers(user.getId());
        Integer maxPage = CommandsUtil.getCountPages(numberUsers, 6);
        Integer pageForSQL = CommandsUtil.getPageForSQL(numPage, maxPage);

        List<User> listUsers = USER_SERVICE_IMP.getLimitUsersWithoutAdmin(user.getId(), 6, 6 * (pageForSQL - 1));
        listUsers.sort(Comparator.comparing(User::getEmail));

        request.getSession().setAttribute(Attributes.REQUEST_LIST_USERS, listUsers);
        request.getSession().setAttribute(Attributes.REQUEST_NUMBER_USERS, numberUsers);
        request.getSession().setAttribute(Attributes.REQUEST_NUMBER_PAGE, pageForSQL);

        return Pages.SHOW_USERS;
    }
}
