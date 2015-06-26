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
	
	<#if modoImpressao>
		<#assign width="100%"/>
	<#else>
		<#assign width="230"/>
	</#if>
	
</head>
<body>
<div id="containerCv">
	<#list candidato.candidatoCurriculos as candidatoCurriculos>
		<a href="javascript:popUp('<@ww.url includeParams="none" value="../candidato/showImagensCurriculo.action?candidato.id=${candidato.id}&nomeImg=${candidatoCurriculos.curriculo}"/>')"><img border="0" width="${width}"  src="<@ww.url includeParams="none" value="../candidato/showImagensCurriculo.action?candidato.id=${candidato.id}&nomeImg=${candidatoCurriculos.curriculo}"/>" title="Clique para ampliar"></a>
	</#list>
</div>

</body>
</html>