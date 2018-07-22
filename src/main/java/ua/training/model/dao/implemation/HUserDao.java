package ua.training.model.dao.implemation;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.controller.commands.exception.DataSqlException;
import ua.training.model.dao.UserDao;
import ua.training.model.dao.mapper.DayRationMapper;
import ua.training.model.dao.mapper.UserMapper;
import ua.training.model.dao.service.implementation.DayRationServiceImp;
import ua.training.model.dao.utility.QueryUtil;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
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
    private UserMapper userMapper = new UserMapper();
    private DayRationServiceImp dayRationServiceImp = new DayRationServiceImp();

    public HUserDao (Session session) {
        this.session = session;
    }

    @Override
    public void create(User entity) {
        String query = DB_PROPERTIES.registerNewUser();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, entity.getName());
            ps.setDate(2, Date.valueOf(entity.getDob()));
            ps.setString(3, entity.getEmail());
            ps.setString(4, entity.getPassword());
            ps.setString(5, entity.getRole().toString());
            ps.setInt(6, entity.getHeight());
            ps.setInt(7, entity.getWeight());
            ps.setInt(8, entity.getWeightDesired());
            ps.setInt(9, entity.getLifeStyleCoefficient());

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_USER_NOT_REGISTERED);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        String query = DB_PROPERTIES.getUserById();
        Optional<User> user = Optional.empty();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = Optional.ofNullable(userMapper.extractFromResultSet(rs));
            }

            rs.close();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_USER_GET_BY_ID);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        String query = DB_PROPERTIES.getAllUsers();
        List<User> users = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = userMapper.extractFromResultSet(rs);
                users.add(user);
            }

            rs.close();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_USER_GET_ALL);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
        return users;
    }

    @Override
    public void update(User entity) {
        String updateUser = DB_PROPERTIES.updateUserParameters();
        String updateDayRation = DB_PROPERTIES.updateDayRationByUser();

        try (PreparedStatement uu = connection.prepareStatement(updateUser);
             PreparedStatement udr = connection.prepareStatement(updateDayRation)) {
            connection.setAutoCommit(false);

            uu.setString(1, entity.getName());
            uu.setDate(2, Date.valueOf(entity.getDob()));
            uu.setString(3, entity.getEmail());
            uu.setString(4, entity.getPassword());
            uu.setInt(5, entity.getHeight());
            uu.setInt(6, entity.getWeight());
            uu.setInt(7, entity.getWeightDesired());
            uu.setInt(8, entity.getLifeStyleCoefficient());
            uu.setInt(9, entity.getId());

            uu.executeUpdate();

            LocalDate localDate = LocalDate.now();
            Optional<DayRation> dayRationSql = dayRationServiceImp.checkDayRationByDateAndUserId(localDate,
                    entity.getId());
            if (dayRationSql.isPresent()) {
                Period period = Period.between(entity.getDob(), localDate);
                Integer userCalories = DayRationMapper.formulaMifflinSanJerura(entity.getLifeStyleCoefficient(),
                        entity.getWeight(), entity.getHeight(), period.getYears());
                Integer userCaloriesDesired = DayRationMapper.formulaMifflinSanJerura(entity.getLifeStyleCoefficient(),
                        entity.getWeightDesired(), entity.getHeight(), period.getYears());
                udr.setInt(1, userCalories * 1000);
                udr.setInt(2, userCaloriesDesired * 1000);
                udr.setDate(3, Date.valueOf(localDate));
                udr.setInt(4, entity.getId());
                udr.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_USER_NOT_UPDATE_PARAMETERS);
            try {
                connection.rollback();
            } catch (SQLException r) {
                log.error(r.getMessage() + Mess.LOG_USER_UPDATE_ROLLBACK);
            }
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }

    @Override
    public void delete(Integer id) {
        String deleteUser = DB_PROPERTIES.deleteUserById();
        String deleteDish = DB_PROPERTIES.deleteDishByUserId();
        String deleteRationComposition = DB_PROPERTIES.deleteCompositionByRationAndUser();
        String deleteDayRation = DB_PROPERTIES.deleteDayRationByUserId();

        try (PreparedStatement du = connection.prepareStatement(deleteUser);
             PreparedStatement dd = connection.prepareStatement(deleteDish);
             PreparedStatement ddr = connection.prepareStatement(deleteDayRation);
             PreparedStatement drc = connection.prepareStatement(deleteRationComposition)) {
            connection.setAutoCommit(false);

            drc.setInt(1, id);
            drc.executeUpdate();

            ddr.setInt(1, id);
            ddr.executeUpdate();

            dd.setInt(1, id);
            dd.executeUpdate();

            du.setInt(1, id);
            du.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_USER_DELETE_BY_ID);
            try {
                connection.rollback();
            } catch (SQLException r) {
                log.error(r.getMessage() + Mess.LOG_USER_DELETE_ROLLBACK);
            }
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_CONNECTION_NOT_CLOSE);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
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
        String query = DB_PROPERTIES.getOrCheckUserByEmail();
        Optional<User> user = Optional.empty();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email.toLowerCase());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = Optional.ofNullable(userMapper.extractFromResultSet(rs));
            }

            rs.close();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_USER_GET_OR_CHECK);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }

        return user;
    }

    /**
     * Get limit list of users for page(pagination)
     *
     * @param adminId Integer
     * @param limit   Integer
     * @param skip    Integer
     * @return listOfUsers Optional<List<User>>
     * @throws DataSqlException
     */
    @Override
    public List<User> getLimitUsersWithoutAdmin(Integer adminId, Integer limit, Integer skip) {
        String query = DB_PROPERTIES.getAllUsersWithoutAdmin();
        List<User> users = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, adminId);
            ps.setInt(2, limit);
            ps.setInt(3, skip);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = userMapper.extractFromResultSet(rs);
                users.add(user);
            }

            rs.close();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_USER_GET_BY_ID);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
        return users;
    }

    /**
     * Count number of users without 'ADMIN' for page(pagination)
     *
     * @param userId Integer
     * @return countUsers Integer
     * @throws DataSqlException
     */
    @Override
    public Integer countUsers(Integer userId) {
        String query = DB_PROPERTIES.countUsersForPage();
        Integer counter = 0;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                counter = rs.getInt(Attributes.SQL_COUNTER);
            }

            rs.close();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_USER_COUNT);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
        return counter;
    }

    /**
     * Delete array of users by email
     *
     * @param emails String[]
     * @throws DataSqlException
     */
    @Override
    public void deleteArrayUsersByEmail(String[] emails) {
        String deleteUser = DB_PROPERTIES.deleteArrayUsersByEmail();
        deleteUser = QueryUtil.addParamAccordingToArrSize(deleteUser, emails.length);
        String deleteDish = DB_PROPERTIES.deleteDishByUserEmail();
        deleteDish = QueryUtil.addParamAccordingToArrSize(deleteDish, emails.length);
        String deleteDayRation = DB_PROPERTIES.deleteDayRationByUserEmail();
        deleteDayRation = QueryUtil.addParamAccordingToArrSize(deleteDayRation, emails.length);
        String deleteRationComposition = DB_PROPERTIES.deleteCompositionArrayByUserEmail();
        deleteRationComposition = QueryUtil.addParamAccordingToArrSize(deleteRationComposition, emails.length);


        try (PreparedStatement du = connection.prepareStatement(deleteUser);
             PreparedStatement dd = connection.prepareStatement(deleteDish);
             PreparedStatement ddr = connection.prepareStatement(deleteDayRation);
             PreparedStatement drc = connection.prepareStatement(deleteRationComposition)) {
            connection.setAutoCommit(false);

            for (int i = 0; i < emails.length; i++) {
                drc.setString((i + 1), emails[i].toLowerCase());
            }
            drc.executeUpdate();

            for (int i = 0; i < emails.length; i++) {
                ddr.setString((i + 1), emails[i].toLowerCase());
            }
            ddr.executeUpdate();

            for (int i = 0; i < emails.length; i++) {
                dd.setString((i + 1), emails[i].toLowerCase());
            }
            dd.executeUpdate();

            for (int i = 0; i < emails.length; i++) {
                du.setString((i + 1), emails[i].toLowerCase());
            }
            du.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_USER_DELETE_BY_EMAIL);
            try {
                connection.rollback();
            } catch (SQLException r) {
                log.error(r.getMessage() + Mess.LOG_USER_DELETE_ROLLBACK);
            }
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }

    /**
     * Change password or role by 'ADMIN'
     *
     * @param entity User
     * @throws DataSqlException
     */
    @Override
    public void updateUserParametersByAdmin(User entity) {
        String query = DB_PROPERTIES.updateUserParametersByAdmin();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, entity.getName());
            ps.setDate(2, Date.valueOf(entity.getDob()));
            ps.setString(3, entity.getEmail());
            ps.setString(4, entity.getPassword());
            ps.setString(5, entity.getRole().toString());
            ps.setInt(6, entity.getHeight());
            ps.setInt(7, entity.getWeight());
            ps.setInt(8, entity.getWeightDesired());
            ps.setInt(9, entity.getLifeStyleCoefficient());
            ps.setInt(10, entity.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_USER_NOT_UPDATE_PARAMETERS);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }
}
