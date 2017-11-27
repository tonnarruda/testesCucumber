<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Faixas Salariais</title>

<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UsuarioAjudaESocialDWR.js?version=${versao}"/>'></script>

<script type="text/javascript">
	function habilitarDesabilitarCamposLinha(campoRisco)
	{
		$(campoRisco).parent().parent().find('input, select, textarea').not(campoRisco).attr('disabled', !campoRisco.checked);
	}
</script>


</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<p><b>Cargo:</b> ${cargo.nome}</p>

	<@display.table name="faixaSalarials" id="faixaSalarial" pagesize=10 class="dados" defaultsort=2 sort="list">
		<@display.column title="Ações" class="acao">
			<a href="../../captacao/nivelCompetencia/listCompetenciasFaixaSalarial.action?faixaSalarial.id=${faixaSalarial.id}"><img border="0" title="Níveis de Competência" src="<@ww.url value="/imgs/competencias.gif"/>"></a>
			<a href="prepareUpdate.action?faixaSalarialAux.id=${faixaSalarial.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<#if !empresaEstaIntegradaEAderiuAoESocial>
				<a href="javascript: newConfirm('Confirma exclusão?', function(){window.location='delete.action?faixaSalarial.id=${faixaSalarial.id}&cargo.id=${cargo.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			<#else>
				<img border="0" title="Devido as adequações ao eSocial, não é possível excluir faixa salarial pelo Fortes RH." src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.5;filter:alpha(opacity=50);">
			</#if>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
	</@display.table>

	<div class="buttonGroup">
		<#if empresaEstaIntegradaEAderiuAoESocial>
			<button class="btnInserirDesabilitado" title="Devido as adequações ao eSocial, não é possível inserir uma faixa salarial pelo Fortes RH.">
		<#else>
			<button class="btnInserir" onclick="window.location='prepareInsert.action?cargoAux.id=${cargo.id}'" accesskey="N">
		</#if>
		</button>
		<button class="btnVoltar" onclick="window.location='../cargo/list.action'" accesskey="V">
		</button>
	</div>
</body>
</html>