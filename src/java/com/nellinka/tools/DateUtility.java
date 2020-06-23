/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ComradeBaz
 */
public class DateUtility {

    public static Date getDateFromString(String theDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
        return sdf.parse(theDate);
        } catch (ParseException e) {
            Logger.safePrint("Couldn't parse date");
        }
        return new Date();
    }
    // Method to calculate the lenght of the guest stay in days
    public static int getLengthOfStay(Date checkInDate, Date checkOutDate) {

        long diff = checkOutDate.getTime() - checkInDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return (int) diffDays;
    }

    // Get the int value for the start day of the guest's stay
    public static int getIntStartDate(Date cInDate) {
        SimpleDateFormat formatDay = new SimpleDateFormat("d");
        String dayString = formatDay.format(cInDate);

        return Integer.parseInt(dayString);
    }

    public static int getIntEndDate(Date cOutDate) {
        SimpleDateFormat formatDay = new SimpleDateFormat("d");
        String dayString = formatDay.format(cOutDate);

        return Integer.parseInt(dayString);
    }

    // Same method but it doesn't specify start/end date 
    public static int getIntDate(Date cOutDate) {
        SimpleDateFormat formatDay = new SimpleDateFormat("d");
        String dayString = formatDay.format(cOutDate);

        return Integer.parseInt(dayString);
    }

    // Get the int value for the month of the guest's 
    public static int getIntMonth(Date aDate) {
        SimpleDateFormat formatDay = new SimpleDateFormat("M");
        String monthString = formatDay.format(aDate);

        return Integer.parseInt(monthString);
    }

    public static int getIntYear(Date cOutDate) {
        SimpleDateFormat formatDay = new SimpleDateFormat("y");
        String yearString = formatDay.format(cOutDate);

        return Integer.parseInt(yearString);
    }

    // Get tomorrow's date
    // Found this at www.mkyong.com
    public static Date getTomorrowsDate() {
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date currentDatePlusOneDay = c.getTime();

        return currentDatePlusOneDay;
    }

    public static Date getDatePlusOneDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date currentDatePlusOneDay = c.getTime();

        return currentDatePlusOneDay;
    }

    public static Date getNextMonth() {
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.MONTH, 1);
        Date currentDatePlusOneMonth = c.getTime();

        return currentDatePlusOneMonth;
    }

    // Get the last date of this month
    public static Date getLastDayOfThisMonth(Date ckInDate) {

        Date thisMonthCheckOutDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        // Get the last date of this month as the checkOutDate for this month
        int lastDayOfThisMonth = DateUtility.getNoOfDaysInCurrentMonth();
        int thisMonthInt = DateUtility.getIntMonth(ckInDate);
        int theCkInYear = DateUtility.getIntYear(ckInDate);
        String dateStringEndMonth = String.valueOf(lastDayOfThisMonth) + "-" + String.valueOf(thisMonthInt) + "-"
                + String.valueOf(theCkInYear);

        try {
            thisMonthCheckOutDate = sdf.parse(dateStringEndMonth);
        } catch (ParseException pe) {

            Logger.safePrint("Parsing error: " + pe.getMessage());
        }
        return thisMonthCheckOutDate;
    }
    
    public static Date getLastDayOfNextMonth(Date ckOutDate) {

        Date nextMonthCheckOutDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        // Get the last date of this month as the checkOutDate for this month
        int lastDayOfNextMonth = DateUtility.getNoOfDaysNextMonth(getNextMonth());
        int nextMonthInt = DateUtility.getIntMonth(getNextMonth());
        int theCkOutYear = DateUtility.getIntYear(ckOutDate);
        String dateStringEndMonth = String.valueOf(lastDayOfNextMonth) + "-" + String.valueOf(nextMonthInt) + "-"
                + String.valueOf(theCkOutYear);

        try {
            nextMonthCheckOutDate = sdf.parse(dateStringEndMonth);
        } catch (ParseException pe) {

            Logger.safePrint("Parsing error: " + pe.getMessage());
        }
        return nextMonthCheckOutDate;
    }

    // Get the first day of next month
    public static Date getFirstDayOfNextMonth(Date ckOutDate) {

        Date nextMonthCheckInDate = new Date();

        int theMonth = DateUtility.getIntMonth(ckOutDate);
        int theCkOutYear = DateUtility.getIntYear(ckOutDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        String dateStringStartMonth = "01-" + String.valueOf(theMonth) + "-" + String.valueOf(theCkOutYear);

        try {
            nextMonthCheckInDate = sdf.parse(dateStringStartMonth);
        } catch (ParseException pe) {

            Logger.safePrint("Parsing error: " + pe.getMessage());
        }
        return nextMonthCheckInDate;
    }

    // Is the guest staying past the end of this month
    public static boolean guestStayingUntilNextMonth(Date checkInDate, Date checkOutDate) {

        Date date = new Date();

        // If the guest stays until the first of next month it is not 
        // treated as the guest staying to next month
        //if ((getIntMonth(checkOutDate)) < (getIntMonth(date))) {
        if ((getIntMonth(checkOutDate)) < (getIntMonth(checkInDate))) {
            return ((getIntMonth(checkOutDate) + 12) - ((getIntMonth(checkInDate))) == 1);
        }

        return (((getIntMonth(checkOutDate) - getIntMonth(checkInDate))) == 1);
    }

    public static boolean guestCheckingOutOnTheFirstOfNextMonth(Date checkInDate, Date checkOutDate) {

        Date date = new Date();

        // If the guest stays until the first of next month it is not 
        // treated as the guest staying to next month
        //if ((getIntMonth(checkOutDate)) < (getIntMonth(date))) {
        if ((getIntMonth(checkOutDate)) < (getIntMonth(checkInDate))) {
            return ((getIntMonth(checkOutDate) + 12) - ((getIntMonth(checkInDate))) == 1
                    && getIntDate(checkOutDate) == 1);
        }
        return ((getIntMonth(checkOutDate) - getIntMonth(checkInDate)) == 1
                && getIntDate(checkOutDate) == 1);
    }

    // Is the guest staying starting from next month
    public static boolean guestStayStartingNextMonth(Date checkInDate, Date checkOutDate) {

        Date today = new Date();
        //if ((getIntMonth(checkOutDate)) < (getIntMonth(today))) {
        if ((getIntMonth(checkOutDate)) < (getIntMonth(checkInDate))) {
            return ((getIntMonth(checkInDate) + 12) - ((getIntMonth(checkInDate))) == 1);
        }
        return (getIntMonth(checkInDate) - getIntMonth(checkInDate) == 1);
    }

    public static boolean guestStayStartingThisMonth(Date checkInDate, Date checkOutDate) {

        Date today = new Date();

        return (getIntMonth(checkInDate) - getIntMonth(today) == 0);
    }
    
    public static boolean guestStayEndingThisMonth(Date checkOutDate) {

        Date today = new Date();

        return (getIntMonth(checkOutDate) - getIntMonth(today) == 0);
    }

    public static boolean guestStayEndingAfterNextMonth(Date checkInDate, Date checkOutDate) {
        Date today = new Date();

        if ((getIntMonth(checkOutDate)) < (getIntMonth(checkInDate))) {
            return ((getIntMonth(checkInDate) + 12) - ((getIntMonth(checkInDate))) > 1);
        }
        return ((getIntMonth(checkOutDate)) - getIntMonth(checkInDate) > 1);
    }

    public static boolean guestStayStartingAfterNextMonth(Date checkInDate, Date checkOutDate) {
        Date today = new Date();

        if ((getIntMonth(checkInDate)) < (getIntMonth(today))) {
            return (getIntMonth(checkInDate) + 12) - ((getIntMonth(today))) > 1;
        }
        return (getIntMonth(checkInDate) - getIntMonth(today) > 1);
    }

    // Get the number of days in the current month
    public static int getNoOfDaysInCurrentMonth() {

        SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat formatYear = new SimpleDateFormat("YYYY");
        String MonthOfName;
        int noOfDaysInMonth = -1;
        String monthString = formatMonth.format(new Date());
        String yearString = formatYear.format(new Date());

        switch (monthString) {
            case "01":
                MonthOfName = "January";
                noOfDaysInMonth = 31;
                break;
            case "02":
                MonthOfName = "February";
                if ((Integer.parseInt(yearString) % 400 == 0)
                        || ((Integer.parseInt(yearString) % 4 == 0)
                        && (Integer.parseInt(yearString) % 100 != 0))) {
                    noOfDaysInMonth = 29;
                } else {
                    noOfDaysInMonth = 28;
                }
                break;
            case "03":
                MonthOfName = "March";
                noOfDaysInMonth = 31;
                break;
            case "04":
                MonthOfName = "April";
                noOfDaysInMonth = 30;
                break;
            case "05":
                MonthOfName = "May";
                noOfDaysInMonth = 31;
                break;
            case "06":
                MonthOfName = "June";
                noOfDaysInMonth = 30;
                break;
            case "07":
                MonthOfName = "July";
                noOfDaysInMonth = 31;
                break;
            case "08":
                MonthOfName = "August";
                noOfDaysInMonth = 31;
                break;
            case "09":
                MonthOfName = "September";
                noOfDaysInMonth = 30;
                break;
            case "10":
                MonthOfName = "October";
                noOfDaysInMonth = 31;
                break;
            case "11":
                MonthOfName = "November";
                noOfDaysInMonth = 30;
                break;
            case "12":
                MonthOfName = "December";
                noOfDaysInMonth = 31;
        }
        return noOfDaysInMonth;
    }

    // Get number of days next month
    // Get the number of days in the current month
    public static int getNoOfDaysNextMonth(Date monthPlusOne) {

        SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat formatYear = new SimpleDateFormat("YYYY");
        String MonthOfName;
        int noOfDaysInMonth = -1;
        String monthString = formatMonth.format(monthPlusOne);
        String yearString = formatYear.format(monthPlusOne);

        switch (monthString) {
            case "01":
                MonthOfName = "January";
                noOfDaysInMonth = 31;
                break;
            case "02":
                MonthOfName = "February";
                if ((Integer.parseInt(yearString) % 400 == 0)
                        || ((Integer.parseInt(yearString) % 4 == 0)
                        && (Integer.parseInt(yearString) % 100 != 0))) {
                    noOfDaysInMonth = 29;
                } else {
                    noOfDaysInMonth = 28;
                }
                break;
            case "03":
                MonthOfName = "March";
                noOfDaysInMonth = 31;
                break;
            case "04":
                MonthOfName = "April";
                noOfDaysInMonth = 30;
                break;
            case "05":
                MonthOfName = "May";
                noOfDaysInMonth = 31;
                break;
            case "06":
                MonthOfName = "June";
                noOfDaysInMonth = 30;
                break;
            case "07":
                MonthOfName = "July";
                noOfDaysInMonth = 31;
                break;
            case "08":
                MonthOfName = "August";
                noOfDaysInMonth = 31;
                break;
            case "09":
                MonthOfName = "September";
                noOfDaysInMonth = 30;
                break;
            case "10":
                MonthOfName = "October";
                noOfDaysInMonth = 31;
                break;
            case "11":
                MonthOfName = "November";
                noOfDaysInMonth = 30;
                break;
            case "12":
                MonthOfName = "December";
                noOfDaysInMonth = 31;
        }
        return noOfDaysInMonth;
    }

    public static String getDateInMySqlFormat() {
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

        String currentDate = sdf.format(dt);

        return currentDate;

    }
    public static String getADateInMySqlFormat(Date date) {

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

        String returnDate = sdf.format(date);

        return returnDate;

    }
    // Get a list of integers that become the table heading 1-30/31 for roomGrid views
    public static List<Integer> getDaysOfMonthForTableHeading(String whatMonth) {

        int result = getNoOfDaysInCurrentMonth();
        int result2 = DateUtility.getNoOfDaysNextMonth(getNextMonth());
        // Add 1 to shift days one position right
        result += 1;
        result2 += 1;
        List<Integer> retList = new ArrayList<>();

        if (whatMonth.equalsIgnoreCase("this_month")) {
            for (int i = 1; i <= result; i++) {
                // Add -1 to the first position in the list
                // Use this value to render the table header, omit the value
                if (i == 1) {
                    retList.add(-1);
                } else {
                    retList.add(i - 1);
                }
            }
        } else if (whatMonth.equalsIgnoreCase("next_month")) {
            for (int j = 1; j <= result2; j++) {
                if (j == 1) {
                    retList.add(-1);
                } else {
                    retList.add(j - 1);
                }
            }
        }
        return retList;
    }
    public static Date getDateObject(String whatMonth, String dayAsString) {

        String dateValue = "";
        String theYearString = "";
        String theMonthString = "";
        Date returnDate = new Date();

        if (whatMonth.equalsIgnoreCase("this_month")) {
            int theMonth = DateUtility.getIntMonth(new Date());
            theMonthString = Integer.toString(theMonth);
            int theYear = DateUtility.getIntYear(new Date());
            theYearString = Integer.toString(theYear);
            dateValue = dayAsString + "-" + theMonthString + "-" + theYearString;
        } else if (whatMonth.equalsIgnoreCase("next_month")) {
            int theMonth = DateUtility.getIntMonth(DateUtility.getNextMonth());
            theMonthString = Integer.toString(theMonth);
            int theYear = DateUtility.getIntYear(DateUtility.getNextMonth());
            theYearString = Integer.toString(theYear);
            dateValue = dayAsString + "-" + theMonthString + "-" + theYearString;
        }
        try {
            returnDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateValue);
        } catch (ParseException e) {
            Logger.safePrint(e.getMessage());
        }
        return returnDate;
    }
}
