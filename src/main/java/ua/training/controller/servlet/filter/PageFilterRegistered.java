package ua.training.controller.servlet.filter;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
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
@WebFilter(urlPatterns = {"/swft/signInOrRegister", "/swft/signInOrRegisterWithError", "/swft/registerNewUser",
        "/swft/logIn"})
public class PageFilterRegistered extends AbstractFilter {

    @Override
    protected void filter(HttpServletRequest request,
                          HttpServletResponse response,
                          FilterChain filterChain, Optional<User> user) throws IOException, ServletException {

        if (user.isPresent()) {
            request.getSession().setAttribute(Attributes.PAGE_NAME, Attributes.PAGE_GENERAL);
            request.getRequestDispatcher(Pages.HOME).forward(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}