package ua.training.controller.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.training.controller.commands.exception.DataHttpException;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.entity.Dish;
import ua.training.model.entity.User;
import ua.training.model.entity.form.FormDish;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * Description: This is the menu controller
 *
 * @author Zakusylo Pavlo
 */
@Log4j2
@Controller
public class MenuController implements GeneralController {

    /**
     * Display general menu page
     *
     * @param modelAndView {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = MENU_API, method = RequestMethod.GET)
    public ModelAndView getGeneralMenuPage(ModelAndView modelAndView) {
        modelAndView.addObject(PAGE_NAME, PAGE_MENU)
                .setViewName(MENU_PAGE);

        return modelAndView;
    }

    /**
     * Display users menu page with an opportunity pagination
     *
     * @param user         {@link User}
     * @param numPage      {@link Integer}
     * @param modelAndView {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = USER_MENU_EDIT, method = RequestMethod.GET)
    public ModelAndView getUsersMenuPage(@SessionAttribute(REQUEST_USER) User user,
                                         @RequestParam(value = REQUEST_NUMBER_PAGE, required = false) Integer numPage,
                                         ModelAndView modelAndView) {
        Integer page = isNull(numPage) ? 1 : numPage;
        Integer numberDish = DISH_SERVICE_IMP.counterDishByUserId(user.getId());

        Integer maxPage = CommandsUtil.getCountPages(numberDish, 5);
        Integer pageForSQL = CommandsUtil.getPageOrAmountForSQL(page, maxPage);

        List<Dish> usersDish = DISH_SERVICE_IMP.getLimitDishesByUserId(user.getId(), 5, 5 * (pageForSQL - 1));
        CommandsUtil.sortListByAnnotationFields(usersDish);

        modelAndView.addObject(PAGE_NAME, PAGE_MENU_EDIT)
                .addObject(REQUEST_FORM_DISH, new FormDish())
                .addObject(SHOW_COLLAPSE_MENU_USERS_PAGE, SHOW_COLLAPSE_ATTRIBUTE_FOR_CCS_CLASS)
                .addObject(REQUEST_NUMBER_PAGE, pageForSQL)
                .addObject(REQUEST_NUMBER_USER_DISH, numberDish)
                .addObject(REQUEST_USERS_DISHES, usersDish)
                .setViewName(MENU_USERS_EDIT_PAGE);

        return modelAndView;
    }

    /**
     * Delete 1 or more users dish
     *
     * @param user         {@link User}
     * @param idDishes     [] {@link Integer}
     * @param modelAndView {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = USER_DELETE_DISH, method = RequestMethod.GET)
    public ModelAndView actionDeleteUsersDish(@SessionAttribute(REQUEST_USER) User user,
                                              @RequestParam(REQUEST_ARR_DISH) Integer[] idDishes,
                                              @RequestParam(REQUEST_NUMBER_PAGE) Integer numPage,
                                              ModelAndView modelAndView) {

        DISH_SERVICE_IMP.deleteArrayDishesByIdAndUser(Arrays.asList(idDishes), user.getId());

        modelAndView.setViewName(MENU_USERS_EDIT_REDIRECT + "?numPage=" + numPage);

        return modelAndView;
    }

    /**
     * Add users dish
     *
     * @param formDish      {@link FormDish}
     * @param bindingResult {@link BindingResult}
     * @param user          {@link User}
     * @param modelAndView  {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = USER_ADD_DISH, method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView actionAddUsersDish(@Valid @ModelAttribute(REQUEST_FORM_DISH) FormDish formDish,
                                           BindingResult bindingResult,
                                           @SessionAttribute(REQUEST_USER) User user,
                                           @RequestParam(REQUEST_NUMBER_PAGE) Integer numPage,
                                           ModelAndView modelAndView) {

        modelAndView.addObject(PAGE_NAME, PAGE_MENU_EDIT)
                .addObject(SHOW_COLLAPSE_MENU_ADD_DISH, SHOW_COLLAPSE_ATTRIBUTE_FOR_CCS_CLASS)
                .addObject(REQUEST_NUMBER_PAGE, numPage)
                .setViewName(MENU_USERS_EDIT_PAGE);

        if (bindingResult.hasErrors()) {
            return modelAndView;
        }

        Dish dishHttp;
        try {
            dishHttp = DISH_MAPPER.extractDishFromHttpForm(formDish, modelAndView);
        } catch (DataHttpException e) {
            log.error(e.getMessage());
            return modelAndView;
        }

        dishHttp.setUser(user);
        dishHttp.setGeneralFood(false);
        DISH_SERVICE_IMP.insertNewDish(dishHttp);

        modelAndView.setViewName(MENU_USERS_EDIT_REDIRECT + "?numPage=" + numPage);

        return modelAndView;
    }

    /**
     * Update users dish
     *
     * @param formDish           {@link FormDish}
     * @param bindingResult      {@link BindingResult}
     * @param modelAndView       {@link ModelAndView}
     * @param redirectAttributes {@link RedirectAttributes}
     * @param modelAndView       {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = USER_UPDATE_DISH, method = RequestMethod.POST)
    public ModelAndView actionUpdateUsersDish(@Valid @ModelAttribute(REQUEST_FORM_DISH) FormDish formDish,
                                              BindingResult bindingResult,
                                              @RequestParam(REQUEST_NUMBER_DISH) Integer idDish,
                                              @RequestParam(REQUEST_NUMBER_PAGE) Integer numPage,
                                              RedirectAttributes redirectAttributes,
                                              ModelAndView modelAndView) {

        modelAndView.setViewName(MENU_USERS_EDIT_REDIRECT + "?numPage=" + numPage);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(PAGE_USER_ERROR, PAGE_WRONG_DATA);
            return modelAndView;
        }

        Dish dishHttp;
        try {
            dishHttp = DISH_MAPPER.extractDishFromHttpForm(formDish, modelAndView);
        } catch (DataHttpException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute(PAGE_USER_ERROR, modelAndView.getModel().get(PAGE_USER_ERROR));
            return modelAndView;
        }

        DISH_SERVICE_IMP.getDishById(idDish)
                .ifPresent(dishSQL -> {
                    CommandsUtil.mergeDishParameters(dishHttp, dishSQL);
                    DISH_SERVICE_IMP.updateDishParameters(dishSQL);
                });

        return modelAndView;
    }
}