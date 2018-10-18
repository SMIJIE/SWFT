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

    String HOME = "/client/homePage";
    String HOME_REDIRECT = "redirect:/swft/user/homePage";

    String SIGN_OR_REGISTER = "/client/signInOrRegister";
    String SIGN_OR_REGISTER_REDIRECT = "redirect:/swft/signInOrRegister";
    String SIGN_OR_REGISTER_WITH_ERROR = "redirect:/signInOrRegisterWithError";

    String USER_SETTINGS = "/client/userSettings";
    String USER_SETTINGS_REDIRECT = "redirect:/swft/user/userSettings";
    String USER_SETTINGS_REDIRECT_WITH_ERROR = "redirect:/user/userSettingsWithError";

    String MENU_PAGE = "/client/menu";
    String MENU_GENERAL_EDIT = "/administrator/menuGeneralEdit";
    String MENU_GENERAL_EDIT_REDIRECT = "redirect:/swft/admin/menuGeneralEdit";
    String MENU_GENERAL_EDIT_WITH_ERROR_REDIRECT = "redirect:/menuGeneralEditWithError";
    String MENU_USERS_EDIT_PAGE = "/client/menuUsersEdit";
    String MENU_USERS_EDIT_REDIRECT = "redirect:/swft/user/menuUsersEdit";
    String MENU_USERS_EDIT_REDIRECT_WITH_ERROR = "redirect:/user/menuUsersEditWithError";
    String MENU_USERS_EDIT_AFTER_UPDATE_REDIRECT = "redirect:/user/menuUsersEditAfterUpdate";

    String SHOW_USERS = "/administrator/showUsers";
    String SHOW_USERS_REDIRECT = "redirect:/swft/admin/showUsers";
    String SHOW_USERS_AFTER_UPDATE_OR_SEARCH_REDIRECT = "redirect:/showUsersAfterUpdateOrSearch";

    String DAY_RATION = "/WEB-INF/client/dayRation.jsp";
    String DAY_RATION_REDIRECT = "redirect:/dayRation";
    String DAY_RATION_LIST_REDIRECT = "redirect:/listUserDayRation";
}
