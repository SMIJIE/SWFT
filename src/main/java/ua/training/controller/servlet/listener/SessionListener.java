package ua.training.controller.servlet.listener;

import ua.training.constant.Attributes;
import ua.training.model.entity.User;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;
import java.util.Optional;

/**
 * Description: Listener for session after timeout
 *
 * @author Zakusylo Pavlo
 */
@WebListener
public class SessionListener implements HttpSessionListener {
    private HashSet<String> allUsers;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        allUsers = (HashSet<String>) httpSessionEvent.getSession().getServletContext().getAttribute(Attributes.REQUEST_USERS_ALL);
        Optional.ofNullable((User) httpSessionEvent.getSession().getAttribute(Attributes.REQUEST_USER))
                .ifPresent(u -> allUsers.remove(u.getEmail()));
        httpSessionEvent.getSession().getServletContext().setAttribute(Attributes.REQUEST_USERS_ALL, allUsers);
    }
}
