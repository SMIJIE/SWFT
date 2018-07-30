package ua.training.model.dao.implemation;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.training.model.dao.utility.DbProperties;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.Roles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class HDayRationDaoTest {
    private Session session;
    private DayRation dayRation;
    private User user;
    private HDayRationDao hDayRationDao;
    private Integer workDayRationId;
    private DbProperties dbProperties;

    @Before
    public void setUp() {
        session = HSesssionFactory.getSession();
        hDayRationDao = new HDayRationDao(session);
        workDayRationId = 1;
        dbProperties = new DbProperties();
        user = User.builder()
                .id(1)
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
                .userCalories(1000)
                .user(user)
                .userCaloriesDesired(2000)
                .build();
    }

    @After
    public void tearDown() {
        session.close();
        hDayRationDao = null;
        user = null;
        dayRation = null;
        workDayRationId = null;
        dbProperties = null;
    }

    @Test
    public void create() {
        session.beginTransaction();

        Integer id = (Integer) session.save(dayRation);
        dayRation.setId(id);

        DayRation temp = session.load(DayRation.class, id);
        assertEquals(dayRation, temp);

        session.getTransaction().rollback();

    }

    @Test
    public void findById() {
        hDayRationDao.findById(workDayRationId).ifPresent(Assert::assertNotNull);
    }

    @Test
    public void update() {
        session.beginTransaction();
        DayRation temp = session.load(DayRation.class, workDayRationId);
        assertNotNull(temp);

        temp.setUserCalories(1111);
        session.update(temp);

        DayRation temp2 = session.load(DayRation.class, workDayRationId);
        assertEquals(temp, temp2);
        assertEquals(1111, temp2.getUserCalories().intValue());

        session.getTransaction().rollback();
    }

    @Test
    public void delete() {
        String hqlDelRationCompos = dbProperties.deleteCompositionByDayRationId();

        session.beginTransaction();
        DayRation temp = session.load(DayRation.class, workDayRationId);
        assertNotNull(temp);

        Query query = session.createQuery(hqlDelRationCompos);
        query.setParameter("idDayRation", temp.getId());
        query.executeUpdate();

        session.delete(temp);
        DayRation temp2 = session.get(DayRation.class, workDayRationId);
        assertNull(temp2);

        session.getTransaction().rollback();
    }

    @Test
    public void checkDayRationByDateAndUserId() {
        session.beginTransaction();
        session.save(dayRation);
        hDayRationDao.checkDayRationByDateAndUserId(dayRation.getDate(), dayRation.getUser().getId())
                .ifPresent(Assert::assertNotNull);
        session.getTransaction().rollback();
    }

    @Test
    public void getMonthlyDayRationByUser() {
        session.beginTransaction();
        DayRation temp = dayRation;
        session.save(dayRation);
        session.save(temp);

        List<DayRation> dayRations = hDayRationDao.getMonthlyDayRationByUser(dayRation.getDate().getMonthValue()
                , dayRation.getDate().getYear()
                , dayRation.getUser().getId());

        assertFalse(dayRations.isEmpty());
        session.getTransaction().rollback();
    }
}