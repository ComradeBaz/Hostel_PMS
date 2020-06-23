/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function updateRetainDepositButtonAndCell(element) {
    // Disable the button after a press so it can't be pressed again
    document.getElementById(element.id).disabled = "true";
    
    document.getElementById(element.id).style.backgroundColor = "#dde7cb";
    document.getElementById(element.id).style.borderColor = "#8caf50";
    document.getElementById(element.id).style.color = "#000000";
    document.getElementById(element.id).value = "Added";
}


