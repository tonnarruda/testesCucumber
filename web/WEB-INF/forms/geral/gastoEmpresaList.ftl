<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Investimentos da Empresa - por Colaborador</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@display.table name="gastoEmpresas" id="gastoEmpresa" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareClonar.action?gastoEmpresa.id=${gastoEmpresa.id}"><img border="0" title="Clonar" src="<@ww.url value="/imgs/clonar.gif"/>"></a>
			<a href="prepareUpdate.action?gastoEmpresa.id=${gastoEmpresa.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?gastoEmpresa.id=${gastoEmpresa.id}&page=${page}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="colaborador.nomeComercial" title="Colaborador"/>
		<@display.column property="mesAno" title="Data" format="{0,date,MM/yyyy}"/>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
		</button>
	</div>
</body>
</html>