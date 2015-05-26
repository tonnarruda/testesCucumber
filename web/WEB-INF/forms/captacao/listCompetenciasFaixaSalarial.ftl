<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
</style>
<title>Competências da Faixa Salarial</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<b>Faixa Salarial:</b> ${faixaSalarial.nome}
	<div style="clear: both;"></div><br />
	
	<@display.table name="configuracaoNivelCompetenciaFaixasSalariais" id="cncFaixaSalarial" class="dados" style="width: 200px">
		<@display.column title="Ações" media="html" style="text-align:center; width:50px;" >
			<a href="prepareUpdateCompetenciasFaixaSalarial.action?configuracaoNivelCompetenciaFaixaSalarial.id=${cncFaixaSalarial.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='deleteCompetenciasFaixaSalarial.action?faixaSalarial.id=${faixaSalarial.id}&configuracaoNivelCompetenciaFaixaSalarial.id=${cncFaixaSalarial.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}"  style="text-align: center"/>
	</@display.table>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsertCompetenciasFaixaSalarial.action?faixaSalarial.id=${faixaSalarial.id}'"></button>
		<button onclick="window.location='../../cargosalario/faixaSalarial/list.action';" class="btnVoltar"></button>
	</div>
</body>
</html>