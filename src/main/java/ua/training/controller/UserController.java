package ua.training.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.training.controller.commands.exception.DataHttpException;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.dao.utility.PasswordEncoder;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.User;
import ua.training.model.entity.form.FormUser;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
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
     * Display welcome(initial) page
     *
     * @param modelAndView {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = DEFAULT, method = RequestMethod.GET)
    public ModelAndView getWelcomePage(ModelAndView modelAndView) {
        modelAndView.addObject(PAGE_NAME, PAGE_GENERAL);
        modelAndView.setViewName(WELCOME_PAGE);

        return modelAndView;
    }

    /**
     * Display sign in/registration page
     *
     * @param modelAndView {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = SIGN_IN_OR_REGISTER, method = RequestMethod.GET)
    public ModelAndView getSignInOrRegisterPage(ModelAndView modelAndView) {
        modelAndView.addObject(PAGE_NAME, PAGE_SIGN_IN_OR_UP);
        modelAndView.addObject(REQUEST_FORM_USER, new FormUser());
        modelAndView.addObject(SHOW_COLLAPSE_SIGN_IN, SHOW_COLLAPSE_ATTRIBUTE_FOR_CCS_CLASS);
        modelAndView.setViewName(SIGN_OR_REGISTER);

        return modelAndView;
    }

    /**
     * Action login
     *
     * @param email              {@link String}
     * @param password           {@link String}
     * @param modelAndView       {@link ModelAndView}
     * @param redirectAttributes {@link RedirectAttributes}
     * @param request            {@link HttpServletRequest}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = LOG_IN, method = RequestMethod.POST)
    public ModelAndView actionLogIn(@RequestParam(value = REQUEST_EMAIL) String email,
                                    @RequestParam(value = REQUEST_PASSWORD) String password,
                                    ModelAndView modelAndView,
                                    RedirectAttributes redirectAttributes,
                                    HttpServletRequest request) {

        modelAndView.setViewName(SIGN_OR_REGISTER_REDIRECT);
        redirectAttributes.addFlashAttribute(PAGE_VALUE_EMAIL_LOG_IN, email);
        redirectAttributes.addFlashAttribute(PAGE_VALUE_PASSWORD_LOG_IN, password);

        String emailSQL = email.toLowerCase();
        String passwordSQL = PasswordEncoder.encodePassword(password);

        Optional<User> userSQL = USER_SERVICE_IMP.getOrCheckUserByEmail(emailSQL);

        if (userSQL.isPresent() && userSQL.get().getPassword().equals(passwordSQL)) {
            return CommandsUtil.openUsersSession(request, userSQL.get(), modelAndView, redirectAttributes);
        } else if (userSQL.isPresent() && !userSQL.get().getPassword().equals(passwordSQL)) {
            redirectAttributes.addFlashAttribute(PAGE_USER_ERROR, PAGE_USER_WRONG_PASSWORD);
        } else {
            redirectAttributes.addFlashAttribute(PAGE_USER_ERROR, PAGE_USER_NOT_EXIST);
        }

        return modelAndView;
    }

    /**
     * Action register new user
     *
     * @param formUser           {@link FormUser}
     * @param bindingResult      {@link BindingResult}
     * @param modelAndView       {@link ModelAndView}
     * @param redirectAttributes {@link RedirectAttributes}
     * @param servletRequest     {@link HttpServletRequest}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = REGISTER_NEW_USER, method = RequestMethod.POST)
    public ModelAndView actionRegisterNewUser(@Valid @ModelAttribute(REQUEST_FORM_USER) FormUser formUser,
                                              BindingResult bindingResult,
                                              RedirectAttributes redirectAttributes,
                                              HttpServletRequest servletRequest,
                                              ModelAndView modelAndView) {

        modelAndView.addObject(PAGE_NAME, PAGE_SIGN_IN_OR_UP);
        modelAndView.addObject(SHOW_COLLAPSE_SIGN_UP, SHOW_COLLAPSE_ATTRIBUTE_FOR_CCS_CLASS);
        modelAndView.setViewName(SIGN_OR_REGISTER);

        if (bindingResult.hasErrors()) {
            return modelAndView;
        }

        User user;
        try {
            user = USER_MAPPER.extractUserFromHttpForm(formUser, modelAndView);
        } catch (DataHttpException e) {
            log.error(e.getMessage());
            return modelAndView;
        }

        Optional<User> userSQL = USER_SERVICE_IMP.getOrCheckUserByEmail(user.getEmail());

        if (!userSQL.isPresent()) {
            USER_SERVICE_IMP.registerNewUser(user);
            log.info(LOG_USER_REGISTERED + "[" + user.getEmail() + "]");
            return CommandsUtil.openUsersSession(servletRequest, user, modelAndView, redirectAttributes);
        } else {
            modelAndView.addObject(PAGE_USER_ERROR, PAGE_USER_EXIST);
        }

        return modelAndView;
    }

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
