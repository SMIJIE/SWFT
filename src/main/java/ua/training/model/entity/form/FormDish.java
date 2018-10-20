package ua.training.model.entity.form;

import lombok.*;
import ua.training.model.entity.enums.FoodCategory;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FormDish extends GeneralFormEntity {
    /**
     * Verify on not NULL
     */
    @NotNull(message = DISH_VALID_FOOD_CATEGORY_NOT_NULL)
    private FoodCategory foodCategory;
    /**
     * Verify on not BLANK and PATTERN
     */
    @NotBlank(message = DISH_VALID_NAME_NOT_BLANK)
    @Pattern(regexp = DISH_NAME_PATTERN, message = DISH_VALID_NAME_WRONG)
    private String name;
    /**
     * Verify on not NULL and on MIN and MAX
     */
    @NotNull(message = DISH_VALID_WEIGHT_SIZE)
    @Min(value = 50, message = DISH_VALID_WEIGHT_SIZE)
    @Max(value = 999, message = DISH_VALID_WEIGHT_SIZE)
    private Double weight;
    /**
     * Verify on not NULL and on MIN and MAX
     */
    @NotNull(message = DISH_VALID_CALORIES_SIZE)
    @Min(value = 0, message = DISH_VALID_CALORIES_SIZE)
    @Max(value = 1000, message = DISH_VALID_CALORIES_SIZE)
    private Double calories;
    /**
     * Verify on not NULL and on MIN and MAX
     */
    @NotNull(message = DISH_VALID_PROTEINS_SIZE)
    @Min(value = 0, message = DISH_VALID_PROTEINS_SIZE)
    @Max(value = 1000, message = DISH_VALID_PROTEINS_SIZE)
    private Double proteins;
    /**
     * Verify on not NULL and on MIN and MAX
     */
    @NotNull(message = DISH_VALID_FATS_SIZE)
    @Min(value = 0, message = DISH_VALID_FATS_SIZE)
    @Max(value = 1000, message = DISH_VALID_FATS_SIZE)
    private Double fats;
    /**
     * Verify on not NULL and on MIN and MAX
     */
    @NotNull(message = DISH_VALID_CARBOHYDRATES_SIZE)
    @Min(value = 0, message = DISH_VALID_CARBOHYDRATES_SIZE)
    @Max(value = 1000, message = DISH_VALID_CARBOHYDRATES_SIZE)
    private Double carbohydrates;
}
