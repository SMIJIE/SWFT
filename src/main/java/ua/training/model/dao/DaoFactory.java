package ua.training.model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.implemation.JDBCDaoFactory;

/**
 * Description: This is the abstract class for SQL Connection
 *
 * @author Zakusylo Pavlo
 */
public abstract class DaoFactory {
    private static volatile DaoFactory daoFactory;
    /**
     * Logger for DaoFactory classes
     *
     * @see LogManager
     */
    public Logger logger = LogManager.getLogger(DaoFactory.class);

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    DaoFactory temp = new JDBCDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }

    public abstract UserDao createUserDao();

    public abstract DishDao createDishDao();

    public abstract DayRationDao createDayRationDao();

    public abstract RationCompositionDao createRationCompositionDao();
}
