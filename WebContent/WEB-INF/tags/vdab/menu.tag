<%@tag description='menu' pageEncoding='UTF-8'%>
<%@attribute name='current' type='java.lang.String'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<section >
	<nav id="top">
		<ul>
			<li><a href="<c:url value='/index.htm'/>">HOME</a></li>
			<li><a href="<c:url value='/mandje.htm'/>">MANDJE</a></li>
			<li class="right">U BENT HIER: ${current}</li>
		</ul>
	</nav>
</section>