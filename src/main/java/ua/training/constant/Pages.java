package ua.training.constant;

/**
 * Description: This is list of pages
 *
 * @author Zakusylo Pavlo
 */
public interface Pages {
    String WELCOME_PAGE = "/welcomePage";

    String HOME = "/client/homePage";
    String HOME_REDIRECT = "redirect:/swft/user/homePage";

    String SIGN_OR_REGISTER = "/client/signInOrRegister";
    String SIGN_OR_REGISTER_REDIRECT = "redirect:/swft/signInOrRegister";

    String USER_SETTINGS = "/client/userSettings";
    String USER_SETTINGS_REDIRECT = "redirect:/swft/user/userSettings";

    String MENU_PAGE = "/client/menu";
    String MENU_GENERAL_EDIT = "/administrator/menuGeneralEdit";
    String MENU_GENERAL_EDIT_REDIRECT = "redirect:/swft/admin/menuGeneralEdit";
    String MENU_USERS_EDIT_PAGE = "/client/menuUsersEdit";
    String MENU_USERS_EDIT_REDIRECT = "redirect:/swft/user/menuUsersEdit";

    String SHOW_USERS = "/administrator/showUsers";
    String SHOW_USERS_REDIRECT = "redirect:/swft/admin/showUsers";

    String DAY_RATION_PAGE = "/client/dayRation";
    String DAY_RATION_REDIRECT = "redirect:/swft/user/dayRation";
}
