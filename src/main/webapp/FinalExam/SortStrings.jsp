<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>SortStrings</title>
	<link href="${pageContext.request.contextPath}/FinalExam/styles.css" rel="stylesheet" type="text/css" />
	<script src="${pageContext.request.contextPath}/FinalExam/script.js"></script>
</head>
<body>
<p><a href="https://mason.gmu.edu/~fibrahi/">Return to Home</a></p>
<h3>Sort Strings</h3>
<h4>SWE 432</h4>
<h4>Fahad Ibrahim</h4>
	<div class="container">
		<div class="inner" style="width: 75%">
			<form method="post" action="${pageContext.request.contextPath}/SortStrings">
				<div class="innerContainer">
					<div class="inputContainer">
						<label for="inputString">Enter Strings For Sorting</label><br>
						<textarea id="inputString" name="inputString" 
							placeholder="Example Input: pants house bird &#10;...." 
							rows="20" cols="25">${inputString}</textarea>
					</div>
					<div class="params">
						<div>
							<p style="margin-bottom: 5px;">Separator:</p>
							<% 
								if(request.getAttribute("radioDelim") != null){
									out.println(request.getAttribute("radioDelim"));
								}
								else{
									%>
									<input type="radio" name="delim" value="space" checked> Space (" ")<br>
									<input type="radio" name="delim" value="comma"> Comma (",")<br>
									<input type="radio" name="delim" value="newLine"> New Line<br>
									<%
								}
							%>
							<p style="margin-bottom: 5px;">Sorting Order:</p>
							<% 
								if(request.getAttribute("radioSortOrder") != null){
									out.println(request.getAttribute("radioSortOrder"));
								}
								else{
									%>
									<input type="radio" name="sortOrder" value="Ascending" checked> Ascending<br>
									<input type="radio" name="sortOrder" value="Descending"> Descending<br>
									<%
								}
							%>
						</div><br>
						<input type="submit" value="Sort Strings">
					</div>
					<div class="inputContainer">
						<label for="sortedString">Sorted Strings</label><br>
						<textarea id="sortedString" name="sortedString" 
							placeholder="Example Output: bird house pants &#10;...." 
							rows="20" cols="25"
							readonly="readyOnly">${sortedString}</textarea>
					</div>
				</div>
				
			</form><br>
		</div>
	</div>
</body>
</html>