package ua.training.controller.commands.action.admin;

import lombok.extern.log4j.Log4j2;
import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.entity.Dish;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * Description: This class update general menu item
 *
 * @author Zakusylo Pavlo
 */
@Log4j2
public class UpdateGeneralDish implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        List<Dish> generalDishes = (List<Dish>) request.getServletContext().getAttribute(Attributes.REQUEST_GENERAL_DISHES);
        String numDish = request.getParameter(Attributes.REQUEST_NUMBER_DISH);

        Optional<Dish> dishHttp = CommandsUtil.extractDishFromHTTP(request);
        if (!dishHttp.isPresent()) {
            return Pages.MENU_GENERAL_EDIT_WITH_ERROR_REDIRECT;
        }

        DISH_SERVICE_IMP.getDishById(Integer.valueOf(numDish))
                .ifPresent(dishSQL -> {
                    CommandsUtil.updateDishParameters(dishHttp.get(), dishSQL);
                    DISH_SERVICE_IMP.updateDishParameters(dishSQL);
                });


        CommandsUtil.sortListByAnnotationFields(generalDishes);
        request.getServletContext().setAttribute(Attributes.REQUEST_GENERAL_DISHES, generalDishes);

        return Pages.MENU_GENERAL_EDIT_REDIRECT;
    }
}
