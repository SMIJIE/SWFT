package ua.training.controller.commands.action;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.Dish;
import ua.training.model.entity.RationComposition;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Description: This class directs users to their day ration page
 *
 * @author Zakusylo Pavlo
 */
public class UserDayRation implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Attributes.REQUEST_USER);
        List<Dish> generalDishes = (List<Dish>) request.getServletContext().getAttribute(Attributes.REQUEST_GENERAL_DISHES);
        List<RationComposition> rationCompositions = new ArrayList<>();
        Optional<DayRation> dayRationSql;

        List<Dish> dishesPerPage = new ArrayList<>();
        dishesPerPage.addAll(generalDishes);
        dishesPerPage.addAll(user.getListDishes());
        CommandsUtil.sortListByAnnotationFields(dishesPerPage);

        dayRationSql = DAY_RATION_SERVICE_IMP.checkDayRationByDateAndUserId(LocalDate.now(), user.getId());
        if (dayRationSql.isPresent()) {
            rationCompositions = dayRationSql.get().getCompositions();
        }

        request.getSession().setAttribute(Attributes.REQUEST_NUMBER_PAGE, 0);
        request.getSession().setAttribute(Attributes.REQUEST_LOCALE_DATE, LocalDate.now());
        request.getSession().setAttribute(Attributes.REQUEST_USERS_COMPOSITION, rationCompositions);
        request.getSession().setAttribute(Attributes.REQUEST_USERS_DISHES, dishesPerPage);
        request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_DATA, Attributes.PAGE_USER_NON_ERROR_DATA);
        request.getSession().setAttribute(Attributes.PAGE_NAME, Attributes.PAGE_RATION);

        return Pages.DAY_RATION;
    }
}
