package ua.training.model.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.training.model.dao.implemation.HDishDao;
import ua.training.model.entity.Dish;
import ua.training.model.service.DishService;

import java.util.List;
import java.util.Optional;

@Service
public class DishServiceImp implements DishService {
    @Autowired
    private HDishDao hDishDao;

    @Override
    public List<Dish> getGeneralDishes() {
        return hDishDao.getAllGeneralDishes();
    }

    @Override
    public void deleteArrayDishesById(List<Integer> array) {
        hDishDao.deleteArrayDishesById(array);
    }

    @Override
    public void deleteArrayDishesByIdAndUser(List<Integer> array,
                                             Integer userId) {

        hDishDao.deleteArrayDishesByIdAndUser(array, userId);
    }

    @Override
    public Optional<Dish> getDishById(Integer id) {
        return hDishDao.findById(id);
    }

    @Override
    public void updateDishParameters(Dish entity) {
        hDishDao.update(entity);
    }

    @Override
    public void insertNewDish(Dish entity) {
        hDishDao.create(entity);
    }

    @Override
    public List<Dish> getLimitDishesByUserId(Integer userId,
                                             Integer limit,
                                             Integer skip) {

        return hDishDao.getLimitDishesByUserId(userId, limit, skip);
    }

    @Override
    public Integer counterDishByUserId(Integer userId) {
        return hDishDao.countDishes(userId);
    }
}
