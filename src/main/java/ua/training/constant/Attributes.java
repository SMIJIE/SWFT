package ua.training.constant;

/**
 * Description: This is list of all attributes
 *
 * @author Zakusylo Pavlo
 */
public interface Attributes {
    String HTML_TEXT = "text/html";
    String HTML_ENCODE = "UTF-8";

    String PAGE_PATH = "/swft/";
    String PAGE_NAME = "pageName";
    String PAGE_VALUE_EMAIL = "valueEmail";
    String PAGE_VALUE_PASSWORD = "valuePassword";
    String PAGE_GENERAL = "page.general";
    String PAGE_RATION = "page.ration";
    String PAGE_MENU = "page.menu";
    String PAGE_SIGN_IN_OR_UP = "page.signInOrRegistration";
    String PAGE_USERS_LIST = "page.menu.users.list";
    String PAGE_MENU_EDIT = "page.menu.edit.mess";
    String PAGE_DEMONSTRATION = "page.demonstration";
    String PAGE_SETTINGS = "page.settings";

    String PAGE_USER_EXIST = "page.user.email.exist";
    String PAGE_USER_WRONG_DATA = "page.wrong.data";
    String PAGE_USER_NOT_EXIST = "page.user.email.not.exist";
    String PAGE_USER_WRONG_PASSWORD = "page.user.wrong.password";
    String PAGE_USER_LOGGED = "page.user.email.logged";
    String PAGE_USER_ERROR_EMAIL = "page.email.wrong";
    String PAGE_USER_ERROR_DATA = "userErrorData";
    String PAGE_NON_ERROR = "nonError";
    String PAGE_USER_ERROR = "userError";
    String PAGE_USER_NON_ERROR_DATA = "nonErrorData";

    String REQUEST_LANGUAGE = "lang";
    String REQUEST_LOCALE_LANGUAGE = "localeLang";
    String REQUEST_LOCALE_DATE = "localeDate";
    String REQUEST_CURRENT_PAGE = "currPage";
    String REQUEST_USER = "user";
    String REQUEST_USERS_ALL = "allUsers";
    String REQUEST_USER_ROLE = "role";
    String REQUEST_USERS_DISHES = "usersDishes";
    String REQUEST_USERS_COMPOSITION = "usersComposition";
    String REQUEST_ARR_DISH = "arrDish";
    String REQUEST_ARR_COMPOSITION = "arrComposition";
    String REQUEST_ARR_EMAIL_USERS = "arrEmailUsers";
    String REQUEST_NUMBER_DISH = "numDish";
    String REQUEST_NUMBER_COMPOSITION = "numComposition";
    String REQUEST_NUMBER_PAGE = "numPage";
    String REQUEST_NUMBER_MONTH = "numMonth";
    String REQUEST_NUMBER_USER_DISH = "numUserDish";
    String REQUEST_NUMBER_USERS = "numUsers";
    String REQUEST_SELECT_BREAKFAST = "selectBreakfast";
    String REQUEST_SELECT_DINNER = "selectDinner";
    String REQUEST_SELECT_SUPPER = "selectSupper";
    String REQUEST_NAME = "name";
    String REQUEST_CATEGORY = "category";
    String REQUEST_DATE_OF_BIRTHDAY = "dob";
    String REQUEST_EMAIL = "email";
    String REQUEST_PASSWORD = "password";
    String REQUEST_HEIGHT = "height";
    String REQUEST_WEIGHT = "weight";
    String REQUEST_CALORIES = "calories";
    String REQUEST_PROTEINS = "proteins";
    String REQUEST_FATS = "fats";
    String REQUEST_CARBOHYDRATES = "carbohydrates";
    String REQUEST_WEIGHT_DESIRED = "weightDesired";
    String REQUEST_LIFESTYLE = "lifestyle";
    String REQUEST_GENERAL_DISHES = "generalDishes";
    String REQUEST_LIST_USERS = "listUsers";
    String REQUEST_AMOUNT = "amount";
    String REQUEST_EMAIL_USERS = "emailUsers";
    String REQUEST_DATE = "date";
    String REQUEST_MONTHLY_DAY_RATION = "monthlyDR";

    String SQL_EXCEPTION = "page.wrong.work.server";

    String DB_PROPERTIES = "db.properties";
    String DB_URL = "db.url";
    String DB_USER = "db.user";
    String DB_PASSWORD = "db.password";

