package com.testapp.municipalitytax.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleDateValidator {
    private ScheduleDateValidator() {

    }

    public static String getDateFormat(String dateStr) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        String correctDate = null;
        try {
            Date date = parser.parse(dateStr);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            correctDate = formatter.format(date);
        } catch (ParseException e) {
            throw new RuntimeException("Exception while parsing date");
        }
        if (correctDate == null || correctDate.isEmpty()) {
            throw new RuntimeException("Exception while parsing date");
        }
        return correctDate;
    }

    public static boolean isTaxCorrect(double tax) {
        return (tax >= 0 && tax <= 1);
    }

    public static boolean isDateCorrect(String dateStr){
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = parser.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
