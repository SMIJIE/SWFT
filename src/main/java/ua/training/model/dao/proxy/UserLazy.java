package ua.training.model.dao.proxy;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.implemation.JDBCDishDao;
import ua.training.model.entity.Dish;
import ua.training.model.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: This is the proxy to get some parameters
 * {@link ua.training.model.entity.User#listDishes}
 *
 * @author Zakusylo Pavlo
 */
public class UserLazy extends User {
    static final DaoFactory DAO_FACTORY = DaoFactory.getInstance();
    static final JDBCDishDao JDBC_DISH_DAO = (JDBCDishDao) DAO_FACTORY.createDishDao();

    @Override
    public ArrayList<Dish> getListDishes() {
        ArrayList<Dish> arrDish = new ArrayList<>();
        List<Dish> listDish = JDBC_DISH_DAO.getAllDishesByUserId(super.getId());

        if (!listDish.isEmpty()) {
            arrDish = (ArrayList<Dish>) listDish;
        }

        return arrDish;
    }
}
