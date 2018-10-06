package ua.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.training.constant.Api;
import ua.training.constant.Attributes;
import ua.training.constant.Pages;

/**
 * Description: This is the user controller
 *
 * @author Zakusylo Pavlo
 */
@Controller
public class UserController {

    /**
     * Display welcome(initial) page
     */
    @RequestMapping(value = Api.DEFAULT, method = RequestMethod.GET)
    public ModelAndView getWelcomePage(ModelAndView modelAndView) {
        modelAndView.addObject(Attributes.PAGE_NAME, Attributes.PAGE_GENERAL);
        modelAndView.setViewName(Pages.WELCOME_PAGE);

        return modelAndView;
    }

    /**
     * Display general menu page
     */
    @RequestMapping(value = Api.MENU, method = RequestMethod.GET)
    public ModelAndView getGeneralMenuPage(ModelAndView modelAndView) {
        modelAndView.addObject(Attributes.PAGE_NAME, Attributes.PAGE_MENU);
        modelAndView.setViewName(Pages.MENU);

        return modelAndView;
    }
}
