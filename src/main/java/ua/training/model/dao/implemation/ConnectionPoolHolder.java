package ua.training.model.dao.implemation;

import org.apache.commons.dbcp.BasicDataSource;
import ua.training.model.dao.utility.DbProperties;

import javax.sql.DataSource;

/**
 * Description: This is the class for pool connection
 * {@link ua.training.model.dao.DaoFactory}
 *
 * @author Zakusylo Pavlo
 */
public class ConnectionPoolHolder {
    /**
     * DbProperties for ConnectionPoolHolder classes
     *
     * @see DbProperties
     */
    private static DbProperties dbProperties = new DbProperties();
    /**
     * DataSource for ConnectionPoolHolder class
     *
     * @see DataSource
     */
    private static volatile DataSource dataSource;

    public static DataSource getDataSource() {

        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setUrl(dbProperties.getUrl());
                    ds.setUsername(dbProperties.getUser());
                    ds.setPassword(dbProperties.getPassword());
                    ds.setMaxActive(20);
                    ds.setMinIdle(5);
                    ds.setMaxIdle(20);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
                }
            }
        }
        return dataSource;
    }
}
