package ua.training.model.dao.implemation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static ua.training.model.dao.GenericDao.DB_PROPERTIES;

@RunWith(MockitoJUnitRunner.class)
public class JDBCDayRationDaoTest {
    @Mock
    private DayRation dayRation;
    @Mock
    private User user;

    private JDBCDayRationDao jdbcDayRationDao;
    private JDBCDaoFactory jdbcDaoFactory;
    private Connection connection;
    private LocalDate dateSearch;
    private LocalDate dateCurrent;
    private Integer userId;
    private Integer dayRationId;
    private Integer userCalories;
    private Integer newUserCalories;
    private Integer userCaloriesDesired;

    @Before
    public void setUp() {
        jdbcDaoFactory = new JDBCDaoFactory();
        connection = jdbcDaoFactory.getConnection();
        jdbcDayRationDao = new JDBCDayRationDao(connection);
        dateSearch = LocalDate.parse("2018-05-13");
        dateCurrent = LocalDate.now();
        userId = 1;
        dayRationId = 1;
        userCalories = 3260;
        newUserCalories = 5000;
        userCaloriesDesired = 2680;
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
        jdbcDaoFactory = null;
        jdbcDayRationDao = null;
        dateSearch = null;
        dateCurrent = null;
        userId = null;
        dayRationId = null;
        userCalories = null;
        newUserCalories = null;
        userCaloriesDesired = null;
    }

    @Test
    public void create() throws SQLException {
        when(dayRation.getDate()).thenReturn(dateCurrent);
        when(dayRation.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(userId);
        when(dayRation.getUserCalories()).thenReturn(userCalories);
        when(dayRation.getUserCaloriesDesired()).thenReturn(userCaloriesDesired);

        connection.setAutoCommit(false);

        jdbcDayRationDao.create(dayRation);

        DayRation dayRation1 = jdbcDayRationDao.checkDayRationByDateAndUserId(dateCurrent, userId).get();
        assertNotNull(dayRation1);

        connection.rollback();
    }

    @Test
    public void findById() {
        DayRation dayRation = jdbcDayRationDao.findById(dayRationId).get();

        assertNotNull(dayRation);
    }

    @Test
    public void findAll() {
        List<DayRation> dayRations = new ArrayList<>();

        assertTrue(dayRations.isEmpty());

        dayRations = jdbcDayRationDao.findAll();

        assertFalse(dayRations.isEmpty());
    }

    @Test
    public void update() throws SQLException {
        when(dayRation.getId()).thenReturn(dayRationId);
        when(dayRation.getDate()).thenReturn(dateSearch);
        when(dayRation.getUserCalories()).thenReturn(newUserCalories);
        when(dayRation.getUserCaloriesDesired()).thenReturn(userCaloriesDesired);

        connection.setAutoCommit(false);

        jdbcDayRationDao.update(dayRation);

        DayRation tempDayRation = jdbcDayRationDao.checkDayRationByDateAndUserId(dateSearch, userId).get();
        assertNotNull(tempDayRation);
        assertEquals(newUserCalories, tempDayRation.getUserCalories());

        connection.rollback();
    }

    @Test
    public void delete() throws SQLException {
        String deleteDayRation = DB_PROPERTIES.deleteDayRation();
        String deleteRationComposition = DB_PROPERTIES.deleteCompositionByDayRationId();

        connection.setAutoCommit(false);
        try (PreparedStatement ddr = connection.prepareStatement(deleteDayRation);
             PreparedStatement drc = connection.prepareStatement(deleteRationComposition)) {

            DayRation tempDayRation = jdbcDayRationDao.findById(dayRationId).get();
            assertNotNull(tempDayRation);

            drc.setInt(1, dayRationId);
            drc.executeUpdate();

            ddr.setInt(1, dayRationId);
            ddr.executeUpdate();

            assertFalse(jdbcDayRationDao.findById(dayRationId).isPresent());

        }
        connection.rollback();
    }

    @Test
    public void checkDayRationByDateAndUserId() {
        DayRation dayRation = jdbcDayRationDao.checkDayRationByDateAndUserId(dateSearch, userId).get();

        assertNotNull(dayRation);
    }

    @Test
    public void getMonthlyDayRationByUser() {
        List<DayRation> dayRationList = new ArrayList<>();

        assertTrue(dayRationList.isEmpty());

        dayRationList = jdbcDayRationDao.getMonthlyDayRationByUser(dateSearch.getMonthValue(),
                dateSearch.getYear(), userId);

        assertFalse(dayRationList.isEmpty());
    }
}