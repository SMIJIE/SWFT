package ua.training.model.dao.mapper;

import lombok.extern.log4j.Log4j2;
import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.controller.commands.exception.DataHttpException;
import ua.training.controller.commands.exception.DataSqlException;
import ua.training.model.entity.RationComposition;
import ua.training.model.entity.enums.FoodIntake;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Log4j2
public class RationCompositionMapper implements ObjectMapper<RationComposition> {
    /**
     * @param rs ResultSet
     * @return new RationComposition
     */
    @Override
    public RationComposition extractFromResultSet(ResultSet rs) {
        Integer id;
        FoodIntake foodIntake;
        Integer numberDish;
        Integer caloriesOfDish;

        try {
            id = rs.getInt(Attributes.SQL_RC_ID);
            foodIntake = FoodIntake.valueOf(rs.getString(Attributes.REQUEST_FOOD_INTAKE));
            numberDish = rs.getInt(Attributes.SQL_NUMBER_OF_DISH);
            caloriesOfDish = rs.getInt(Attributes.SQL_CALORIES_OF_DISH);

        } catch (SQLException e) {
            log.error(e.getMessage() + Mess.LOG_RATION_COMPOSITION_RS_NOT_EXTRACT);
            throw new DataSqlException(Attributes.SQL_EXCEPTION);
        }

        return RationComposition.builder()
                .id(id)
                .foodIntake(foodIntake)
                .numberOfDish(numberDish)
                .caloriesOfDish(caloriesOfDish)
                .build();
    }

    /**
     * @param req HttpServletRequest
     * @return new RationComposition
     */
    @Override
    public RationComposition extractFromHttpServletRequest(HttpServletRequest req) {
        // TODO: not yet used
        return null;
    }

    /**
     * Check RationComposition by regex
     *
     * @param rationComposition RationComposition
     * @throws DataHttpException
     */
    @Override
    public void checkByRegex(RationComposition rationComposition) {
        // TODO: not yet used
    }

    /**
     * @param cache             Map<Integer, RationComposition>
     * @param rationComposition RationComposition
     * @return new RationComposition or exist RationComposition
     */
    @Override
    public RationComposition makeUnique(Map<Integer, RationComposition> cache, RationComposition rationComposition) {
        cache.putIfAbsent(rationComposition.getId(), rationComposition);
        return cache.get(rationComposition.getId());
    }
}
