package ua.training.controller.commands.action.pages;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.entity.Dish;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;

/**
 * Description: This class flipping pages of users dishes
 *
 * @author Zakusylo Pavlo
 */
public class ListDishPage implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Attributes.REQUEST_USER);
        String attrPage = request.getParameter(Attributes.REQUEST_NUMBER_PAGE);
        Integer sessionPage = (Integer) request.getSession().getAttribute(Attributes.REQUEST_NUMBER_PAGE);

        Integer numPage = isNullOrEmpty(attrPage) ? sessionPage : Integer.valueOf(attrPage);

        Integer numberDish = DISH_SERVICE_IMP.counterDishByUserId(user.getId());

        Integer maxPage = CommandsUtil.getCountPages(numberDish, 5);
        Integer pageForSQL = CommandsUtil.getPageForSQL(numPage, maxPage);

        List<Dish> listUsers = DISH_SERVICE_IMP.getLimitDishesByUserId(user.getId(), 5, 5 * (pageForSQL - 1));
        CommandsUtil.sortListByAnnotationFields(listUsers);

        request.getSession().setAttribute(Attributes.REQUEST_NUMBER_USER_DISH, numberDish);
        request.getSession().setAttribute(Attributes.REQUEST_USERS_DISHES, listUsers);
        request.getSession().setAttribute(Attributes.REQUEST_NUMBER_PAGE, pageForSQL);
        request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_DATA, Attributes.PAGE_USER_NON_ERROR_DATA);

        return Pages.MENU_USERS_EDIT;
    }
}
