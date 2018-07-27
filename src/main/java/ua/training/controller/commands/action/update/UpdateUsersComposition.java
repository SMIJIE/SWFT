package ua.training.controller.commands.action.update;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.utility.CommandsUtil;
import ua.training.model.entity.RationComposition;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Description: This class update users ration composition
 *
 * @author Zakusylo Pavlo
 */
public class UpdateUsersComposition implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String numComposition = request.getParameter(Attributes.REQUEST_NUMBER_COMPOSITION);
        Integer amount = Integer.valueOf(request.getParameter(Attributes.REQUEST_AMOUNT));
        Integer setAmount = CommandsUtil.getPageOrAmountForSQL(amount, 5);

        Optional<RationComposition> rationCompositionSQL;
        rationCompositionSQL = RATION_COMPOSITION_SERVICE_IMP.getCompositionById(Integer.valueOf(numComposition));
        if (rationCompositionSQL.isPresent() && rationCompositionSQL.get().getNumberOfDish() != setAmount) {
            rationCompositionSQL.get().setNumberOfDish(setAmount);
            RATION_COMPOSITION_SERVICE_IMP.updateCompositionById(rationCompositionSQL.get());
        }

        return Pages.DAY_RATION_LIST_REDIRECT;
    }
}
