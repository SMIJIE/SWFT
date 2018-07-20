package ua.training.model.entity.enums;

/**
 * This is the enums of user roles
 * {@link ua.training.model.entity.User#role}
 *
 * @author Zakusylo Pavlo
 */
public enum Roles {
    ADMIN("ADMIN"), USER("USER"), UNKNOWN("UNKNOWN");

    private String value;

    Roles(String value) {
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
