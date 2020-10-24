<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Assignment 7</title>
	<link href="${pageContext.request.contextPath}/Assignment7/styles.css" rel="stylesheet" type="text/css" />
	<script src="${pageContext.request.contextPath}/Assignment7/script.js"></script>
</head>
<body>
<p><a href="https://mason.gmu.edu/~fibrahi/">Return to Home</a></p>
<h3>Logic Calculator</h3>
<h4>SWE 432</h4>
<h4>Fahad Ibrahim</h4>
	<div class="container">
		<div class="push"></div>
		
		<div class="inner" style="width: 50%">
			<form method="post" action="${pageContext.request.contextPath}/LogicTable2">
				<div>Instructions: </div>
				<ul>
				  <li>Valid Logic Operands:
				  	<ul>
						<li>AND / && / &</li>
						<li>OR  / || / |</li>
						<li>XOR / ^ / !=</li>
						<li>NOT -> ! (only symbol not word)</li>
					</ul>
				  </li>
				  <li>Valid Variable Symbols: A, B, C.... Z</li>
				</ul>
				<label for="expression">Enter Logic Expression</label><br>
				<textarea id="expression" name="expression" 
					placeholder="Example: A AND (B || !C) ...." style="height: 100px">${expression}</textarea>
				<input type="submit" value="Create Truth Table"><br>
				<input type="submit" value="Clear History" name="clearHistory" style="margin-top: 10px">

			</form><br>
			<div class="dom">
				<button onclick="clearTextArea()">Clear Text Field</button>
				<button onclick="resetBoxSize()">Reset Textbox Size</button>
			</div>
			<div style="text-align: center;">
			<pre>${empty echo ? 'No expression entered' : echo}</pre>
			</div>
			<% 
				if(request.getAttribute("table") != null && request.getAttribute("expression") != ""){
					out.println(request.getAttribute("table"));
				}
			%>
		</div>
			
		<div class="historyBox">
			<table id="tableID" style="width: 75%;">
				<tr>
					<th>History</th>
				</tr>
				<%
					if (request.getAttribute("history") != null) {
						out.println(request.getAttribute("history"));
					}
				%>
			</table>
		</div>
	</div>
</body>
</html>