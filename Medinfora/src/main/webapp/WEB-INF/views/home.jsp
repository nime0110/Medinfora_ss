<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<% String ctxPath = request.getContextPath(); %>
<html>
<head>
	<title>Home</title>
</head>
<body>
	<h1>
		Hello world!  
	</h1>
	<img src="<%= ctxPath%>/resources/images/advertisement_02.png">
</body>
</html>