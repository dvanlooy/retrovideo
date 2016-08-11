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
			<c:when test="${empty fout}">
				<c:choose>
					<c:when
						test='${not empty gereserveerdeFilms && empty klantenMetFilmGereserveerd}'>
						<c:forEach var="film" items="${gereserveerdeFilms}">
							<c:url value="/beheerreservaties.htm" var="filmid">
								<c:param name="filmid" value="${film.id}" />
							</c:url>
							<p>
								<a href="${filmid}">${film.titel}</a>
							</p>
						</c:forEach>
					</c:when>
					<c:when
						test='${not empty geselecteerdeFilm && not empty klantenMetFilmGereserveerd}'>
						<h2>${geselecteerdeFilm.titel}&nbsp;werd&nbsp;gereserveerd&nbsp;door:</h2>
						<c:forEach var="klantReservatie" items="${klantenMetFilmGereserveerd}">
							<c:url value="/beheerreservaties.htm" var="klantid">
								<c:param name="filmid" value="${geselecteerdeFilm.id}" />
								<c:param name="klantid" value="${klantReservatie.key.id}" />
							</c:url>
							<p>
								${klantReservatie.key.voornaam}&nbsp;${klantReservatie.key.familienaam}&nbsp;op&nbsp;<fmt:formatDate type="both" dateStyle="full" timeStyle="medium" value='${klantReservatie.value}'/>:&nbsp;<a href="${klantid}">verwijder</a>
							</p>
						</c:forEach>
						<p class="gaterug">
							<a href=<c:url value="/beheerreservaties.htm"/>>Ga terug...</a>
						</p>
					</c:when>
					<c:otherwise>
						Geen reservaties te beheren!
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<div class="fout">${fout}</div>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>