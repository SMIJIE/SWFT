package ua.training.model.dao.implemation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.model.dao.mapper.DayRationMapper;
import ua.training.model.dao.utility.QueryUtil;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.Roles;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static ua.training.model.dao.GenericDao.DB_PROPERTIES;

@RunWith(MockitoJUnitRunner.class)
public class JDBCUserDaoTest {
    @Mock
    private User user;
    private JDBCDaoFactory jdbcDaoFactory;
    private Connection connection;
    private JDBCUserDao jdbcUserDao;
    private JDBCDayRationDao jdbcDayRationDao;
    private Integer id;
    private String name;
    private LocalDate dob;
    private String email;
    private String emailSearch;
    private String password;
    private Roles roles;
    private Roles rolesNew;
    private Integer height;
    private Integer weight;
    private Integer weightNew;
    private Integer weightDesired;
    private Integer lifeStyleCoefficient;

    @Before
    public void setUp() {
        jdbcDaoFactory = new JDBCDaoFactory();
        connection = jdbcDaoFactory.getConnection();
        jdbcUserDao = new JDBCUserDao(connection);
        jdbcDayRationDao = new JDBCDayRationDao(connection);
        id = 1;
        name = "Mock";
        dob = LocalDate.parse("1993-06-15");
        email = "Mock@gmail.com";
        emailSearch = "Zakusylo@gmail.com";
        password = "qwerty";
        roles = Roles.ADMIN;
        rolesNew = Roles.USER;
        height = 18000;
        weight = 80000;
        weightNew = 60000;
        weightDesired = 70000;
        lifeStyleCoefficient = 1500;
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
        jdbcDaoFactory = null;
        jdbcUserDao = null;
        jdbcDayRationDao = null;
        id = null;
        name = null;
        dob = null;
        email = null;
        emailSearch = null;
        password = null;
        roles = null;
        rolesNew = null;
        height = null;
        weight = null;
        weightNew = null;
        weightDesired = null;
        lifeStyleCoefficient = null;
    }

    @Test
    public void create() throws SQLException {
        when(user.getName()).thenReturn(name);
        when(user.getDob()).thenReturn(dob);
        when(user.getEmail()).thenReturn(email);
        when(user.getPassword()).thenReturn(password);
        when(user.getRole()).thenReturn(roles);
        when(user.getHeight()).thenReturn(height);
        when(user.getWeight()).thenReturn(weight);
        when(user.getWeightDesired()).thenReturn(weightDesired);
        when(user.getLifeStyleCoefficient()).thenReturn(lifeStyleCoefficient);

        connection.setAutoCommit(false);
        jdbcUserDao.create(user);

        User tempUser = jdbcUserDao.getOrCheckUserByEmail(email).get();
        assertNotNull(tempUser);

        connection.rollback();
    }

    @Test
    public void findById() {
        User tempUser = jdbcUserDao.findById(id).get();
        assertNotNull(tempUser);
    }

    @Test
    public void findAll() {
        List<User> users = new ArrayList<>();
        assertTrue(users.isEmpty());

        users = jdbcUserDao.findAll();
        assertFalse(users.isEmpty());
    }

    @Test
    public void update() throws SQLException {
        String updateUser = DB_PROPERTIES.updateUserParameters();
        String updateDayRation = DB_PROPERTIES.updateDayRationByUser();

        connection.setAutoCommit(false);
        try (PreparedStatement uu = connection.prepareStatement(updateUser);
             PreparedStatement udr = connection.prepareStatement(updateDayRation)) {

            uu.setString(1, name);
            uu.setDate(2, Date.valueOf(dob));
            uu.setString(3, email);
            uu.setString(4, password);
            uu.setInt(5, height);
            uu.setInt(6, weightNew);
            uu.setInt(7, weightDesired);
            uu.setInt(8, lifeStyleCoefficient);
            uu.setInt(9, id);

            uu.executeUpdate();

            LocalDate localDate = LocalDate.now();
            Optional<DayRation> dayRationSql = jdbcDayRationDao.checkDayRationByDateAndUserId(localDate, id);
            if (dayRationSql.isPresent()) {
                Period period = Period.between(dob, localDate);
                Integer userCalories = DayRationMapper.formulaMifflinSanJerura(lifeStyleCoefficient,
                        weightNew, height, period.getYears());
                Integer userCaloriesDesired = DayRationMapper.formulaMifflinSanJerura(lifeStyleCoefficient,
                        weightDesired, height, period.getYears());

                udr.setInt(1, userCalories);
                udr.setInt(2, userCaloriesDesired);
                udr.setDate(3, Date.valueOf(localDate));
                udr.setInt(4, id);
                udr.executeUpdate();

                User tempUserResult = jdbcUserDao.findById(id).get();
                assertEquals(weightNew, tempUserResult.getWeight());
            }
        }
        connection.rollback();
    }

