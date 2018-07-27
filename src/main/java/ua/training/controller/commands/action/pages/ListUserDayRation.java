package ua.training.controller.commands.action.pages;

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

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;

/**
 * Description: This class flipping users day ration pages
 *
 * @author Zakusylo Pavlo
 */
public class ListUserDayRation implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Attributes.REQUEST_USER);
        List<Dish> generalDishes = (List<Dish>) request.getServletContext().getAttribute(Attributes.REQUEST_GENERAL_DISHES);
        String attrPage = request.getParameter(Attributes.REQUEST_NUMBER_PAGE);
        Integer attrSessionPage = (Integer) request.getSession().getAttribute(Attributes.REQUEST_NUMBER_PAGE);

        Integer page = isNullOrEmpty(attrPage) ? attrSessionPage : Integer.valueOf(attrPage);
        LocalDate localDate = LocalDate.now().plusDays(page);

        List<RationComposition> rationCompositions = new ArrayList<>();

        List<Dish> dishesPerPage = new ArrayList<>();
        dishesPerPage.addAll(generalDishes);
        Optional.ofNullable(user.getListDishes())
                .ifPresent(dishesPerPage::addAll);

        CommandsUtil.sortListByAnnotationFields(dishesPerPage);

        DAY_RATION_SERVICE_IMP.checkDayRationByDateAndUserId(localDate, user.getId())
        .ifPresent(dayRation -> rationCompositions.addAll(dayRation.getCompositions()));

        request.getSession().setAttribute(Attributes.REQUEST_NUMBER_PAGE, page);
        request.getSession().setAttribute(Attributes.REQUEST_LOCALE_DATE, localDate);
        request.getSession().setAttribute(Attributes.REQUEST_USERS_COMPOSITION, rationCompositions);
        request.getSession().setAttribute(Attributes.REQUEST_USERS_DISHES, dishesPerPage);
        request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_DATA, Attributes.PAGE_USER_NON_ERROR_DATA);
        request.getSession().setAttribute(Attributes.PAGE_NAME, Attributes.PAGE_RATION);

        return Pages.DAY_RATION;
    }
}
