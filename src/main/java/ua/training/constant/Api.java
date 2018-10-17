package ua.training.constant;

/**
 * Description: This is list of all api(urls)
 *
 * @author Zakusylo Pavlo
 */
public interface Api {
    String DEFAULT = "/";
    String SIGN_IN_OR_REGISTER = "/signInOrRegister";
    String LOG_IN = "/logIn";
    String LOG_OUT = "/logOut";
    String REGISTER_NEW_USER = "/registerNewUser";
    String HOME_PAGE = "/homePage";

    String MENU_API = "/menu";
    String MENU_USERS_EDIT = "/menuUsersEdit";

    String USER_SETTINGS_PAGE = "/userSettings";
    String USER_DELETE_DISH = "/userDeleteDishItem";
    String USER_ADD_DISH = "/addNewDish";
    String USER_UPDATE_DISH = "/userUpdateDish";
    String USER_UPDATE_PARAMETERS = "/userUpdateParameters";

    String ADMIN_MENU_GENERAL_EDIT  = "/admin/menuGeneralEdit";
    String ADMIN_MENU_GENERAL_UPDATE_DISH  = "/admin/updateGeneralDish";
    String ADMIN_MENU_GENERAL_DELETE_ITEM = "/admin/deleteGeneralDishItem";
    String ADMIN_USERS_SHOW= "/admin/showUsers";


}
