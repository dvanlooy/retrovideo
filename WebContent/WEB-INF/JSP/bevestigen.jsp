<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<head>
<c:import url="/WEB-INF/JSP/head.jsp">
	<c:param name="title" value="Retrovideo: Bevestigen" />
</c:import>
</head>
<body>
	<vdab:menu current='Bevestigen' />
	<div class="wrapper">
		<h1>Bevestigen</h1>
		<c:choose>
			<c:when test="${empty fout}">
				<c:choose>
					<c:when test="${aantal > 1}">
						<c:set var="film_woord" value="films" />
					</c:when>
					<c:otherwise>
						<c:set var="film_woord" value="film" />
					</c:otherwise>
				</c:choose>
				<p>${aantal}&nbsp;${film_woord}&nbsp;voor
					${klant.voornaam}&nbsp;${klant.familienaam}</p>
				<form method="post" action="<c:url value="/rapport.htm"/>">
					<input type="submit" value="Bevestigen">
				</form>
			</c:when>
		</c:choose>
		<c:choose>
			<c:when test="${not empty fout}">
				<div class="fout">${fout}</div>
			</c:when>
		</c:choose>

	</div>
	<vdab:footer />
</body>
</html>