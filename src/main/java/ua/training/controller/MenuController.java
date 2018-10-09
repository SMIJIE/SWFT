package ua.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Description: This is the menu controller
 *
 * @author Zakusylo Pavlo
 */
@Controller
public class MenuController implements GeneralController {

    /**
     * Display general menu page
     *
     * @param modelAndView ModelAndView
     * @return modelAndView ModelAndView
     */
    @RequestMapping(value = MENU_API, method = RequestMethod.GET)
    public ModelAndView getGeneralMenuPage(ModelAndView modelAndView) {
        modelAndView.addObject(PAGE_NAME, PAGE_MENU);
        modelAndView.setViewName(MENU_PAGE);

        return modelAndView;
    }
}
