/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * This function opens the popup to show the guest selectedon the gridView
 * And it changes the colour of the cell for the guest selected
 */
// Variables to store values from the last call to the function
var lastCallerId = "";
var lastColour = "";
var lastParentNode = "";
var lastButtonImage = "";
var lastButtonToCallToggleFormId = "";
var lastGuestReservationId = "";
var secondToLastImage = "";
var currentPopUpFormVisible = "";
var lastPopUpFormVisible = "";
var nextTd = "";
var lastTd = "";
var lastCellColour = "";
var isLastCallerHasChanged = false;
var theCellNumber = 0;
var theOriginalCallerId;

function toggleForm(element) {
    // Get the id of the button that called the function
    var currentCallerId = element.id;

    getTableRow(element.id);
    lastCellColour = document.getElementById(element.id).parentNode.style.backgroundColor;

    if (lastCallerIdHasChanged() == "TRUE") {
        lastCallerId = originalCallerId;
    }

    // User clicks the same button twice in succession, opening and closing the form
    // The first if statement is invoked on the second click
    if ((document.getElementById("guestSummaryForm").style.display === "block") && (currentCallerId === lastCallerId)) {
        document.getElementById("guestSummaryForm").style.display = "none";
        // Change the cell button colour to it's original
        document.getElementById(currentCallerId).parentNode.style.backgroundColor = lastColour;
        document.getElementById(currentCallerId).src = lastImage;

        // The user has clicked one button to open the guestSummaryForm, then extendsTheGuestCheckOutByOne to extend the guest stay
        // This action closes the open guestSummaryForm and opens updateGuestForm with the new reservation checkOutDate
        // The below else if is executed if the user extends a guest, then clicks another editButton to open another guest
        // reservation details then clicks teh same editButton to close it
    } else if ((document.getElementById("updateGuestForm").style.display === "block") && (currentCallerId === lastCallerId)) {
        // Hide the table
        document.getElementById("updateGuestForm").style.display = "none";
        // Change the cell button colour to it's original
        document.getElementById(currentCallerId).parentNode.style.backgroundColor = lastColour;
        document.getElementById(currentCallerId).src = lastImage;

        // The user opened details for one guest
        // The user clicks another editGuest button either for the same or a different user
    } else if ((document.getElementById("guestSummaryForm").style.display === "block") && (currentCallerId !== lastCallerId)) {
        ///lastColour = document.getElementById(currentCallerId).parentNode.style.backgroundColor;
        lastImage = document.getElementById(currentCallerId).src;
        ///document.getElementById(lastCallerId).parentNode.style.backgroundColor = lastColour;
        ///document.getElementById(lastCallerId).src = lastImage;
        document.getElementById(lastCallerId).parentNode.style.backgroundColor = lastColour;
        document.getElementById(lastCallerId).src = lastImage;
        //document.getElementById(currentCallerId).parentNode.style.backgroundColor = "#426B69";
        document.getElementById(currentCallerId).src = "resources\\images\\occupiedBedSelected.png";
        lastColour = document.getElementById(currentCallerId).parentNode.style.backgroundColor;

    } else if ((document.getElementById("updateGuestForm").style.display === "block") && (currentCallerId !== lastCallerId)) {
        ///lastColour = document.getElementById(currentCallerId).parentNode.style.backgroundColor;
        lastImage = document.getElementById(currentCallerId).src;
        ///document.getElementById(lastCallerId).parentNode.style.backgroundColor = lastColour;
        ///document.getElementById(lastCallerId).src = lastImage;
        document.getElementById(lastCallerId).parentNode.style.backgroundColor = lastColour;
        document.getElementById(lastCallerId).src = lastImage;
        //document.getElementById(currentCallerId).parentNode.style.backgroundColor = "#426B69";
        document.getElementById(currentCallerId).src = "resources\\images\\occupiedBedSelected.png";
        lastColour = document.getElementById(currentCallerId).parentNode.style.backgroundColor;

    } else {
        // The first time an editGuest button is clicked the below code executes
        // The element is not displayed, display it
        document.getElementById("guestSummaryForm").style.display = "block";
        // Get the default colour for the cell containing the button that called the function
        lastColour = document.getElementById(currentCallerId).parentNode.style.backgroundColor;
        lastImage = document.getElementById(currentCallerId).src;
        // Set the image for the cell that has been selected
        document.getElementById(currentCallerId).src = "resources\\images\\occupiedBedSelected.png";
        currentPopUpFormVisible = document.getElementById("guestSummaryForm");
    }


    isLastCallerHasChanged = false;
    lastButtonToCallToggleFormId = element.id;
    lastCallerId = element.id;
    theOriginalCallerId = element.id;
    theCellNumber = 0;
    originalCallerId = element.id;
    lastParentNode = document.getElementById(currentCallerId).parentNode;
    lastGuestReservationId = document.getElementById(lastCallerId).nextSibling.textContent;
}

