package ua.training.model.dao.implemation;

import lombok.extern.log4j.Log4j2;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.controller.commands.exception.DataSqlException;
import ua.training.model.dao.UserDao;
import ua.training.model.dao.mapper.DayRationMapper;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.Dish;
import ua.training.model.entity.RationComposition;
import ua.training.model.entity.User;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Log4j2
public class HUserDao implements UserDao {
    /**
     * The main runtime interface between a Java application and Hibernate.
     *
     * @see Session
     */
    private Session session;

    HUserDao(Session session) {
        this.session = session;
    }

    @Override
    public void create(User entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }

    /**
     * Load user from database by id
     *
     * @param id Integer
     * @throws DataSqlException
     */
    @Override
    public Optional<User> findById(Integer id) {
        try {
            return Optional.of(session.load(User.class, id));
        } catch (ObjectNotFoundException e) {
            log.error(e.getMessage() + Mess.LOG_USER_GET_BY_ID);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }

    @Override
    public List<User> findAll() {
        String hql = DB_PROPERTIES.getAllUsers();
        Query<User> query = session.createQuery(hql, User.class);

        return query.getResultList();
    }

    /**
     * Update user and current day ration
     *
     * @param entity User
     */
    @Override
    public void update(User entity) {
        String hql = DB_PROPERTIES.getDayRationByDateAndUser();
        LocalDate localDate = LocalDate.now();
        Period period = Period.between(entity.getDob(), localDate);

        session.beginTransaction();
        session.update(entity);

        Query<DayRation> query = session.createQuery(hql, DayRation.class);
        query.setParameter("oDate", localDate);
        query.setParameter("idUser", entity.getId());

        try {
            Optional<DayRation> dayRation = Optional.ofNullable(query.getSingleResult());
            dayRation.ifPresent(dr -> {
                Integer userCalories = DayRationMapper.formulaMifflinSanJerura(entity.getLifeStyleCoefficient(),
                        entity.getWeight(), entity.getHeight(), period.getYears());
                Integer userCaloriesDesired = DayRationMapper.formulaMifflinSanJerura(entity.getLifeStyleCoefficient(),
                        entity.getWeightDesired(), entity.getHeight(), period.getYears());
                dr.setUserCalories(userCalories * 1000);
                dr.setUserCaloriesDesired(userCaloriesDesired * 1000);
                session.update(dr);
            });
        } catch (NoResultException e) {
            log.error(e.getMessage() + Mess.LOG_DAY_RATION_GET_BY_DATE_AND_USER);
        }
        session.getTransaction().commit();
    }

    @Override
    public void delete(Integer id) {
        String hqlDelRationCompos = DB_PROPERTIES.deleteCompositionByRationAndUser();
        String hqlDelDayRation = DB_PROPERTIES.deleteDayRationByUserId();
        String hqlDelDish = DB_PROPERTIES.deleteDishByUserId();

        Optional<User> user = findById(id);
        user.ifPresent(u -> {
            session.beginTransaction();
            Query<RationComposition> compositionQuery = session.createQuery(hqlDelRationCompos, RationComposition.class);
            Query<DayRation> dayRationQuery = session.createQuery(hqlDelDayRation, DayRation.class);
            Query<Dish> dishQuery = session.createQuery(hqlDelDish, Dish.class);

            compositionQuery.setParameter("idUser", id);
            dayRationQuery.setParameter("idUser", id);
            dishQuery.setParameter("idUser", id);

            compositionQuery.executeUpdate();
            dayRationQuery.executeUpdate();
            dishQuery.executeUpdate();

            session.delete(u);
            session.getTransaction().commit();
        });
    }

    @Override
    public void close() {
        session.close();
    }

    /**
     * Search and return User by email
     *
     * @param email String
     * @return user Optional<User>
     * @throws DataSqlException
     */
    @Override
    public Optional<User> getOrCheckUserByEmail(String email) {
        String hql = DB_PROPERTIES.getOrCheckUserByEmail();
        Optional<User> user;

        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("email", email);

        try {
            user = Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            user = Optional.empty();
        }
        return user;
    }

    /**
     * Get limit list of users for page(pagination)
     *
     * @param adminId Integer
     * @param limit   Integer
     * @param skip    Integer
     * @return listOfUsers List<User>
     */
    @Override
    public List<User> getLimitUsersWithoutAdmin(Integer adminId, Integer limit, Integer skip) {
        String hql = DB_PROPERTIES.getAllUsersWithoutAdmin();

        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("idUser", adminId);
        query.setFirstResult(skip);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    /**
     * Count number of users without 'ADMIN' for page(pagination)
     *
     * @param userId Integer
     * @return countUsers Integer
     */
    @Override
    public Integer countUsers(Integer userId) {
        String hql = DB_PROPERTIES.countUsersForPage();

        Query query = session.createQuery(hql);
        query.setParameter("idUser", userId);

        Long counter = (Long) query.getSingleResult();
        return counter.intValue();
    }

    /**
     * Delete array of users by email
     *
     * @param emails List<String>
     */
    @Override
    public void deleteArrayUsersByEmail(List<String> emails) {
        String hqlDelRationCompos = DB_PROPERTIES.deleteCompositionArrayByUserEmail();
        String hqlDelDayRation = DB_PROPERTIES.deleteDayRationByUserEmail();
        String hqlDelDish = DB_PROPERTIES.deleteDishByUserEmail();
        String hqlDelUser = DB_PROPERTIES.deleteArrayUsersByEmail();

        session.beginTransaction();

        Query<RationComposition> compositionQuery = session.createQuery(hqlDelRationCompos, RationComposition.class);
        Query<DayRation> dayRationQuery = session.createQuery(hqlDelDayRation, DayRation.class);
        Query<Dish> dishQuery = session.createQuery(hqlDelDish, Dish.class);
        Query<User> userQuery = session.createQuery(hqlDelUser, User.class);

        compositionQuery.setParameter("emails", emails);
        dayRationQuery.setParameter("emails", emails);
        dishQuery.setParameter("emails", emails);
        userQuery.setParameter("emails", emails);

        compositionQuery.executeUpdate();
        dayRationQuery.executeUpdate();
        dishQuery.executeUpdate();
        userQuery.executeUpdate();

        session.getTransaction().commit();
    }
}
