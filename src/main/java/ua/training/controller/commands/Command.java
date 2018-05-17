package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.mapper.DayRationMapper;
import ua.training.model.dao.mapper.DishMapper;
import ua.training.model.dao.mapper.UserMapper;
import ua.training.model.dao.service.implementation.DayRationServiceImp;
import ua.training.model.dao.service.implementation.DishServiceImp;
import ua.training.model.dao.service.implementation.RationCompositionServiceImp;
import ua.training.model.dao.service.implementation.UserServiceImp;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: This is the main Interface for commands
 *
 * @author Zakusylo Pavlo
 */
public interface Command {
    /**
     * Logger for Command classes
     *
     * @see LogManager
     */
    Logger LOGGER = LogManager.getLogger(Command.class);
    UserServiceImp USER_SERVICE_IMP = new UserServiceImp();
    DishServiceImp DISH_SERVICE_IMP = new DishServiceImp();
    DayRationServiceImp DAY_RATION_SERVICE_IMP = new DayRationServiceImp();
    RationCompositionServiceImp RATION_COMPOSITION_SERVICE_IMP = new RationCompositionServiceImp();
    UserMapper USER_MAPPER = new UserMapper();
    DishMapper DISH_MAPPER = new DishMapper();
    DayRationMapper DAY_RATION = new DayRationMapper();

    String execute(HttpServletRequest request);
}
