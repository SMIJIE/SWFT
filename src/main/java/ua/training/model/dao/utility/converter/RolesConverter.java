package ua.training.model.dao.utility.converter;

import ua.training.model.entity.enums.Roles;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RolesConverter implements AttributeConverter<Roles, String> {

    @Override
    public String convertToDatabaseColumn(Roles attribute) {
        return attribute.getValue();
    }

    @Override
    public Roles convertToEntityAttribute(String dbData) {
        return Roles.valueOf(dbData);
    }
}
