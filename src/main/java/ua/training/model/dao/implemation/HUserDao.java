package ua.training.model.dao.implemation;

import lombok.extern.log4j.Log4j2;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.training.controller.exception.DataSqlException;
import ua.training.controller.mapper.DayRationMapper;
import ua.training.model.dao.UserDao;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.User;
import ua.training.model.utility.DbProperties;

import javax.annotation.PostConstruct;
import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Log4j2
@Repository
public class HUserDao implements UserDao {
    @Autowired
    private HSessionFactory hSessionFactory;
    private Session session;
    private DbProperties dbProperties;

    @PostConstruct
    public void init() {
        session = hSessionFactory.getSession();
        dbProperties = new DbProperties();
    }

    @Override
    public void create(User entity) {
        session.beginTransaction();
        Integer id = (Integer) session.save(entity);
        entity.setId(id);
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
            log.error(e.getMessage() + LOG_USER_GET_BY_ID);
            throw new DataSqlException(SQL_EXCEPTION);
        }
    }

    /**
     * Update user and current day ration
     *
     * @param entity User
     */
    @Override
    public void update(User entity) {
        String hql = dbProperties.getDayRationByDateAndUser();
        LocalDate localDate = LocalDate.now();
        Period period = Period.between(entity.getDob(), localDate);

        session.beginTransaction();
        session.update(entity);

        Query<DayRation> query = session.createQuery(hql, DayRation.class);
        query.setParameter("oDate", localDate);
        query.setParameter("idUser", entity.getId());

        try {
            Optional.ofNullable(query.getSingleResult())
                    .ifPresent(dr -> {
                        Integer userCalories = DayRationMapper.formulaMifflinSanJerura(entity.getLifeStyleCoefficient(),
                                entity.getWeight(), entity.getHeight(), period.getYears());
                        Integer userCaloriesDesired = DayRationMapper.formulaMifflinSanJerura(entity.getLifeStyleCoefficient(),
                                entity.getWeightDesired(), entity.getHeight(), period.getYears());
                        dr.setUserCalories(userCalories * 1000);
                        dr.setUserCaloriesDesired(userCaloriesDesired * 1000);

                        session.update(dr);
                    });
        } catch (NoResultException e) {
            log.info(e.getMessage() + LOG_DAY_RATION_GET_BY_DATE_AND_USER);
        }

        session.getTransaction().commit();
    }

    @Override
    public void delete(Integer id) {
        String hqlDelRationCompos = dbProperties.deleteCompositionByRationAndUser();
        String hqlDelDayRation = dbProperties.deleteDayRationByUserId();
        String hqlDelDish = dbProperties.deleteDishByUserId();

        findById(id).ifPresent(u -> {
            session.beginTransaction();

            Query compositionQuery = session.createQuery(hqlDelRationCompos);
            Query dayRationQuery = session.createQuery(hqlDelDayRation);
            Query dishQuery = session.createQuery(hqlDelDish);

            compositionQuery.setParameter("idUser", id);
            dayRationQuery.setParameter("idUser", id);
            dishQuery.setParameter("idUser", id);

            compositionQuery.executeUpdate();
            dayRationQuery.executeUpdate();
            dishQuery.executeUpdate();

            session.delete(u);
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
     * Search and return User by email
     *
     * @param email String
     * @return user Optional<User>
     */
    @Override
    public Optional<User> getOrCheckUserByEmail(String email) {
        String hql = dbProperties.getOrCheckUserByEmail();
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
        String hql = dbProperties.getAllUsersWithoutAdmin();

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
        String hql = dbProperties.countUsersForPage();

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
        String hqlDelRationCompos = dbProperties.deleteCompositionArrayByUserEmail();
        String hqlDelDayRation = dbProperties.deleteDayRationByUserEmail();
        String hqlDelDish = dbProperties.deleteDishByUserEmail();
        String hqlDelUser = dbProperties.deleteArrayUsersByEmail();

        session.beginTransaction();

        Query compositionQuery = session.createQuery(hqlDelRationCompos);
        Query dayRationQuery = session.createQuery(hqlDelDayRation);
        Query dishQuery = session.createQuery(hqlDelDish);
        Query userQuery = session.createQuery(hqlDelUser);

        compositionQuery.setParameter("emails", emails);
        dayRationQuery.setParameter("emails", emails);
        dishQuery.setParameter("emails", emails);
        userQuery.setParameter("emails", emails);

        compositionQuery.executeUpdate();
        dayRationQuery.executeUpdate();
        dishQuery.executeUpdate();
        userQuery.executeUpdate();

        session.getTransaction().commit();
        session.clear();
    }
}
