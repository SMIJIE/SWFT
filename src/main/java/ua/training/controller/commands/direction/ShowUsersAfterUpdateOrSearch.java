package ua.training.controller.commands.direction;

import ua.training.constant.Pages;
import ua.training.controller.commands.Command;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: This class directs 'ADMIN' to page with users list after update
 *
 * @author Zakusylo Pavlo
 */
public class ShowUsersAfterUpdateOrSearch implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return Pages.SHOW_USERS;
    }
}
