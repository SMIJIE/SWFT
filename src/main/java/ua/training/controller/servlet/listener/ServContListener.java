package ua.training.controller.servlet.listener;

import ua.training.constant.Attributes;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.dao.service.implementation.DishServiceImp;
import ua.training.model.entity.Dish;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Description: First initialization of data before Servlets
 *
 * @author Zakusylo Pavlo
 */
@WebListener
public class ServContListener implements ServletContextListener {
    private CopyOnWriteArraySet allUsers = new CopyOnWriteArraySet();
    private DishServiceImp dishServiceImp = new DishServiceImp();

    /**
     * Initialize 'set' of users and 'list' of general dishes
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().setAttribute(Attributes.REQUEST_USERS_ALL, allUsers);

        List<Dish> general = dishServiceImp.getGeneralDishes();

        if (!general.isEmpty()) {
            CommandsUtil.sortListByAnnotationFields(general);
            servletContextEvent.getServletContext().setAttribute(Attributes.REQUEST_GENERAL_DISHES, general);
        }
    }

    /**
     * Destroy 'set' of users and 'list' of general dishes
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        allUsers = null;
        dishServiceImp = null;
    }
}
