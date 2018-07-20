package ua.training.model.dao.utility.converter;

import ua.training.model.entity.enums.FoodCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class FoodCategoryConverter implements AttributeConverter<FoodCategory, String> {

    @Override
    public String convertToDatabaseColumn(FoodCategory attribute) {
        return attribute.getValue();
    }

    @Override
    public FoodCategory convertToEntityAttribute(String dbData) {
        return FoodCategory.valueOf(dbData);
    }
}
