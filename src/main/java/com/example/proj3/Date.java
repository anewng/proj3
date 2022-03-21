package bank;

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

    /**
     Constructor takes mm/dd/yyyy and creates a Date object.
     @param date the date in String form of Date object you're creating.
     */
    public Date(String date) {
        if (date.length() == 10) {
            month = Integer.parseInt(date.substring(0, 2));
            day = Integer.parseInt(date.substring(3, 5));
            year = Integer.parseInt(date.substring(6, 10));
        } else if (date.length() == 9) {
            if (date.charAt(1) == '/') {
                month = Integer.parseInt(date.substring(0, 1));
                day = Integer.parseInt(date.substring(2, 4));
                year = Integer.parseInt(date.substring(5, 9));
            } else {
                month = Integer.parseInt(date.substring(0, 2));
                day = Integer.parseInt(date.substring(3, 4));
                year = Integer.parseInt(date.substring(5, 9));
            }
        } else if (date.length() == 8) {
            month = Integer.parseInt(date.substring(0, 1));
            day = Integer.parseInt(date.substring(2, 3));
            year = Integer.parseInt(date.substring(4, 8));
        }

    }

    /**
     Creates a Date object of today's date.
     */
    public Date() {
        Calendar date = Calendar.getInstance();
        month = date.get(Calendar.MONTH);
        day = date.get(Calendar.DAY_OF_MONTH);
        year = date.get(Calendar.YEAR);
    }

    /**
     Check to see if the date is a valid Date object.
     @return true if the date is valid, false if not.
     */
    public boolean isValid() {
        if (year < 1900 || year > 2022) return false;
        if (day < 1) return false;
        if (month <= 0 || month >= 13) return false;
        if (((month == 1) || (month == 3) || (month == 5) || (month == 7)
                || (month == 8) || (month == 10) || (month == 12))
                && day > 31) {
            return false;
        } else if (((month == 4) || (month == 6) || (month == 9) || (month == 11))
                && day > 30) {
            return false;
        } else if (month == 2 && isLeapYear() && day >= 30) {
            return false;
        } else if (month == 2 && !isLeapYear() && day >= 29) {
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
