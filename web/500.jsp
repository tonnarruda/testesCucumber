<%@ page isErrorPage="true" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix='ww' uri='webwork' %>
<%@ taglib prefix='authz' uri='http://www.springframework.org/security/tags' %>
<html>
<head>
	<title>Erro interno do servidor</title>

	<style type="text/css">
		@import url('<ww:url includeParams="none" value="/css/syntaxhighlighter/shCore.css"/>');
		@import url('<ww:url includeParams="none" value="/css/syntaxhighlighter/shThemeDefault.css"/>');
		@import url('<ww:url includeParams="none" value="/css/jquery.alerts.css"/>');
		@import url('<ww:url includeParams="none" value="/css/default.css"/>');
		@import url('<ww:url includeParams="none" value="/css/fortes.css"/>');
		@import url('<ww:url includeParams="none" value="/css/botoes.css"/>');
		@import url('<ww:url includeParams="none" value="/css/menu.css"/>');
		@import url('<ww:url includeParams="none" value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>');
		
		#detalhesException { display: none; width: 720px; height: 300px; overflow: auto; }
		.waDivFormulario table td { font-size: 12px; }
		.msgEnvio { text-align: justify; }
	</style>

	<script type="text/javascript" src="<ww:url includeParams="none" value="/js/morro.js"/>"></script>
	<script type='text/javascript' src='<ww:url includeParams="none" value="/js/jQuery/jquery-1.4.4.min.js"/>'></script>
	<script type='text/javascript' src='<ww:url includeParams="none" value="/js/jQuery/jquery.alerts.js"/>'></script>
	<script type="text/javascript" src="<ww:url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>"></script>
	<script type="text/javascript" src='<ww:url includeParams="none" value="/js/syntaxhighlighter/shCore.js"/>'></script>
	<script type="text/javascript" src='<ww:url includeParams="none" value="/js/syntaxhighlighter/shBrushPlain.js"/>'></script>
	<script type="text/javascript" src="<ww:url includeParams="none" value="/dwr/interface/MorroDWR.js"/>"></script>
	<script type="text/javascript" src="<ww:url includeParams="none" value="/dwr/engine.js"/>"></script>
	<script type='text/javascript' src='<ww:url includeParams="none" value="/js/fortes.js"/>'></script>
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
			<img src='<ww:url includeParams="none" value="/imgs/topo_img_right.png"/>' border='0' align='absMiddle' />
		</div>
		<div id="logoDiv"><a href="<ww:url value='/'/>"><img src='<ww:url includeParams="none" value="/imgs/topo_ico.png"/>' border='0'/></a></div>
	</div>
		
	<ul id="menuDropDown"></ul>
	
	<div style="clear: both"></div>
	<div id="waDiv">
		<br />
		<div id="waDivTitulo">Erro Interno do Servidor</div>
		
		<div class="waDivFormulario">
			<table width="100%">
				<tr>
					<td valign="top">
						<center>
							<font face=verdana size=2 color=black>
								<p>
								ERRO 500 - ERRO INTERNO DO SERVIDOR
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
						<input type="button" class="btnEnviar" onclick="enviar('<ww:property value="%{exception.message}" default="Erro interno do servidor"/>', '<ww:property value="%{exception.class}"/>');"/>
					</td>
				</tr>
			</table>
		</div>
		
		<br /><br />
	</div>
	
	<div id="detalhesException">
		<pre id="log" class="brush:plain; wrap-lines:true"><ww:property value="%{exceptionStack}"/></pre>
	</div>
</body>
</html>