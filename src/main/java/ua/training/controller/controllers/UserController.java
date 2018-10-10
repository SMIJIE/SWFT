package ua.training.controller.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.User;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description: This is the user controller
 *
 * @author Zakusylo Pavlo
 */
@Log4j2
@Controller
public class UserController implements GeneralController {
    /**
     * Display home page
     *
     * @param user         {@link User}
     * @param modelAndView {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = HOME_PAGE, method = RequestMethod.GET)
    public ModelAndView getHomePage(@SessionAttribute(REQUEST_USER) User user,
                                    ModelAndView modelAndView) {

        LocalDate localDate = LocalDate.now();

        List<DayRation> dayRations = DAY_RATION_SERVICE_IMP.getMonthlyDayRationByUser(localDate.getMonthValue(),
                localDate.getYear(), user.getId());

        Map<DayRation, Integer> rationsWithCalories = dayRations.stream()
                .sorted(Comparator.comparing(rwc -> rwc.getDate().getDayOfMonth()))
                .collect(Collectors.toMap(Function.identity(),
                        dr -> RATION_COMPOSITION_SERVICE_IMP.sumCaloriesCompositionByRationId(dr.getId()),
                        (e1, e2) -> e1, LinkedHashMap::new));


        modelAndView.setViewName(HOME);
        modelAndView.addObject(PAGE_NAME, PAGE_GENERAL);
        modelAndView.addObject(REQUEST_NUMBER_PAGE, 0);
        modelAndView.addObject(REQUEST_NUMBER_MONTH, localDate.getMonthValue());
        modelAndView.addObject(REQUEST_MONTHLY_DAY_RATION, rationsWithCalories);

        return modelAndView;
    }
}
