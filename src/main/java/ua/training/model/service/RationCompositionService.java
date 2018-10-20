package ua.training.model.service;

import ua.training.model.entity.RationComposition;
import ua.training.model.entity.enums.FoodIntake;

import java.util.List;
import java.util.Optional;

public interface RationCompositionService {
    void insertNewDayRation(RationComposition entity);

    Integer sumCaloriesCompositionByRationId(Integer idDayRation);

    Optional<RationComposition> getCompositionByRationDishFoodIntake(Integer rationId,
                                                                     FoodIntake foodIntake,
                                                                     Integer dishId);

    void updateCompositionById(RationComposition entity);

    void deleteArrayCompositionById(List<Integer> compositionId);

    Optional<RationComposition> getCompositionById(Integer compositionId);

}
