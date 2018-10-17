package ua.training.controller.servlet.filter;

import ua.training.constant.Api;
import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * Description: Abstract class for all filters
 *
 * @author Zakusylo Pavlo
 * @see Filter
 * @see FilterChain
 */
public abstract class AbstractFilter implements Filter,Attributes, Api {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        Optional<User> user = Optional.ofNullable((User) session.getAttribute(REQUEST_USER));

        filter(request, response, filterChain, user);
    }

    @Override
    public void destroy() {
    }

    protected abstract void filter(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain,
                                   Optional<User> user) throws IOException, ServletException;
}
