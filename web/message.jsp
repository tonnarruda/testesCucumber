<%@ taglib prefix='ww' uri='webwork' %>
<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
	<%@page contentType="text/html"%>
	<%@page pageEncoding="UTF-8"%>
<html>
<head>
	<meta name="decorator" content="app">
	<ww:head />
	<title>Mensagem</title>
</head>
<body>
	<ww:actionmessage />

	<button onclick="history.go(-1)" class="btnVoltar"></button>
</body>
</html>