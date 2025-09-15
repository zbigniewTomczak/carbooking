package utils;

import java.util.Date;
import java.util.Calendar;

public class DateUtils {

    public static Date addDays(Date from, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(from);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }
}

