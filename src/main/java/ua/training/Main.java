package ua.training;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ua.training.model.dao.implemation.HSesssionFactory;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        Session session = HSesssionFactory.getSession();

        List<String> aaa = Arrays.asList("qwerty@gmail.com");
        session.beginTransaction();
//        Query sss = session.createQuery("SELECT src.id FROM RationComposition src WHERE src.dayRation.user.email IN (:emails)");
        Query sss = session.createQuery("DELETE FROM RationComposition drc WHERE drc.id IN(SELECT src.id FROM RationComposition src WHERE src.dayRation.user.email IN (:emails))");
        sss.setParameter("emails", aaa);
        sss.executeUpdate();
        session.getTransaction().commit();
//        sss.getResultList().stream().forEach(System.out::println);
    }
}
