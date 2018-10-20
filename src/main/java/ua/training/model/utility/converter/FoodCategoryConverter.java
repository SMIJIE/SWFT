package ua.training.model.utility.converter;

import ua.training.model.entity.enums.FoodCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Description: Convert ua.training.model.entity.enums.FoodCategory to java.lang.String and back
 *
 * @author Zakusylo Pavlo
 */
@Converter(autoApply = true)
public class FoodCategoryConverter implements AttributeConverter<FoodCategory, String> {
    /**
     * @param attribute FoodCategory
     * @return object String
     */
    @Override
    public String convertToDatabaseColumn(FoodCategory attribute) {
        return attribute.getValue();
    }

    /**
     * @param dbData String
     * @return object FoodCategory
     */
    @Override
    public FoodCategory convertToEntityAttribute(String dbData) {
        return FoodCategory.valueOf(dbData);
    }
}
