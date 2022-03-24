package com.example.bank;

import java.util.Calendar;

/**
 The Date class is used to create/manipulate/access Date objects.
 @author Annie Wang, Jasmine Flanders
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    public static final int LONG_DATE_LENGTH = 10;
    public static final int MEDIUM_DATE_LENGTH = 9;
    public static final int SHORT_DATE_LENGTH = 8;
    public static final int LONG_DATE_MONTH_START_INDEX = 0;
    public static final int LONG_DATE_MONTH_END_INDEX = 2;
    public static final int LONG_DATE_DAY_START_INDEX = 3;
    public static final int LONG_DATE_DAY_END_INDEX = 5;
    public static final int LONG_DATE_YEAR_START_INDEX = 6;
    public static final int LONG_DATE_YEAR_END_INDEX = 10;
    public static final int SLASH_INDEX = 1;
    public static final int MEDIUM_DATE_MONTH_START_INDEX_CASE_1 = 0;
    public static final int MEDIUM_DATE_MONTH_END_INDEX_CASE_1 = 1;
    public static final int MEDIUM_DATE_DAY_START_INDEX_CASE_1 = 2;
    public static final int MEDIUM_DATE_DAY_END_INDEX_CASE_1 = 4;
    public static final int MEDIUM_DATE_YEAR_START_INDEX_CASE_1 = 5;
    public static final int MEDIUM_DATE_YEAR_END_INDEX_CASE_1 = 9;
    public static final int MEDIUM_DATE_MONTH_START_INDEX_CASE_2 = 0;
    public static final int MEDIUM_DATE_MONTH_END_INDEX_CASE_2 = 2;
    public static final int MEDIUM_DATE_DAY_START_INDEX_CASE_2 = 3;
    public static final int MEDIUM_DATE_DAY_END_INDEX_CASE_2 = 4;
    public static final int MEDIUM_DATE_YEAR_START_INDEX_CASE_2 = 5;
    public static final int MEDIUM_DATE_YEAR_END_INDEX_CASE_2 = 9;
    public static final int SHORT_DATE_MONTH_START_INDEX = 0;
    public static final int SHORT_DATE_MONTH_END_INDEX = 1;
    public static final int SHORT_DATE_DAY_START_INDEX = 2;
    public static final int SHORT_DATE_DAY_END_INDEX = 3;
    public static final int SHORT_DATE_YEAR_START_INDEX = 4;
    public static final int SHORT_DATE_YEAR_END_INDEX = 8;
    public static final int MIN_YEAR = 1900;
    public static final int MAX_YEAR = 2022;
    public static final int MIN_DAY = 0;
    public static final int MIN_MONTH = 0;
    public static final int MAX_MONTH = 13;
    public static final int JANUARY = 1;
    public static final int FEBRUARY = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final int OCTOBER = 10;
    public static final int NOVEMBER = 11;
    public static final int DECEMBER = 12;
    public static final int LONG_MONTH_MAX_DAYS = 31;
    public static final int SHORT_MONTH_MAX_DAYS = 30;
    public static final int LEAP_YEAR_MAX_DAYS = 30;
    public static final int NON_LEAP_YEAR_MAX_DAYS = 29;

    /**
     Constructor takes mm/dd/yyyy and creates a Date object.
     @param date the date in String form of Date object you're creating.
     */
    public Date(String date) {
        if (date.length() == LONG_DATE_LENGTH) {
            month = Integer.parseInt(date.substring(LONG_DATE_MONTH_START_INDEX, LONG_DATE_MONTH_END_INDEX));
            day = Integer.parseInt(date.substring(LONG_DATE_DAY_START_INDEX, LONG_DATE_DAY_END_INDEX));
            year = Integer.parseInt(date.substring(LONG_DATE_YEAR_START_INDEX, LONG_DATE_YEAR_END_INDEX));
        } else if (date.length() == MEDIUM_DATE_LENGTH) {
            if (date.charAt(SLASH_INDEX) == '/') {
                month = Integer.parseInt(date.substring(MEDIUM_DATE_MONTH_START_INDEX_CASE_1, MEDIUM_DATE_MONTH_END_INDEX_CASE_1));
                day = Integer.parseInt(date.substring(MEDIUM_DATE_DAY_START_INDEX_CASE_1, MEDIUM_DATE_DAY_END_INDEX_CASE_1));
                year = Integer.parseInt(date.substring(MEDIUM_DATE_YEAR_START_INDEX_CASE_1, MEDIUM_DATE_YEAR_END_INDEX_CASE_1));
            } else {
                month = Integer.parseInt(date.substring(MEDIUM_DATE_MONTH_START_INDEX_CASE_2, MEDIUM_DATE_MONTH_END_INDEX_CASE_2));
                day = Integer.parseInt(date.substring(MEDIUM_DATE_DAY_START_INDEX_CASE_2, MEDIUM_DATE_DAY_END_INDEX_CASE_2));
                year = Integer.parseInt(date.substring(MEDIUM_DATE_YEAR_START_INDEX_CASE_2, MEDIUM_DATE_YEAR_END_INDEX_CASE_2));
            }
        } else if (date.length() == SHORT_DATE_LENGTH) {
            month = Integer.parseInt(date.substring(SHORT_DATE_MONTH_START_INDEX, SHORT_DATE_MONTH_END_INDEX));
            day = Integer.parseInt(date.substring(SHORT_DATE_DAY_START_INDEX, SHORT_DATE_DAY_END_INDEX));
            year = Integer.parseInt(date.substring(SHORT_DATE_YEAR_START_INDEX, SHORT_DATE_YEAR_END_INDEX));
        }

    }

    /**
     Creates a Date object of today's date.
     */
    public Date() {
        Calendar date = Calendar.getInstance();
        month = date.get(Calendar.MONTH) + 1;
        day = date.get(Calendar.DAY_OF_MONTH);
        year = date.get(Calendar.YEAR);
    }

    /**
     Check to see if the date is a valid Date object.
     @return true if the date is valid, false if not.
     */
    public boolean isValid() {
        if (year < MIN_YEAR || year > MAX_YEAR) return false;
        if (day < MIN_DAY) return false;
        if (month <= MIN_MONTH || month >= MAX_MONTH) return false;
        if (((month == JANUARY) || (month == MARCH) || (month == MAY) || (month == JULY)
                || (month == AUGUST) || (month == OCTOBER) || (month == DECEMBER))
                && day > LONG_MONTH_MAX_DAYS) {
            return false;
        } else if (((month == APRIL) || (month == JUNE) || (month == SEPTEMBER) || (month == NOVEMBER))
                && day > SHORT_MONTH_MAX_DAYS) {
            return false;
        } else if (month == FEBRUARY && isLeapYear() && day >= LEAP_YEAR_MAX_DAYS) {
            return false;
        } else if (month == FEBRUARY && !isLeapYear() && day >= NON_LEAP_YEAR_MAX_DAYS) {
            return false;
        }
        return true;
    }

    /**
     Returns ints corresponding to the relationship between the two dates being compared.
     @param date the date being compared to.
     @return 1 if date being compared is larger, -1 if date being compared is smaller, and 0 if dates equal.
     */
    @Override
    public int compareTo(Date date) {
        if (this.day == date.day && this.month == date.month && this.year == date.year) {
            return 0;
        } else if ((this.year < date.year)
                || (this.year == date.year && this.month < date.month)
                || (this.year == date.year && this.month == date.month && this.day < date.day)) {
            return -1;
        }
        return 1;
    }

    /**
     Checks to see if the date is in the future or not.
     @return true if the date is past today's date, false if it is before today's date.
     */
    public boolean isFutureDate() {
        Date today = new Date();
        if (this.compareTo(today) == -1) {
            return false;
        }
        return true;
    }

    /**
     Checks to see if the date is a leap year date or not.
     @return true if the year of the date is a leap year, false if it is not a leap year.
     */
    public boolean isLeapYear() {
        if (year % QUADRENNIAL == 0) {
            if (year % CENTENNIAL == 0) {
                if (year % QUATERCENTENNIAL == 0) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     Converts the date into a string.
     @return String of the Date object.
     */
    public String toString() {
        return +this.month + "/" + this.day + "/" + this.year;
    }

}