function closeForm() {
    document.getElementById("guestSummaryForm").style.display = "none";
}
// Toggle the extras div on the editGuest.xhtml page
function toggleExtras() {

    var isDisplayed = document.getElementById("myExtrasPopup").style.display;

    if (isDisplayed !== "block")
        document.getElementById("myExtrasPopup").style.display = "block";
    else
        document.getElementById("myExtrasPopup").style.display = "none";
}

function toggleNotes() {

    var isDisplayed = document.getElementById("theNotesPopup").style.display;

    if (isDisplayed !== "block")
        document.getElementById("theNotesPopup").style.display = "block";
    else
        document.getElementById("theNotesPopup").style.display = "none";
}
// Update the view when the guest is extended
// Change the colour and image for the cell and enable the button
function toggleUpdatedGuestForm(element) {

    var table = document.getElementById('tblSqlResult');
    numberOfCells = table.rows[0].cells.length;

    var buttonForCheckOutDate = getCallerCheckOutDate(lastCallerId);
    if (buttonForCheckOutDate != -1) {
        var buttonForDayAfterCheckOutDate = getNextCellButtonId(buttonForCheckOutDate);
        formatCellForExtendGuest(buttonForDayAfterCheckOutDate);

        popUpAnUpdatedForm();
    } else {
        alert("Open \"Next Month\" to confirm the new check out date");
    }
}

function formatCellForExtendGuest(newButton) {
    // Change the icon on the button
    document.getElementById(newButton).src = "resources\\images\\occupiedBedNormal.png";
    // Change the colour of the cell
    document.getElementById(newButton).nextSibling.parentNode.style.backgroundColor = lastCellColour;
    // Enable the button
    document.getElementById(newButton).disabled = false;
}

function lastCallerIdHasChanged() {
    return isLastCallerHasChanged;
}

function getCellNumber(theString) {

    var patternForCellNumber = /[\d]+/g;
    var cellNumbers = theString.match(patternForCellNumber);
    var cellNumber = cellNumbers[3];

    return cellNumber;
}

