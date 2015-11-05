<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>

	<title>Históricos dos Níveis de Competência</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="nivelCompetenciaHistoricos" id="nivelCompetenciaHistorico" class="dados" defaultsort=2 style="width: 200px">
		<@display.column title="Ações" class="acao">
			<a href="../configHistoricoNivel/prepareUpdate.action?nivelCompetenciaHistorico.id=${nivelCompetenciaHistorico.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="javascript:;" onclick="javascript:newConfirm('Confirma exclusão?', function(){window.location='delete.action?nivelCompetenciaHistorico.id=${nivelCompetenciaHistorico.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="dataFormatada" title="Data"  style="text-align: center"/>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='../configHistoricoNivel/prepareInsert.action'"></button>
	</div>
</body>
</html>
