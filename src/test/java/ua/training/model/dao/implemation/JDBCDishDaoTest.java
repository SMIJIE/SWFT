package ua.training.model.dao.implemation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.model.dao.utility.QueryUtil;
import ua.training.model.entity.Dish;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.FoodCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.when;
import static ua.training.model.dao.GenericDao.DB_PROPERTIES;

@RunWith(MockitoJUnitRunner.class)
public class JDBCDishDaoTest {
    @Mock
    private Dish dish;
    @Mock
    private User user;
    private JDBCDishDao jdbcDishDao;
    private JDBCDaoFactory jdbcDaoFactory;
    private Connection connection;
    private Integer[] array;
    private Integer userId;
    private Integer dishId;
    private FoodCategory foodCategory;
    private String name;
    private Integer weight;
    private Integer weightNew;
    private Integer calories;
    private Integer proteins;
    private Integer fats;
    private Integer carbohydrates;
    private Boolean generalFood;

    @Before
    public void setUp() {
        jdbcDaoFactory = new JDBCDaoFactory();
        connection = jdbcDaoFactory.getConnection();
        jdbcDishDao = new JDBCDishDao(connection);
        array = new Integer[]{1, 2};
        userId = 1;
        dishId = 1;
        foodCategory = FoodCategory.HOT;
        name = "Scamble";
        weight = 250000;
        weightNew = 500000;
        calories = 450000;
        proteins = 1500;
        fats = 500;
        carbohydrates = 1500;
        generalFood = true;
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
        jdbcDaoFactory = null;
        jdbcDishDao = null;
        array = null;
        userId = null;
        dishId = null;
        foodCategory = null;
        name = null;
        weight = null;
        weightNew = null;
        calories = null;
        proteins = null;
        fats = null;
        carbohydrates = null;
        generalFood = null;
    }

