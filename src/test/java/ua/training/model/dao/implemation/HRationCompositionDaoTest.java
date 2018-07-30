package ua.training.model.dao.implemation;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.training.model.dao.utility.DbProperties;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.Dish;
import ua.training.model.entity.RationComposition;
import ua.training.model.entity.enums.FoodCategory;
import ua.training.model.entity.enums.FoodIntake;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.*;

public class HRationCompositionDaoTest {
    private Session session;
    private HRationCompositionDao hRationCompositionDao;
    private DayRation dayRation;
    private Dish dish;
    private RationComposition rationComposition;
    private Integer workRationCompositionId;
    private DbProperties dbProperties;

    @Before
    public void setUp() {
        session = HSesssionFactory.getSession();
        hRationCompositionDao = new HRationCompositionDao(session);
        workRationCompositionId = 1;
        dbProperties = new DbProperties();
        dayRation = DayRation.builder()
                .id(1)
                .date(LocalDate.now())
                .userCalories(1000)
                .userCaloriesDesired(2000)
                .build();
        dish = Dish.builder()
                .id(1)
                .foodCategory(FoodCategory.HOT)
                .name("Mockito")
                .weight(1000)
                .calories(1000)
                .proteins(1000)
                .fats(1000)
                .carbohydrates(1000)
                .generalFood(false)
                .build();
        rationComposition = RationComposition.builder()
                .dayRation(dayRation)
                .foodIntake(FoodIntake.BREAKFAST)
                .dish(dish)
                .numberOfDish(3)
                .caloriesOfDish(1000)
                .build();
    }

    @After
    public void tearDown() {
        session.close();
        hRationCompositionDao = null;
        workRationCompositionId = null;
        dbProperties = null;
        dish = null;
        dayRation = null;
        rationComposition = null;
    }

    @Test
    public void create() {
        session.beginTransaction();
        Integer id = (Integer) session.save(rationComposition);
        rationComposition.setId(id);

        RationComposition temp = session.load(RationComposition.class, id);

        assertEquals(rationComposition, temp);

        session.getTransaction().rollback();
    }

    @Test
    public void findById() {
        hRationCompositionDao.findById(workRationCompositionId)
                .ifPresent(Assert::assertNotNull);
    }

    @Test
    public void update() {
        session.beginTransaction();

        RationComposition temp = session.load(RationComposition.class, workRationCompositionId);
        assertNotNull(temp);

        temp.setNumberOfDish(5);
        session.update(temp);

        RationComposition temp2 = session.load(RationComposition.class, workRationCompositionId);
        assertEquals(temp, temp2);
        assertEquals(5, temp2.getNumberOfDish().intValue());

        session.getTransaction().rollback();
    }

    @Test
    public void delete() {
        session.beginTransaction();
        RationComposition temp = session.load(RationComposition.class, workRationCompositionId);
        assertNotNull(temp);

        session.delete(temp);

        RationComposition temp2 = session.get(RationComposition.class, workRationCompositionId);
        assertNull(temp2);

        session.getTransaction().rollback();
    }

    @Test
    public void getSumCaloriesCompositionByRationId() {
        RationComposition temp = session.load(RationComposition.class, workRationCompositionId);
        Integer sum = hRationCompositionDao.getSumCaloriesCompositionByRationId(temp.getDayRation().getId());
        assertNotNull(sum);
    }

    @Test
    public void getCompositionByRationDishFoodIntake() {
        RationComposition temp = session.load(RationComposition.class, workRationCompositionId);
        assertNotNull(temp);

        hRationCompositionDao.getCompositionByRationDishFoodIntake(
                temp.getDayRation().getId()
                , temp.getFoodIntake()
                , temp.getDish().getId())
                .ifPresent(rc -> assertEquals(temp, rc));
    }

    @Test
    public void deleteArrayCompositionById() {
        List<Integer> array = Arrays.asList(1, 2, 3);
        String hql = dbProperties.deleteArrayCompositionById();

        session.beginTransaction();
        RationComposition temp = session.load(RationComposition.class, 1);
        assertNotNull(temp);

        Query query = session.createQuery(hql);
        query.setParameter("idRC", array);
        query.executeUpdate();

        RationComposition temp2 = session.get(RationComposition.class, 1);
        assertNull(temp2);

        session.getTransaction().rollback();
    }
}