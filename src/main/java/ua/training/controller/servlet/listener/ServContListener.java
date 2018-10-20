package ua.training.controller.servlet.listener;

import ua.training.constant.Attributes;
import ua.training.controller.controllers.utility.ControllerUtil;
import ua.training.model.dao.service.implementation.DishServiceImp;
import ua.training.model.entity.Dish;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
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

        List<Dish> generalList = dishServiceImp.getGeneralDishes();
        ControllerUtil.sortListByAnnotationFields(generalList);

        Map<String, List<Dish>> dishes = ControllerUtil.addGeneralDishToContext(generalList);

        if (!generalList.isEmpty()) {
            servletContextEvent.getServletContext().setAttribute(REQUEST_GENERAL_DISHES, dishes);
        }
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
