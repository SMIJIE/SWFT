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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * Description: This is the admin controller
 *
 * @author Zakusylo Pavlo
 */
@Log4j2
@Controller
public class AdminController implements GeneralController {
    /**
     * Display general menu edit page
     *
     * @param modelAndView {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = ADMIN_MENU_GENERAL_EDIT, method = RequestMethod.GET)
    public ModelAndView getGeneralMenuEditPage(ModelAndView modelAndView) {
        modelAndView.addObject(PAGE_NAME, PAGE_MENU_EDIT)
                .addObject(REQUEST_FORM_DISH, new FormDish())
                .setViewName(MENU_GENERAL_EDIT);

        return modelAndView;
    }

    /**
     * Update general dish(without updating the 'name' and 'foodCategory')
     *
     * @param formDish           {@link FormDish}
     * @param bindingResult      {@link BindingResult}
     * @param idDish             {@link Integer}
     * @param servletRequest     {@link HttpServletRequest}
     * @param redirectAttributes {@link RedirectAttributes}
     * @param modelAndView       {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = ADMIN_MENU_GENERAL_UPDATE_DISH, method = RequestMethod.POST)
    public ModelAndView actionUpdateGeneralDish(@Valid @ModelAttribute(REQUEST_FORM_DISH) FormDish formDish,
                                                BindingResult bindingResult,
                                                @RequestParam(REQUEST_NUMBER_DISH) Integer idDish,
                                                HttpServletRequest servletRequest,
                                                RedirectAttributes redirectAttributes,
                                                ModelAndView modelAndView) {

        modelAndView.setViewName(MENU_GENERAL_EDIT_REDIRECT);

        if (bindingResult.hasErrors()) {
            if (bindingResult.getErrorCount() == 1 &&
                    !isNull(bindingResult.getFieldError(REQUEST_NAME))) {
                formDish.setName("Example");
            } else {
                redirectAttributes.addFlashAttribute(PAGE_USER_ERROR, PAGE_WRONG_DATA);
                return modelAndView;
            }
        }

        Dish dishHttp;
        try {
            dishHttp = DISH_MAPPER.extractDishFromHttpForm(formDish, modelAndView);
            dishHttp.setName("");
        } catch (DataHttpException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute(PAGE_USER_ERROR, modelAndView.getModel().get(PAGE_USER_ERROR));
            return modelAndView;
        }

        DISH_SERVICE_IMP.getDishById(Integer.valueOf(idDish))
                .ifPresent(dishSQL -> {
                    CommandsUtil.mergeDishParameters(dishHttp, dishSQL);
                    DISH_SERVICE_IMP.updateDishParameters(dishSQL);
                });


        CommandsUtil.addGeneralDishToContext(servletRequest.getServletContext());

        return modelAndView;
    }

    /**
     * Delete general dish item
     *
     * @param idDishes       []             {@link Integer}
     * @param servletRequest {@link HttpServletRequest}
     * @param modelAndView   {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = ADMIN_MENU_GENERAL_DELETE_ITEM, method = RequestMethod.GET)
    public ModelAndView actionDeleteGeneralDishItem(@RequestParam(value = REQUEST_ARR_DISH) Integer[] idDishes,
                                                    HttpServletRequest servletRequest,
                                                    ModelAndView modelAndView) {
        modelAndView.setViewName(MENU_GENERAL_EDIT_REDIRECT);

        DISH_SERVICE_IMP.deleteArrayDishesById(Arrays.asList(idDishes));

        CommandsUtil.addGeneralDishToContext(servletRequest.getServletContext());

        return modelAndView;
    }

    /**
     * Delete general dish item
     *
     * @param modelAndView {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = ADMIN_USERS_SHOW, method = RequestMethod.GET)
    public ModelAndView getShowUsersPage(@SessionAttribute(REQUEST_USER) User user,
                                         @RequestParam(value = REQUEST_NUMBER_PAGE, required = false) Integer numPage,
                                         ModelAndView modelAndView) {

        Integer page = isNull(numPage) ? 1 : numPage;
        Integer numberUsers = USER_SERVICE_IMP.countUsers(user.getId());
        Integer maxPage = CommandsUtil.getCountPages(numberUsers, 6);
        Integer pageForSQL = CommandsUtil.getPageOrAmountForSQL(page, maxPage);

        List<User> listUsers = USER_SERVICE_IMP.getLimitUsersWithoutAdmin(user.getId(), 6, 6 * (pageForSQL - 1));
        listUsers.sort(Comparator.comparing(User::getEmail));

        Integer forPage = USER_SERVICE_IMP.countUsers(user.getId());

        modelAndView.addObject(PAGE_NAME, PAGE_USERS_LIST)
                .addObject(REQUEST_NUMBER_PAGE, pageForSQL)
                .addObject(REQUEST_NUMBER_USERS, forPage)
                .addObject(REQUEST_LIST_USERS, listUsers)
                .setViewName(SHOW_USERS);

        return modelAndView;
    }
}