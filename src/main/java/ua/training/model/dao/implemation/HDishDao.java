package ua.training.model.dao.implemation;

import lombok.extern.log4j.Log4j2;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.training.controller.exception.DataSqlException;
import ua.training.model.dao.DishDao;
import ua.training.model.entity.Dish;
import ua.training.model.utility.DbProperties;

import java.util.List;
import java.util.Optional;

@Log4j2
@Repository
public class HDishDao implements DishDao {
    @Autowired
    private HSesssionFactory hSesssionFactory;
    private Session session = hSesssionFactory.getSession();
    @Autowired
    private DbProperties dbProperties;

    @Override
    public void create(Dish entity) {
        session.beginTransaction();
        Integer id = (Integer) session.save(entity);
        entity.setId(id);
        session.getTransaction().commit();
    }

    /**
     * Load dish from database by id
     *
     * @param id Integer
     * @throws DataSqlException
     */
    @Override
    public Optional<Dish> findById(Integer id) {
        try {
            return Optional.of(session.load(Dish.class, id));
        } catch (ObjectNotFoundException e) {
            log.error(e.getMessage() + LOG_DISH_GET_BY_ID);
            throw new DataSqlException(SQL_EXCEPTION);
        }
    }

    @Override
    public void update(Dish entity) {
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
    }

    /**
     * Load and delete dish from database by id
     *
     * @param id Integer
     */
    @Override
    public void delete(Integer id) {
        String hqlDelRationCompos = dbProperties.deleteCompositionArrayByDish();
        findById(id).ifPresent(d -> {
            session.beginTransaction();

            Query queryDelRationCompos = session.createQuery(hqlDelRationCompos);
            queryDelRationCompos.setParameter("idDish", id);
            queryDelRationCompos.executeUpdate();

            session.delete(d);
            session.getTransaction().commit();
            session.clear();
        });
    }

    @Override
    public void close() {
        session.flush();
        session.close();
    }

    /**
     * Return all general dishes
     *
     * @return list List<Dish>
     */
    @Override
    public List<Dish> getAllGeneralDishes() {
        String hql = dbProperties.getGeneralDishes();

        Query<Dish> query = session.createQuery(hql, Dish.class);
        query.setParameter("isGeneralFood", true);

        return query.getResultList();
    }

    /**
     * Return users dishes with limit for page
     *
     * @param userId Integer
     * @param limit  Integer
     * @param skip   Integer
     * @return list List<Dish>
     */
    @Override
    public List<Dish> getLimitDishesByUserId(Integer userId, Integer limit, Integer skip) {
        String hql = dbProperties.getDishByUserId();

        Query<Dish> query = session.createQuery(hql, Dish.class);
        query.setParameter("idUser", userId);
        query.setFirstResult(skip);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    /**
     * Delete array of dishes
     *
     * @param array List<Integer>
     */
    @Override
    public void deleteArrayDishesById(List<Integer> array) {
        String hqlDeleteDish = dbProperties.deleteArrayDishById();
        String hqlDelRationCompos = dbProperties.deleteCompositionArrayByDish();

        session.beginTransaction();

        Query queryDelRationCompos = session.createQuery(hqlDelRationCompos);
        Query queryDeleteDish = session.createQuery(hqlDeleteDish);

        queryDelRationCompos.setParameter("idDish", array);
        queryDeleteDish.setParameter("idDish", array);

        queryDelRationCompos.executeUpdate();
        queryDeleteDish.executeUpdate();

        session.getTransaction().commit();
        session.clear();
    }

    /**
     * Delete array of dishes by user
     *
     * @param array  List<Integer>
     * @param userId Integer
     */
    @Override
    public void deleteArrayDishesByIdAndUser(List<Integer> array, Integer userId) {
        String hqlDeleteDish = dbProperties.deleteArrayDishByIdAndUser();
        String hqlDelRationCompos = dbProperties.deleteCompositionArrayByDishAndUser();

        session.beginTransaction();

        Query queryDelRationCompos = session.createQuery(hqlDelRationCompos);
        Query queryDeleteDish = session.createQuery(hqlDeleteDish);

        queryDelRationCompos.setParameter("idDish", array);
        queryDelRationCompos.setParameter("idUser", userId);
        queryDeleteDish.setParameter("idDish", array);
        queryDeleteDish.setParameter("idUser", userId);

        queryDelRationCompos.executeUpdate();
        queryDeleteDish.executeUpdate();

        session.getTransaction().commit();
        session.clear();
    }

    /**
     * Count number of dishes by user for page
     *
     * @param userId Integer
     * @return counter Integer
     */
    @Override
    public Integer countDishes(Integer userId) {
        String hql = dbProperties.getCountDishesByUserId();

        Query query = session.createQuery(hql);
        query.setParameter("idUser", userId);

        Long counter = (Long) query.getSingleResult();
        return counter.intValue();
    }
}
