package ua.training.controller;

import ua.training.constant.Api;
import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.constant.Pages;
import ua.training.model.dao.mapper.DayRationMapper;
import ua.training.model.dao.mapper.DishMapper;
import ua.training.model.dao.mapper.UserMapper;
import ua.training.model.dao.service.implementation.DayRationServiceImp;
import ua.training.model.dao.service.implementation.DishServiceImp;
import ua.training.model.dao.service.implementation.RationCompositionServiceImp;
import ua.training.model.dao.service.implementation.UserServiceImp;

/**
 * Description: This is the main Interface for controllers
 *
 * @author Zakusylo Pavlo
 */
public interface GeneralController extends Api, Attributes, Pages, Mess {
    UserServiceImp USER_SERVICE_IMP = new UserServiceImp();
    DishServiceImp DISH_SERVICE_IMP = new DishServiceImp();
    DayRationServiceImp DAY_RATION_SERVICE_IMP = new DayRationServiceImp();
    RationCompositionServiceImp RATION_COMPOSITION_SERVICE_IMP = new RationCompositionServiceImp();

    UserMapper USER_MAPPER = new UserMapper();
    DishMapper DISH_MAPPER = new DishMapper();
    DayRationMapper DAY_RATION = new DayRationMapper();
}
