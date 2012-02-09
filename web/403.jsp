<%@ page isErrorPage="true" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix='ww' uri='webwork' %>
<html>
<head>
	<title>Acesso Negado</title>

	<style type="text/css">
		@import url('<ww:url includeParams="none" value="/css/default.css"/>');
		@import url('<ww:url includeParams="none" value="/css/fortes.css"/>');
		@import url('<ww:url includeParams="none" value="/css/botoes.css"/>');
		@import url('<ww:url includeParams="none" value="/css/menu.css"/>');
	</style>
</head>

<body>
	<div id="topDiv">
		<div id="userDiv">
			<span class="saudacao">
				&nbsp;
			</span>
			<span class="nomeUsuario">&nbsp;</span>
			<span class="nomeEmpresa">&nbsp;</span>
			<br />
			<span class="nomeEmpresa">&nbsp;&nbsp;</span>
		</div>
		<div id="userDiv1">
			<img src='<ww:url includeParams="none" value="/imgs/topo_img_right.jpg"/>' border='0' align='absMiddle' />
		</div>
		<div id="logoDiv"><a href="<ww:url value='/'/>"><img src='<ww:url includeParams="none" value="/imgs/topo_ico.jpg"/>' border='0'/></a></div>
	</div>
		
	<ul id="menuDropDown"></ul>
	
	<div style="clear: both"></div>
	<div id="waDiv">
		<br />
		<div id="waDivTitulo">Acesso Negado</div>
		
		<div class="waDivFormulario">
			<center>
				<font face=verdana size=2 color=black>
					<p>
					ERRO 403 - ACESSO NEGADO
					<font face=verdana size=2 color=black>
					<br>
					<br>Você não tem autorização para acessar esta página.
					<br>Dúvidas entre em contato com 
					<a href="mailto:suporte.rh@grupofortes.com.br">suporte.rh@grupofortes.com.br</a>
					</p>
				</font>
			
				<a href="<ww:url value='/'/>" style="color:blue;font-size:13px !important;">Voltar</a>
			</center>
		</div>
		
		<br /><br />
	</div>
</body>
</html>