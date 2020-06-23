/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var whatMonthIsIt;

function getNumberOfDaysThisMonth() {
    var d = new Date();
    var n = d.getMonth();

    switch (n) {
        case 0:
            return 31;
        case 1:
            return 28;
        case 2:
            return 31;
        case 3:
            return 30;
        case 4:
            return 31;
        case 5:
            return 30;
        case 6:
            return 31;
        case 7:
            return 31;
        case 8:
            return 30;
        case 9:
            return 31;
        case 10:
            return 30;
        case 11:
            return 31;
    }
}

function getNumberOfDaysNextMonth() {
    var d = new Date();
    var n = d.getMonth();

    if (n == 11)
        return 31;

    switch (n + 1) {
        case 0:
            return 31;
        case 1:
            return 28;
        case 2:
            return 31;
        case 3:
            return 30;
        case 4:
            return 31;
        case 5:
            return 30;
        case 6:
            return 31;
        case 7:
            return 31;
        case 8:
            return 30;
        case 9:
            return 31;
        case 10:
            return 30;
        case 11:
            return 31;
    }
}
function setWhatMonth(element) {
    var theMonth = element.id;

    if(theMonth == "thisMonthButton") {
        whatMonthIsIt = "thisMonthButton";
        return "this_month";
    }
    if(theMonth == "nextMonthButton") {
        whatMonthIsIt = "nextMonthButton";
        return "next_month";
    }
}
function getWhatMonth() {
    return whatMonthIsIt;
}
// To cope with checkInDate in December, checkOut in January
// where checkOutDate int is < than checkInDate (1 < 12)
function getNumberOfDaysInMonth(theMonth) {
    var d = new Date();
    var n = d.getMonth();
    
    if(theMonth == "thisMonthButton") {
        // doNothing
    }
    if(theMonth == "nextMonthButton") {
        n = n + 1;
        if(n == 11)
            return 31;
    }

    switch (n) {
        case 0:
            return 31;
        case 1:
            return 28;
        case 2:
            return 31;
        case 3:
            return 30;
        case 4:
            return 31;
        case 5:
            return 30;
        case 6:
            return 31;
        case 7:
            return 31;
        case 8:
            return 30;
        case 9:
            return 31;
        case 10:
            return 30;
        case 11:
            return 31;
    }
}

