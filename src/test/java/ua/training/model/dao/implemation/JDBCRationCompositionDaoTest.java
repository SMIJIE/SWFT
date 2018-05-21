package ua.training.model.dao.implemation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.Dish;
import ua.training.model.entity.RationComposition;
import ua.training.model.entity.enums.FoodIntake;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JDBCRationCompositionDaoTest {
    @Mock
    private RationComposition rationComposition;
    @Mock
    private DayRation dayRation;
    @Mock
    private Dish dish;
    private JDBCRationCompositionDao jdbcRationCompositionDao;
    private JDBCDaoFactory jdbcDaoFactory;
    private Connection connection;
    private Integer rcId;
    private Integer drId;
    private Integer dishId;
    private FoodIntake foodIntake;
    private Integer numberOfDish;
    private Integer numberOfDishNew;
    private Integer caloriesOfDish;

    @Before
    public void setUp() {
        jdbcDaoFactory = new JDBCDaoFactory();
        connection = jdbcDaoFactory.getConnection();
        jdbcRationCompositionDao = new JDBCRationCompositionDao(connection);
        rcId = 1;
        drId = 1;
        dishId = 1;
        foodIntake = FoodIntake.BREAKFAST;
        numberOfDish = 5;
        numberOfDishNew = 1;
        caloriesOfDish = 500000;
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
        jdbcDaoFactory = null;
        jdbcRationCompositionDao = null;
        rcId = null;
        drId = null;
        dishId = null;
        foodIntake = null;
        numberOfDish = null;
        numberOfDishNew = null;
        caloriesOfDish = null;
    }

    @Test
    public void create() throws SQLException {
        when(rationComposition.getDayRation()).thenReturn(dayRation);
        when(dayRation.getId()).thenReturn(drId);
        when(rationComposition.getFoodIntake()).thenReturn(foodIntake);
        when(rationComposition.getDish()).thenReturn(dish);
        when(dish.getId()).thenReturn(dishId);
        when(rationComposition.getNumberOfDish()).thenReturn(numberOfDish);
        when(rationComposition.getCaloriesOfDish()).thenReturn(caloriesOfDish);

        connection.setAutoCommit(false);

        List<RationComposition> rationCompositions = jdbcRationCompositionDao.findAll();
        assertFalse(rationCompositions.isEmpty());

        jdbcRationCompositionDao.create(rationComposition);

        List<RationComposition> rationCompositionsResult = jdbcRationCompositionDao.findAll();
        assertEquals((rationCompositions.size() + 1), rationCompositionsResult.size());

        connection.rollback();
    }

    @Test
    public void findById() {
        RationComposition rationComposition = jdbcRationCompositionDao.findById(dishId).get();
        assertNotNull(rationComposition);
    }

    @Test
    public void findAll() {
        List<RationComposition> rationCompositions = new ArrayList<>();
        assertTrue(rationCompositions.isEmpty());

        rationCompositions = jdbcRationCompositionDao.findAll();
        assertFalse(rationCompositions.isEmpty());
    }

    @Test
    public void update() throws SQLException {
        when(rationComposition.getDayRation()).thenReturn(dayRation);
        when(dayRation.getId()).thenReturn(drId);
        when(rationComposition.getFoodIntake()).thenReturn(foodIntake);
        when(rationComposition.getDish()).thenReturn(dish);
        when(dish.getId()).thenReturn(dishId);
        when(rationComposition.getNumberOfDish()).thenReturn(numberOfDishNew);
        when(rationComposition.getCaloriesOfDish()).thenReturn(caloriesOfDish);
        when(rationComposition.getId()).thenReturn(rcId);

        connection.setAutoCommit(false);

        jdbcRationCompositionDao.update(rationComposition);

        rationComposition = jdbcRationCompositionDao.findById(rcId).get();
        assertEquals(numberOfDishNew, rationComposition.getNumberOfDish());

        connection.rollback();
    }

    @Test
    public void delete() throws SQLException {
        connection.setAutoCommit(false);

        List<RationComposition> rationCompositions = jdbcRationCompositionDao.findAll();
        assertFalse(rationCompositions.isEmpty());

        jdbcRationCompositionDao.delete(drId);

        List<RationComposition> rationCompositionsResult = jdbcRationCompositionDao.findAll();
        assertEquals((rationCompositions.size() - 1), rationCompositionsResult.size());

        connection.rollback();
    }

    @Test
    public void getSumCaloriesCompositionByRationId() {
        Integer sum = jdbcRationCompositionDao.getSumCaloriesCompositionByRationId(drId);
        assertNotNull(sum);
    }

    @Test
    public void getCompositionByRationDishFoodIntake() {
        RationComposition rationComposition = jdbcRationCompositionDao.getCompositionByRationDishFoodIntake(drId,
                foodIntake, dishId).get();
        assertNotNull(rationComposition);
    }

    @Test
    public void getAllCompositionByRation() {
        List<RationComposition> rationCompositions = new ArrayList<>();
        assertTrue(rationCompositions.isEmpty());

        rationCompositions = jdbcRationCompositionDao.getAllCompositionByRation(drId);
        assertFalse(rationCompositions.isEmpty());
    }

    @Test
    public void deleteArrayCompositionById() throws SQLException {
        Integer[] array = new Integer[]{1, 2, 3};

        connection.setAutoCommit(false);

        List<RationComposition> rationCompositions = jdbcRationCompositionDao.findAll();
        assertFalse(rationCompositions.isEmpty());

        jdbcRationCompositionDao.deleteArrayCompositionById(array);

        List<RationComposition> rationCompositionsResult = jdbcRationCompositionDao.findAll();
        assertEquals((rationCompositions.size() - 3), rationCompositionsResult.size());

        connection.rollback();
    }

    @Test
    public void updateCompositionAmountOfDish() throws SQLException {
        when(rationComposition.getNumberOfDish()).thenReturn(numberOfDishNew);
        when(rationComposition.getId()).thenReturn(rcId);

        connection.setAutoCommit(false);

        jdbcRationCompositionDao.updateCompositionAmountOfDish(rationComposition);

        rationComposition = jdbcRationCompositionDao.findById(rcId).get();
        assertEquals(numberOfDishNew, rationComposition.getNumberOfDish());

        connection.rollback();
    }
}