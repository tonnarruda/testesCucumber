<%@ page isErrorPage="true"%>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix='ww' uri='webwork' %>
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
		
		#envioErro { display: none; }
		#envioErro p { text-align: justify }
		#detalhesException {
			display: none;
			height: 300px;
			overflow: auto;
		}
	</style>
	
	<script type="text/javascript" src='<ww:url includeParams="none" value="/js/syntaxhighlighter/shCore.js"/>'></script>
	<script type="text/javascript" src='<ww:url includeParams="none" value="/js/syntaxhighlighter/shBrushPlain.js"/>'></script>
	<script type="text/javascript" src="<ww:url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>"></script>
	<script type="text/javascript" src="<ww:url includeParams="none" value="/dwr/interface/MorroDWR.js"/>"></script>
	<script type="text/javascript" src="<ww:url includeParams="none" value="/dwr/engine.js"/>"></script>
	<script type="text/javascript">
		var mensagem;
		<ww:if test="exception.message != null">
			mensagem = '<ww:property value="%{exception.message}"/>';
		</ww:if>
		<ww:else>
			mensagem = 'Erro interno do servidor';
		</ww:else>
		
		$(function() {
			$('#envioErro').dialog({title: 'Fortes RH',
									width: 600,
									height: 300,
									buttons: [
										    {
										        text: "Enviar",
										        click: function() {
										        	MorroDWR.enviar(mensagem, '', function(dados) { alert(dados); });
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
	<div class="errorMessage">
		<table>
			<tr>
				<td>
					<img src="<ww:url includeParams="none" value="/imgs/erro_msg.gif"/>">
				</td>
				<td>
					<ww:if test="exception.message != null">
						<ww:property value="%{exception.message}"/>
					</ww:if>
					<ww:else>
						Erro interno do servidor
					</ww:else>
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
	
	<script type="text/javascript">
		SyntaxHighlighter.config.clipboardSwf = '<ww:url includeParams="none" value="/js/syntaxhighlighter/clipboard.swf"/>';
		SyntaxHighlighter.all();
	</script>
</body>
</html>