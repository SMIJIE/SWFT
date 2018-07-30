package ua.training.model.dao.implemation;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.training.model.dao.utility.DbProperties;
import ua.training.model.entity.Dish;
import ua.training.model.entity.enums.FoodCategory;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.*;

public class HDishDaoTest {
    private Dish dish;
    private Session session;
    private HDishDao hDishDao;
    private Integer workDishId;
    private Integer workUserId;
    private DbProperties dbProperties;


    @Before
    public void setUp() {
        session = HSesssionFactory.getSession();
        hDishDao = new HDishDao(session);
        workDishId = 1;
        workUserId = 1;
        dbProperties = new DbProperties();
        dish = Dish.builder()
                .foodCategory(FoodCategory.HOT)
                .name("Mockito")
                .weight(1000)
                .calories(1000)
                .proteins(1000)
                .fats(1000)
                .carbohydrates(1000)
                .generalFood(false)
                .build();
    }

    @After
    public void tearDown() {
        session.close();
        hDishDao = null;
        dish = null;
        workDishId = null;
        workUserId = null;
        dbProperties = null;
    }

    @Test
    public void create() {
        session.beginTransaction();

        Integer id = (Integer) session.save(dish);
        dish.setId(id);

        Dish temp = session.load(Dish.class, id);
        assertEquals(dish, temp);
        session.getTransaction().rollback();
    }

    @Test
    public void findById() {
        hDishDao.findById(workDishId)
                .ifPresent(Assert::assertNotNull);
    }

    @Test
    public void update() {
        session.beginTransaction();

        Dish temp = session.load(Dish.class, workDishId);
        temp.setFoodCategory(FoodCategory.DESSERT);
        session.update(temp);

        Dish temp2 = session.load(Dish.class, workDishId);
        assertEquals(FoodCategory.DESSERT, temp2.getFoodCategory());

        session.getTransaction().rollback();
    }

    @Test
    public void delete() {
        String hqlDelRationCompos = dbProperties.deleteCompositionArrayByDish();

        session.beginTransaction();
        Dish temp = session.get(Dish.class, workDishId);
        assertNotNull(temp);

        Query queryDelRationCompos = session.createQuery(hqlDelRationCompos);
        queryDelRationCompos.setParameter("idDish", temp.getId());
        queryDelRationCompos.executeUpdate();

        session.delete(temp);

        Dish temp2 = session.get(Dish.class, workDishId);
        assertNull(temp2);

        session.getTransaction().rollback();
    }

    @Test
    public void getAllGeneralDishes() {
        List<Dish> dishes = hDishDao.getAllGeneralDishes();
        assertFalse(dishes.isEmpty());
        dishes.forEach(dish -> assertTrue(dish.getGeneralFood()));
    }

    @Test
    public void getLimitDishesByUserId() {
        List<Dish> dishes = hDishDao.getLimitDishesByUserId(1, 6, 0);
        assertFalse(dishes.isEmpty());
    }

    @Test
    public void deleteArrayDishesById() {
        List<Integer> array = Arrays.asList(1, 2, 3);
        String hqlDeleteDish = dbProperties.deleteArrayDishById();
        String hqlDelRationCompos = dbProperties.deleteCompositionArrayByDish();

        session.beginTransaction();
        Dish temp = session.load(Dish.class, 1);
        assertNotNull(temp);

        Query queryDelRationCompos = session.createQuery(hqlDelRationCompos);
        Query queryDeleteDish = session.createQuery(hqlDeleteDish);

        queryDelRationCompos.setParameter("idDish", array);
        queryDeleteDish.setParameter("idDish", array);

        queryDelRationCompos.executeUpdate();
        queryDeleteDish.executeUpdate();

        Dish temp2 = session.get(Dish.class, 1);
        assertNull(temp2);

        session.getTransaction().rollback();
    }

    @Test
    public void deleteArrayDishesByIdAndUser() {
        List<Integer> array = Arrays.asList(1, 2);

        String hqlDeleteDish = dbProperties.deleteArrayDishByIdAndUser();
        String hqlDelRationCompos = dbProperties.deleteCompositionArrayByDishAndUser();

        session.beginTransaction();
        Dish temp = session.load(Dish.class, 1);
        assertNotNull(temp);

        Query queryDelRationCompos = session.createQuery(hqlDelRationCompos);
        Query queryDeleteDish = session.createQuery(hqlDeleteDish);

        queryDelRationCompos.setParameter("idDish", array);
        queryDelRationCompos.setParameter("idUser", workUserId);
        queryDeleteDish.setParameter("idDish", array);
        queryDeleteDish.setParameter("idUser", workUserId);

        queryDelRationCompos.executeUpdate();
        queryDeleteDish.executeUpdate();

        Dish temp2 = session.get(Dish.class, 1);
        assertNull(temp2);

        session.getTransaction().rollback();
    }

    @Test
    public void countDishes() {
        Integer count = hDishDao.countDishes(workUserId);
        assertNotNull(count);
    }
}