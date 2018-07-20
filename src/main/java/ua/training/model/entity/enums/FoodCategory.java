package ua.training.model.entity.enums;

/**
 * This is the enums of category for dishes
 * {@link ua.training.model.entity.Dish#foodCategory}
 *
 * @author Zakusylo Pavlo
 */
public enum FoodCategory {
    LUNCHEON("LUNCHEON"), SOUP("SOUP"), HOT("HOT"), DESSERT("DESSERT");

    private String value;

    FoodCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
