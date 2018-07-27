package ua.training.controller.commands.action.purge;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.entity.User;

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
        User user = (User) request.getSession().getAttribute(Attributes.REQUEST_USER);

        String[] arrCompositions = request
                .getParameter(Attributes.REQUEST_ARR_COMPOSITION)
                .split(",");

        Integer[] idCompositions = CommandsUtil.stringArrayToInteger(arrCompositions);

        RATION_COMPOSITION_SERVICE_IMP.deleteArrayCompositionById(Arrays.asList(idCompositions));
        user.getDayRations()
                .forEach(dr -> dr.getCompositions()
                        .removeIf(rc -> Arrays.asList(idCompositions).contains(rc.getId())));

        return Pages.DAY_RATION_REDIRECT;
    }
}
