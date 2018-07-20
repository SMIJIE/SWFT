package ua.training.model.dao.utility.converter;

import ua.training.model.entity.enums.FoodIntake;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class FoodIntakeConverter implements AttributeConverter<FoodIntake, String> {

    @Override
    public String convertToDatabaseColumn(FoodIntake attribute) {
        return attribute.getValue();
    }

    @Override
    public FoodIntake convertToEntityAttribute(String dbData) {
        return FoodIntake.valueOf(dbData);
    }
}
