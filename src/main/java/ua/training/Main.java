package ua.training;

import org.hibernate.Session;
import ua.training.model.dao.implemation.HSesssionFactory;
import ua.training.model.entity.User;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        Session session = HSesssionFactory.getSession();

//        Query<RationComposition> sss = session.createQuery("FROM RationComposition " +
//                "WHERE dayRation.id IN (:oDayRation)", RationComposition.class);
//        Integer[] id = {1, 24, 4};
//        sss.setParameter("oDayRation", Arrays.asList(id));
//        List<RationComposition> sss2 = sss.getResultList();
//        for (RationComposition composition :
//                sss2) {
//            System.out.println(composition.getId());
//        }

        User user = session.load(User.class, 1);
        System.out.println(user);

        user.getListDishes().stream()
                .forEach(System.out::println);

    }
}
