/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.tools;

import com.nellinka.staticdata.GuestStatus;
import java.util.Date;

/**
 *
 * @author ComradeBaz
 */
public class SearchStrings {

    // Get the Strings to build search queries based on user input
    public static String getSearchStringPart(String value, String field) {

        String retValue = " cIG.status=:";
        int index = 0;
        int length = GuestStatus.getAllGuestStatusInteger().size();

        switch (field) {
            case "guestStatus":
                if (value.equalsIgnoreCase("any")) {
                    return "";
                } else if (value.equalsIgnoreCase("Saved - Not Checked In")) {
                    return " cIG.status=0";
                } else if (value.equalsIgnoreCase("Checked In")) {
                    return " cIG.status=1";
                } else if (value.equalsIgnoreCase("Checked Out")) {
                    return " cIG.status=2";
                } else if (value.equalsIgnoreCase("Changed Room")) {
                    return " cIG.status=3";
                } else {
                    return " cIG.status=:" + value;
                }
            case "roomName":
                if (value.equalsIgnoreCase("any")) {
                    return "";
                } else {
                    return " cIG.roomName=\"" + value + "\"";
                }
            case "country": {
                if (value.equalsIgnoreCase("any")) {
                    return "";
                } else {
                    return " cIG.country=\"" + value + "\"";
                }
            }
            case "checkInDate": {
                if (value.equalsIgnoreCase("any")) {
                    return "";
                } else {
                    return " cIG.checkInDate=\"" + value + "\"";
                }
            }
            case "checkOutDate": {
                if (value.equalsIgnoreCase("any")) {
                    return "";
                } else {
                    return " cIG.checkOutDate=\"" + value + "\"";
                }
            }
        }
        return "";
    }

    public static String buildSearchString(String status, String roomName, String country, String checkInDate, String checkOutDate) {
        // SELECT cIG FROM CheckedInGuests cIG WHERE cIG.country = "country" AND cIG.checkInDate >= "date" AND cIG.checkOutDate <= "date" 
        boolean dateConditionSet = false;
        String retValue = "SELECT cIG FROM CheckedInGuests cIG";
        //String dateCondition = " cIG.checkInDate>=\"" + checkInDate + "\" AND cIG.checkOutDate<=\"" + checkOutDate + "\"";
        String dateCondition = "";

        // Get the date condition String
        if (checkInDate.equalsIgnoreCase("")) {
            if (checkOutDate.equalsIgnoreCase("")) {
                // do nothing
            } else {
                dateCondition += " cIG.checkOutDate=\"" + checkOutDate + "\"";
                dateConditionSet = true;
            }
        } else {
            //dateCondition += " cIG.checkInDate>=\"" + checkInDate + "\"";
            //dateConditionSet = true;
            if (checkOutDate.equalsIgnoreCase("")) {
                // It's not a date range so set dateCondition =
                dateCondition += " cIG.checkInDate=\"" + checkInDate + "\"";
                dateConditionSet = true;
            } else {
                //dateCondition += " AND cIG.checkOutDate<=\"" + checkOutDate + "\"";
                dateCondition += " cIG.checkInDate>=\"" + checkInDate + "\"" + " AND cIG.checkOutDate<=\"" + checkOutDate + "\"";
                dateConditionSet = true;
            }
        }
        boolean statusSet = false;
        boolean roomNameSet = false;

        if (status.equalsIgnoreCase("")) {
            if (roomName.equalsIgnoreCase("")) {
                if (country.equalsIgnoreCase("")) {
                    if (dateConditionSet) {
                        return retValue + " WHERE " + dateCondition;
                    } else {
                        return retValue;
                    }
                }
            }
        } else {
            retValue += " WHERE " + status;
            statusSet = true;
        }
        if (roomName.equalsIgnoreCase("")) {
            if (country.equalsIgnoreCase("")) {
                if (dateConditionSet) {
                    return retValue + " AND " + dateCondition;
                } else {
                    return retValue;
                }
            }
        } else {
            if (statusSet) {
                retValue += " AND " + roomName;
            } else {
                retValue += " WHERE " + roomName;
                roomNameSet = true;
            }
        }
        if (country.equalsIgnoreCase("")) {
            if (dateConditionSet) {
                return retValue + " AND " + dateCondition;
            } else {
                return retValue;
            }
        } else {
            if ((statusSet) || (roomNameSet)) {
                if (dateConditionSet) {
                    retValue += " AND " + country;
                    return retValue + " AND " + dateCondition;
                } else {
                    return retValue;
                }
            } else {
                retValue += " WHERE " + country;
                if (dateConditionSet) {
                    return retValue + " AND " + dateCondition;
                } else {
                    return retValue;
                }
            }
        }
    }
}
