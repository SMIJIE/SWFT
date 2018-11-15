package ua.training.model.dao.implemation;

import com.mysql.jdbc.Driver;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.Dish;
import ua.training.model.entity.RationComposition;
import ua.training.model.entity.User;
import ua.training.model.utility.converter.DateConverter;
import ua.training.model.utility.converter.FoodCategoryConverter;
import ua.training.model.utility.converter.FoodIntakeConverter;
import ua.training.model.utility.converter.RolesConverter;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * Description: This is the class for Hibernate Session
 *
 * @author Zakusylo Pavlo
 */
@Component
public class HSessionFactory {
    @Value("#{dbProperties.url}")
    private String url;
    @Value("#{dbProperties.user}")
    private String user;
    @Value("#{dbProperties.password}")
    private String password;

    /**
     * Represents one approach for bootstrapping Hibernate.
     *
     * @see Configuration
     */
    private volatile Configuration config;
    /**
     * The main contract here is the creation of {@link Session} instances.
     *
     * @see SessionFactory
     */
    private SessionFactory sessionFactory;

    @PostConstruct
    public void init() {
        sessionFactory = initializeSessionFactory();
    }

    /**
     * Build and return Session Factory
     *
     * @return object SessionFactory
     */
    private SessionFactory initializeSessionFactory() {
        if (config == null) {
            synchronized (SessionFactory.class) {
                if (config == null) {
                    Properties jpaProps = getProperties();
                    config = getConfig(jpaProps);
                }
            }
        }
        return config.buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    /**
     * Put 'Environment' attributes and return Properties
     *
     * @return properties Properties
     */
    private Properties getProperties() {
        Properties properties = new Properties();

        properties.put(Environment.DRIVER, Driver.class.getCanonicalName());
        properties.put(Environment.DIALECT, MySQL5Dialect.class.getCanonicalName());
        properties.put(Environment.URL, url);
        properties.put(Environment.USER, user);
        properties.put(Environment.PASS, password);
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
    private Configuration getConfig(Properties jpaProps) {
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

