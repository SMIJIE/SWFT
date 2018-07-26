package ua.training;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ua.training.model.dao.implemation.HSesssionFactory;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        Session session = HSesssionFactory.getSession();

        List<Integer> aaa = Arrays.asList(94, 95, 96);
        session.beginTransaction();
        Query sss = session.createQuery("DELETE FROM RationComposition WHERE id IN (:iaa)");
        sss.setParameter("iaa", aaa);
        sss.executeUpdate();
        session.getTransaction().commit();
    }
}
