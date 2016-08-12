<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<head>
<c:import url="/WEB-INF/JSP/head.jsp">
	<c:param name="title" value="Retrovideo: Reservaties" />
</c:import>
</head>
<body>
	<vdab:menu current='Grijze zone'/>
	<div class="center">
		<img src="<c:url value='/images/404-gentlemen.jpg'/>" alt="404-ERROR">
	</div>
	<vdab:footer/>
</body>
</html>