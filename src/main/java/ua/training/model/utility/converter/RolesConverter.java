package ua.training.model.utility.converter;

import ua.training.model.entity.enums.Roles;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Description: Convert ua.training.model.entity.enums.Roles to java.lang.String and back
 *
 * @author Zakusylo Pavlo
 */
@Converter(autoApply = true)
public class RolesConverter implements AttributeConverter<Roles, String> {
    /**
     * @param attribute Roles
     * @return object String
     */
    @Override
    public String convertToDatabaseColumn(Roles attribute) {
        return attribute.getValue();
    }

    /**
     * @param dbData String
     * @return object Roles
     */
    @Override
    public Roles convertToEntityAttribute(String dbData) {
        return Roles.valueOf(dbData);
    }
}