<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<head>
<c:import url="/WEB-INF/JSP/head.jsp">
	<c:param name="title" value="Klant" />
</c:import>
</head>
<body>
	<vdab:menu current='Klant' />
	<div class="wrapper">
		<h1>Klant</h1>
		<form method="get" action="">
			<label>Familienaam bevat:</label><br> <input type="text"
				name="zoekopdracht" value="${param.zoekopdracht}" autofocus /> <input
				type="submit" value="Zoeken" /><span class="fout">${fout}</span>
		</form>
		<br>
		<c:if test='${not empty klanten}'>
			<table>
				<thead>
					<tr>
						<th>Naam</th>
						<th>Straat - Huisnummer</th>
						<th>Postcode</th>
						<th>Gemeente</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="klant" items="${klanten}">
						<c:url value="/bevestigen.htm" var="bevestigen">
							<c:param name="id" value="${klant.id}" />
						</c:url>
						<tr>
							<td><a href="${bevestigen}">${klant.voornaam}&nbsp;${klant.familienaam}</a></td>
							<td>${klant.straatNummer}</td>
							<td>${klant.postcode}</td>
							<td>${klant.gemeente}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<c:if test='${not empty param and empty fout and empty klanten}'>
			<div class='fout'>Geen klanten gevonden</div>
		</c:if>
	</div>
</body>
</html>