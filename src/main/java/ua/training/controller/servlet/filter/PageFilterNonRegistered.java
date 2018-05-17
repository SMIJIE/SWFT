package ua.training.controller.servlet.filter;

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
 * Description: Direct non registered users to users pages
 *
 * @author Zakusylo Pavlo
 */
@WebFilter(urlPatterns = {"/swft/homePage", "/swft/userSettings", "/swft/updateUserParameters",
        "/swft/userSettingsWithError", "/swft/logOut", "/swft/menuUsersEdit", "/swft/menuUsersEditAfterUpdate",
        "/swft/addNewDish", "/swft/listDishPage", "/swft/deleteUsersMenuItem", "/swft/updateUsersDish", "/swft/dayRation",
        "/swft/createNewRation", "/swft/listHomePage", "/swft/listUserDayRation", "/swft/deleteUsersComposition",
        "/swft/updateUsersComposition", "/swft/menuUsersEditWithError"})
public class PageFilterNonRegistered extends AbstractFilter {

    @Override
    protected void filter(HttpServletRequest request,
                          HttpServletResponse response,
                          FilterChain filterChain, Optional<User> user) throws IOException, ServletException {

        if (user.isPresent()) {
            filterChain.doFilter(request, response);
        } else {
            request.getRequestDispatcher(Pages.INDEX).forward(request, response);
        }
    }
}