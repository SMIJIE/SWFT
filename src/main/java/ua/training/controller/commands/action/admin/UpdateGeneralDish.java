package ua.training.controller.commands.action.admin;

import lombok.extern.log4j.Log4j2;
import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.exception.DataHttpException;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.entity.Dish;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Description: This class update general menu item
 *
 * @author Zakusylo Pavlo
 */
@Log4j2
public class UpdateGeneralDish implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String numDish = request.getParameter(Attributes.REQUEST_NUMBER_DISH);

        Dish dishHttp;
        try {
            dishHttp = DISH_MAPPER.extractFromHttpServletRequest(request);
        } catch (DataHttpException e) {
            request.getSession().setAttribute(Attributes.PAGE_USER_ERROR_DATA, Attributes.PAGE_USER_WRONG_DATA);
            log.error(e.getMessage());
            return Pages.MENU_GENERAL_EDIT_WITH_ERROR_REDIRECT;
        }

        DISH_SERVICE_IMP.getDishById(Integer.valueOf(numDish))
                .ifPresent(dishSQL -> {
                    dishHttp.setId(dishSQL.getId());
                    dishHttp.setFoodCategory(dishSQL.getFoodCategory());
                    dishHttp.setName(dishSQL.getName());
                    DISH_SERVICE_IMP.updateDishParameters(dishHttp);

                });

        List<Dish> general = DISH_SERVICE_IMP.getGeneralDishes();
        CommandsUtil.sortListByAnnotationFields(general);
        request.getServletContext().setAttribute(Attributes.REQUEST_GENERAL_DISHES, general);

        return Pages.MENU_GENERAL_EDIT_REDIRECT;
    }
}
