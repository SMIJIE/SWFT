package ua.training.model.dao.implemation;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.training.model.dao.mapper.DayRationMapper;
import ua.training.model.dao.utility.DbProperties;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.Roles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

public class HUserDaoTest {
    private User user;
    private DayRation dayRation;
    private Session session;
    private HUserDao hUserDao;
    private Integer workId;
    private DbProperties dbProperties;

    @Before
    public void setUp() {
        session = HSesssionFactory.getSession();
        hUserDao = new HUserDao(session);
        workId = 1;
        dbProperties = new DbProperties();
        user = User.builder()
                .name("Mock")
                .dob(LocalDate.parse("1993-06-15"))
                .email("Mock@gmail.com")
                .password("qwerty")
                .role(Roles.ADMIN)
                .height(18000)
                .weight(80000)
                .weightDesired(70000)
                .lifeStyleCoefficient(1500)
                .build();
        dayRation = DayRation.builder()
                .date(LocalDate.now())
                .user(user)
                .userCalories(1000)
                .userCaloriesDesired(2000)
                .build();
    }

    @After
    public void tearDown() {
        session.close();
        hUserDao = null;
        user = null;
        dayRation = null;
        workId = null;
        dbProperties = null;
    }

    @Test
    public void create() {
        session.beginTransaction();

        Integer id = (Integer) session.save(user);
        user.setId(id);

        User tempUser = session.load(User.class, user.getId());
        assertNotNull(tempUser);

        session.getTransaction().rollback();
    }

    @Test
    public void findById() {
        hUserDao.findById(workId)
                .ifPresent(Assert::assertNotNull);
    }

    @Test
    public void update() {
        String hql = dbProperties.getDayRationByDateAndUser();

        session.beginTransaction();

        User tempUser = session.load(User.class, workId);
        dayRation.setUser(tempUser);
        Integer id = (Integer) session.save(dayRation);

        DayRation tempDayRation = session.load(DayRation.class, id);

        tempUser.setName("CheckOp");
        Query<DayRation> query = session.createQuery(hql, DayRation.class);
        query.setParameter("oDate", tempDayRation.getDate());
        query.setParameter("idUser", tempUser.getId());

        Integer userCalories = DayRationMapper.formulaMifflinSanJerura(tempUser.getLifeStyleCoefficient(),
                tempUser.getWeight(), tempUser.getHeight(), 20);
        Integer userCaloriesDesired = DayRationMapper.formulaMifflinSanJerura(tempUser.getLifeStyleCoefficient(),
                tempUser.getWeightDesired(), tempUser.getHeight(), 20);
        tempDayRation.setUserCalories(userCalories * 1000);
        tempDayRation.setUserCaloriesDesired(userCaloriesDesired * 1000);

        session.update(tempUser);
        session.update(tempDayRation);

        tempUser = session.load(User.class, workId);
        tempDayRation = session.load(DayRation.class, id);

        Integer tempInt = userCalories * 1000;

        assertEquals("CheckOp", tempUser.getName());
        assertEquals(tempInt, tempDayRation.getUserCalories());

        session.getTransaction().rollback();
    }

    //ToDO: Check user from database when call method session.delete
    @Test
    public void delete() {
        String hqlDelRationCompos = dbProperties.deleteCompositionByRationAndUser();
        String hqlDelDayRation = dbProperties.deleteDayRationByUserId();
        String hqlDelDish = dbProperties.deleteDishByUserId();

        session.beginTransaction();

        User tempUser = session.load(User.class, 8);
        assertNotNull(tempUser);

        Query compositionQuery = session.createQuery(hqlDelRationCompos);
        Query dayRationQuery = session.createQuery(hqlDelDayRation);
        Query dishQuery = session.createQuery(hqlDelDish);

        compositionQuery.setParameter("idUser", tempUser.getId());
        dayRationQuery.setParameter("idUser", tempUser.getId());
        dishQuery.setParameter("idUser", tempUser.getId());

        compositionQuery.executeUpdate();
        dayRationQuery.executeUpdate();
        dishQuery.executeUpdate();

        session.delete(tempUser);

        session.getTransaction().rollback();
    }

    @Test
    public void getOrCheckUserByEmail() {
        hUserDao.getOrCheckUserByEmail("Zakusylo@gmail.com").ifPresent(Assert::assertNotNull);
    }

    @Test
    public void getLimitUsersWithoutAdmin() {
        List<User> users = new ArrayList<>();
        assertTrue(users.isEmpty());

        users = hUserDao.getLimitUsersWithoutAdmin(workId, 6, 0);
        assertFalse(users.isEmpty());
    }

    @Test
    public void countUsers() {
        Integer count = hUserDao.countUsers(workId);
        assertNotNull(count);
    }

    @Test
    public void deleteArrayUsersByEmail() {
        List<String> emails = Arrays.asList("Mark@bigmir.net", "Sasha@bigmir.net");

        String hqlDelRationCompos = dbProperties.deleteCompositionArrayByUserEmail();
        String hqlDelDayRation = dbProperties.deleteDayRationByUserEmail();
        String hqlDelDish = dbProperties.deleteDishByUserEmail();
        String hqlDelUser = dbProperties.deleteArrayUsersByEmail();

        Integer count = hUserDao.countUsers(workId) - 2;

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

        assertEquals(count, hUserDao.countUsers(workId));
        session.getTransaction().rollback();
    }
}