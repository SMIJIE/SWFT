package ua.training;

import com.mysql.jdbc.Driver;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.query.Query;
import ua.training.model.dao.utility.DbProperties;
import ua.training.model.dao.utility.converter.DateConverter;
import ua.training.model.dao.utility.converter.FoodCategoryConverter;
import ua.training.model.dao.utility.converter.FoodIntakeConverter;
import ua.training.model.dao.utility.converter.RolesConverter;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.Dish;
import ua.training.model.entity.RationComposition;
import ua.training.model.entity.User;

import java.util.List;
import java.util.Properties;

import static java.util.Objects.isNull;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {

        DbProperties dbProperties = new DbProperties();

        Properties jpaProps = new Properties();
        jpaProps.put(Environment.DRIVER, Driver.class.getCanonicalName());
        jpaProps.put(Environment.DIALECT, MySQL5Dialect.class.getCanonicalName());
        jpaProps.put(Environment.URL, dbProperties.getUrl());
        jpaProps.put(Environment.USER, dbProperties.getUser());
        jpaProps.put(Environment.PASS, dbProperties.getPassword());
        jpaProps.put(Environment.HBM2DDL_AUTO, "validate");
        jpaProps.put(Environment.SHOW_SQL, true);

        Configuration congig = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Dish.class)
                .addAnnotatedClass(DayRation.class)
                .addAnnotatedClass(RationComposition.class)
                .addProperties(jpaProps);

        congig.addAttributeConverter(DateConverter.class);
        congig.addAttributeConverter(RolesConverter.class);
        congig.addAttributeConverter(FoodCategoryConverter.class);
        congig.addAttributeConverter(FoodIntakeConverter.class);

        System.out.println(isNull(congig.buildSessionFactory()));

        Session session = congig.buildSessionFactory().openSession();

        List<User> customerQuery = session.createQuery("FROM User", User.class).getResultList();

        Query<User> customerQuery2 = session.createQuery("FROM User WHERE id = :idUser", User.class);
        customerQuery2.setParameter("idUser", 1);
        User aaa = customerQuery2.getSingleResult();

        System.out.println(aaa);

//        for (User u : customerQuery) {
//            System.out.println(u);
//        }
    }
}
