package ua.training.controller.servlet.filter;

import ua.training.model.entity.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Description: Set Character Encoding
 *
 * @author Zakusylo Pavlo
 */
@WebFilter(urlPatterns = "/*")
public class EncodingFilter extends AbstractFilter {

    @Override
    protected void filter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Optional<User> user) throws IOException, ServletException {
        response.setContentType(HTML_TEXT);
        response.setCharacterEncoding(HTML_ENCODE);
        request.setCharacterEncoding(HTML_ENCODE);

        filterChain.doFilter(request, response);
    }
}