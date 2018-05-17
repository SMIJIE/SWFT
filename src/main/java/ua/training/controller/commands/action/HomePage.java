package ua.training.controller.commands.action;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description: This class directs users to their home page
 *
 * @author Zakusylo Pavlo
 */
public class HomePage implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Attributes.REQUEST_USER);
        LocalDate localDate = LocalDate.now();

        List<DayRation> dayRations = DAY_RATION_SERVICE_IMP.getMonthlyDayRationByUser(localDate.getMonthValue(),
                localDate.getYear(), user.getId());

        Map<DayRation, Integer> rationsWithCalories = dayRations.stream()
                .sorted(Comparator.comparing(rwc -> rwc.getDate().getDayOfMonth()))
                .collect(Collectors.toMap(Function.identity(),
                        dr -> RATION_COMPOSITION_SERVICE_IMP.sumCaloriesCompositionByRationId(dr.getId()),
                        (e1, e2) -> e1, LinkedHashMap::new));

        request.getSession().setAttribute(Attributes.REQUEST_NUMBER_PAGE, 0);
        request.getSession().setAttribute(Attributes.REQUEST_NUMBER_MONTH, localDate.getMonthValue());
        request.getSession().setAttribute(Attributes.REQUEST_MONTHLY_DAY_RATION, rationsWithCalories);
        request.getSession().setAttribute(Attributes.PAGE_NAME, Attributes.PAGE_GENERAL);
        return Pages.HOME;
    }
}
