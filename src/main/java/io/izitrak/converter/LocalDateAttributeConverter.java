package io.izitrak.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        if (localDate != null){
            return Date.valueOf(localDate);
        }else {
            return null;
        }
    }


    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
        if (dbData != null){
            return dbData.toLocalDate();
        }else {
            return null;
        }
    }
}
