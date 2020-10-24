/**
 * 
 */
 
window.onload = function() {
	var table = document.getElementById("tableID");
	if (table != null) {
	    for (var i = 1; i < table.rows.length; i++) {
	        for (var j = 0; j < table.rows[i].cells.length; j++)
	        table.rows[i].cells[j].onclick = function () {
	            document.getElementById("expression").value = this.textContent;
	        };
	    }
	}
}
 
 function clearTextArea() {
    document.getElementById("expression").value = "";
}

function resetBoxSize() {
    document.getElementById("expression").style.height = "100px";
}