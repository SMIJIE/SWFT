package ua.training.model.dao.implemation;

import com.mysql.jdbc.Driver;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.MySQL5Dialect;
import ua.training.model.dao.utility.DbProperties;
import ua.training.model.dao.utility.converter.DateConverter;
import ua.training.model.dao.utility.converter.FoodCategoryConverter;
import ua.training.model.dao.utility.converter.FoodIntakeConverter;
import ua.training.model.dao.utility.converter.RolesConverter;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.Dish;
import ua.training.model.entity.RationComposition;
import ua.training.model.entity.User;

import java.util.Properties;

/**
 * Description: This is the class for Hibernate Session
 * {@link ua.training.model.dao.DaoFactory}
 *
 * @author Zakusylo Pavlo
 */
public class HSesssionFactory {
    /**
     * DbProperties for Hibernate Session
     *
     * @see DbProperties
     */
    private static DbProperties dbProperties = new DbProperties();
    /**
     * The main contract here is the creation of {@link Session} instances.
     *
     * @see SessionFactory
     */
    private static final SessionFactory SESSION_FACTORY = initializeSessionFactory();
    /**
     * Represents one approach for bootstrapping Hibernate.
     *
     * @see Configuration
     */
    private static volatile Configuration CONFIG;

    /**
     * Build and return Session Factory
     *
     * @return object SessionFactory
     */
    private static SessionFactory initializeSessionFactory() {
        if (CONFIG == null) {
            synchronized (SessionFactory.class) {
                if (CONFIG == null) {
                    Properties jpaProps = getProperties();
                    CONFIG = getConfig(jpaProps);
                }
            }
        }
        return CONFIG.buildSessionFactory();
    }

    public static Session getSession() {
        return SESSION_FACTORY.openSession();
    }

    /**
     * Put 'Environment' attributes and return Properties
     *
     * @return properties Properties
     */
    private static Properties getProperties() {
        Properties properties = new Properties();

        properties.put(Environment.DRIVER, Driver.class.getCanonicalName());
        properties.put(Environment.DIALECT, MySQL5Dialect.class.getCanonicalName());
        properties.put(Environment.URL, dbProperties.getUrl());
        properties.put(Environment.USER, dbProperties.getUser());
        properties.put(Environment.PASS, dbProperties.getPassword());
        properties.put(Environment.HBM2DDL_AUTO, "validate");
        properties.put(Environment.SHOW_SQL, true);

        return properties;
    }

    /**
     * Add attributes and return Configuration
     *
     * @param jpaProps Properties
     * @return config Configuration
     */
    private static Configuration getConfig(Properties jpaProps) {
        Configuration config = new Configuration();

        config.addAnnotatedClass(User.class);
        config.addAnnotatedClass(Dish.class);
        config.addAnnotatedClass(DayRation.class);
        config.addAnnotatedClass(RationComposition.class);
        config.addProperties(jpaProps);
        config.addAttributeConverter(DateConverter.class);
        config.addAttributeConverter(RolesConverter.class);
        config.addAttributeConverter(FoodCategoryConverter.class);
        config.addAttributeConverter(FoodIntakeConverter.class);

        return config;
    }
}

