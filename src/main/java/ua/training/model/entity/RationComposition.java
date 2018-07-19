package ua.training.model.entity;

import lombok.*;
import ua.training.model.entity.enums.FoodIntake;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RationComposition implements Entity<Integer> {
    private Integer id;
    private DayRation dayRation;
    /**
     * Food intake per day
     */
    private FoodIntake foodIntake;
    private Dish dish;
    private Integer numberOfDish;
    private Integer caloriesOfDish;
}
