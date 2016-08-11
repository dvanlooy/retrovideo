<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<head>
<c:import url="/WEB-INF/JSP/head.jsp">
	<c:param name="title" value="Retrovideo: Beheer Reservaties" />
</c:import>
</head>
<body>
	<vdab:menu current='Beheer Reservaties' />
	<div class="wrapper">
		<h1>Beheer Reservaties</h1>
		<c:choose>
			<c:when test='${not empty reservaties}'>
				<c:forEach var="film" items="${gereserveerdeFilms}">
					<c:url value="/beheerreservaties.htm" var="filmid">
						<c:param name="filmid" value="${film.id}" />
					</c:url>
					<p>
						<a href="${filmid}">${film.titel}</a>
					</p>
				</c:forEach>
			</c:when>
			<c:otherwise>
			Geen reservaties te beheren!
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>