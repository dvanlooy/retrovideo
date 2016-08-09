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
				<c:url value='/' var='detailURL'>
					<c:param name='id' value="${genre.id}" />
				</c:url>
				<li><a href="<c:out value='${detailURL}'/>">${genre.naam}</a></li>
			</c:forEach>
		</ul>
	</nav>
</body>
</html>