<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
</style>
<title>Faixas Salariais</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<p><b>Cargo:</b> ${cargo.nome}</p>

	<@display.table name="faixaSalarials" id="faixaSalarial" pagesize=10 class="dados" defaultsort=2 sort="list">
		<@display.column title="Ações" class="acao">
			<!-- <a href="../../captacao/nivelCompetencia/prepareCompetenciasByFaixa.action?faixaSalarial.id=${faixaSalarial.id}"><img border="0" title="Níveis de Competência" src="<@ww.url value="/imgs/competencias.gif"/>"></a> -->
			<a href="../../captacao/nivelCompetencia/listCompetenciasFaixaSalarial.action?faixaSalarial.id=${faixaSalarial.id}"><img border="0" title="Níveis de Competência" src="<@ww.url value="/imgs/competencias.gif"/>"></a>
			<a href="prepareUpdate.action?faixaSalarialAux.id=${faixaSalarial.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="javascript: newConfirm('Confirma exclusão?', function(){window.location='delete.action?faixaSalarial.id=${faixaSalarial.id}&cargo.id=${cargo.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
	</@display.table>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action?cargoAux.id=${cargo.id}'" accesskey="N">
		</button>
		<button class="btnVoltar" onclick="window.location='../cargo/list.action'" accesskey="V">
		</button>
	</div>
</body>
</html>