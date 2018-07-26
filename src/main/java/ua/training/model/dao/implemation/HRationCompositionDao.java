package ua.training.model.dao.implemation;

import lombok.extern.log4j.Log4j2;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.controller.commands.exception.DataSqlException;
import ua.training.model.dao.RationCompositionDao;
import ua.training.model.entity.RationComposition;
import ua.training.model.entity.enums.FoodIntake;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Log4j2
public class HRationCompositionDao implements RationCompositionDao {
    /**
     * The main runtime interface between a Java application and Hibernate.
     *
     * @see Session
     */
    private Session session;

    HRationCompositionDao(Session session) {
        this.session = session;
    }

    @Override
    public void create(RationComposition entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }

    /**
     * Load ration composition from database by id
     *
     * @param id Integer
     * @throws DataSqlException
     */
    @Override
    public Optional<RationComposition> findById(Integer id) {
        try {
            return Optional.of(session.load(RationComposition.class, id));
        } catch (ObjectNotFoundException e) {
            log.error(e.getMessage() + Mess.LOG_RATION_COMPOSITION_GET_BY_ID);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }

    @Override
    public List<RationComposition> findAll() {
        String hql = DB_PROPERTIES.getAllComposition();
        Query<RationComposition> query = session.createQuery(hql, RationComposition.class);

        return query.getResultList();
    }

    @Override
    public void update(RationComposition entity) {
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
    }

    @Override
    public void delete(Integer id) {
        Optional<RationComposition> composition = findById(id);
        composition.ifPresent(c -> {
            session.beginTransaction();
            session.delete(c);
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
     * Calculate sum of calories
     *
     * @param idDayRation Integer
     * @return sumCalories Integer
     */
    @Override
    public Integer getSumCaloriesCompositionByRationId(Integer idDayRation) {
        String hql = DB_PROPERTIES.getSumCaloriesCompositionByRationId();
        Long sumCalories = 0L;

        Query query = session.createQuery(hql);
        query.setParameter("idDayRation", idDayRation);

        Optional<Object> temp = Optional.ofNullable(query.getSingleResult());
        if (temp.isPresent()) {
            sumCalories = (Long) temp.get();
        }

        return sumCalories.intValue();
    }

    /**
     * Get RationComposition by DayRation & Dish & FoodIntake
     *
     * @param rationId   Integer
     * @param foodIntake FoodIntake
     * @param dishId     Integer
     * @return rationComposition Optional<RationComposition>
     */
    @Override
    public Optional<RationComposition> getCompositionByRationDishFoodIntake(Integer rationId,
                                                                            FoodIntake foodIntake,
                                                                            Integer dishId) {
        Optional<RationComposition> rationComposition;
        String hql = DB_PROPERTIES.getCompositionByRationAndDish();

        try {
            Query<RationComposition> query = session.createQuery(hql, RationComposition.class);
            query.setParameter("idDayRation", rationId);
            query.setParameter("oFoodIntake", foodIntake);
            query.setParameter("idDish", dishId);

            rationComposition = Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            rationComposition = Optional.empty();
        }

        return rationComposition;
    }

    /**
     * Get all RationComposition by DayRation
     *
     * @param rationId Integer
     * @return rationCompositions List<RationComposition>
     */
    @Override
    public List<RationComposition> getAllCompositionByRation(Integer rationId) {
        String hql = DB_PROPERTIES.getAllCompositionByRation();

        Query<RationComposition> query = session.createQuery(hql, RationComposition.class);
        query.setParameter("idDayRation", rationId);

        return query.getResultList();
    }

    /**
     * Delete array of RationComposition
     *
     * @param compositionId List<Integer>
     */
    @Override
    public void deleteArrayCompositionById(List<Integer> compositionId) {
        String hql = DB_PROPERTIES.deleteArrayCompositionById();

        session.beginTransaction();

        Query query = session.createQuery(hql);
        query.setParameter("idRC", compositionId);
        query.executeUpdate();

        session.getTransaction().commit();
        session.clear();
    }
}