    String DB_SQL_USER_BY_EMAIL = "sql.userGetOrCheckByEmail";
    String DB_SQL_USER_GET_LIMIT_WITHOUT_ADMIN = "sql.userGetLimitWithoutAdmin";
    String DB_SQL_USER_DELETE_ARRAY_BY_EMAIL = "sql.userDeleteArrayByEmail";
    String DB_SQL_USER_COUNT_FOR_PAGE = "sql.userCountForPage";
    String DB_SQL_DISH_BY_USER = "sql.dishGetByUserId";
    String DB_SQL_DISH_DELETE_BY_USER_ID = "sql.dishDeleteByUserId";
    String DB_SQL_DISH_DELETE_BY_USER_EMAIL = "sql.dishDeleteByUserEmail";
    String DB_SQL_DISH_DELETE_ARRAY_BY_ID = "sql.dishDeleteArrayById";
    String DB_SQL_DISH_DELETE_ARRAY_BY_ID_AND_USERS = "sql.dishDeleteArrayByIdAndUser";
    String DB_SQL_DISH_GET_GENERAL = "sql.dishGetAllGeneral";
    String DB_SQL_DISH_COUNT = "sql.dishGetCountForPage";
    String DB_SQL_DAY_RATION_GET_BY_DATE_AND_USER = "sql.dayRationGetByDateAndUser";
    String DB_SQL_DAY_RATION_GET_MONTHLY_BY_USER = "sql.dayRationGetMonthlyByUser";
    String DB_SQL_DAY_RATION_DELETE_BY_USER_ID = "sql.dayRationDeleteByUserId";
    String DB_SQL_DAY_RATION_DELETE_BY_USER_EMAIL = "sql.dayRationDeleteByUserEmail";
    String DB_SQL_RATION_COMPOSITION_GET_BY_RATION_AND_DISH = "sql.rationCompositionGetByRationDishFoodIntake";
    String DB_SQL_RATION_COMPOSITION_SUM_CALORIES_BY_RATION = "sql.rationCompositionSumCaloriesByRationId";
    String DB_SQL_RATION_COMPOSITION_DELETE_BY_DAY_RATION_ID = "sql.rationCompositionDeleteByDayRationId";
    String DB_SQL_RATION_COMPOSITION_DELETE_ARRAY_BY_ID = "sql.rationCompositionDeleteArrayById";
    String DB_SQL_RATION_COMPOSITION_DELETE_BY_RATION_AND_USER = "sql.rationCompositionDeleteByRationIdAndUserId";
    String DB_SQL_RATION_COMPOSITION_DELETE_ARRAY_BY_DISH_AND_USER = "sql.rationCompositionDeleteArrayByDishAndUser";
    String DB_SQL_RATION_COMPOSITION_DELETE_ARRAY_BY_DISH = "sql.rationCompositionDeleteArrayByDish";
    String DB_SQL_RATION_COMPOSITION_DELETE_ARRAY_BY_USER_EMAIL = "sql.rationCompositionDeleteArrayByUserEmail";

    String COMMAND_SIGN_OR_REGISTER = "signInOrRegister";
    String COMMAND_SIGN_OR_REGISTER_WITH_ERROR = "signInOrRegisterWithError";
    String COMMAND_LOG_IN = "logIn";
    String COMMAND_LOG_OUT = "logOut";
    String COMMAND_REGISTER_NEW_USER = "registerNewUser";
    String COMMAND_HOME_PAGE = "homePage";
    String COMMAND_CHANGE_LANGUAGE = "changeLanguage";
    String COMMAND_USER_SETTINGS = "userSettings";
    String COMMAND_USER_SETTINGS_WITH_ERROR = "userSettingsWithError";
    String COMMAND_USER_UPDATE_PARAMETERS = "updateUserParameters";
    String COMMAND_USER_UPDATE_BY_ADMIN = "updateUsers";
    String COMMAND_SHOW_USERS = "showUsers";
    String COMMAND_SHOW_USERS_UPDATE_SEARCH_BY_ADMIN = "showUsersAfterUpdateOrSearch";
    String COMMAND_SHOW_USERS_BY_EMAIL_BY_ADMIN = "searchUsersByEmail";
    String COMMAND_DELETE_USERS = "deleteUsers";
    String COMMAND_DAY_RATION = "dayRation";
    String COMMAND_CREATE_DAY_RATION = "createNewRation";
    String COMMAND_LIST_HOME_PAGE = "listHomePage";
    String COMMAND_LIST_USER_DAY_RATION = "listUserDayRation";
    String COMMAND_DELETE_USER_COMPOSITION = "deleteUsersComposition";
    String COMMAND_UPDATE_USER_COMPOSITION = "updateUsersComposition";
    String COMMAND_MENU = "menu";
    String COMMAND_MENU_GENERAL_EDIT = "menuGeneralEdit";
    String COMMAND_MENU_GENERAL_EDIT_WITH_ERROR = "menuGeneralEditWithError";
    String COMMAND_MENU_GENERAL_DELETE = "deleteGeneralMenuItem";
    String COMMAND_MENU_GENERAL_UPDATE = "updateGeneralDish";
    String COMMAND_MENU_USERS_DELETE = "deleteUsersMenuItem";
    String COMMAND_MENU_USERS_UPDATE = "updateUsersDish";
    String COMMAND_MENU_USERS_EDIT = "menuUsersEdit";
    String COMMAND_MENU_USERS_EDIT_WITH_ERROR = "menuUsersEditWithError";
    String COMMAND_MENU_USERS_EDIT_AFTER_UPDATE = "menuUsersEditAfterUpdate";
    String COMMAND_MENU_ADD_DISH = "addNewDish";
    String COMMAND_MENU_LIST_DISH_PAGE = "listDishPage";
    String COMMAND_MENU_LIST_USERS_PAGE = "listUsersPage";
}
