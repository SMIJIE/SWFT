package ua.training.controller.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.training.constant.Attributes;
import ua.training.controller.controllers.exception.DataHttpException;
import ua.training.controller.controllers.utility.ControllerUtil;
import ua.training.model.dao.utility.PasswordEncoder;
import ua.training.model.entity.User;
import ua.training.model.entity.form.FormUser;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

/**
 * Description: This is the statement action controller
 *
 * @author Zakusylo Pavlo
 */
@Log4j2
@Controller
public class StatementController implements GeneralController {

    /**
     * Display welcome(initial) page
     *
     * @param modelAndView {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = DEFAULT, method = RequestMethod.GET)
    public ModelAndView getWelcomePage(ModelAndView modelAndView) {
        modelAndView.addObject(PAGE_NAME, PAGE_GENERAL)
                .setViewName(WELCOME_PAGE);

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
        modelAndView.addObject(PAGE_NAME, PAGE_SIGN_IN_OR_UP)
                .addObject(REQUEST_FORM_USER, new FormUser())
                .addObject(SHOW_COLLAPSE_SIGN_IN, SHOW_COLLAPSE_ATTRIBUTE_FOR_CCS_CLASS)
                .setViewName(SIGN_OR_REGISTER);

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
    public ModelAndView actionLogIn(@RequestParam(REQUEST_EMAIL) String email,
                                    @RequestParam(REQUEST_PASSWORD) String password,
                                    ModelAndView modelAndView,
                                    RedirectAttributes redirectAttributes,
                                    HttpServletRequest request) {

        modelAndView.setViewName(SIGN_OR_REGISTER_REDIRECT);
        redirectAttributes.addFlashAttribute(PAGE_VALUE_EMAIL_LOG_IN, email)
                .addFlashAttribute(PAGE_VALUE_PASSWORD_LOG_IN, password);

        String emailSQL = email.toLowerCase();
        String passwordSQL = PasswordEncoder.encodePassword(password);

        Optional<User> userSQL = USER_SERVICE_IMP.getOrCheckUserByEmail(emailSQL);

        if (userSQL.isPresent() && userSQL.get().getPassword().equals(passwordSQL)) {
            return ControllerUtil.openUsersSession(request, userSQL.get(), modelAndView, redirectAttributes);
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
    @RequestMapping(value = REGISTER_NEW_USER, method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView actionRegisterNewUser(@Valid @ModelAttribute(REQUEST_FORM_USER) FormUser formUser,
                                              BindingResult bindingResult,
                                              RedirectAttributes redirectAttributes,
                                              HttpServletRequest servletRequest,
                                              ModelAndView modelAndView) {

        modelAndView.addObject(PAGE_NAME, PAGE_SIGN_IN_OR_UP)
                .addObject(SHOW_COLLAPSE_SIGN_UP, SHOW_COLLAPSE_ATTRIBUTE_FOR_CCS_CLASS)
                .setViewName(SIGN_OR_REGISTER);

        if (bindingResult.hasErrors()) {
            return modelAndView;
        }

        User userHttp;
        try {
            userHttp = USER_MAPPER.extractEntityFromHttpForm(formUser, modelAndView);
        } catch (DataHttpException e) {
            log.error(e.getMessage());
            return modelAndView;
        }

        Optional<User> userSQL = USER_SERVICE_IMP.getOrCheckUserByEmail(userHttp.getEmail());

        if (!userSQL.isPresent()) {
            USER_SERVICE_IMP.registerNewUser(userHttp);
            log.info(LOG_USER_REGISTERED + "[" + userHttp.getEmail() + "]");
            return ControllerUtil.openUsersSession(servletRequest, userHttp, modelAndView, redirectAttributes);
        } else {
            modelAndView.addObject(PAGE_USER_ERROR, PAGE_USER_EXIST);
        }

        return modelAndView;
    }

    /**
     * Action log out
     *
     * @param user               {@link User}
     * @param servletRequest     {@link HttpServletRequest}
     * @param redirectAttributes {@link RedirectAttributes}
     * @param modelAndView       {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = USER_LOG_OUT, method = RequestMethod.GET)
    public ModelAndView actionLogOut(@SessionAttribute(REQUEST_USER) User user,
                                     HttpServletRequest servletRequest,
                                     RedirectAttributes redirectAttributes,
                                     ModelAndView modelAndView) {

        ControllerUtil.deleteUsersFromContext(servletRequest, user.getEmail());

        servletRequest.getSession().setAttribute(Attributes.REQUEST_USER, null);

        log.info(LOG_USER_LOGGED_OUT + "[" + user.getEmail() + "]");

        redirectAttributes.addAttribute(PAGE_NAME, PAGE_DEMONSTRATION);
        modelAndView.setViewName(SIGN_OR_REGISTER_REDIRECT);

        return modelAndView;
    }
}