    @Test
    public void delete() throws SQLException {
        String deleteUser = DB_PROPERTIES.deleteUserById();
        String deleteDish = DB_PROPERTIES.deleteDishByUserId();
        String deleteRationComposition = DB_PROPERTIES.deleteCompositionByRationAndUser();
        String deleteDayRation = DB_PROPERTIES.deleteDayRationByUserId();

        connection.setAutoCommit(false);
        try (PreparedStatement du = connection.prepareStatement(deleteUser);
             PreparedStatement dd = connection.prepareStatement(deleteDish);
             PreparedStatement ddr = connection.prepareStatement(deleteDayRation);
             PreparedStatement drc = connection.prepareStatement(deleteRationComposition)) {

            drc.setInt(1, id);
            drc.executeUpdate();

            ddr.setInt(1, id);
            ddr.executeUpdate();

            dd.setInt(1, id);
            dd.executeUpdate();

            du.setInt(1, id);
            du.executeUpdate();

            Optional<User> tempUser = jdbcUserDao.findById(id);
            assertFalse(tempUser.isPresent());
        }
        connection.rollback();
    }

    @Test
    public void getOrCheckUserByEmail() {
        User tempUser = jdbcUserDao.getOrCheckUserByEmail(emailSearch).get();
        assertNotNull(tempUser);
    }

    @Test
    public void getLimitUsersWithoutAdmin() {
        List<User> users = new ArrayList<>();
        assertTrue(users.isEmpty());

        users = jdbcUserDao.getLimitUsersWithoutAdmin(id, 6, 0);
        assertFalse(users.isEmpty());
    }

    @Test
    public void countUsers() {
        Integer count = jdbcUserDao.countUsers(id);
        assertNotNull(count);
    }

    @Test
    public void deleteArrayUsersByEmail() throws SQLException {
        String[] emails = new String[]{"Mark@bigmir.net", "Sasha@bigmir.net"};

        String deleteUser = DB_PROPERTIES.deleteArrayUsersByEmail();
        deleteUser = QueryUtil.addParamAccordingToArrSize(deleteUser, emails.length);
        String deleteDish = DB_PROPERTIES.deleteDishByUserEmail();
        deleteDish = QueryUtil.addParamAccordingToArrSize(deleteDish, emails.length);
        String deleteDayRation = DB_PROPERTIES.deleteDayRationByUserEmail();
        deleteDayRation = QueryUtil.addParamAccordingToArrSize(deleteDayRation, emails.length);
        String deleteRationComposition = DB_PROPERTIES.deleteCompositionArrayByUserEmail();
        deleteRationComposition = QueryUtil.addParamAccordingToArrSize(deleteRationComposition, emails.length);

        connection.setAutoCommit(false);
        try (PreparedStatement du = connection.prepareStatement(deleteUser);
             PreparedStatement dd = connection.prepareStatement(deleteDish);
             PreparedStatement ddr = connection.prepareStatement(deleteDayRation);
             PreparedStatement drc = connection.prepareStatement(deleteRationComposition)) {

            Integer count = jdbcUserDao.countUsers(id);
            assertNotNull(count);

            for (int i = 0; i < emails.length; i++) {
                drc.setString((i + 1), emails[i].toLowerCase());
            }
            drc.executeUpdate();

            for (int i = 0; i < emails.length; i++) {
                dd.setString((i + 1), emails[i].toLowerCase());
            }
            dd.executeUpdate();

            for (int i = 0; i < emails.length; i++) {
                ddr.setString((i + 1), emails[i].toLowerCase());
            }
            ddr.executeUpdate();

            for (int i = 0; i < emails.length; i++) {
                du.setString((i + 1), emails[i].toLowerCase());
            }
            du.executeUpdate();

            Integer countResult = count - 2;
            assertEquals(countResult, jdbcUserDao.countUsers(id));
        }
        connection.rollback();
    }

    @Test
    public void updateUserParametersByAdmin() throws SQLException {
        when(user.getName()).thenReturn(name);
        when(user.getDob()).thenReturn(dob);
        when(user.getEmail()).thenReturn(email);
        when(user.getPassword()).thenReturn(password);
        when(user.getRole()).thenReturn(rolesNew);
        when(user.getHeight()).thenReturn(height);
        when(user.getWeight()).thenReturn(weight);
        when(user.getWeightDesired()).thenReturn(weightDesired);
        when(user.getLifeStyleCoefficient()).thenReturn(lifeStyleCoefficient);
        when(user.getId()).thenReturn(id);

        connection.setAutoCommit(false);

        jdbcUserDao.updateUserParametersByAdmin(user);

        User tempUser = jdbcUserDao.findById(id).get();
        assertEquals(rolesNew, tempUser.getRole());

        connection.rollback();
    }
}