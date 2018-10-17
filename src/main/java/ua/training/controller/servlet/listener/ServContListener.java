package ua.training.controller.servlet.listener;

import ua.training.constant.Attributes;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.dao.service.implementation.DishServiceImp;
import ua.training.model.entity.Dish;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Description: First initialization of data before Servlets
 *
 * @author Zakusylo Pavlo
 */
@WebListener
public class ServContListener implements ServletContextListener, Attributes {
    private CopyOnWriteArraySet allUsers = new CopyOnWriteArraySet();
    private DishServiceImp dishServiceImp = new DishServiceImp();

    /**
     * Initialize 'set' of users and 'list' of general dishes
     *
     * @param servletContextEvent {@link ServletContextEvent}
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().setAttribute(REQUEST_USERS_ALL, allUsers);

        CommandsUtil.addGeneralDishToContext(servletContextEvent.getServletContext());

    }

    /**
     * Destroy 'set' of users and 'list' of general dishes
     *
     * @param servletContextEvent {@link ServletContextEvent}
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        allUsers = null;
        dishServiceImp = null;
    }
}
