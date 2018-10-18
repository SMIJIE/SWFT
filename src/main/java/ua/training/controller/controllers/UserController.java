package ua.training.controller.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.training.controller.commands.exception.DataHttpException;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.User;
import ua.training.model.entity.form.FormUser;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

/**
 * Description: This is the user controller
 *
 * @author Zakusylo Pavlo
 */
@Log4j2
@Controller
public class UserController implements GeneralController {
    /**
     * Display home page with an opportunity pagination
     *
     * @param user         {@link User}
     * @param numPage      {@link Integer}
     * @param modelAndView {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = USER_HOME_PAGE, method = RequestMethod.GET)
    public ModelAndView getHomePage(@SessionAttribute(REQUEST_USER) User user,
                                    @RequestParam(value = REQUEST_NUMBER_PAGE, required = false) Integer numPage,
                                    ModelAndView modelAndView) {

        Integer page = isNull(numPage) ? 0 : numPage;
        LocalDate localDate = LocalDate.now().plusMonths(page);

        List<DayRation> dayRations = DAY_RATION_SERVICE_IMP.getMonthlyDayRationByUser(localDate.getMonthValue(),
                localDate.getYear(), user.getId());

        Map<DayRation, Integer> rationsWithCalories = dayRations.stream()
                .sorted(Comparator.comparing(rwc -> rwc.getDate().getDayOfMonth()))
                .collect(Collectors.toMap(Function.identity(),
                        dr -> RATION_COMPOSITION_SERVICE_IMP.sumCaloriesCompositionByRationId(dr.getId()),
                        (e1, e2) -> e1, LinkedHashMap::new));


        modelAndView.addObject(PAGE_NAME, PAGE_GENERAL)
                .addObject(REQUEST_NUMBER_PAGE, page)
                .addObject(REQUEST_NUMBER_MONTH, localDate.getMonthValue())
                .addObject(REQUEST_MONTHLY_DAY_RATION, rationsWithCalories)
                .setViewName(HOME);

        return modelAndView;
    }

    /**
     * Display user settings page
     *
     * @param modelAndView {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = USER_SETTINGS_PAGE, method = RequestMethod.GET)
    public ModelAndView getUserSettingsPage(ModelAndView modelAndView) {

        modelAndView.addObject(PAGE_NAME, PAGE_SETTINGS)
                .addObject(REQUEST_FORM_USER, new FormUser())
                .setViewName(USER_SETTINGS);

        return modelAndView;
    }

    /**
     * Action update user parameters with check passwords(without updating the 'password' if he isEmpty)
     *
     * @param formUser           {@link FormUser}
     * @param bindingResult      {@link BindingResult}
     * @param user               {@link User}
     * @param servletRequest     {@link HttpServletRequest}
     * @param redirectAttributes {@link RedirectAttributes}
     * @param modelAndView       {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = USER_UPDATE_PARAMETERS, method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView actionUpdateUsersParameters(@Valid @ModelAttribute(REQUEST_FORM_USER) FormUser formUser,
                                                    BindingResult bindingResult,
                                                    @SessionAttribute(REQUEST_USER) User user,
                                                    HttpServletRequest servletRequest,
                                                    RedirectAttributes redirectAttributes,
                                                    ModelAndView modelAndView) {

        modelAndView.addObject(PAGE_NAME, PAGE_SETTINGS)
                .setViewName(USER_SETTINGS);

        String tempPass = formUser.getPassword();
        String tempPassConf = formUser.getPasswordConfirm();
        boolean strEquals = tempPass.equals(tempPassConf);

        if (bindingResult.hasErrors()) {
            if (bindingResult.getErrorCount() == 1 &&
                    !isNull(bindingResult.getFieldError(REQUEST_PASSWORD))) {
            } else {
                return modelAndView;
            }
        }

        if (isNotEmpty(tempPass) || isNotEmpty(tempPassConf)) {
            if (tempPass.length() < 2 || tempPassConf.length() < 2 || !strEquals) {
                modelAndView.addObject(PAGE_USER_ERROR, PAGE_USER_NOT_MATCH_PASSWORDS);
                return modelAndView;
            }
        } else {
            bindingResult.rejectValue(REQUEST_PASSWORD, "");
        }

        User userHttp;
        try {
            userHttp = USER_MAPPER.extractUserFromHttpForm(formUser, modelAndView);
        } catch (DataHttpException e) {
            log.error(e.getMessage());
            return modelAndView;
        }

        Optional<User> userSQL = USER_SERVICE_IMP.getOrCheckUserByEmail(userHttp.getEmail());
        if (!userSQL.isPresent() ||
                userSQL.get().getEmail().equalsIgnoreCase(user.getEmail())) {

            CommandsUtil.deleteUsersFromContext(servletRequest, user.getEmail());
            CommandsUtil.addUsersToContext(servletRequest, userHttp.getEmail());

            CommandsUtil.mergeUserParameters(userHttp, user);
            USER_SERVICE_IMP.updateUserParameters(user);

            log.info(LOG_USER_UPDATE_PARAMETERS + "[" + user.getEmail() + "]");

            servletRequest.getSession().setAttribute(REQUEST_USER, user);
        } else {
            redirectAttributes.addFlashAttribute(PAGE_USER_ERROR, PAGE_USER_EXIST);
        }

        modelAndView.setViewName(USER_SETTINGS_REDIRECT);

        return modelAndView;
    }
}