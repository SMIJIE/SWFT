package ua.training.controller.servlet.listener;

import ua.training.constant.Attributes;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.dao.service.implementation.DishServiceImp;
import ua.training.model.entity.Dish;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
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

        List<Dish> generalList = dishServiceImp.getGeneralDishes();
        CommandsUtil.sortListByAnnotationFields(generalList);

        Map<String, List<Dish>> generalMap = new HashMap<>();
        generalList.forEach(dish -> {
            String category = dish
                    .getFoodCategory()
                    .toString()
                    .toLowerCase();
            generalMap.putIfAbsent(category, new ArrayList<>());
            generalMap.get(category).add(dish);
        });

        if (!generalList.isEmpty()) {
            CommandsUtil.sortListByAnnotationFields(generalList);
            servletContextEvent.getServletContext().setAttribute(REQUEST_GENERAL_DISHES, generalMap);
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
