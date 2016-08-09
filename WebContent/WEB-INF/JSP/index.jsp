<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!doctype html>
<html lang='nl'>
<head>
<c:import url="/WEB-INF/JSP/head.jsp">
	<c:param name="title" value="Retrovideo: Reservaties" />
</c:import>
</head>
<body>
	<h1>Reservaties</h1>
	<nav>
		<ul>
			<c:forEach var='genre' items='${genres}'>
				<c:url value='/' var='genreURL'>
					<c:param name='id' value="${genre.id}" />
				</c:url>
				<li><c:choose>
						<c:when test='${param.id == genre.id.toString()}'>
							${genre.naam}
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
			<c:forEach var='film' items='${films}'>
				<c:url value='/filmdetail.htm' var='filmdetailURL'>
					<c:param name='id' value="${film.id}" />
				</c:url>
				<li><a href="<c:out value='${filmdetailURL}'/>">${film.titel}</a></li>
			</c:forEach>
		</c:otherwise>
	</c:choose>

</body>
</html>