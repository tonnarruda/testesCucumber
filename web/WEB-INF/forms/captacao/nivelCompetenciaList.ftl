<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<title>Níveis de Competência</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="nivelCompetencias" id="nivelCompetencia" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="../configHistoricoNivel/prepareUpdate.action?nivelCompetencia.id=${nivelCompetencia.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){ window.location='delete.action?nivelCompetencia.id=${nivelCompetencia.id}' });"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="descricao" title="Descrição"/>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
		<button class="btnDividirPercentualIgualmente" onclick="newConfirm('Tem certeza que deseja dividir o percentual mínimo igualmente?</br>Isso acarretará na alteração de todos os percentuais mínimo dos níveis de competência.', function(){window.location='gerarPercentualIgualmente.action'});"></button>
	</div>
</body>
</html>
