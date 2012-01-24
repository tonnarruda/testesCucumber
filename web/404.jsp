<%@ page isErrorPage="true" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix='ww' uri='webwork' %>
<html>
<head>
	<title>Página não encontrada</title>
	
	<% if (request.getAttribute("javax.servlet.error.status_code") != null && request.getAttribute("javax.servlet.error.status_code").toString().equals("404")) { %>
		<meta http-equiv="refresh" content="0;url=<ww:url includeParams="none" value="/404.jsp"/>" />
	<% } %>
</head>
<body bgcolor=white>
<center>
<font face=verdana size=2 color=black>
	<p>
	ERRO 404 - PÁGINA NÃO ENCONTRADA
	<font face=verdana size=2 color=black>
	<br>
	<br>Verifique o endereço digitado.
	<br>Dúvidas entre em contato com 
	<a href="mailto:suporte.rh@grupofortes.com.br">suporte.rh@grupofortes.com.br</a>
	</p>
</font>
</center>

</body>
</html>