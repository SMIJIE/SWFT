package ua.training.model.dao.utility;

import lombok.extern.log4j.Log4j2;
import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.controller.controllers.exception.DataSqlException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Description: Class that get properties and query for SQL
 *
 * @author Zakusylo Pavlo
 */
@Log4j2
public class DbProperties {
    /**
     * Represents a persistent set of properties
     *
     * @see Properties
     */
    private Properties props;

    public DbProperties() {

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(Attributes.DB_PROPERTIES)) {
            props = new Properties();
            props.load(is);
        } catch (IOException ex) {
            log.error(ex.getMessage() + Mess.LOG_DB_PROPERTIES);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }

    public String getUrl() {
        return props.getProperty(Attributes.DB_URL);
    }

    public String getUser() {
        return props.getProperty(Attributes.DB_USER);
    }

    public String getPassword() {
        return props.getProperty(Attributes.DB_PASSWORD);
    }

    public String getOrCheckUserByEmail() {
        return props.getProperty(Attributes.DB_SQL_USER_BY_EMAIL);
    }

    public String getAllUsersWithoutAdmin() {
        return props.getProperty(Attributes.DB_SQL_USER_GET_LIMIT_WITHOUT_ADMIN);
    }

    public String deleteArrayUsersByEmail() {
        return props.getProperty(Attributes.DB_SQL_USER_DELETE_ARRAY_BY_EMAIL);
    }

    public String countUsersForPage() {
        return props.getProperty(Attributes.DB_SQL_USER_COUNT_FOR_PAGE);
    }

    public String getDishByUserId() {
        return props.getProperty(Attributes.DB_SQL_DISH_BY_USER);
    }

    public String getCountDishesByUserId() {
        return props.getProperty(Attributes.DB_SQL_DISH_COUNT);
    }

    public String deleteDishByUserId() {
        return props.getProperty(Attributes.DB_SQL_DISH_DELETE_BY_USER_ID);
    }

    public String deleteDishByUserEmail() {
        return props.getProperty(Attributes.DB_SQL_DISH_DELETE_BY_USER_EMAIL);
    }

    public String deleteArrayDishById() {
        return props.getProperty(Attributes.DB_SQL_DISH_DELETE_ARRAY_BY_ID);
    }

    public String deleteArrayDishByIdAndUser() {
        return props.getProperty(Attributes.DB_SQL_DISH_DELETE_ARRAY_BY_ID_AND_USERS);
    }

    public String getGeneralDishes() {
        return props.getProperty(Attributes.DB_SQL_DISH_GET_GENERAL);
    }

    public String getDayRationByDateAndUser() {
        return props.getProperty(Attributes.DB_SQL_DAY_RATION_GET_BY_DATE_AND_USER);
    }

    public String getMonthlyDayRationByUser() {
        return props.getProperty(Attributes.DB_SQL_DAY_RATION_GET_MONTHLY_BY_USER);
    }

    public String deleteDayRationByUserId() {
        return props.getProperty(Attributes.DB_SQL_DAY_RATION_DELETE_BY_USER_ID);
    }

    public String deleteDayRationByUserEmail() {
        return props.getProperty(Attributes.DB_SQL_DAY_RATION_DELETE_BY_USER_EMAIL);
    }

    public String getCompositionByRationAndDish() {
        return props.getProperty(Attributes.DB_SQL_RATION_COMPOSITION_GET_BY_RATION_AND_DISH);
    }

    public String getSumCaloriesCompositionByRationId() {
        return props.getProperty(Attributes.DB_SQL_RATION_COMPOSITION_SUM_CALORIES_BY_RATION);
    }

    public String deleteCompositionByDayRationId() {
        return props.getProperty(Attributes.DB_SQL_RATION_COMPOSITION_DELETE_BY_DAY_RATION_ID);
    }

    public String deleteArrayCompositionById() {
        return props.getProperty(Attributes.DB_SQL_RATION_COMPOSITION_DELETE_ARRAY_BY_ID);
    }

    public String deleteCompositionByRationAndUser() {
        return props.getProperty(Attributes.DB_SQL_RATION_COMPOSITION_DELETE_BY_RATION_AND_USER);
    }

    public String deleteCompositionArrayByDishAndUser() {
        return props.getProperty(Attributes.DB_SQL_RATION_COMPOSITION_DELETE_ARRAY_BY_DISH_AND_USER);
    }

    public String deleteCompositionArrayByDish() {
        return props.getProperty(Attributes.DB_SQL_RATION_COMPOSITION_DELETE_ARRAY_BY_DISH);
    }

    public String deleteCompositionArrayByUserEmail() {
        return props.getProperty(Attributes.DB_SQL_RATION_COMPOSITION_DELETE_ARRAY_BY_USER_EMAIL);
    }
}
