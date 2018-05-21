package ua.training.model.dao.implemation;

import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.controller.commands.exception.DataSqlException;
import ua.training.model.dao.DayRationDao;
import ua.training.model.dao.mapper.DayRationMapper;
import ua.training.model.entity.DayRation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCDayRationDao implements DayRationDao {
    /**
     * Connection for JDBCDayRationDao
     *
     * @see JDBCDaoFactory#getConnection()
     */
    private Connection connection;
    private DayRationMapper dayRationMapper = new DayRationMapper();

    public JDBCDayRationDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(DayRation entity) {
        String query = DB_PROPERTIES.insertNewDayRation();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDate(1, Date.valueOf(entity.getDate()));
            ps.setInt(2, entity.getUser().getId());
            ps.setInt(3, entity.getUserCalories());
            ps.setInt(4, entity.getUserCaloriesDesired());

            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DAY_RATION_NOT_INSERTED);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }

    @Override
    public Optional<DayRation> findById(Integer id) {
        Optional<DayRation> dayRation = Optional.empty();
        String query = DB_PROPERTIES.getDayRationById();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dayRation = Optional.ofNullable(dayRationMapper.extractFromResultSet(rs));
            }

            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DAY_RATION_GET_BY_ID);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }

        return dayRation;
    }

    @Override
    public List<DayRation> findAll() {
        List<DayRation> dayRations = new ArrayList<>();
        String query = DB_PROPERTIES.getGetAllDaRations();

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Optional<DayRation> dayRation = Optional.ofNullable(dayRationMapper.extractFromResultSet(rs));
                dayRation.ifPresent(dayRations::add);
            }

            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DAY_RATION_GET_BY_ID);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }

        return dayRations;
    }

    @Override
    public void update(DayRation entity) {
        String query = DB_PROPERTIES.updateDayRation();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDate(1, Date.valueOf(entity.getDate()));
            ps.setInt(2, entity.getUserCalories());
            ps.setInt(3, entity.getUserCaloriesDesired());
            ps.setInt(4, entity.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DAY_RATION_NOT_UPDATE_PARAMETERS);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
    }

    @Override
    public void delete(Integer id) {
        String deleteDayRation = DB_PROPERTIES.deleteDayRation();
        String deleteRationComposition = DB_PROPERTIES.deleteCompositionByDayRationId();

        try (PreparedStatement ddr = connection.prepareStatement(deleteDayRation);
             PreparedStatement drc = connection.prepareStatement(deleteRationComposition)) {
            connection.setAutoCommit(false);

            drc.setInt(1, id);
            drc.executeUpdate();

            ddr.setInt(1, id);
            ddr.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DAY_RATION_DELETE_BY_ID);
            try {
                connection.rollback();
            } catch (SQLException r) {
                LOGGER.error(r.getMessage() + Mess.LOG_DAY_RATION_DELETE_ROLLBACK);
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
     * Check DayRation by date and user
     *
     * @param localDate LocalDate
     * @param idUser    Integer
     * @return dayRation Optional<DayRation>
     * @throws DataSqlException
     */
    @Override
    public Optional<DayRation> checkDayRationByDateAndUserId(LocalDate localDate, Integer idUser) {
        Optional<DayRation> dayRation = Optional.empty();
        String query = DB_PROPERTIES.getDayRationByDateAndUser();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDate(1, Date.valueOf(localDate));
            ps.setInt(2, idUser);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dayRation = Optional.ofNullable(dayRationMapper.extractFromResultSet(rs));
            }

            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DAY_RATION_GET_BY_ID);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }
        return dayRation;
    }

    /**
     * Get monthly DayRation for graphic
     *
     * @param monthVal Integer
     * @param year     Integer
     * @param userId   Integer
     * @return dayRationList List<DayRation>
     * @throws DataSqlException
     */
    @Override
    public List<DayRation> getMonthlyDayRationByUser(Integer monthVal, Integer year, Integer userId) {
        String query = DB_PROPERTIES.getMonthlyDayRationByUser();
        List<DayRation> dayRationList = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, monthVal);
            ps.setInt(2, year);
            ps.setInt(3, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Optional<DayRation> dayRation = Optional.ofNullable(dayRationMapper.extractFromResultSet(rs));
                dayRation.ifPresent(dayRationList::add);
            }

            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage() + Mess.LOG_DAY_RATION_GET_MONTHLY_BY_USER);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }

        return dayRationList;
    }
}
