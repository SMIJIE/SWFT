package ua.training.controller.servlet.filter;

import lombok.extern.log4j.Log4j2;
import ua.training.constant.Mess;
import ua.training.model.entity.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Description: Non permit registered users to demonstration pages
 *
 * @author Zakusylo Pavlo
 */
@WebFilter(urlPatterns = {"/swft/signInOrRegister", "/signInOrRegister", "/swft/registerNewUser", "/registerNewUser",
        "/swft/logIn", "/logIn", "/swft/", "/"})
@Log4j2
public class PageFilterRegistered extends AbstractFilter {

    @Override
    protected void filter(HttpServletRequest request,
                          HttpServletResponse response,
                          FilterChain filterChain, Optional<User> user) throws IOException, ServletException {

        if (user.isPresent()) {
            log.warn(Mess.LOG_USER_GO_NON_REGISTERED_URL + " - [" + user.get().getEmail() + "]");
            request.getRequestDispatcher(USER_HOME_PAGE).forward(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}