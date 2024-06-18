package fr.elowyr.basics.utils;

import java.util.Calendar;

public class DateUtils {

    public static int[] getDate() {
        int[] values = new int[2];
        Calendar calendar = Calendar.getInstance();
        values[0] = calendar.get(5);
        values[1] = calendar.get(2);
        return values;
    }

    public static boolean isCurrentDate(int hours, int minutes, int seconds) {
        Calendar now = Calendar.getInstance();
        int currentHours = now.get(11);
        int currentMinutes = now.get(12);
        int currentSeconds = now.get(13);
        return currentHours == hours && currentMinutes == minutes && currentSeconds == seconds;
    }

    public static boolean isCurrentDate(int day, int hours, int minutes, int seconds) {
        Calendar now = Calendar.getInstance();
        int currentDay = now.get(7);
        int currentHours = now.get(11);
        int currentMinutes = now.get(12);
        int currentSeconds = now.get(13);
        return currentDay == day && currentHours == hours && currentMinutes == minutes && currentSeconds == seconds;
    }
}
