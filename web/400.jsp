<%@ page isErrorPage="true" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix='ww' uri='webwork' %>
<html>
<head>
	<title>Solicitação com erro</title>
	
	<% if (request.getAttribute("javax.servlet.error.status_code") != null && request.getAttribute("javax.servlet.error.status_code").toString().equals("400")) { %>
		<meta http-equiv="refresh" content="0;url=<ww:url includeParams="none" value="/400.jsp"/>" />
	<% } %>
</head>
<body bgcolor=white>
<center>
<font face=verdana size=2 color=black>
	<p>
	ERRO 400 - SOLICITAÇÃO COM ERRO
	<font face=verdana size=2 color=black>
	<br>
	<br>Dúvidas entre em contato com 
	<a href="mailto:suporte.rh@grupofortes.com.br">suporte.rh@grupofortes.com.br</a>
	</p>
</font>
</center>

</body>
</html>