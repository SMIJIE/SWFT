package ua.training.model.entity.enums;

/**
 * This is the enums of food intake per day
 * {@link ua.training.model.entity.RationComposition#foodIntake}
 *
 * @author Zakusylo Pavlo
 */
public enum FoodIntake {
    BREAKFAST("BREAKFAST"), DINNER("DINNER"), SUPPER("SUPPER");

    private String value;

    FoodIntake(String value) {
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
