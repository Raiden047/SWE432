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

var xhr

function solveLogic(str) // Called from HTML onkeyup
{
   if (str.length == 0) {
      document.getElementById("result").innerHTML = "";
      document.getElementById("echo").innerHTML = "No expression entered";
      return;
   }
   xhr=GetXmlHttpObject();
   if (xhr == null) {
      alert("Your browser does not support XMLHTTP!");
      return;
   }

   // Backend handler - response software component
   var url = window.location.href;
   url = url + "?Expression=" + encodeURIComponent(str);
   //url = url + "&sid=" + Math.random();
   url = url + "&CheckExpression=" + encodeURIComponent("false");
   //url = url + "&sid=" + Math.random();
   xhr.onreadystatechange = getLogicTable; // callback function when server responds
   xhr.open("POST", url, true); // An HTTP GET request to url, true=asynchronous
   xhr.send(null); // Send the request asynchronously
}

function getLogicTable() // On return, change the DOM with the response text
{
   if (xhr.readyState == 4) {
   	  var json = JSON.parse(xhr.responseText);
	  //console.log(json);
      document.getElementById("result").innerHTML = json.table;
      document.getElementById("echo").innerHTML = json.echo;
   }
}

function GetXmlHttpObject()
{
   if (window.XMLHttpRequest)
   {  // code for IE7+, Firefox, Chrome, Opera, Safari
      return new XMLHttpRequest();
   }
   if (window.ActiveXObject)
   { // code for IE6, IE5 (backwards compatibility is a pain!)
     return new ActiveXObject("Microsoft.XMLHTTP");
   }
   return null;
}

function setFocus(){
  document.getElementById("expression").focus();
}

