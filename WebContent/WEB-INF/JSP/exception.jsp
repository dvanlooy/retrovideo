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
	<vdab:menu current='Grijze zone' />
	<div id="exception">
		<div id="exception_left">
			<h2>Something went terribly wrong...</h2>
			<p>You must be thinking: "Oops! I did it again!". But alack and alas... we take all the blame.</p>
			<p>A team of highly trained monkeys has been commandeered to deal with this troublesome situation.</p>
			<p>Our Chief Executive Monkey wishes to express his sincerest apologies to you.</p>
			<p> 
			Please send an email to ${mail} with a short series of events that triggered this unfortunate error message.
			If you mention your home address, one of our monkeys will be happy to send you a bunch of bananas as a token of our appreciation.
			</p>
		</div>
		<div id="exception_right">
			<img src="<c:url value='/images/exception.jpg'/>" alt="ERROR">
		</div>

	</div>





	<vdab:footer />
</body>
</html>