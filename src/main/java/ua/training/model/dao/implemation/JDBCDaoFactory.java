package ua.training.model.dao.implemation;

import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.controller.commands.exception.DataSqlException;
import ua.training.model.dao.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    /**
     * DataSource for DaoFactory
     *
     * @see ConnectionPoolHolder#getDataSource()
     */
    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    /**
     * Connection for JDBCUserDao
     *
     * @return a connection to the data source
     */
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage() + Mess.LOG_CONNECTION_NOT);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }


    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }

    @Override
    public DishDao createDishDao() {
        return new JDBCDishDao(getConnection());
    }

    @Override
    public DayRationDao createDayRationDao() {
        return new JDBCDayRationDao(getConnection());
    }

    @Override
    public RationCompositionDao createRationCompositionDao() {
        return new JDBCRationCompositionDao(getConnection());
    }
}
