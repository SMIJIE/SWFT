package ua.training.controller.servlet.listener;

import ua.training.constant.Attributes;
import ua.training.model.entity.User;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Description: Session listener
 *
 * @author Zakusylo Pavlo
 */
@WebListener
public class SessionListener implements HttpSessionListener {
    private CopyOnWriteArraySet<String> allUsers;

    /**
     * Set session timeout
     *
     * @param httpSessionEvent HttpSessionEvent
     */
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setMaxInactiveInterval(30 * 60);
    }

    /**
     * Delete user after session timeout
     *
     * @param httpSessionEvent HttpSessionEvent
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        allUsers = (CopyOnWriteArraySet<String>) httpSessionEvent
                .getSession()
                .getServletContext()
                .getAttribute(Attributes.REQUEST_USERS_ALL);

        Optional.ofNullable((User) httpSessionEvent
                .getSession()
                .getAttribute(Attributes.REQUEST_USER))
                .ifPresent(u -> allUsers.remove(u.getEmail()));

        httpSessionEvent.getSession().getServletContext().setAttribute(Attributes.REQUEST_USERS_ALL, allUsers);
    }
}
