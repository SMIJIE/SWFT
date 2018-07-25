package ua.training;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ua.training.model.dao.implemation.HSesssionFactory;
import ua.training.model.entity.RationComposition;

import java.util.List;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
//        Session session = HSesssionFactory.getSession();
//
//        Query<RationComposition> sss = session.createQuery("FROM RationComposition " +
//                "WHERE dish.user.id = :idUser", RationComposition.class);
//        sss.setParameter("idUser", 2);
//
//        List<RationComposition> sss2 = sss.getResultList();
//        for (RationComposition composition :
//                sss2) {
//            System.out.println(composition.getId());
//        }

        System.out.println(Math.floor(2.7));
        System.out.println(20%5);
        System.out.println(21%5);

//        User user = session.load(User.class, 1);
//        System.out.println(user);
//
//        user.getListDishes().stream()
//                .forEach(System.out::println);

    }
}
