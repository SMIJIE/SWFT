package ua.training.model.dao.implemation;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.controller.commands.exception.DataSqlException;
import ua.training.model.dao.RationCompositionDao;
import ua.training.model.dao.mapper.DishMapper;
import ua.training.model.dao.mapper.RationCompositionMapper;
import ua.training.model.dao.utility.QueryUtil;
import ua.training.model.entity.Dish;
import ua.training.model.entity.RationComposition;
import ua.training.model.entity.enums.FoodIntake;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Log4j2
public class HRationCompositionDao implements RationCompositionDao {
    /**
     * The main runtime interface between a Java application and Hibernate.
     *
     * @see Session
     */
    private Session session;
    private RationCompositionMapper rationCompositionMapper = new RationCompositionMapper();
    private DishMapper dishMapper = new DishMapper();

    public HRationCompositionDao (Session session) {
        this.session = session;
    }

    @Override
    public void create(RationComposition entity) {
        String query = DB_PROPERTIES.insertNewRationComposition();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, entity.getDayRation().getId());
            ps.setString(2, entity.getFoodIntake().toString());
            ps.setInt(3, entity.getDish().getId());
            ps.setInt(4, entity.getNumberOfDish());
            ps.setInt(5, entity.getCaloriesOfDish());

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_RATION_COMPOSITION_NOT_INSERTED);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }

    }

    @Override
    public Optional<RationComposition> findById(Integer id) {
        Optional<RationComposition> rationComposition = Optional.empty();
        String query = DB_PROPERTIES.getDayCompositionById();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rationComposition = Optional.ofNullable(rationCompositionMapper.extractFromResultSet(rs));
            }

            rs.close();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_RATION_COMPOSITION_GET_BY_ID);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }

        return rationComposition;
    }

    @Override
    public List<RationComposition> findAll() {
        List<RationComposition> rationCompositions = new ArrayList<>();
        String query = DB_PROPERTIES.getAllComposition();

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Optional<RationComposition> rationComposition = Optional.ofNullable(rationCompositionMapper.extractFromResultSet(rs));
                rationComposition.ifPresent(rationCompositions::add);
            }

            rs.close();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_RATION_COMPOSITION_GET_ALL);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }

        return rationCompositions;
    }

    @Override
    public void update(RationComposition entity) {
        String query = DB_PROPERTIES.updateRationComposition();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, entity.getDayRation().getId());
            ps.setString(2, entity.getFoodIntake().toString());
            ps.setInt(3, entity.getDish().getId());
            ps.setInt(4, entity.getNumberOfDish());
            ps.setInt(5, entity.getCaloriesOfDish());
            ps.setInt(6, entity.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_RATION_COMPOSITION_NOT_UPDATE_PARAMETERS);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }

    @Override
    public void delete(Integer id) {
        String query = DB_PROPERTIES.deleteCompositionById();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_RATION_COMPOSITION_DELETE_BY_ID);
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
     * Calculate sum of calories
     *
     * @param idDayRation Integer
     * @return sumCalories Integer
     * @throws DataSqlException
     */
    @Override
    public Integer getSumCaloriesCompositionByRationId(Integer idDayRation) {
        String query = DB_PROPERTIES.getSumCaloriesCompositionByRationId();
        Integer sumCalories = 0;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idDayRation);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sumCalories = rs.getInt(Attributes.SQL_SUM);
            }

            rs.close();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_RATION_COMPOSITION_SUM_CALORIES_BY_RATION);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }

        return sumCalories;
    }

    /**
     * Get RationComposition
     *
     * @param rationId   Integer
     * @param foodIntake FoodIntake
     * @param dishId     Integer
     * @return rationComposition Optional<RationComposition>
     * @throws DataSqlException
     */
    @Override
    public Optional<RationComposition> getCompositionByRationDishFoodIntake(Integer rationId, FoodIntake foodIntake, Integer dishId) {
        Optional<RationComposition> rationComposition = Optional.empty();
        String query = DB_PROPERTIES.getCompositionByRationAndDish();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, rationId);
            ps.setString(2, foodIntake.toString());
            ps.setInt(3, dishId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rationComposition = Optional.ofNullable(rationCompositionMapper.extractFromResultSet(rs));
            }

            rs.close();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_RATION_COMPOSITION_GET_BY_RATION_AND_DISH);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
        return rationComposition;
    }

    /**
     * Get all RationComposition by DayRation
     *
     * @param rationId Integer
     * @return rationCompositions List<RationComposition>
     * @throws DataSqlException
     */
    @Override
    public List<RationComposition> getAllCompositionByRation(Integer rationId) {
        List<RationComposition> rationCompositions = new ArrayList<>();
        Map<Integer, Dish> dishMap = new HashMap<>();

        String query = DB_PROPERTIES.getAllCompositionByRation();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, rationId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Optional<RationComposition> rationComposition = Optional.ofNullable(rationCompositionMapper.extractFromResultSet(rs));
                Optional<Dish> dish = Optional.ofNullable(dishMapper.extractFromResultSet(rs));

                if (rationComposition.isPresent()) {
                    if (dish.isPresent()) {
                        dish.get().setCalories(rationComposition.get().getCaloriesOfDish());
                        rationComposition.get().setDish(dishMapper.makeUnique(dishMap, dish.get()));
                    }
                    rationCompositions.add(rationComposition.get());
                }
            }

            rs.close();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_RATION_COMPOSITION_GET_BY_RATION);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }

        return rationCompositions;
    }

    /**
     * Delete array of RationComposition
     *
     * @param compositionId Integer[]
     * @throws DataSqlException
     */
    @Override
    public void deleteArrayCompositionById(Integer[] compositionId) {
        String query = DB_PROPERTIES.deleteArrayCompositionById();
        query = QueryUtil.addParamAccordingToArrSize(query, compositionId.length);

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            for (int i = 0; i < compositionId.length; i++) {
                ps.setInt((i + 1), compositionId[i]);
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_RATION_COMPOSITION_DELETE_BY_ID);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }

    /**
     * Update amount of dish RationComposition
     *
     * @param entity RationComposition
     * @throws DataSqlException
     */
    @Override
    public void updateCompositionAmountOfDish(RationComposition entity) {
        String query = DB_PROPERTIES.updateCompositionAmountOfDish();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, entity.getNumberOfDish());
            ps.setInt(2, entity.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_RATION_COMPOSITION_NOT_UPDATE_PARAMETERS);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }
}
