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
		<c:if test="${empty fout}">
		<c:choose>
			<c:when test='${not empty reservaties && empty klantenMetFilmGereserveerd}'>
				<c:forEach var="film" items="${gereserveerdeFilms}">
					<c:url value="/beheerreservaties.htm" var="filmid">
						<c:param name="filmid" value="${film.id}" />
					</c:url>
					<p>
						<a href="${filmid}">${film.titel}</a>
					</p>
				</c:forEach>
			</c:when>
			<c:when test='${not empty reservaties && not empty klantenMetFilmGereserveerd}'>
			<h2>${geselecteerdeFilm.titel}&nbsp;werd gereserveerd door:</h2>
			<c:forEach var="klant" items="${klantenMetFilmGereserveerd}">
					<c:url value="/beheerreservaties.htm" var="klantid">
						<c:param name="filmid" value="${geselecteerdeFilm.id}" />
						<c:param name="klantid" value="${klant.id}" />
					</c:url>
					<p>
						<a href="${klantid}">${klant.voornaam}&nbsp;${klant.familienaam}</a>
					</p>
				</c:forEach>
				<p class="gaterug"><a href=<c:url value="/beheerreservaties.htm"/>>Ga terug...</a></p>
			</c:when>
			<c:otherwise>
			Geen reservaties te beheren!
			</c:otherwise>
		</c:choose>
		</c:if>
	</div>
</body>
</html>