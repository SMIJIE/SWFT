package ua.training.model.dao.service;

import ua.training.model.entity.Dish;

import java.util.List;
import java.util.Optional;

public interface DishService extends Service {
    List<Dish> getGeneralDishes();

    void deleteArrayDishesById(Integer[] array);

    void deleteArrayDishesByIdAndUser(Integer[] array,
                                      Integer userId);

    Optional<Dish> getDishById(Integer id);

    void updateDishParameters(Dish entity);

    void updateDishParametersByIdAndUser(Dish entity);

    void insertNewDish(Dish entity);

    List<Dish> getLimitDishesByUserId(Integer userId,
                                      Integer limit,
                                      Integer skip);

    Integer counterDishByUserId(Integer userId);
}
