<%@tag description='menu' pageEncoding='UTF-8'%>
<%@attribute name='current' required='true' type='java.lang.String'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<section>
	<nav id="top">
		<ul>
			<li><a href="<c:url value='/index.htm'/>">RESERVEREN</a></li>
			<c:if test='${current != "Klant"}'>
				<li><a href="<c:url value='/klant.htm'/>">KLANT</a></li>
			</c:if>
			<c:if test='${current != "Mandje"}'>
				<li><a href="<c:url value='/mandje.htm'/>">MANDJE</a></li>
			</c:if>
						<c:if test='${current != "Beheer Reservaties"}'>
				<li><a href="<c:url value='/beheerreservaties.htm'/>">BEHEER RESERVATIES</a></li>
			</c:if>
			<li class="right">U BENT HIER: ${current}</li>
		</ul>
	</nav>
</section>