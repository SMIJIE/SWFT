package ua.training.constant;

/**
 * Description: This is list of all attributes
 *
 * @author Zakusylo Pavlo
 */
public interface Attributes {
    String HTML_TEXT = "text/html";
    String HTML_ENCODE = "UTF-8";

    String SHOW_COLLAPSE_SIGN_IN = "showCollapseSignIn";
    String SHOW_COLLAPSE_SIGN_UP = "showCollapseSignUp";
    String SHOW_COLLAPSE_MENU_USERS_PAGE = "showCollapseMenuUsersPage";
    String SHOW_COLLAPSE_MENU_ADD_DISH = "showCollapseMenuAddDish";
    String SHOW_COLLAPSE_DAY_RATION_COMPOSITION = "showCollapseDayRationComposition";
    String SHOW_COLLAPSE_ATTRIBUTE_FOR_CCS_CLASS = "show";

    String PAGE_NAME = "pageName";
    String PAGE_VALUE_EMAIL_LOG_IN = "valueEmailLogIn";
    String PAGE_VALUE_PASSWORD_LOG_IN = "valuePasswordLogIn";
    String PAGE_GENERAL = "page.general";
    String PAGE_RATION = "page.ration";
    String PAGE_MENU = "page.menu";
    String PAGE_SIGN_IN_OR_UP = "page.signInOrRegistration";
    String PAGE_USERS_LIST = "menu.users.list";
    String PAGE_MENU_EDIT = "menu.edit.mess";
    String PAGE_DEMONSTRATION = "page.demonstration";
    String PAGE_SETTINGS = "page.settings";

    String PAGE_USER_EXIST = "user.email.exist";
    String PAGE_USER_NOT_EXIST = "user.email.not.exist";
    String PAGE_USER_WRONG_PASSWORD = "user.inappropriate.password";
    String PAGE_USER_NOT_MATCH_PASSWORDS = "user.not.match.passwords";
    String PAGE_USER_LOGGED = "user.email.logged";
    String PAGE_USER_ERROR = "userError";

    String REQUEST_LOCALE_DATE = "localeDate";
    String REQUEST_USER = "user";
    String REQUEST_FORM_USER = "formUser";
    String REQUEST_FORM_DISH = "formDish";
    String REQUEST_FORM_RATION_COMPOSITION = "formRC";
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
    String REQUEST_NAME = "name";
    String REQUEST_EMAIL = "email";
    String REQUEST_PASSWORD = "password";
    String REQUEST_GENERAL_DISHES = "generalDishes";
    String REQUEST_LIST_USERS = "listUsers";
    String REQUEST_MONTHLY_DAY_RATION = "monthlyDR";

    String SQL_EXCEPTION = "wrong.work.server";

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
}
