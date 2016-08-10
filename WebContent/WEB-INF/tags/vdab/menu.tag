<%@tag description='menu' pageEncoding='UTF-8'%>
<%@attribute name='current' required='true' type='java.lang.String'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<section >
	<nav id="top">
		<ul>
			<li><a href="<c:url value='/index.htm'/>">HOME</a></li>
			<c:choose>
			<c:when test='${current == "Mandje"}'>
			<li><a href="<c:url value='/klant.htm'/>">KLANT</a></li>
			</c:when>
			<c:when test='${current == "Klant" or current == "Overzicht Genres"}'>
			<li><a href="<c:url value='/mandje.htm'/>">MANDJE</a></li>
			</c:when>
			</c:choose>
			<li class="right">U BENT HIER: ${current}</li>
		</ul>
	</nav>
</section>