var row = -1;
var cell = -1;
function getTableRow(buttonIdString) {
    // j_idt28:0:j_idt30:8:myEditButton
    var res = buttonIdString.split(":");
    row = res[1];
    cell = res[3];

    return row;
}
function getTableCell(buttonIdString) {
    // j_idt28:0:j_idt30:8:myEditButton
    var res = buttonIdString.split(":");
    row = res[1];
    cell = res[3];

    return cell;
}
function getCallerButtonId(buttonIdString) {
    // j_idt28:0:j_idt30:8:myEditButton
    var res = buttonIdString.split(":");
    theId = res[3];

    return theId;
}
var cellBackgroundColour;
var numberOfCells;
function moveBed(element) {

    var table = document.getElementById('tblSqlResult');
    var numberOfRows = table.rows.length;
    numberOfCells = table.rows[0].cells.length;

    var caller = getCallerButtonId(element.id);
    var newBedNumber = -1;

    // The lastCallerId will change if the user extends the guest
    // Use the originalCallerId to get the button that opened the guest details
    if (lastCallerIdHasChanged()) {
        lastCallerId = originalCallerId;
        isLastCallerHasChanged = false;
    }
    var row = parseInt(getTableRow(lastCallerId));
    var cell = parseInt(getTableCell(lastCallerId));

    var reservationId = getCellValue(lastCallerId);
    cellBackgroundColour = document.getElementById(lastCallerId).parentNode.style.backgroundColor;

    var lastDayId = getCallerCheckOutDate(lastCallerId);
    var nextAvailableBed = getNextAvailableBed(caller, row, cell, getTableCell(lastDayId), numberOfRows, reservationId);
    if ((nextAvailableBed >= 0) && (nextAvailableBed < numberOfRows - 1)) {
        doTheMove(nextAvailableBed, cell, getTableCell(lastDayId), reservationId);
        clearGuestBed(row, cell, getTableCell(lastDayId));
        popUpAnUpdatedForm();
        // Check the user is not moving the guest off the grid
    } else if (nextAvailableBed < 0) {
        alert("Move guest to a higher bed number");
    } else if (nextAvailableBed >= numberOfRows - 1) {
        alert("Move guest to a lower bed number");
    }
}
// Get the id for the button in the cell for the guest's last night
function getCallerCheckOutDate(cellCallerId) {
    //alert("1");
    var reservationId = getCellValue(cellCallerId);

    if (getValueInNextCell(cellCallerId) >= 0) {
        //alert("2");
        while (getValueInNextCell(cellCallerId) == reservationId) {
            cellCallerId = getNextCellButtonId(cellCallerId);
            //alert("3");
        }
        // If the guest was extended before they were moved the reservationId isn't set in the new days
        // Check to see if the icon has been changed - if it has, and if the reservationId is 0 
        // then the cells apply to the current guest
        var occupiedCell = /^.*occupiedBedNormal.png/;
        //var notOccupiedCell = /^.*emptyBedNormal.png/;
        while (getValueInNextCell(cellCallerId) == 0) {
            //alert(document.getElementById(getNextCellButtonId(cellCallerId)).src);
            var result = (document.getElementById(getNextCellButtonId(cellCallerId)).src).match(occupiedCell);
            //var result1 = (document.getElementById(getNextCellButtonId(cellCallerId)).src).match(notOccupiedCell);
            //if (document.getElementById(getNextCellButtonId(cellCallerId)).src == "http:\/\/localhost:8080\/HostelPMS_FlowExample_Backup\/faces\/resources\/images\/occupiedBedNormal.png") {
            //if (document.getElementById(getNextCellButtonId(cellCallerId)).src == "http:\/\/68.66.253.39:8080\/HostelPMS_FlowExample_Backup\/faces\/resources\/images\/occupiedBedNormal.png") {
            if (document.getElementById(getNextCellButtonId(cellCallerId)).src.match(occupiedCell))  {
                cellCallerId = getNextCellButtonId(cellCallerId);
                //alert("5");
            } else {
                // The image in the next cell does not indicate guest has extended - return current cellCallerId value
                //alert("6");
                return cellCallerId;
            }
        }
        //alert("7");
        //alert(getValueInNextCell(cellCallerId));
        return -1;
    }
    // If the next cell isn't available 
    return -1;
}
// Get the cellValue (reservationId) in the cell
function getCellValue(theCallerId) {
    return document.getElementById(theCallerId).parentNode.textContent;
}
// The value will be a reservationId or 0
function getValueInNextCell(currentCellButtonId) {

    if (getTableCell(currentCellButtonId) < numberOfCells - 1) {
        return document.getElementById(currentCellButtonId).parentNode.nextSibling.nextSibling.textContent;
    } else {
        // There is no next cell
        return -1;
    }
}
// Get the id of the button in the next cell 
function getNextCellButtonId(thisCellButtonId) {
    var cellIndex = parseInt(getTableCell(thisCellButtonId));
    var nextCellIndex = cellIndex + 1;
    var rowIndex = parseInt(getTableRow(thisCellButtonId));

    var nextCellButtonId = "j_idt28:" + rowIndex + ":j_idt30:" + nextCellIndex + ":myEditButton";

    return nextCellButtonId;
}
function getButtonIdValueByRowAndCell(theRow, theCell) {
    var theButton = "j_idt28:" + theRow + ":j_idt30:" + theCell + ":myEditButton";
    return getCellValue(theButton);
}
function getButtonIdByRowAndCell(theRow, theCell) {
    var theButton = "j_idt28:" + theRow + ":j_idt30:" + theCell + ":myEditButton";

    return theButton;
}
// Format the correct cells
function doTheMove(newBedNumber, firstDay, lastDay, reservationId) {
    // Find a row where the dates are available for the guest
    for (var i = firstDay; i <= lastDay; i++) {
        var newButtonId = "j_idt28:" + newBedNumber + ":j_idt30:" + i + ":myEditButton";
        document.getElementById(newButtonId).parentNode.style.backgroundColor = cellBackgroundColour;
        if (i === firstDay) {
            document.getElementById(newButtonId).src = "resources\\images\\occupiedBedSelected.png";
        } else {
            document.getElementById(newButtonId).src = "resources\\images\\occupiedBedNormal.png";
        }
        document.getElementById(newButtonId).disabled = false;

        updateInnerHTML(newButtonId, reservationId);
    }
}
function updateInnerHTML(buttonId, reservationId) {
    var cellContent = document.getElementById(buttonId).parentNode.innerHTML;
    var tagMatch = /<[^>]+>/;
    var resultMatch = cellContent.match(tagMatch);
    var newInnerHTML = resultMatch + reservationId;
    document.getElementById(buttonId).parentNode.innerHTML = newInnerHTML;
}
function clearGuestBed(row, firstDay, lastDay) {
    var newCellColour;
    if ((row == 1) || ((row % 2 == 1))) {
        newCellColour = "#d2dfb9";
    } else {
        newCellColour = "#dde7cb";
    }
    for (var i = firstDay; i <= lastDay; i++) {
        var newButtonId = "j_idt28:" + row + ":j_idt30:" + i + ":myEditButton";
        document.getElementById(newButtonId).parentNode.style.backgroundColor = newCellColour;
        document.getElementById(newButtonId).src = "resources\\images\\emptyBedNormal.png";
        document.getElementById(newButtonId).disabled = "true";

        updateInnerHTML(newButtonId, 0);
    }
}
// Check row by row in the required direction if there is a bed available
function getNextAvailableBed(caller, currentBedNumber, firstDay, lastDay, numberOfRows, reservationId) {

    if (caller == "upOneBedNumber") {
        var tempNewBedNumber = currentBedNumber + 1;
        while (tempNewBedNumber < numberOfRows - 1) {
            for (var i = firstDay; i <= lastDay; i++) {
                var newButtonId = getButtonIdByRowAndCell(tempNewBedNumber, i);
                if ((getCellValue(newButtonId) == 0) || (getCellValue(newButtonId) == reservationId)) {
                    if (i == lastDay) {
                        // Set lastCallerId to the new button Id so calls to toggleForm(element)
                        // reference the correct table cell
                        lastCallerId = getButtonIdByRowAndCell(tempNewBedNumber, firstDay);
                        originalCalerId = lastCallerId;
                        return tempNewBedNumber;
                    }
                } else if (getCellValue(newButtonId) != 0) {
                    tempNewBedNumber++;
                    break;
                }
            }
        }
        return tempNewBedNumber;
    }
    if (caller == "downOneBedNumber") {
        var tempNewBedNumber = currentBedNumber - 1;
        while (tempNewBedNumber >= 0) {
            for (var i = firstDay; i <= lastDay; i++) {
                var newButtonId = getButtonIdByRowAndCell(tempNewBedNumber, i);
                if ((getCellValue(newButtonId) == 0) || (getCellValue(newButtonId) == reservationId)) {
                    if (i == lastDay) {
                        // Set lastCallerId to the new button Id so calls to toggleForm(element)
                        // reference the correct table cell
                        lastCallerId = getButtonIdByRowAndCell(tempNewBedNumber, firstDay);
                        originalCalerId = lastCallerId;
                        return tempNewBedNumber;
                    }
                } else if (getCellValue(newButtonId) != 0) {
                    tempNewBedNumber--;
                    break;
                }
            }
        }
        return tempNewBedNumber;
    }
}
function popUpAnUpdatedForm() {

    if (document.getElementById("guestSummaryForm").style.display === "block") {
        document.getElementById("guestSummaryForm").style.display = "none";
        document.getElementById("updateGuestForm").style.display = "block";
        currentPopUpFormVisible = document.getElementById("updateGuestForm");
    } else if (document.getElementById("updateGuestForm").style.display === "block") {
        document.getElementById("updateGuestForm").style.display = "none";
        document.getElementById("guestSummaryForm").style.display = "block";
        currentPopUpFormVisible = document.getElementById("guestSummaryForm");
    }
}
function changeToPaid(element) {
    var button = document.getElementById(element.id);

    button.value = "Paid";
    button.disabled = true;
}
function toggleUpdatedCheckOutsForm() {
    if (document.getElementById("checkingOutTodayForm").style.display === "block") {
        document.getElementById("checkingOutTodayForm").style.display = "none";
        document.getElementById("updateGuestForm").style.display = "block";
    } else if (document.getElementById("updateGuestForm").style.display === "block") {
        document.getElementById("updateGuestForm").style.display = "none";
        document.getElementById("checkingOutTodayForm").style.display = "block";
    }
}
function manageExtrasBillNotesPopup() {

    if (document.getElementById("theBillPopup").style.display === "block") {
        document.getElementById("theBillPopup").style.display = "none";
        document.getElementById("updateGuestForm").style.display = "block";

    } else if (document.getElementById("updateGuestForm").style.display === "block") {
        document.getElementById("updateGuestForm").style.display = "none";
        document.getElementById("guestSummaryForm").style.display = "block";
        currentPopUpFormVisible = document.getElementById("guestSummaryForm");
    }
}
function togglePopup(thePopup) {

    var isDisplayed = document.getElementById(thePopup).style.display;
    closePopups();
    if (isDisplayed !== "block") {
        document.getElementById(thePopup).style.display = "block";
    } else
        document.getElementById(thePopup).style.display = "none";
}
function closePopups() {
    document.getElementById("theBillPopup").style.display = "none";
    document.getElementById("theNotesPopup").style.display = "none";
    document.getElementById("myExtrasPopup").style.display = "none";
}

function closeBillPopUp() {
    document.getElementById("theBillPopup").style.display = "none";
}

function closeExtrasPopUp() {
    document.getElementById("myExtrasPopup").style.display = "none";
}

function closeNotesPopUp() {
    document.getElementById("theNotesPopup").style.display = "none";
}