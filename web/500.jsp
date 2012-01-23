<%@ page isErrorPage="true" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix='ww' uri='webwork' %>
<html>
<head>
	<title>Erro interno do servidor</title>

	<% if (request.getAttribute("javax.servlet.error.status_code") != null && request.getAttribute("javax.servlet.error.status_code").toString().equals("500")) { %>
		<meta http-equiv="refresh" content="0;url=<ww:url includeParams="none" value="/500.jsp"/>" />
	<% } %>
</head>
<body bgcolor=white>
<center>
<font face=verdana size=5 color=red>
	<p>
	ERRO 500 - ERRO INTERNO DO SERVIDOR
	<font face=verdana size=2 color=black>
	<br>
	<br>A requisição não pode ser completada.
	<br>Dúvidas entre em contato com 
	<a href="mailto:suporte.rh@grupofortes.com.br">suporte.rh@grupofortes.com.br</a>
	</p>
</font>
</center>

</body>
</html>