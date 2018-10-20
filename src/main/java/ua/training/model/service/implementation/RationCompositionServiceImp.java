package ua.training.model.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.training.model.dao.implemation.HRationCompositionDao;
import ua.training.model.entity.RationComposition;
import ua.training.model.entity.enums.FoodIntake;
import ua.training.model.service.RationCompositionService;

import java.util.List;
import java.util.Optional;

@Service
public class RationCompositionServiceImp implements RationCompositionService {
    @Autowired
    private HRationCompositionDao hRationCompositionDao;

    @Override
    public void insertNewDayRation(RationComposition entity) {
        hRationCompositionDao.create(entity);
    }

    @Override
    public Integer sumCaloriesCompositionByRationId(Integer idDayRation) {
        return hRationCompositionDao.getSumCaloriesCompositionByRationId(idDayRation);
    }

    @Override
    public Optional<RationComposition> getCompositionByRationDishFoodIntake(Integer rationId,
                                                                            FoodIntake foodIntake,
                                                                            Integer dishId) {

        return hRationCompositionDao.getCompositionByRationDishFoodIntake(rationId, foodIntake, dishId);
    }

    @Override
    public void updateCompositionById(RationComposition entity) {
        hRationCompositionDao.update(entity);
    }

    @Override
    public void deleteArrayCompositionById(List<Integer> compositionId) {
        hRationCompositionDao.deleteArrayCompositionById(compositionId);
    }

    @Override
    public Optional<RationComposition> getCompositionById(Integer compositionId) {
        return hRationCompositionDao.findById(compositionId);
    }
}
