/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * ClearTextBox() clears the textBox on the addExtras page
 * ClearTextBoxRooms() clears it on the addRooms page
 */

function clearTextBoxRooms() {
    document.getElementById("j_idt9:noOfBeds").value = "";
    document.getElementById("j_idt9:rate").value = "";
}

function clearTextBoxAddExtra() {
    document.getElementById("j_idt9:extraAmount").value = "";
}

function clearTextBoxSettings() {
    document.getElementById("noOfBeds").value = "";
    document.getElementById("rate").value = "";
    document.getElementById("conversionRate").value = "";
    document.getElementById("extraAmount").value = "";
    document.getElementById("newConversionRate").value = "";
    if (document.getElementById("newNumberOfBeds").value == 0)
        document.getElementById("newNumberOfBeds").value = "";
    if (document.getElementById("newRoomRate").value == 0.00)
        document.getElementById("newRoomRate").value = "";
}

function clearTextBoxSaveGuest() {
    document.getElementById("firstName").value = "";
    document.getElementById("lastName").value = "";
    document.getElementById("idNumber").value = "";
    document.getElementById("country").value = "";    
}

function clearTextBoxLogin() {
    document.getElementById("userName").value = "";
    document.getElementById("password").value = "";
}

function clearTextBoxSignUp() {
    document.getElementById("email").value = "";
    document.getElementById("password").value = "";
}