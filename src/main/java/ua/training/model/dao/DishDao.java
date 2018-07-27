package ua.training.model.dao;

import ua.training.model.entity.Dish;

import java.util.List;

public interface DishDao extends GenericDao<Dish> {
    List<Dish> getAllGeneralDishes();

    List<Dish> getLimitDishesByUserId(Integer userId,
                                      Integer limit,
                                      Integer skip);

    void deleteArrayDishesById(List<Integer> array);

    void deleteArrayDishesByIdAndUser(List<Integer> array,
                                      Integer userId);

    Integer countDishes(Integer userId);
}
