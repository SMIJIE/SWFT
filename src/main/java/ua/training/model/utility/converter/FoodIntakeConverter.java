package ua.training.model.utility.converter;

import ua.training.model.entity.enums.FoodIntake;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Description: Convert ua.training.model.entity.enums.FoodIntake to java.lang.String and back
 *
 * @author Zakusylo Pavlo
 */
@Converter(autoApply = true)
public class FoodIntakeConverter implements AttributeConverter<FoodIntake, String> {
    /**
     * @param attribute FoodIntake
     * @return object String
     */
    @Override
    public String convertToDatabaseColumn(FoodIntake attribute) {
        return attribute.getValue();
    }

    /**
     * @param dbData String
     * @return object FoodIntake
     */
    @Override
    public FoodIntake convertToEntityAttribute(String dbData) {
        return FoodIntake.valueOf(dbData);
    }
}
