package ua.training.model.dao.service.implementation;

import ua.training.model.dao.RationCompositionDao;
import ua.training.model.dao.service.RationCompositionService;
import ua.training.model.entity.RationComposition;
import ua.training.model.entity.enums.FoodIntake;

import java.util.List;
import java.util.Optional;

public class RationCompositionServiceImp implements RationCompositionService {
    private RationCompositionDao rationCompositionDao = DAO_FACTORY.createRationCompositionDao();

    @Override
    public void insertNewDayRation(RationComposition entity) {
        rationCompositionDao.create(entity);
    }

    @Override
    public Integer sumCaloriesCompositionByRationId(Integer idDayRation) {
        return rationCompositionDao.getSumCaloriesCompositionByRationId(idDayRation);
    }

    @Override
    public Optional<RationComposition> getCompositionByRationDishFoodIntake(Integer rationId,
                                                                            FoodIntake foodIntake,
                                                                            Integer dishId) {

        return rationCompositionDao.getCompositionByRationDishFoodIntake(rationId, foodIntake, dishId);
    }

    @Override
    public void updateCompositionById(RationComposition entity) {
        rationCompositionDao.update(entity);
    }

    @Override
    public void deleteArrayCompositionById(List<Integer> compositionId) {
        rationCompositionDao.deleteArrayCompositionById(compositionId);
    }

    @Override
    public Optional<RationComposition> getCompositionById(Integer compositionId) {
        return rationCompositionDao.findById(compositionId);
    }
}
