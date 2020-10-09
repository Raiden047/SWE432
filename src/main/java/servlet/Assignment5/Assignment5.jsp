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
				  <li>Valid Logic Operands: ||, &&, !</li>
				  <li>Valid Variable Symbols: A/a, B/b, .... Z/z</li>
				</ul>
				<label for="expression">Enter Logic Expression</label><br>
				<textarea id="expression" name="expression" 
					placeholder="Example: (A && B) || !c ...." style="height: 100px">${expression}</textarea>
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
	<p>Fahad created the style of the entire page and the styling for the buttons, textboxs, etc. 
	He also handled the sending of the information using the form given to us. 
	Casey tested out the final product to see if anything could go wrong with it, and wrote up the 
	collaboration paragraph. He also gave some feedback if more work is going to be done with this.</p>
</body>
</html>