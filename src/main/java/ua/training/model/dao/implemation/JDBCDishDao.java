package ua.training.model.dao.implemation;

import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.controller.commands.exception.DataSqlException;
import ua.training.model.dao.DishDao;
import ua.training.model.dao.mapper.DishMapper;
import ua.training.model.dao.mapper.UserMapper;
import ua.training.model.dao.utility.QueryUtil;
import ua.training.model.entity.Dish;
import ua.training.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;

public class JDBCDishDao implements DishDao {
    /**
     * Connection for JDBCDishDao
     *
     * @see JDBCDaoFactory#getConnection()
     */
    private Connection connection;
    private UserMapper userMapper = new UserMapper();
    private DishMapper dishMapper = new DishMapper();

    public JDBCDishDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Dish entity) {
        String query = DB_PROPERTIES.insertNewDish();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, entity.getFoodCategory().toString());
            ps.setString(2, entity.getName());
            ps.setInt(3, entity.getWeight());
            ps.setInt(4, entity.getCalories());
            ps.setInt(5, entity.getProteins());
            ps.setInt(6, entity.getFats());
            ps.setInt(7, entity.getCarbohydrates());
            ps.setInt(8, entity.getUser().getId());
            ps.setBoolean(9, entity.getGeneralFood());

            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DISH_NOT_INSERTED);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }

    @Override
    public Optional<Dish> findById(Integer id) {
        String query = DB_PROPERTIES.getDishById();
        Optional<Dish> dish = Optional.empty();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dish = Optional.ofNullable(dishMapper.extractFromResultSet(rs));
            }

            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DISH_GET_BY_ID);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }

        return dish;
    }

    @Override
    public List<Dish> findAll() {
        String query = DB_PROPERTIES.getAllDishes();
        List<Dish> dishes = new ArrayList<>();
        Map<Integer, User> users = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Optional<Dish> dish = Optional.ofNullable(dishMapper.extractFromResultSet(rs));
                Optional<User> user = Optional.empty();

                if (!isNullOrEmpty(rs.getString(Attributes.SQL_USER_ID))) {
                    user = Optional.ofNullable(userMapper.extractFromResultSet(rs));
                }

                if (dish.isPresent()) {
                    user.ifPresent(u -> dish.get().setUser(userMapper.makeUnique(users, u)));
                    dishes.add(dish.get());
                }
            }
            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DISH_GET_BY_ID);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
        return dishes;
    }

    @Override
    public void update(Dish entity) {
        String query = DB_PROPERTIES.updateDishParameters();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, entity.getWeight());
            ps.setInt(2, entity.getCalories());
            ps.setInt(3, entity.getProteins());
            ps.setInt(4, entity.getFats());
            ps.setInt(5, entity.getCarbohydrates());
            ps.setInt(6, entity.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DISH_NOT_UPDATE_PARAMETERS);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }

    @Override
    public void delete(Integer id) {
        String deleteDish = DB_PROPERTIES.deleteDishById();
        String deleteRationComposition = DB_PROPERTIES.deleteCompositionArrayByDish();

        try (PreparedStatement dd = connection.prepareStatement(deleteDish);
             PreparedStatement drc = connection.prepareStatement(deleteRationComposition)) {
            connection.setAutoCommit(false);

            drc.setInt(1, id);
            drc.executeUpdate();

            dd.setInt(1, id);
            dd.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DISH_DELETE_BY_ID);
            try {
                connection.rollback();
            } catch (SQLException r) {
                LOGGER.error(r.getMessage() + Mess.LOG_DISH_DELETE_ROLLBACK);
            }
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_CONNECTION_NOT_CLOSE);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }

    /**
     * Return all general dishes
     *
     * @return returnDishes Optional<List<Dish>>
     * @throws DataSqlException
     */
    @Override
    public List<Dish> getAllGeneralDishes() {
        String query = DB_PROPERTIES.getGeneralDishes();
        List<Dish> dishes = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setBoolean(1, true);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Optional<Dish> dish = Optional.ofNullable(dishMapper.extractFromResultSet(rs));
                dish.ifPresent(dishes::add);
            }

            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DISH_GET_GENERAL);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
        return dishes;
    }

    /**
     * Return users dishes with limit for page
     *
     * @param userId Integer
     * @param limit  Integer
     * @param skip   Integer
     * @return returnDishes Optional<List<Dish>>
     * @throws DataSqlException
     */
    @Override
    public List<Dish> getLimitDishesByUserId(Integer userId, Integer limit, Integer skip) {
        String query = DB_PROPERTIES.getLimitDishByUserId();
        List<Dish> dishes = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, limit);
            ps.setInt(3, skip);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Optional<Dish> dish = Optional.ofNullable(dishMapper.extractFromResultSet(rs));
                dish.ifPresent(dishes::add);
            }

            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DISH_GET_BY_USER);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
        return dishes;
    }

    /**
     * Return all users dishes
     *
     * @param userId Integer
     * @return returnDishes Optional<List<Dish>>
     * @throws DataSqlException
     */
    @Override
    public List<Dish> getAllDishesByUserId(Integer userId) {
        String query = DB_PROPERTIES.getDishByUserId();
        List<Dish> dishes = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Optional<Dish> dish = Optional.ofNullable(dishMapper.extractFromResultSet(rs));
                dish.ifPresent(dishes::add);
            }

            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DISH_GET_BY_USER);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
        return dishes;
    }

    /**
     * Delete array of dishes
     *
     * @param array Integer[]
     * @throws DataSqlException
     */
    @Override
    public void deleteArrayDishesById(Integer[] array) {
        String deleteDish = DB_PROPERTIES.deleteArrayDishById();
        deleteDish = QueryUtil.addParamAccordingToArrSize(deleteDish, array.length);
        String deleteRationComposition = DB_PROPERTIES.deleteCompositionArrayByDish();
        deleteRationComposition = QueryUtil.addParamAccordingToArrSize(deleteRationComposition, array.length);

        try (PreparedStatement dd = connection.prepareStatement(deleteDish);
             PreparedStatement drc = connection.prepareStatement(deleteRationComposition)) {
            connection.setAutoCommit(false);

            for (int i = 0; i < array.length; i++) {
                drc.setInt((i + 1), array[i]);
            }
            drc.executeUpdate();

            for (int i = 0; i < array.length; i++) {
                dd.setInt((i + 1), array[i]);
            }
            dd.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DISH_DELETE_BY_ID);
            try {
                connection.rollback();
            } catch (SQLException r) {
                LOGGER.error(r.getMessage() + Mess.LOG_DISH_DELETE_ROLLBACK);
            }
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }

    /**
     * Delete array of dishes by user
     *
     * @param array  Integer[]
     * @param userId Integer
     * @throws DataSqlException
     */
    @Override
    public void deleteArrayDishesByIdAndUser(Integer[] array, Integer userId) {
        String deleteDish = DB_PROPERTIES.deleteArrayDishByIdAndUser();
        String deleteComposition = DB_PROPERTIES.deleteCompositionArrayByDishAndUser();
        deleteDish = QueryUtil.addParamAccordingToArrSize(deleteDish, array.length);
        deleteComposition = QueryUtil.addParamAccordingToArrSize(deleteComposition, array.length);

        try (PreparedStatement dd = connection.prepareStatement(deleteDish);
             PreparedStatement dc = connection.prepareStatement(deleteComposition)) {
            connection.setAutoCommit(false);

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

            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DISH_DELETE_BY_ID);
            try {
                connection.rollback();
            } catch (SQLException r) {
                LOGGER.error(r.getMessage() + Mess.LOG_DISH_DELETE_ROLLBACK);
            }
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }

    /**
     * Count number of users dishes for page
     *
     * @param userId Integer
     * @return counter Integer
     * @throws DataSqlException
     */
    @Override
    public Integer countDishes(Integer userId) {
        String query = DB_PROPERTIES.getCountDishesByUserId();
        Integer counter = 0;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                counter = rs.getInt(Attributes.SQL_COUNTER);
            }

            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DISH_COUNT_BY_USER_ID);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
        return counter;
    }

    /**
     * Update dish parameters by user
     *
     * @param entity Dish
     * @throws DataSqlException
     */
    @Override
    public void updateDishParametersByIdAndUser(Dish entity) {
        String query = DB_PROPERTIES.updateDishParametersByIdAndUser();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, entity.getWeight());
            ps.setInt(2, entity.getCalories());
            ps.setInt(3, entity.getProteins());
            ps.setInt(4, entity.getFats());
            ps.setInt(5, entity.getCarbohydrates());
            ps.setInt(6, entity.getId());
            ps.setInt(7, entity.getUser().getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DISH_NOT_UPDATE_PARAMETERS);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }
}
