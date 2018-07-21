package ua.training.model.dao.utility.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Description: Convert java.time.LocalDate to java.sql.Date and back
 *
 * @author Zakusylo Pavlo
 */
@Converter(autoApply = true)
public class DateConverter implements AttributeConverter<LocalDate, Date> {
    /**
     * @param attribute LocalDate
     * @return object Date
     */
    @Override
    public Date convertToDatabaseColumn(LocalDate attribute) {
        return Date.valueOf(attribute);
    }

    /**
     * @param dbData Date
     * @return object LocalDate
     */
    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
        return dbData.toLocalDate();
    }
}
