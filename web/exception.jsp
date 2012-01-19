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
		#detalhesException {
			display: none;
			height: 400px;
			overflow: auto;
		}
	</style>
	
	<script src='<ww:url includeParams="none" value="/js/syntaxhighlighter/shCore.js"/>'></script>
	<script src='<ww:url includeParams="none" value="/js/syntaxhighlighter/shBrushPlain.js"/>'></script>
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
	
	<a href="javascript:;" onclick="$('#detalhesException').slideToggle()">Detalhes</a>
	
	<div id="detalhesException">
		<pre id="log" class="brush:plain; wrap-lines: true"><ww:property value="%{exceptionStack}"/></pre>
	</div>
	
	<script type="text/javascript">
		SyntaxHighlighter.config.clipboardSwf = '<ww:url includeParams="none" value="/js/syntaxhighlighter/clipboard.swf"/>';
		SyntaxHighlighter.all();
	</script>
</body>
</html>