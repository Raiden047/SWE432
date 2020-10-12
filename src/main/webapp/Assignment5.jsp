<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Fahad Ibrahim</title>
	<link href="${pageContext.request.contextPath}/styles.css" rel="stylesheet" type="text/css" />
	<script src="${pageContext.request.contextPath}/script.js"></script>
</head>
<body>
<p><a href="https://mason.gmu.edu/~fibrahi/">Return to Home</a></p>
<h3>Logic Calculator</h3>
<h4>SWE 432</h4>
<h4>Collaborators: Fahad Ibrahim, Casey Haley</h4>
	<div class="container">
		<div class="inner" style="width: 50%">
			<form method="post" action="${pageContext.request.contextPath}/LogicTable">
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
				  <li>Valid Variable Symbols: A, B, C, .... Z</li>
				</ul>
				<label for="expression">Enter Logic Expression</label><br>
				<textarea id="expression" name="expression" 
					placeholder="Example: (A AND B) || !C ...." style="height: 100px">${expression}</textarea>
				<input type="submit" value="Create Truth Table">

			</form><br>
			<div class="dom">
				<button onclick="clearTextArea()">Clear</button>
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
		
	</div>
	
	<h5>Collaboration Summary:</h5>
	<p>Fahad fixed up a few things with the interface while also moving the old code to the new java GET/POST code. 
		He also handled the sending of the information using a servlet.  
		Casey handled the backend computation, testing the web page and writting the collaborative summary. 
		Fahad then looked over the backend to make it better..</p>
</body>
</html>