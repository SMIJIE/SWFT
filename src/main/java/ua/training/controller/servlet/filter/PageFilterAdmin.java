package ua.training.controller.servlet.filter;

import lombok.extern.log4j.Log4j2;
import ua.training.constant.Mess;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.Roles;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Description: Non permit users with role non 'ADMIN' to admin pages
 *
 * @author Zakusylo Pavlo
 */
@WebFilter(urlPatterns = {"/swft/admin/*", "/admin/*"})
@Log4j2
public class PageFilterAdmin extends AbstractFilter {

    @Override
    protected void filter(HttpServletRequest request,
                          HttpServletResponse response,
                          FilterChain filterChain, Optional<User> user) throws IOException, ServletException {

        if (user.isPresent()) {
            if (user.get().getRole().equals(Roles.ADMIN)) {
                filterChain.doFilter(request, response);
            } else {
                log.warn(Mess.LOG_USER_GO_ADMIN_URL + " - [" + user.get().getEmail() + "]");
                request.getRequestDispatcher(USER_HOME_PAGE).forward(request, response);
            }
        } else {
            log.warn(Mess.LOG_USER_GO_ADMIN_URL + " - [" + Roles.UNKNOWN + "]");
            request.getRequestDispatcher(DEFAULT).forward(request, response);
        }
    }
}