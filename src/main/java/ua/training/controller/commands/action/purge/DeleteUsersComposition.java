package ua.training.controller.commands.action.purge;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.utility.CommandsUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Description: This class delete users ration composition
 *
 * @author Zakusylo Pavlo
 */
public class DeleteUsersComposition implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String[] arrDish = request
                .getParameter(Attributes.REQUEST_ARR_COMPOSITION)
                .split(",");

        Integer[] idDishes = CommandsUtil.stringArrayToInteger(arrDish);

        RATION_COMPOSITION_SERVICE_IMP.deleteArrayCompositionById(Arrays.asList(idDishes));

        return Pages.DAY_RATION_REDIRECT;
    }
}
