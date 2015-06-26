<html>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="Content-Language" content="pt-br" />
	<meta http-equiv="Cache-Control" content="no-cache, no-store" />
	<meta http-equiv="Pragma" content="no-cache, no-store" />
	<meta http-equiv="Expires" content="0" />
	<@ww.head />
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/curriculo.css?version=${versao}"/>');
	</style>
</head>
<body>
<div id="containerExamePalografico">
	<#if candidato?exists>
		<@ww.textarea label="Exame Palográfico" id="examePalografico" name="candidato.examePalografico" cssStyle="width:100%" readonly="readonly"></@ww.textarea>
		
		<br />
		
		<#if candidato.examePalografico?exists>
			<strong>Resultado:</strong><br />
			<pre>${candidato.resultadoExamePalografico}</pre>
		</#if>
	</#if>
</div>
</body>
</html>