    @Test
    public void create() throws SQLException {
        when(dish.getFoodCategory()).thenReturn(foodCategory);
        when(dish.getName()).thenReturn(name);
        when(dish.getWeight()).thenReturn(weight);
        when(dish.getCalories()).thenReturn(calories);
        when(dish.getProteins()).thenReturn(proteins);
        when(dish.getFats()).thenReturn(fats);
        when(dish.getCarbohydrates()).thenReturn(carbohydrates);
        when(dish.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(userId);
        when(dish.getGeneralFood()).thenReturn(generalFood);

        connection.setAutoCommit(false);

        Integer count = jdbcDishDao.countDishes(userId) + 1;

        jdbcDishDao.create(dish);

        Integer countAfterCreate = jdbcDishDao.countDishes(userId);

        assertEquals(count, countAfterCreate);

        connection.rollback();
    }

    @Test
    public void findById() {
        Dish dish = jdbcDishDao.findById(dishId).get();
        assertNotNull(dish);
    }

    @Test
    public void findAll() {
        List<Dish> dishes = new ArrayList<>();
        assertTrue(dishes.isEmpty());
        dishes = jdbcDishDao.findAll();
        assertFalse(dishes.isEmpty());
    }

    @Test
    public void update() throws SQLException {
        when(dish.getWeight()).thenReturn(weightNew);
        when(dish.getCalories()).thenReturn(calories);
        when(dish.getProteins()).thenReturn(proteins);
        when(dish.getFats()).thenReturn(fats);
        when(dish.getCarbohydrates()).thenReturn(carbohydrates);
        when(dish.getId()).thenReturn(dishId);

        connection.setAutoCommit(false);

        jdbcDishDao.update(dish);

        Dish tempDish = jdbcDishDao.findById(dishId).get();
        assertEquals(weightNew, tempDish.getWeight());

        connection.rollback();
    }

    @Test
    public void delete() throws SQLException {
        String deleteDish = DB_PROPERTIES.deleteDishById();
        String deleteRationComposition = DB_PROPERTIES.deleteCompositionArrayByDish();


        connection.setAutoCommit(false);
        try (PreparedStatement dd = connection.prepareStatement(deleteDish);
             PreparedStatement drc = connection.prepareStatement(deleteRationComposition)) {

            Optional<Dish> dish = jdbcDishDao.findById(dishId);
            assertTrue(dish.isPresent());

            drc.setInt(1, dishId);
            drc.executeUpdate();

            dd.setInt(1, dishId);
            dd.executeUpdate();

            dish = jdbcDishDao.findById(dishId);
            assertFalse(dish.isPresent());

        }
        connection.rollback();
    }

    @Test
    public void getAllGeneralDishes() {
        List<Dish> dishes = new ArrayList<>();
        assertTrue(dishes.isEmpty());

        dishes = jdbcDishDao.getAllGeneralDishes();

        assertFalse(dishes.isEmpty());
    }

    @Test
    public void getLimitDishesByUserId() {
        List<Dish> dishes = new ArrayList<>();
        assertTrue(dishes.isEmpty());

        dishes = jdbcDishDao.getLimitDishesByUserId(userId, 6, 0);

        assertFalse(dishes.isEmpty());
    }

    @Test
    public void getAllDishesByUserId() {
        List<Dish> dishes = new ArrayList<>();
        assertTrue(dishes.isEmpty());

        dishes = jdbcDishDao.getAllDishesByUserId(userId);

        assertFalse(dishes.isEmpty());
    }

    @Test
    public void deleteArrayDishesById() throws SQLException {
        array = new Integer[]{3, 4};

        String deleteDish = DB_PROPERTIES.deleteArrayDishById();
        deleteDish = QueryUtil.addParamAccordingToArrSize(deleteDish, array.length);
        String deleteRationComposition = DB_PROPERTIES.deleteCompositionArrayByDish();
        deleteRationComposition = QueryUtil.addParamAccordingToArrSize(deleteRationComposition, array.length);

        connection.setAutoCommit(false);
        try (PreparedStatement dd = connection.prepareStatement(deleteDish);
             PreparedStatement drc = connection.prepareStatement(deleteRationComposition)) {

            List<Dish> dishes = jdbcDishDao.getAllGeneralDishes();
            assertFalse(dishes.isEmpty());

            for (int i = 0; i < array.length; i++) {
                drc.setInt((i + 1), array[i]);
            }
            drc.executeUpdate();

            for (int i = 0; i < array.length; i++) {
                dd.setInt((i + 1), array[i]);
            }
            dd.executeUpdate();

            List<Dish> dishesResult = jdbcDishDao.getAllGeneralDishes();
            assertEquals((dishes.size() - 2), dishesResult.size());
        }
        connection.rollback();
    }

    @Test
    public void deleteArrayDishesByIdAndUser() throws SQLException {
        String deleteDish = DB_PROPERTIES.deleteArrayDishByIdAndUser();
        String deleteComposition = DB_PROPERTIES.deleteCompositionArrayByDishAndUser();
        deleteDish = QueryUtil.addParamAccordingToArrSize(deleteDish, array.length);
        deleteComposition = QueryUtil.addParamAccordingToArrSize(deleteComposition, array.length);

        connection.setAutoCommit(false);
        try (PreparedStatement dd = connection.prepareStatement(deleteDish);
             PreparedStatement dc = connection.prepareStatement(deleteComposition)) {

            List<Dish> usersDishes = jdbcDishDao.getAllDishesByUserId(userId);
            Integer count = usersDishes.size();

            for (int i = 0; i < array.length; i++) {
                dc.setInt((i + 1), array[i]);
            }
            dc.setInt(array.length + 1, userId);
            dc.executeUpdate();

            for (int i = 0; i < array.length; i++) {
                dd.setInt((i + 1), array[i]);
            }
            dd.setInt(array.length + 1, userId);
            dd.executeUpdate();

            List<Dish> usersDishesResult = jdbcDishDao.getAllDishesByUserId(userId);
            assertEquals((count - 2), usersDishesResult.size());

        }
        connection.rollback();
    }

    @Test
    public void countDishes() {
        Integer count = jdbcDishDao.countDishes(userId);
        assertNotNull(count);
    }

    @Test
    public void updateDishParametersByIdAndUser() throws SQLException {
        when(dish.getWeight()).thenReturn(weightNew);
        when(dish.getCalories()).thenReturn(calories);
        when(dish.getProteins()).thenReturn(proteins);
        when(dish.getFats()).thenReturn(fats);
        when(dish.getCarbohydrates()).thenReturn(carbohydrates);
        when(dish.getId()).thenReturn(dishId);
        when(dish.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(userId);

        connection.setAutoCommit(false);

        jdbcDishDao.updateDishParametersByIdAndUser(dish);

        Dish tempDish = jdbcDishDao.findById(dishId).get();
        assertEquals(weightNew, tempDish.getWeight());

        connection.rollback();
    }
}