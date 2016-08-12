<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<head>
<c:import url="/WEB-INF/JSP/head.jsp">
	<c:param name="title" value="Retrovideo: Rapport" />
</c:import>
</head>
<body>
	<vdab:menu current='Rapport' />
	<div class="wrapper">
		<h1>Rapport</h1>
		<c:choose>
			<c:when test="${succes == true}">
		<p>De Reservatie is OK</p>
		</c:when>
			<c:otherwise>
				<p>De Reservatie is niet volledig gelukt.</p>
				<p>Volgende films zijn niet gereserveerd:</p>
				<c:forEach var="reservatie" items="${reservaties}">
					<c:if test="${reservatie.value == false}">
		${reservatie.key.titel}
		</c:if>
				</c:forEach>
			</c:otherwise>
		</c:choose>

	</div>
	<vdab:footer />
</body>
</html>