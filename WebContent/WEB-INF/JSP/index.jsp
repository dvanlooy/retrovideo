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
<vdab:menu current='Overzicht Genres'/>
<div class="wrapper">
	<h1>Reservaties</h1>
	<nav class='genres'>
		<ul>
			<c:forEach var='genre' items='${genres}'>
				<c:url value='/index.htm' var='genreURL'>
					<c:param name='id' value="${genre.id}" />
				</c:url>
				<li><c:choose>
						<c:when test='${param.id == genre.id.toString()}'>
							<span class="activemenuitem">${genre.naam}</span>
						</c:when>
						<c:otherwise>
							<a href="<c:out value='${genreURL}'/>">${genre.naam}</a>

						</c:otherwise>
					</c:choose></li>
			</c:forEach>
		</ul>
	</nav>
	<c:choose>
		<c:when test='${not empty fout}'>
			<div class='fout'>${fout}</div>
		</c:when>
		<c:when test="${empty films && not empty param.id}">
			<div class='fout'>Geen films gevonden</div>
		</c:when>
		<c:otherwise>
			<nav>
				<ul>
					<c:forEach var='film' items='${films}'>
						<c:choose>
							<c:when test='${film.gereserveerd < film.voorraad}'>
								<c:set var='beschikbaarheid' value="reservatie mogelijk" />
							</c:when>
							<c:when test='${film.gereserveerd == film.voorraad}'>
								<c:set var='beschikbaarheid' value="reservatie niet mogelijk" />
							</c:when>
						</c:choose>
						<c:url value='/filmdetail.htm' var='filmdetailURL'>
							<c:param name='id' value="${film.id}" />
						</c:url>
						<li><a href="<c:out value='${filmdetailURL}'/>"> <img
								src=<c:url value='/images/${film.id}.jpg'/> alt='${film.titel}'
								title='${film.titel}: ${beschikbaarheid}'>
						</a></li>
					</c:forEach>
				</ul>
			</nav>
		</c:otherwise>
	</c:choose>
</div>
	<vdab:footer />
</body>
</html>