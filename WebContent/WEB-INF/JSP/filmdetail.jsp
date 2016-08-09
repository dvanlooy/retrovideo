<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<head>
<c:import url="/WEB-INF/JSP/head.jsp">
	<c:param name="title" value="Retrovideo: Reservaties" />
</c:import>
</head>
<body>
	<script>
		document.getElementById("form").onsubmit = function() {
			if (!navigator.cookieEnabled) {
				alert("Dit werkt enkel met cookies enabled");
				return false;
			}
			document.getElementById("knopinmandje").disabled = true;
		};
	</script>
	<vdab:menu current='Detail Film'/>
	<div class="wrapper">
		<c:choose>
			<c:when test='${not empty fout}'>
				<div class='fout'>${fout}</div>
			</c:when>
			<c:otherwise>



				<h1>${film.titel}</h1>
				<img src=<c:url value='/images/${film.id}.jpg'/> alt='${film.titel}'
					title='${film.titel}: ${beschikbaarheid}'>
				<dl>
					<dt>Prijs</dt>
					<dd>€ ${film.prijs}</dd>
					<dt>Voorraad</dt>
					<dd>${film.voorraad}</dd>
					<dt>Gereserveerd</dt>
					<dd>${film.gereserveerd}</dd>
					<dt>Beschikbaar</dt>
					<dd>${beschikbaar}</dd>
				</dl>
				<form method="post" action="<c:url value="/mandje.htm" />" id="form">
					<input name="id" value="${film.id}" hidden="true" /> <input
						type="submit" value="In mandje" name="inmandje" id="knopinmandje"
						<c:if test="${beschikbaar <= 0}">disabled="true"</c:if> />
				</form>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>