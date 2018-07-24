package ua.training.model.dao.service.implementation;

import ua.training.model.dao.DishDao;
import ua.training.model.dao.service.DishService;
import ua.training.model.entity.Dish;

import java.util.List;
import java.util.Optional;

public class DishServiceImp implements DishService {
    private DishDao dishDao = DAO_FACTORY.createDishDao();

    @Override
    public List<Dish> getGeneralDishes() {
        return dishDao.getAllGeneralDishes();
    }

    @Override
    public void deleteArrayDishesById(List<Integer> array) {
        dishDao.deleteArrayDishesById(array);
    }

    @Override
    public void deleteArrayDishesByIdAndUser(List<Integer> array,
                                             Integer userId) {
        dishDao.deleteArrayDishesByIdAndUser(array, userId);
    }

    @Override
    public Optional<Dish> getDishById(Integer id) {
        return dishDao.findById(id);
    }

    @Override
    public void updateDishParameters(Dish entity) {
        dishDao.update(entity);
    }

    @Override
    public void insertNewDish(Dish entity) {
        dishDao.create(entity);
    }

    @Override
    public List<Dish> getLimitDishesByUserId(Integer userId,
                                             Integer limit,
                                             Integer skip) {
        return dishDao.getLimitDishesByUserId(userId, limit, skip);
    }

    @Override
    public Integer counterDishByUserId(Integer userId) {
        return dishDao.countDishes(userId);
    }
}
