package ua.training.controller.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.training.controller.commands.exception.DataHttpException;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.dao.utility.PasswordEncoder;
import ua.training.model.entity.Dish;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.Roles;
import ua.training.model.entity.form.FormDish;
import ua.training.model.entity.form.FormUser;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isEmpty;

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
                .addObject(REQUEST_FORM_USER, new FormUser())
                .addObject(REQUEST_NUMBER_PAGE, pageForSQL)
                .addObject(REQUEST_NUMBER_USERS, forPage)
                .addObject(REQUEST_LIST_USERS, listUsers)
                .setViewName(SHOW_USERS);

        return modelAndView;
    }

    /**
     * Search users by email
     *
     * @param user         {@link User}
     * @param email        {@link String}
     * @param modelAndView {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = ADMIN_USERS_SEARCH_BY_EMAIL, method = RequestMethod.POST)
    public ModelAndView actionSearchUsersByEmail(@SessionAttribute(REQUEST_USER) User user,
                                                 @RequestParam(REQUEST_EMAIL) String email,
                                                 ModelAndView modelAndView) {

        List<User> listUsers = new ArrayList<>();

        if (!user.getEmail().equalsIgnoreCase(email)) {
            USER_SERVICE_IMP.getOrCheckUserByEmail(email)
                    .ifPresent(listUsers::add);
        }

        modelAndView.addObject(PAGE_NAME, PAGE_USERS_LIST)
                .addObject(REQUEST_FORM_USER, new FormUser())
                .addObject(REQUEST_NUMBER_PAGE, 1)
                .addObject(REQUEST_LIST_USERS, listUsers)
                .setViewName(SHOW_USERS);

        return modelAndView;
    }

    /**
     * Update users by email
     *
     * @param email              {@link String}
     * @param password           {@link String}
     * @param role               {@link String}
     * @param numPage            {@link Integer}
     * @param redirectAttributes {@link RedirectAttributes}
     * @param modelAndView       {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = ADMIN_USERS_UPDATE_BY_EMAIL, method = RequestMethod.POST)
    public ModelAndView actionUpdateUsersByEmail(@RequestParam(REQUEST_EMAIL) String email,
                                                 @RequestParam(REQUEST_PASSWORD) String password,
                                                 @RequestParam(REQUEST_USER_ROLE) String role,
                                                 @RequestParam(value = REQUEST_NUMBER_PAGE) Integer numPage,
                                                 RedirectAttributes redirectAttributes,
                                                 ModelAndView modelAndView) {

        modelAndView.setViewName(SHOW_USERS_REDIRECT + "?numPage=" + numPage);

        boolean flag = false;
        boolean flagPassword = isEmpty(password);
        boolean flagPasswordLength = password.length() >= 3;

        Optional<User> userSQL = USER_SERVICE_IMP.getOrCheckUserByEmail(email);
        if (userSQL.isPresent()) {
            if (!flagPassword) {
                if (!flagPasswordLength) {
                    redirectAttributes.addFlashAttribute(PAGE_USER_ERROR, USER_VALID_PASSWORD_SIZE);
                    modelAndView.setViewName(SHOW_USERS_REDIRECT + "?numPage=" + numPage);
                    return modelAndView;
                }
                flag = true;
                userSQL.get().setPassword(PasswordEncoder.encodePassword(password));
            }

            if (!Roles.valueOf(role).equals(userSQL.get().getRole())) {
                flag = true;
                userSQL.get().setRole(Roles.valueOf(role));
            }
        }

        if (flag) {
            USER_SERVICE_IMP.updateUserParameters(userSQL.get());
        }

        return modelAndView;
    }

    /**
     * Delete 1 or more users by email
     *
     * @param emailUsers         [] {@link String}
     * @param numPage            {@link Integer}
     * @param httpServletRequest {@link HttpServletRequest}
     * @param modelAndView       {@link ModelAndView}
     * @return modelAndView {@link ModelAndView}
     */
    @RequestMapping(value = ADMIN_USERS_DELETE_BY_EMAIL, method = RequestMethod.GET)
    public ModelAndView actionDeleteUsersByEmail(@RequestParam(REQUEST_ARR_EMAIL_USERS) String[] emailUsers,
                                                 @RequestParam(REQUEST_NUMBER_PAGE) Integer numPage,
                                                 HttpServletRequest httpServletRequest,
                                                 ModelAndView modelAndView) {

        USER_SERVICE_IMP.deleteArrayUsersByEmail(Arrays.asList(emailUsers));
        CommandsUtil.deleteUsersFromContext(httpServletRequest, emailUsers);

        modelAndView.setViewName(SHOW_USERS_REDIRECT + "?numPage=" + numPage);

        return modelAndView;
    }
}