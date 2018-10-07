package ua.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.training.constant.Api;
import ua.training.constant.Attributes;
import ua.training.constant.Pages;

/**
 * Description: This is the menu controller
 *
 * @author Zakusylo Pavlo
 */
@Controller
public class MenuController {

    /**
     * Display general menu page
     *
     * @param modelAndView ModelAndView
     * @return modelAndView ModelAndView
     */
    @RequestMapping(value = Api.MENU, method = RequestMethod.GET)
    public ModelAndView getGeneralMenuPage(ModelAndView modelAndView) {
        modelAndView.addObject(Attributes.PAGE_NAME, Attributes.PAGE_MENU);
        modelAndView.setViewName(Pages.MENU);

        return modelAndView;
    }
}
