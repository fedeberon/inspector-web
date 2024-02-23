package com.ideaas.services.bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class DateTimeUtil {

    public static LocalDateTime convertToLocalDateTimeViaSqlTimestamp(Date dateToConvert) {
        assert dateToConvert != null : "[ERROR-SCOPESSI] * Date must´n be null";
        return new java.sql.Timestamp(dateToConvert.getTime()).toLocalDateTime();
    }

    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * This function allows you to convert the {@link java.time.LocalDateTime date}
     * to a custom string. The main function of this method is to make up for the
     * lack of formatting a {@link java.time.LocalDateTime date} in the JSP files,
     * in addition, to be able to override the default output of the
     * {@link LocalDateTime#toString()} (which by default omits the seconds when they
     * are equal to 00) to match with the pattern
     * {@link DateTimeFormatter#ISO_DATE_TIME ISO_DATE_TIME}.
     * @param localDateTime
     * @return
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        if(Objects.isNull(localDateTime)) return "";
        return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * This function allows you to convert the {@link java.time.LocalDateTime date}
     * to a custom string. The main function of this method is to make up for the
     * lack of formatting a {@link java.time.LocalDateTime date} in the JSP files,
     * in addition, to be able to customize the default output of the
     * {@link LocalDateTime#toString()} (which by default omits the seconds when they
     * are equal to 00).
     * @param localDateTime
     * @param patter
     * @return
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime, String patter) {
        if(Objects.isNull(localDateTime)) return "";
        return localDateTime.format(DateTimeFormatter.ofPattern(patter));
    }

    /**
     * This function allows you to convert the {@link java.time.LocalDate date}
     * to a custom string.
     * @param localDate
     * @return
     */
    public static String formatLocalDate(LocalDate localDate) {
        if(Objects.isNull(localDate)) return "";
        return localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
