package ua.training.constant;

/**
 * Description: This is list messages
 *
 * @author Zakusylo Pavlo
 */
public interface Mess {
    String LOG_DB_PROPERTIES = "\n\tProblem to read file 'db.properties'";
    String LOG_NOT_WRITE_CUSTOM_TAG = "\n\tProblem to write custom tag";
    String LOG_NOT_ACCESSIBLE_ENTITY_FIELDS = "\n\tProblem to get entity fields";

    String LOG_USER_DOUBLE_AUTH = "Double authorization";
    String LOG_USER_LOGGED = "User logged";
    String LOG_USER_LOGGED_OUT = "User logged out";
    String LOG_USER_REGISTERED = "User registered";
    String LOG_USER_UPDATE_PARAMETERS = "User update parameters";
    String LOG_USER_GET_BY_ID = "\n\tProblem to get user by id";
    String LOG_USER_HTTP_NOT_EXTRACT = "\n\tProblem to extract user from HttpServletRequest";
    String LOG_USER_GO_ADMIN_URL = "\n\tTries to go by admin urls";
    String LOG_USER_GO_USER_URL = "\n\tTries to go by user urls";
    String LOG_USER_GO_NON_REGISTERED_URL = "\n\tTries to go by user(non registered) urls";

    String LOG_DISH_GET_BY_ID = "\n\tProblem to get dish by id";
    String LOG_DISH_HTTP_NOT_EXTRACT = "\n\tProblem to extract dish from HttpServletRequest";

    String LOG_DAY_RATION_GET_BY_ID = "\n\tProblem to get day ration by id";
    String LOG_DAY_RATION_GET_BY_DATE_AND_USER = "\n\tProblem to get day ration by date and user";
    String LOG_DAY_RATION_HTTP_NOT_EXTRACT = "\n\tProblem to extract day ration from HttpServletRequest";

    String LOG_RATION_COMPOSITION_GET_BY_ID = "\n\tProblem to get composition by id";
}
