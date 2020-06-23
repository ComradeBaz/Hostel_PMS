/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// Function cycles throught the cells of a table by row 
var lastReservationId = -1;
//var myColours = ["#F2CDBD", "#EDD2DA", "#CDD8D4", "#D7DFD4", 
//"#D7C2A8", "#D4D7A8", "#D4DAF9"];
var myColours = ["#CDD8D4", "#D7DFD4", "#D4DAF9"];
var currentColour = myColours[getNextColour()];
var index = 0;

function formatButtons() {

    var myVar;
    myVar = setTimeout(showPage, 400);

    var table = document.getElementById('tblSqlResult');

    // Change the -1 in the heading to 
    document.getElementById('tblSqlResult').rows[0].cells[0].style.font = "italic small-caps bold 0px Annie Use Your Telescope";

    // matches the value in the cell appended to the end of the button in it
    var reservationIdPattern = /\s+\d+/;
    // find the name of the button in the current cell
    //buttonIdPattern = /j_idt\d*:j_idt\d*:\d*:j_idt\d*:\d*:myEditButton/;
    //buttonIdPattern = /gridForm:j_idt\d*:\d*:j_idt\d*:\d*:myEditButton/;
    buttonIdPattern = /j_idt\d*:\d*:j_idt\d*:\d*:myEditButton/;
    var i;
    var j;
    var colourNumber = 0;
    var colourNumberString = "";
    var lastColour;
    // Max number of days in a month
    // Used to avoid searching past the end of a row
    var maxJ = table.rows[0].cells.length - 1;
    var index = 0;
    var length = myColours.length;

    for (var i = 0, row; row = table.rows[i]; i++) {
        for (var j = 0, col; col = row.cells[j]; j++) {

            // Change the colour of todays date to highlight it on the table
            var whatMonthIsIt = document.getElementById("updatedGuest:whatMonthIsIt").value;
            if (whatMonthIsIt == "this_month") {
                table.rows[0].cells[new Date().getDate()].style.color = "red";
            }

            var result = table.rows[i].cells[j].innerHTML;

            table.rows[0].cells[0].innerHTML.src = "resources\\images\\occupiedBedNormal.png";

            // Update the first column with int values that represent the bed numbers
            // j_idt10:j_idt15:0:j_idt17:0:myEditButton is the id for the button in first cell
            // j_idt11:j_idt17:0:j_idt19:1:myEditButton
            //var bedNumberCellPattern = /j_idt\d*:j_idt\d*:\d*:j_idt\d*:[0]:myEditButton/;
            var bedNumberCellPattern = /j_idt\d*:\d*:j_idt\d*:[0]:myEditButton/;

            var bedNumberCellId = result.match(bedNumberCellPattern);


            if (bedNumberCellId != null) {
                // Disable the button in the cell
                document.getElementById(bedNumberCellId).disabled = true;
                var theCell = document.getElementById(bedNumberCellId);
                document.getElementById(bedNumberCellId).parentNode.removeChild(theCell);
                // Continue to the next cell
                continue;
            }

            var theMatches = result.match(reservationIdPattern);


            if (theMatches > 0) {
                // There is a reservationId set for the cell
                // Find the button in the current cell
                var nameButtonId = result.match(buttonIdPattern);


                // Change the image on the button
                if (nameButtonId != null) {
                    document.getElementById(nameButtonId).src = "resources\\images\\occupiedBedNormal.png";
                    //alert(document.getElementById(nameButtonId).src);

                    // Get the reservationId in the cell
                    if (j < maxJ)
                        var reservationId = document.getElementById(nameButtonId).nextSibling.textContent;

                    //if(col = row.cells[j])
                    if (j < maxJ)
                        nextTd = document.getElementById(nameButtonId).parentNode.nextSibling;
                    for (; ; ) {
                        if (nextTd.tagName != "TD") {
                            nextTd = nextTd.nextSibling;
                        }
                        break;
                    }
                    // Get the colour to use for the cells

                    var nextReservationId = nextTd.textContent;

                    if ((reservationId > 0) && (reservationId == lastReservationId)) {
                        document.getElementById(nameButtonId).parentNode.style.backgroundColor = currentColour;
                        lastColour = currentColour;
                    } else if ((reservationId > 0) && (reservationId != lastReservationId)) {
                        // Get a new colour
                        currentColour = myColours[getNextColour()];
                        //if (currentColour == lastColour) {
                        //currentColour = myColours[getNextColour()];
                        //}
                        document.getElementById(nameButtonId).parentNode.style.backgroundColor = currentColour;
                        lastColour = currentColour;
                    }

                    lastReservationId = reservationId;
                }
            }
            // === gives no results
            if (theMatches == 0) {
                // Apply empty bed icon if there is no reservationID associated with the cell
                var emptyButtonId = result.match(buttonIdPattern);
                document.getElementById(emptyButtonId).src = "resources\\images\\emptyBedNormal.png";
                // Disable the button
                document.getElementById(emptyButtonId).disabled = true;
            }
            // Cell value is less than 0 and editable with an icon if a guest has temporarily been
            // added to the cell with -reservationId to indicate they are checking out today
            // The user can click the button and extend or clear the alert
            // This feature may only be available on the first of the month
            if (theMatches < 0) {
                // Apply empty bed icon if there is no reservationID associated with the cell
                var labelGoesHereId = result.match(buttonIdPattern);
                document.getElementById(labelGoesHereId).src = "resources\\images\\roomIcon25px.png";
                // Disable the button
                //document.getElementById(emptyButtonId).disabled = true;
            }
        }
    }
}
function showPage() {
    //document.getElementById("loader").style.display = "none";
    document.getElementById("theTable").style.display = "block";
}
function getRandomIndex() {

    var myColoursRef = ["#F2CDBD", "#EDD2DA", "#CDD8D4", "#D7DFD4",
        "#D7C2A8", "#D4D7A8", "#D4DAF9"];

    var randomIndex = Math.floor(Math.random() * 8);
    while (randomIndex === lastIndex) {
        randomIndex = Math.floor(Math.random() * 8);
    }
    var lastIndex = randomIndex;

    return randomIndex;
}
function getNextColour() {
    if (index < myColours.length - 1)
        return index++;
    else
        return 0;
}

