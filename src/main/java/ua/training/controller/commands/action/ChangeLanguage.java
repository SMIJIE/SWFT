package ua.training.controller.commands.action;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.constant.RegexExpress;
import ua.training.controller.commands.Command;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Description: This class change the language(locale)
 *
 * @author Zakusylo Pavlo
 */
public class ChangeLanguage implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String localeFromPage = request.getParameter(Attributes.REQUEST_LANGUAGE);
        String reqPage = request.getParameter(Attributes.REQUEST_CURRENT_PAGE);

        String[] langCount = localeFromPage.split("_");

        Locale locale = new Locale(langCount[0], langCount[1]);

        boolean tempFlag = reqPage.matches(RegexExpress.LANG_PAGE);

        String respPage = tempFlag ? reqPage.replace(Attributes.PAGE_PATH, "") : Attributes.COMMAND_HOME_PAGE;
        request.getSession().setAttribute(Attributes.REQUEST_LOCALE_LANGUAGE, locale);

        return Pages.REDIRECT + respPage;
    }
}
