<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		
		.dados { width: 710px; margin: 10px !important; }
		.dados td { border: none; }
	</style>

	<title>Competências do Candidato</title>
</head>
<body>
	<#if niveisCompetenciaFaixaSalariaisSalvos?exists>
		<@display.table name="niveisCompetenciaFaixaSalariaisSalvos" id="configuracaoNivelCompetencia" class="dados">
			<@display.column title="Competência" property="competenciaDescricao"/>
			<@display.column title="Nível" property="nivelCompetencia.descricao"/>
		</@display.table>
	</#if>
</body>
</html>
