package ua.training.model.entity;

import lombok.*;
import ua.training.model.dao.utility.SortAnnotation;
import ua.training.model.entity.enums.FoodCategory;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Dish implements Entity<Integer> {
    private Integer id;
    /**
     * Category of dishes
     */
    @SortAnnotation
    private FoodCategory foodCategory;
    private String name;
    private Integer weight;
    @SortAnnotation
    private Integer calories;
    private Integer proteins;
    private Integer fats;
    private Integer carbohydrates;
    private User user;
    /**
     * Parameter for basic and custom dishes
     */
    private Boolean generalFood;
}
