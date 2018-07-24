package ua.training.constant;

/**
 * Description: This is list messages
 *
 * @author Zakusylo Pavlo
 */
public interface Mess {
    String LOG_DB_PROPERTIES = "\n\tProblem to read file 'db.properties'";
    String LOG_CONNECTION_NOT = "\n\tProblem to get connection";
    String LOG_CONNECTION_NOT_CLOSE = "\n\tProblem to close connection";
    String LOG_NOT_WRITE_CUSTOM_TAG = "\n\tProblem to write custom tag";
    String LOG_NOT_ACCESSIBLE_ENTITY_FIELDS = "\n\tProblem to get entity fields";

    String LOG_USER_DOUBLE_AUTH = "Double authorization";
    String LOG_USER_LOGGED = "User logged";
    String LOG_USER_LOGGED_OUT = "User logged out";
    String LOG_USER_REGISTERED = "User registered";
    String LOG_USER_UPDATE_PARAMETERS = "User update parameters";
    String LOG_USER_GET_OR_CHECK = "\n\tProblem to get user by email";
    String LOG_USER_GET_BY_ID = "\n\tProblem to get user by id";
    String LOG_USER_GET_ALL = "\n\tProblem to get all users";
    String LOG_USER_COUNT = "\n\tProblem to count users";
    String LOG_USER_DELETE_BY_EMAIL = "\n\tProblem to delete user by email";
    String LOG_USER_DELETE_ROLLBACK = "\n\tProblem to rollback delete user";
    String LOG_USER_UPDATE_ROLLBACK = "\n\tProblem to rollback update user";
    String LOG_USER_NOT_REGISTERED = "\n\tProblem to register new user";
    String LOG_USER_DELETE_BY_ID = "\n\tProblem to delete user by id";
    String LOG_USER_NOT_UPDATE_PARAMETERS = "\n\tProblem to update user parameters";
    String LOG_USER_RS_NOT_EXTRACT = "\n\tProblem to extract user from ResultSet";
    String LOG_USER_HTTP_NOT_EXTRACT = "\n\tProblem to extract user from HttpServletRequest";
    String LOG_USER_GO_ADMIN_URL = "\n\tTries to go by admin urls";
    String LOG_USER_GO_USER_URL = "\n\tTries to go by user urls";
    String LOG_USER_GO_NON_REGISTERED_URL = "\n\tTries to go by user(non registered) urls";

    String LOG_DISH_GET_BY_ID = "\n\tProblem to get dish by id";
    String LOG_DISH_COUNT_BY_USER_ID = "\n\tProblem to count dishes by user id";
    String LOG_DISH_GET_BY_USER = "\n\tProblem to get dish by user";
    String LOG_DISH_GET_GENERAL = "\n\tProblem to get general dish";
    String LOG_DISH_DELETE_BY_ID = "\n\tProblem to delete dish by id";
    String LOG_DISH_NOT_INSERTED = "\n\tProblem to insert new dish";
    String LOG_DISH_NOT_UPDATE_PARAMETERS = "\n\tProblem to update dish parameters";
    String LOG_DISH_DELETE_ROLLBACK = "\n\tProblem to rollback delete dish";
    String LOG_DISH_RS_NOT_EXTRACT = "\n\tProblem to extract dish from ResultSet";
    String LOG_DISH_HTTP_NOT_EXTRACT = "\n\tProblem to extract dish from HttpServletRequest";

    String LOG_DAY_RATION_GET_BY_ID = "\n\tProblem to get day ration by id";
    String LOG_DAY_RATION_GET_BY_DATE_AND_USER = "\n\tProblem to get day ration by date and user";
    String LOG_DAY_RATION_GET_MONTHLY_BY_USER = "\n\tProblem to get monthly day rations by user";
    String LOG_DAY_RATION_DELETE_BY_ID = "\n\tProblem to delete day ration by id";
    String LOG_DAY_RATION_NOT_INSERTED = "\n\tProblem to insert new day ration";
    String LOG_DAY_RATION_NOT_UPDATE_PARAMETERS = "\n\tProblem to update day ration parameters";
    String LOG_DAY_RATION_RS_NOT_EXTRACT = "\n\tProblem to extract day ration from ResultSet";
    String LOG_DAY_RATION_HTTP_NOT_EXTRACT = "\n\tProblem to extract day ration from HttpServletRequest";
    String LOG_DAY_RATION_DELETE_ROLLBACK = "\n\tProblem to rollback delete day ration";

    String LOG_RATION_COMPOSITION_SUM_CALORIES_BY_RATION = "\n\tProblem to sum calories by day ration";
    String LOG_RATION_COMPOSITION_GET_BY_ID = "\n\tProblem to get composition by id";
    String LOG_RATION_COMPOSITION_GET_BY_RATION_AND_DISH = "\n\tProblem to get composition by ration and dish";
    String LOG_RATION_COMPOSITION_GET_ALL = "\n\tProblem to get all composition";
    String LOG_RATION_COMPOSITION_GET_BY_RATION = "\n\tProblem to get composition by ration";
    String LOG_RATION_COMPOSITION_DELETE_BY_ID = "\n\tProblem to delete composition by id";
    String LOG_RATION_COMPOSITION_NOT_INSERTED = "\n\tProblem to insert new composition";
    String LOG_RATION_COMPOSITION_NOT_UPDATE_PARAMETERS = "\n\tProblem to update composition parameters";
    String LOG_RATION_COMPOSITION_RS_NOT_EXTRACT = "\n\tProblem to extract composition from ResultSet";
}
