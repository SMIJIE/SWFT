package ua.training.constant;

/**
 * Description: This is list of pages
 *
 * @author Zakusylo Pavlo
 */
public interface Pages {
    String REDIRECT = "redirect:/";
    String INDEX = "/index.jsp";
    String WELCOME_PAGE = "/welcomePage";

    String HOME = "/user/homePage";
    String HOME_REDIRECT = "redirect:/swft/homePage";

    String SIGN_OR_REGISTER = "/user/signInOrRegister";
    String SIGN_OR_REGISTER_REDIRECT = "redirect:/swft/signInOrRegister";
    String SIGN_OR_REGISTER_WITH_ERROR = "redirect:/signInOrRegisterWithError";

    String USER_SETTINGS = "/user/userSettings";
    String USER_SETTINGS_REDIRECT = "redirect:/swft/userSettings";
    String USER_SETTINGS_REDIRECT_WITH_ERROR = "redirect:/userSettingsWithError";

    String MENU_PAGE = "/user/menu";
    String MENU_GENERAL_EDIT = "/admin/menuGeneralEdit";
    String MENU_GENERAL_EDIT_REDIRECT = "redirect:/swft/admin/menuGeneralEdit";
    String MENU_GENERAL_EDIT_WITH_ERROR_REDIRECT = "redirect:/menuGeneralEditWithError";
    String MENU_USERS_EDIT_PAGE = "/user/menuUsersEdit";
    String MENU_USERS_EDIT_REDIRECT = "redirect:/swft/menuUsersEdit";
    String MENU_USERS_EDIT_REDIRECT_WITH_ERROR = "redirect:/menuUsersEditWithError";
    String MENU_USERS_EDIT_AFTER_UPDATE_REDIRECT = "redirect:/menuUsersEditAfterUpdate";

    String SHOW_USERS = "/admin/showUsers";
    String SHOW_USERS_REDIRECT = "redirect:/showUsers";
    String SHOW_USERS_AFTER_UPDATE_OR_SEARCH_REDIRECT = "redirect:/showUsersAfterUpdateOrSearch";

    String DAY_RATION = "/WEB-INF/user/dayRation.jsp";
    String DAY_RATION_REDIRECT = "redirect:/dayRation";
    String DAY_RATION_LIST_REDIRECT = "redirect:/listUserDayRation";
}
