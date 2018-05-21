package ua.training.controller.servlet.context;

import ua.training.constant.Attributes;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.dao.service.implementation.DishServiceImp;
import ua.training.model.entity.Dish;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * Description: First initialization of data before Servlets
 *
 * @author Zakusylo Pavlo
 */
@WebListener
public class ServletContext implements ServletContextListener {
    private HashSet<String> allUsers = new HashSet<>();
    private DishServiceImp dishServiceImp = new DishServiceImp();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().setAttribute(Attributes.REQUEST_USERS_ALL, allUsers);
        servletContextEvent.getServletContext().setAttribute(Attributes.PAGE_NAME, Attributes.PAGE_DEMONSTRATION);
        List<Dish> general = dishServiceImp.getGeneralDishes();

        if (!general.isEmpty()) {
            CommandsUtil.sortListByAnnotationFields(general);
            servletContextEvent.getServletContext().setAttribute(Attributes.REQUEST_GENERAL_DISHES, general);
        }

        servletContextEvent.getServletContext().setAttribute(Attributes.REQUEST_LOCALE_LANGUAGE, new Locale("en", "US"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        allUsers = null;
        dishServiceImp = null;
    }
}
