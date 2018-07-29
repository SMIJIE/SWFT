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
        Integer id = 10;
        session.beginTransaction();
        Query sss = session.createQuery("DELETE FROM RationComposition WHERE dayRation IN " +
                "(FROM DayRation WHERE user.id = :idUser)");
//        Query sss = session.createQuery("DELETE FROM RationComposition WHERE dayRation IN" +
//                "(FROM DayRation WHERE user IN (FROM User WHERE email IN (:emails)))");
//        sss.setParameter("emails", aaa);
        sss.setParameter("idUser", id);
        sss.executeUpdate();
        session.getTransaction().commit();
//        sss.getResultList().forEach(System.out::println);
    }
}
