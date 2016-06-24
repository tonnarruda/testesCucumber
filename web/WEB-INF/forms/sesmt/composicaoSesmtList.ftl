<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<title>Composição do SESMT</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="composicaoSesmts" id="composicaoSesmt" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="javascript: executeLink('prepareUpdate.action?composicaoSesmt.id=${composicaoSesmt.id}');"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('delete.action?composicaoSesmt.id=${composicaoSesmt.id}&page=${page}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>

		</@display.column>
		<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="text-align: center;"/>
		<@display.column property="qtdTecnicosSeguranca" title="Téc. Seg." style="text-align: right; width: 120px;"/>
		<@display.column property="qtdEngenheirosSeguranca" title="Eng. Seg." style="text-align: right; width: 120px;"/>
		<@display.column property="qtdAuxiliaresEnfermagem" title="Aux. Enf." style="text-align: right; width: 120px;"/>
		<@display.column property="qtdEnfermeiros" title="Enfermeiros" style="text-align: right; width: 120px;"/>
		<@display.column property="qtdMedicos" title="Médicos" style="text-align: right; width: 120px;"/>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action');"></button>
	</div>
</body>
</html>
