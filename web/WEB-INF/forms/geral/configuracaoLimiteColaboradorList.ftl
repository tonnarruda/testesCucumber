<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<title>Configuração do limite de Colaboradores por Cargo</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="configuracaoLimiteColaboradors" id="configuracaoLimiteColaborador" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="javascript: executeLink('prepareUpdate.action?configuracaoLimiteColaborador.id=${configuracaoLimiteColaborador.id}');"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="if (confirm('Confirma exclusão?')) executeLink('delete.action?configuracaoLimiteColaborador.id=${configuracaoLimiteColaborador.id}&configuracaoLimiteColaborador.areaOrganizacional.id=${configuracaoLimiteColaborador.areaOrganizacional.id}');"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="descricao" title="Contrato"/>
		<@display.column property="areaOrganizacional.nome" title="Área Organizacional"/>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="executeLink('prepareInsert.action');"></button>
		<button class="btnRelatorio" onclick="executeLink('imprimir.action');"></button>
		<button class="btnRelatorioExportar" onclick="executeLink('exportar.action');"></button>
	</div>
</body>
</html>
