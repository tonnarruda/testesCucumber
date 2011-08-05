<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>

	<title>Competências da Faixa Salarial</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<p><b>Cargo:</b> ${faixaSalarial.cargo.nome} &nbsp;&nbsp;&nbsp; <b>Faixa:</b> ${faixaSalarial.nome}</p>
	
	<@display.table name="niveisCompetenciaFaixaSalarial" id="nivelCompetenciaFaixaSalarial" class="dados">
		<@display.column title="Competência">
			<@ww.checkbox id="${nivelCompetenciaFaixaSalarial.competenciaId}" name="" theme="simple"/>
			${nivelCompetenciaFaixaSalarial.competenciaDescricao}
		</@display.column>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnGravar" onclick="jAlert('Em desenvolvimento!')"></button>
		<button class="btnVoltar" onclick="window.location='../../cargosalario/faixaSalarial/list.action?cargo.id=${faixaSalarial.cargo.id}'"></button>
	</div>
</body>
</html>
