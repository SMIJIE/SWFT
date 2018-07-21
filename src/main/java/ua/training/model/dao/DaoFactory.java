package ua.training.model.dao;

import ua.training.model.dao.implemation.JDBCDaoFactory;

/**
 * Description: This is the abstract class for SQL Connection
 *
 * @author Zakusylo Pavlo
 */
public abstract class DaoFactory {
    private static volatile DaoFactory daoFactory;

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
