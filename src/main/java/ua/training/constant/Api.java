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
    String REGISTER_NEW_USER = "/registerNewUser";
    String MENU_API = "/menu";

    String USER_HOME_PAGE = "/user/homePage";
    String USER_SETTINGS_PAGE = "/user/userSettings";
    String USER_MENU_EDIT = "/user/menuUsersEdit";
    String USER_DELETE_DISH = "/user/userDeleteDishItem";
    String USER_ADD_DISH = "/user/addNewDish";
    String USER_UPDATE_DISH = "/user/userUpdateDish";
    String USER_UPDATE_PARAMETERS = "/user/userUpdateParameters";
    String USER_LOG_OUT = "/user/logOut";
    String USER_DAY_RATION = "/user/dayRation";
    String USER_CREATE_DAY_RATION = "/user/createNewRation";
    String USER_UPDATE_COMPOSITION = "/user/userUpdateComposition";
    String USER_DELETE_COMPOSITION = "/user/userDeleteComposition";

    String ADMIN_MENU_GENERAL_EDIT = "/admin/menuGeneralEdit";
    String ADMIN_MENU_GENERAL_UPDATE_DISH = "/admin/updateGeneralDish";
    String ADMIN_MENU_GENERAL_DELETE_ITEM = "/admin/deleteGeneralDishItem";
    String ADMIN_USERS_SHOW = "/admin/showUsers";
    String ADMIN_USERS_SEARCH_BY_EMAIL = "/admin/searchUsersByEmail";
    String ADMIN_USERS_UPDATE_BY_EMAIL = "/admin/updateUsersByEmail";
    String ADMIN_USERS_DELETE_BY_EMAIL = "/admin/deleteUsersByEmail";


}
