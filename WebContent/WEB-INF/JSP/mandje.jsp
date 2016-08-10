<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<head>
<c:import url="/WEB-INF/JSP/head.jsp">
	<c:param name="title" value="Retrovideo: Mandje" />
</c:import>
</head>
<body>
	<vdab:menu current='Mandje' />
	<div class="wrapper">
		<h1>Mandje</h1>
		<c:choose>
			<c:when test='${not empty FilmsInMandje}'>
				<form action="" method="post">
					<table>
						<thead>
							<tr>
								<th>Film</th>
								<th>Prijs</th>
								<th><input type="submit" value="Verwijderen" class="verwijderenknop"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="film" items="${FilmsInMandje}">
								<tr>
									<td>${film.titel}</td>
									<td>&euro; ${film.prijs}</td>
									<td class="center"><input type='checkbox' name='remove'
										value='${film.id}'></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</form>
			</c:when>
			<c:otherwise>
			<img alt="Leeg Mandje" src="<c:url value='/images/leegmandje.png'/>">
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>