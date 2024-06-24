<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<% String ctxPath = request.getContextPath(); %>
<!DOCTYPE html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Home</title>
</head>
<body>
	<h1>
		MYPATH : <%=ctxPath %>
	</h1>
	<img src="<%= ctxPath%>/resources/images/advertisement_01.png">
</body>
</html>