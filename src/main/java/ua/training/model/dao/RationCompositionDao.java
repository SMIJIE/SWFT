package ua.training.model.dao;

import ua.training.model.entity.RationComposition;
import ua.training.model.entity.enums.FoodIntake;

import java.util.List;
import java.util.Optional;

public interface RationCompositionDao extends GenericDao<RationComposition> {
    Integer getSumCaloriesCompositionByRationId(Integer idDayRation);

    Optional<RationComposition> getCompositionByRationDishFoodIntake(Integer rationId,
                                                                     FoodIntake foodIntake,
                                                                     Integer dishId);

    List<RationComposition> getAllCompositionByRation(Integer rationId);

    void deleteArrayCompositionById(List<Integer> compositionId);
}
