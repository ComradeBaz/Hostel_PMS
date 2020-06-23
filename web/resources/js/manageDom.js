/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var displayDeposits = false;
var displayExtras = false;
var displayOtherReservations = false;
var displayNotes = false;
// When the user clicks on div, open the popup
function formatTable() {

    //var depositTableLength = document.getElementById("showGuestForm:depositsTable").rows.length;
    //var extrasTableLength = document.getElementById("showGuestForm:extrasTable").rows.length;
    var otherReservationsTableLength = document.getElementById("showGuestForm:otherReservations").rows.length;
    var depositChild = document.getElementById("showGuestDeposits");
    var depositParent = depositChild.parentNode;
    var extraChild = document.getElementById("showGuestExtras");
    var extraParent = extraChild.parentNode;
    var otherChild = document.getElementById("showGuestOtherReservations");
    var otherParent = extraChild.parentNode;
    var noteChild = document.getElementById("noteTextArea");
    var noteParent = noteChild.parentNode;
    var noteText = noteChild.textContent.trim();
    var noteButtonPressed = document.getElementById("showGuestForm:notesHeadingButton");
    var noteParentOfButton = noteButtonPressed.parentNode;
    var otherButtonPressed = document.getElementById("showGuestForm:otherReservationsHeadingButton");
    var otherParentOfButton = otherButtonPressed.parentNode;
    var depositTable = document.getElementById("showGuestForm:depositsTable");
    var extrasTable = document.getElementById("showGuestForm:extrasTable");
    var otherReservationsTable = document.getElementById("showGuestForm:otherReservations");

    if (depositTable.rows[0].cells[0].innerHTML == "") {
        var newText = "No Deposits";
        removeTheChild(depositParent, depositChild);
        changeTextOnButton("showGuestForm:depositHeadingButton", newText);
    } else {
        displayDeposits = true;
    }

    if (extrasTable.rows[0].cells[0].innerHTML == "") {
        var newText = "No Extras";
        removeTheChild(extraParent, extraChild);
        changeTextOnButton("showGuestForm:extrasHeadingButton", newText);
    } else {
        displayExtras = true;
    }

    if (otherReservationsTable.rows[0].cells[0].innerHTML == "Room") {
        var newText = "No Other Rooms";
        removeTheChild(otherParent, otherChild);
        changeTextOnButton("showGuestForm:otherReservationsHeadingButton", newText);
        removeTheChild(otherParentOfButton, otherButtonPressed);
    } else {
        //alert(otherReservationsTable.rows[0].cells[0].innerHTML);
        displayOtherReservations = true;
    }
    if (noteText === "") {
        var newText = "No Notes";
        removeTheChild(noteParent, noteChild);
        changeTextOnButton("showGuestForm:notesHeadingButton", newText);
        removeTheChild(noteParentOfButton, noteButtonPressed);
    } else {
        displayNotes = true;
    }

    initialiseAccordionDisplay();
}

function formatSaveCheckInPage() {
// if the status is 0 the guest is saved not checked in 
// id "save" is to update a guest not already checked in (status = 0)
// id "checkInGuest" updates a guest who is not checked in
// id "saveChanges" updates a guest who is checked in (status = 1)
    var guestStatus = document.getElementById("hiddenGuestStatus").value;
    // If the guest has been saved but not checked in
    if (guestStatus == 0) {
        var child = document.getElementById("saveChanges");
        var parent = child.parentNode;
        removeTheChild(parent, child);
        clearTextBoxes();
        child = document.getElementById("cancelToDashboard");
        parent = child.parentNode;
        removeTheChild(parent, child);
    }
// If the guest is checked in
    if (guestStatus == 1) {
        var child = document.getElementById("save");
        var parent = child.parentNode;
        removeTheChild(parent, child);
        child = document.getElementById("checkInGuest");
        parent = child.parentNode;
        removeTheChild(parent, child);
        child = document.getElementById("deleteGuest");
        parent = child.parentNode;
        removeTheChild(parent, child);
        child = document.getElementById("cancelToPendingCheckins");
        parent = child.parentNode;
        removeTheChild(parent, child);
    }
// If the guest has been checked out
    if (guestStatus == 2) {
        var child = document.getElementById("save");
        var parent = child.parentNode;
        removeTheChild(parent, child);
        child = document.getElementById("checkInGuest");
        parent = child.parentNode;
        removeTheChild(parent, child);
        var child = document.getElementById("saveChanges");
        var parent = child.parentNode;
        removeTheChild(parent, child);
    }
    setTimeout(showPage("checkInGuestForm"), 400);
}

function removeTheChild(theParent, theChild) {
    theParent.removeChild(theChild);
}

function changeTextOnButton(buttonId, newText) {
    document.getElementById(buttonId).value = newText;
}
function initialiseAccordionDisplay() {

    var acc = document.getElementsByClassName("accordionControllers");
    var i;
    for (i = 0; i < acc.length; i++) {
        acc[i].addEventListener("click", function () {

            var panel = this.nextElementSibling;
            if (panel.style.maxHeight) {
                panel.style.maxHeight = null;
            } else {
                panel.style.maxHeight = panel.scrollHeight + "px";
            }
        });
    }
}

function showPage(elementId) {
    document.getElementById(elementId).style.display = "block";
}

function clearTextBoxes() {

    if (document.getElementById("rate").value == 0.00)
        document.getElementById("rate").value = "";
    if (document.getElementById("payBillForm:amountPaid").value == 0.00)
        document.getElementById("payBillForm:amountPaid").value = "";
    if (document.getElementById("discount").value == 0.00)
        document.getElementById("discount").value = "";
}