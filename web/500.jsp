<%@ page isErrorPage="true" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix='ww' uri='webwork' %>
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
		
		#envioErro { display: none; }
		#envioErro p { text-align: justify }
		#detalhesException {
			display: none;
			height: 300px;
			overflow: auto;
		}
	</style>
	
	<script type='text/javascript' src='<ww:url includeParams="none" value="/js/jQuery/jquery-1.4.4.min.js"/>'></script>
	<script type='text/javascript' src='<ww:url includeParams="none" value="/js/jQuery/jquery.alerts.js"/>'></script>
	<script type="text/javascript" src="<ww:url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>"></script>
	<script type="text/javascript" src='<ww:url includeParams="none" value="/js/syntaxhighlighter/shCore.js"/>'></script>
	<script type="text/javascript" src='<ww:url includeParams="none" value="/js/syntaxhighlighter/shBrushPlain.js"/>'></script>
	<script type="text/javascript" src="<ww:url includeParams="none" value="/dwr/interface/MorroDWR.js"/>"></script>
	<script type="text/javascript" src="<ww:url includeParams="none" value="/dwr/engine.js"/>"></script>
	<script type="text/javascript">
		var mensagem = '<ww:property value="%{exception.message}" default="Erro interno do servidor"/>';
		
		$(function() {
			$('#envioErro').dialog({title: 'Fortes RH',
									width: 600,
									height: 300,
									buttons: [
										    {
										        text: "Enviar",
										        click: function() {
										        	MorroDWR.enviar(mensagem, location.href, function(dados) { jAlert(dados); });
										        }
										    },
										    {
										        text: "Não enviar",
										        click: function() { $(this).dialog("close"); }
										    }
										] 
									});
		});
		
		function exibirOcultarDetalhes(link)
		{
			if ($(link).text() == 'Ver Detalhes')
			{
				$('#detalhesException').show();
				$('#envioErro').dialog( "option", "height", 600 );
				$(link).text('Ocultar Detalhes');
			} 
			else
			{
				$('#detalhesException').hide();
				$('#envioErro').dialog( "option", "height", 300 );
				$(link).text('Ver Detalhes');
			}
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
			<img src='<ww:url includeParams="none" value="/imgs/topo_img_right.jpg"/>' border='0' align='absMiddle' />
		</div>
		<div id="logoDiv"><a href="<ww:url value='/'/>"><img src='<ww:url includeParams="none" value="/imgs/topo_ico.jpg"/>' border='0'/></a></div>
	</div>
		
	<ul id="menuDropDown"></ul>
	
	<div style="clear: both"></div>
	<div id="waDiv">
		<br />
		<div id="waDivTitulo">Erro</div>
		
		<div class="waDivFormulario">
			<div class="errorMessage">
				<table>
					<tr>
						<td>
							<img src="<ww:url includeParams="none" value="/imgs/erro_msg.gif"/>">
						</td>
						<td>
							<ww:property value="%{exception.message}" default="Erro interno do servidor"/>
						</td>
					</tr>
				</table>
			</div>
			
			<div id="envioErro">
				<p>Ocorreu um erro inesperado.</p>
				<br />
				<strong>Envie este problema para a Fortes</strong>
				<p>Contribua para o aprimoramento deste "software" enviando o problema para a Fortes. O relatório que é enviado contém dados detalhados a respeito do problema e do ambiente (sistema operacional, processos, memória, processador, circunstância, etc.), facilitando assim o trabalho de nossos engenheiros em encontrar e corrigir o problema.</p>
				
				<a href="javascript:;" onclick="exibirOcultarDetalhes(this)">Ver Detalhes</a>
				<div id="detalhesException">
					<pre id="log" class="brush:plain; wrap-lines:true"><ww:property value="%{exceptionStack}"/></pre>
				</div>
			</div>
			
			<!-- 
			<center>
				<font face=verdana size=2 color=black>
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
			 -->
		</div>
		
		<br /><br />
	</div>
	
	<script type="text/javascript">
		SyntaxHighlighter.config.clipboardSwf = '<ww:url includeParams="none" value="/js/syntaxhighlighter/clipboard.swf"/>';
		SyntaxHighlighter.all();
	</script>
</body>
</html>