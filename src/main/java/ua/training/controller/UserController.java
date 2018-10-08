package ua.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.training.constant.Api;
import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.dao.utility.PasswordEncoder;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description: This is the user controller
 *
 * @author Zakusylo Pavlo
 */
@Controller
public class UserController implements GeneralController {

    /**
     * Display welcome(initial) page
     *
     * @param modelAndView ModelAndView
     * @return modelAndView ModelAndView
     */
    @RequestMapping(value = Api.DEFAULT, method = RequestMethod.GET)
    public ModelAndView getWelcomePage(ModelAndView modelAndView) {
        modelAndView.addObject(Attributes.PAGE_NAME, Attributes.PAGE_GENERAL);
        modelAndView.setViewName(Pages.WELCOME_PAGE);

        return modelAndView;
    }

    /**
     * Display sign in/registration page
     *
     * @param modelAndView ModelAndView
     * @return modelAndView ModelAndView
     */
    @RequestMapping(value = Api.SIGN_IN_OR_REGISTER, method = RequestMethod.GET)
    public ModelAndView getSignInOrRegisterPage(ModelAndView modelAndView) {
        modelAndView.addObject(Attributes.PAGE_NAME, Attributes.PAGE_SIGN_IN_OR_UP);
        modelAndView.addObject(Attributes.REQUEST_FORM_USER, new User());
        modelAndView.setViewName(Pages.SIGN_OR_REGISTER);

        return modelAndView;
    }

    /**
     * Action login
     *
     * @param email              String
     * @param password           String
     * @param modelAndView       ModelAndView
     * @param redirectAttributes RedirectAttributes
     * @param request            HttpServletRequest
     * @return modelAndView ModelAndView
     */
    @RequestMapping(value = Api.LOG_IN, method = RequestMethod.POST)
    public ModelAndView actionLogIn(@RequestParam(value = Attributes.REQUEST_EMAIL) String email,
                                    @RequestParam(value = Attributes.REQUEST_PASSWORD) String password,
                                    ModelAndView modelAndView,
                                    RedirectAttributes redirectAttributes,
                                    HttpServletRequest request) {

        modelAndView.setViewName(Pages.SIGN_OR_REGISTER_REDIRECT);
        redirectAttributes.addFlashAttribute(Attributes.PAGE_VALUE_EMAIL_LOG_IN, email);
        redirectAttributes.addFlashAttribute(Attributes.PAGE_VALUE_PASSWORD_LOG_IN, password);

        String emailSQL = email.toLowerCase();
        String passwordSQL = PasswordEncoder.encodePassword(password);

        Optional<User> userSQL = USER_SERVICE_IMP.getOrCheckUserByEmail(emailSQL);

        if (userSQL.isPresent() && userSQL.get().getPassword().equals(passwordSQL)) {
            return CommandsUtil.openUsersSession(request, userSQL.get(), modelAndView, redirectAttributes);
        } else if (userSQL.isPresent() && !userSQL.get().getPassword().equals(passwordSQL)) {
            redirectAttributes.addFlashAttribute(Attributes.PAGE_USER_ERROR, Attributes.PAGE_USER_WRONG_PASSWORD);
        } else {
            redirectAttributes.addFlashAttribute(Attributes.PAGE_USER_ERROR, Attributes.PAGE_USER_NOT_EXIST);
        }

        return modelAndView;
    }

    @RequestMapping(value = Api.REGISTER_NEW_USER, method = RequestMethod.POST)
    public ModelAndView actionRegisterNewUser(@ModelAttribute(Attributes.REQUEST_FORM_USER) User user,
                                              ModelAndView modelAndView) {
//        String returnPage = Pages.SIGN_OR_REGISTER_WITH_ERROR;
//
//        Optional<User> userHttp = CommandsUtil.extractUserFromHTTP(request);
//        if (!userHttp.isPresent()) {
//            request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_EMAIL, Attributes.PAGE_USER_WRONG_DATA);
//            return returnPage;
//        }
//
//        Optional<User> userSQL = USER_SERVICE_IMP.getOrCheckUserByEmail(userHttp.get().getEmail());
//
//        if (!userSQL.isPresent()) {
//            USER_SERVICE_IMP.registerNewUser(userHttp.get());
//            log.info(Mess.LOG_USER_REGISTERED + "[" + userHttp.get().getEmail() + "]");
////            returnPage = CommandsUtil.openUsersSession(request, userHttp.get());
//        } else {
//            request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_EMAIL, Attributes.PAGE_USER_EXIST);
//        }
        System.out.println("=====================================================================================");
        System.out.println(user);
        modelAndView.addObject(Attributes.PAGE_NAME, Attributes.PAGE_SIGN_IN_OR_UP);

        return modelAndView;
    }

    /**
     * Display home page
     *
     * @param user         User
     * @param modelAndView ModelAndView
     * @return modelAndView ModelAndView
     */
    @RequestMapping(value = Api.HOME_PAGE, method = RequestMethod.GET)
    public ModelAndView getHomePage(@SessionAttribute(Attributes.REQUEST_USER) User user,
                                    ModelAndView modelAndView) {

        LocalDate localDate = LocalDate.now();

        List<DayRation> dayRations = DAY_RATION_SERVICE_IMP.getMonthlyDayRationByUser(localDate.getMonthValue(),
                localDate.getYear(), user.getId());

        Map<DayRation, Integer> rationsWithCalories = dayRations.stream()
                .sorted(Comparator.comparing(rwc -> rwc.getDate().getDayOfMonth()))
                .collect(Collectors.toMap(Function.identity(),
                        dr -> RATION_COMPOSITION_SERVICE_IMP.sumCaloriesCompositionByRationId(dr.getId()),
                        (e1, e2) -> e1, LinkedHashMap::new));


        modelAndView.setViewName(Pages.HOME);
        modelAndView.addObject(Attributes.PAGE_NAME, Attributes.PAGE_GENERAL);
        modelAndView.addObject(Attributes.REQUEST_NUMBER_PAGE, 0);
        modelAndView.addObject(Attributes.REQUEST_NUMBER_MONTH, localDate.getMonthValue());
        modelAndView.addObject(Attributes.REQUEST_MONTHLY_DAY_RATION, rationsWithCalories);

        return modelAndView;
    }
}
