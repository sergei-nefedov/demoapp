package pers.nefedov.demoapp.mappers;

import org.springframework.stereotype.Component;
import pers.nefedov.demoapp.exceptions.IncorrectDateFormatException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
@Component
public class DateMapperImpl implements DateMapper {
    @Override
    public String mapDateToString(Date date) {
        return DateFormat.getDateInstance().format(date);
    }

    @Override
    public Date mapToDate(String dateString) {
        if (dateString == null) return null;
        Date date;
        try {
            date = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(dateString);
        } catch (ParseException e) {
            throw new IncorrectDateFormatException();
        }
        return date;
    }
}
