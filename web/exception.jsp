<%@ page isErrorPage="true"%>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix='ww' uri='webwork' %>
<%@ taglib prefix='authz' uri='http://acegisecurity.org/authz' %>
<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>	
	<meta name="decorator" content="app">
	<ww:head />
	<title>Erro Inesperado</title>
	
	<link type="text/css" rel="stylesheet" href="<ww:url value="/css/syntaxhighlighter/shCore.css"/>"/>
	<link type="text/css" rel="stylesheet" href="<ww:url value="/css/syntaxhighlighter/shThemeDefault.css"/>"/>
	
	<style type="text/css">
		@import url('<ww:url includeParams="none" value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>');
	
		#detalhesException { display: none; height: 400px; overflow: auto; }
		.msgEnvio { text-align: justify; }
	</style>
	
	<script type="text/javascript" src="<ww:url includeParams="none" value="/js/morro.js"/>"></script>
	<script type="text/javascript" src="<ww:url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>"></script>
	<script type="text/javascript" src='<ww:url includeParams="none" value="/js/syntaxhighlighter/shCore.js"/>'></script>
	<script type="text/javascript" src='<ww:url includeParams="none" value="/js/syntaxhighlighter/shBrushPlain.js"/>'></script>
	<script type="text/javascript" src="<ww:url includeParams="none" value="/dwr/engine.js"/>"></script>
	<script type="text/javascript">
		
		$(function(){
			SyntaxHighlighter.config.clipboardSwf = '<ww:url includeParams="none" value="/js/syntaxhighlighter/clipboard.swf"/>';
			SyntaxHighlighter.all();		
		});
		
		function exibirDetalhes()
		{
			$('#detalhesException').dialog({ modal: true, title: 'Detalhes do Erro', width: 740, height: 340  });
		}
	</script>
</head>

<body>
	<table width="100%">
		<tr>
			<td valign="top">
				<center>
					<font face=verdana size=2 color=black>
						<p>
						ERRO INESPERADO
						<font face=verdana size=2 color=black>
						<br>
						<br>A requisição não pode ser completada.
						<% if (request.getAttribute("javax.servlet.forward.request_uri") == null || !request.getAttribute("javax.servlet.forward.request_uri").toString().contains("/externo/")) { %>
							<br>Dúvidas entre em contato com 
							<a href="mailto:suporte.rh@grupofortes.com.br">suporte.rh@grupofortes.com.br</a>
						<% } %>
						</p>
					</font>
				</center>
			</td>
			<td width="40%" valign="top">
				<strong>Deseja enviar este problema para a Fortes?</strong>
				<p class="msgEnvio">Contribua para o aprimoramento deste "software" enviando o problema para a Fortes. O relatório que é enviado contém dados detalhados a respeito do problema e do ambiente (sistema operacional, processos, memória, processador, circunstância, etc.), facilitando assim o trabalho de nossa equipe para encontrar e corrigir o problema.</p>
				<a href="javascript:;" onclick="exibirDetalhes()">Ver Detalhes do Erro</a>
			</td>
		</tr>
		<tr>
			<td align="center">
				<input type="button" class="btnVoltarInicial" onclick="location.href='<ww:url value='/'/>'"/>
			</td>
			<td align="center">
				<input type="button" class="btnEnviar" onclick="enviar('<ww:property value="%{exception.message}" default="Erro inesperado"/>', '<ww:property value="%{exception.class}"/>');"/>
			</td>
		</tr>
	</table>
	
	<div id="detalhesException">
		<pre id="log" class="brush:plain; wrap-lines:true"><ww:property value="%{exceptionStack}"/></pre>
	</div>
</body>
</html>