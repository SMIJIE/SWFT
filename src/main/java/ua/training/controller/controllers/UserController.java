package ua.training.controller.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.training.controller.exception.DataHttpException;
import ua.training.controller.mapper.DayRationMapper;
import ua.training.controller.mapper.UserMapper;
import ua.training.controller.utility.ControllerUtil;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.Dish;
import ua.training.model.entity.RationComposition;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.FoodIntake;
import ua.training.model.entity.form.FormDayRationComposition;
import ua.training.model.entity.form.FormUser;
import ua.training.model.service.implementation.DayRationServiceImp;
import ua.training.model.service.implementation.RationCompositionServiceImp;
import ua.training.model.service.implementation.UserServiceImp;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

/**
 * Description: This is the user controller
 *
 * @author Zakusylo Pavlo
 */
@Log4j2
@Controller
public class UserController implements GeneralController {
    @Autowired
    private UserServiceImp userServiceImp;
    @Autowired
    private DayRationServiceImp dayRationServiceImp;
    @Autowired
    private RationCompositionServiceImp rationCompositionServiceImp;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DayRationMapper dayRationMapper;

    /**
     * Display home page with an opportunity pagination
     *
     * @param user         {@link User}
     * @param numPage      {@link Integer}
     * @param modelAndView {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @GetMapping(USER_HOME_PAGE)
    public ModelAndView getHomePage(@SessionAttribute(REQUEST_USER) User user,
                                    @RequestParam(value = REQUEST_NUMBER_PAGE, required = false) Integer numPage,
                                    ModelAndView modelAndView) {

        Integer page = isNull(numPage) ? 0 : numPage;
        LocalDate localDate = LocalDate.now().plusMonths(page);

        List<DayRation> dayRations = dayRationServiceImp.getMonthlyDayRationByUser(localDate.getMonthValue(),
                localDate.getYear(), user.getId());

        Map<DayRation, Integer> rationsWithCalories = dayRations.stream()
                .sorted(Comparator.comparing(rwc -> rwc.getDate().getDayOfMonth()))
                .collect(Collectors.toMap(Function.identity(),
                        dr -> rationCompositionServiceImp.sumCaloriesCompositionByRationId(dr.getId()),
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
    @GetMapping(USER_SETTINGS_PAGE)
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
            userHttp = userMapper.extractEntityFromHttpForm(formUser, modelAndView);
        } catch (DataHttpException e) {
            log.error(e.getMessage());
            return modelAndView;
        }

        Optional<User> userSQL = userServiceImp.getOrCheckUserByEmail(userHttp.getEmail());
        if (!userSQL.isPresent() ||
                userSQL.get().getEmail().equalsIgnoreCase(user.getEmail())) {

            ControllerUtil.deleteUsersFromContext(servletRequest, user.getEmail());
            ControllerUtil.addUsersToContext(servletRequest, userHttp.getEmail());

            ControllerUtil.mergeUserParameters(userHttp, user);
            userServiceImp.updateUserParameters(user);

            log.info(LOG_USER_UPDATE_PARAMETERS + "[" + user.getEmail() + "]");

            servletRequest.getSession().setAttribute(REQUEST_USER, user);
        } else {
            redirectAttributes.addFlashAttribute(PAGE_USER_ERROR, PAGE_USER_EXIST);
        }

        modelAndView.setViewName(USER_SETTINGS_REDIRECT);

        return modelAndView;
    }

    /**
     * Display user day ration page with an opportunity pagination
     *
     * @param user               {@link User}
     * @param numPage            {@link Integer}
     * @param httpServletRequest {@link HttpServletRequest}
     * @param modelAndView       {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @GetMapping(USER_DAY_RATION)
    public ModelAndView getUserDayRationPage(@SessionAttribute(REQUEST_USER) User user,
                                             @RequestParam(value = REQUEST_NUMBER_PAGE, required = false) Integer numPage,
                                             HttpServletRequest httpServletRequest,
                                             ModelAndView modelAndView) {

        Integer page = isNull(numPage) ? 0 : numPage;
        LocalDate localDate = LocalDate.now().plusDays(page);

        Map<String, List<Dish>> generalDishes = (Map<String, List<Dish>>) httpServletRequest.getServletContext().getAttribute(REQUEST_GENERAL_DISHES);

        List<RationComposition> rationCompositions = new ArrayList<>();
        dayRationServiceImp.checkDayRationByDateAndUserId(localDate, user.getId())
                .ifPresent(dayRation -> rationCompositions.addAll(dayRation.getCompositions()));

        Map<String, List<RationComposition>> usersRC = ControllerUtil.getDatRationByFoodIntake(rationCompositions);

        List<Dish> dishesPerPage = new ArrayList<>();
        generalDishes.values().forEach(dishesPerPage::addAll);

        Optional.ofNullable(user.getListDishes())
                .ifPresent(dishesPerPage::addAll);

        ControllerUtil.sortListByAnnotationFields(dishesPerPage);

        Map<String, List<Dish>> allDishes = ControllerUtil.addGeneralDishToContext(dishesPerPage);

        modelAndView.addObject(PAGE_NAME, PAGE_RATION)
                .addObject(REQUEST_FORM_RATION_COMPOSITION, new FormDayRationComposition())
                .addObject(REQUEST_NUMBER_PAGE, page)
                .addObject(REQUEST_LOCALE_DATE, localDate)
                .addObject(REQUEST_USERS_COMPOSITION, usersRC)
                .addObject(REQUEST_USERS_DISHES, allDishes)
                .addObject(SHOW_COLLAPSE_DAY_RATION_COMPOSITION, SHOW_COLLAPSE_ATTRIBUTE_FOR_CCS_CLASS)
                .setViewName(DAY_RATION_PAGE);

        return modelAndView;
    }

    /**
     * Update user day ration composition
     *
     * @param numPage      {@link Integer}
     * @param idRC         {@link Integer}
     * @param formDRC      {@link FormDayRationComposition}
     * @param modelAndView {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @PostMapping(value = USER_UPDATE_COMPOSITION)
    public ModelAndView actionUpdateUsersComposition(@RequestParam(REQUEST_NUMBER_PAGE) Integer numPage,
                                                     @RequestParam(REQUEST_NUMBER_COMPOSITION) Integer idRC,
                                                     @ModelAttribute(REQUEST_FORM_RATION_COMPOSITION) FormDayRationComposition formDRC,
                                                     ModelAndView modelAndView) {

        Integer setAmount = formDRC.getNumberOfDish() <= 1 ? 1 : formDRC.getNumberOfDish();

        Optional<RationComposition> rationCompositionSQL;
        rationCompositionSQL = rationCompositionServiceImp.getCompositionById(idRC);

        if (rationCompositionSQL.isPresent()) {
            if (rationCompositionSQL.get().getNumberOfDish() != setAmount ||
                    rationCompositionSQL.get().getFoodIntake() != formDRC.getFoodIntake()) {
                rationCompositionSQL.get().setNumberOfDish(setAmount);
                rationCompositionSQL.get().setFoodIntake(formDRC.getFoodIntake());
                rationCompositionServiceImp.updateCompositionById(rationCompositionSQL.get());
            }
        }

        modelAndView.setViewName(DAY_RATION_REDIRECT + "?numPage=" + numPage);

        return modelAndView;
    }

    /**
     * Delete 1 or more users ration compositions
     *
     * @param idCompositions [] {@link Integer}
     * @param numPage        {@link Integer}
     * @param modelAndView   {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @GetMapping(USER_DELETE_COMPOSITION)
    public ModelAndView actionDeleteUsersComposition(@RequestParam(REQUEST_ARR_COMPOSITION) Integer[] idCompositions,
                                                     @RequestParam(REQUEST_NUMBER_PAGE) Integer numPage,
                                                     ModelAndView modelAndView) {

        rationCompositionServiceImp.deleteArrayCompositionById(Arrays.asList(idCompositions));

        modelAndView.setViewName(DAY_RATION_REDIRECT + "?numPage=" + numPage);

        return modelAndView;
    }

    /**
     * Create users ration compositions
     *
     * @param numPage      {@link Integer}
     * @param formDRC      {@link FormDayRationComposition}
     * @param modelAndView {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @PostMapping(USER_CREATE_DAY_RATION)
    public ModelAndView actionCreateUsersDayRation(@SessionAttribute(REQUEST_USER) User user,
                                                   @RequestParam(REQUEST_NUMBER_PAGE) Integer numPage,
                                                   @ModelAttribute(REQUEST_FORM_RATION_COMPOSITION) FormDayRationComposition formDRC,
                                                   RedirectAttributes redirectAttributes,
                                                   ModelAndView modelAndView) {

        modelAndView.setViewName(DAY_RATION_REDIRECT + "?numPage=" + numPage);

        Optional<DayRation> dayRationSql;

        if (nonNull(formDRC.getBreakfast()) || nonNull(formDRC.getDinner()) || nonNull(formDRC.getSupper())) {
            formDRC.setUser(user);

            DayRation dayRationHttp;
            try {
                dayRationHttp = dayRationMapper.extractEntityFromHttpForm(formDRC, modelAndView);
            } catch (DataHttpException e) {
                log.error(e.getMessage());
                redirectAttributes.addFlashAttribute(PAGE_USER_ERROR, modelAndView.getModel().get(PAGE_USER_ERROR));
                return modelAndView;
            }

            dayRationSql = dayRationServiceImp.checkDayRationByDateAndUserId(dayRationHttp.getDate(),
                    dayRationHttp.getUser().getId());

            if (!dayRationSql.isPresent()) {
                dayRationServiceImp.insertNewDayRation(dayRationHttp);
                dayRationHttp.setCompositions(new ArrayList<>());
                dayRationSql = Optional.of(dayRationHttp);
            }

            ControllerUtil.createRationComposition(formDRC.getBreakfast(), dayRationSql.get(), FoodIntake.BREAKFAST);
            ControllerUtil.createRationComposition(formDRC.getDinner(), dayRationSql.get(), FoodIntake.DINNER);
            ControllerUtil.createRationComposition(formDRC.getSupper(), dayRationSql.get(), FoodIntake.SUPPER);
        }

        return modelAndView;
    }
}