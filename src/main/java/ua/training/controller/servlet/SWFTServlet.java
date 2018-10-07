package ua.training.controller.servlet;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ua.training.config.AppConfig;
import ua.training.controller.servlet.filter.EncodingFilter;
import ua.training.controller.servlet.listener.ServContListener;
import ua.training.controller.servlet.listener.SessionListener;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Description: This is the SWFTServlet class
 *
 * @author Zakusylo Pavlo
 */
public class SWFTServlet extends
        AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/swft/*", "/"};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new EncodingFilter()};
    }

    /**
     * Add listeners to servlet context
     *
     * @param servletContext ServletContext
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.addListener(new SessionListener());
        servletContext.addListener(new ServContListener());
    }
}

