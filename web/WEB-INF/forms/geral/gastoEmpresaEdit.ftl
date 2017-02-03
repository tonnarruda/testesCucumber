<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>

	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>" type="text/css">

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/formataValores.js?version=${versao}"/>"></script>

	<@ww.head/>
	<#if gastoEmpresa.id?exists>
		<title>Editar Investimentos da Empresa - por Colaborador</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
	<#else>
		<title>Inserir Investimentos da Empresa - por Colaborador</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
	</#if>

		<#assign validarCampos="return validaFormulario('form', new Array('mesAno','colab'), new Array('mesAno'))"/>

		<#include "../ftl/mascarasImports.ftl" />

	<script language='javascript'>

		function populaColaborador()
		{
			dwr.util.useLoadingMessage('Carregando...');
			var areasIds = getArrayCheckeds(document.forms[0], 'areasCheck');
			var estabelecimentosIds = getArrayCheckeds(document.forms[0], 'estabelecimentosCheck');
			if(areasIds.length == 0 && estabelecimentosIds.length == 0)
				dwr.util.removeAllOptions("colab");
			else
				ColaboradorDWR.getColaboradoresAreaEstabelecimento(areasIds, estabelecimentosIds, <@authz.authentication operation="empresaId"/>, createListColaborador);
		}

		function createListColaborador(data)
		{
			dwr.util.removeAllOptions("colab");
			document.getElementById("colab").options[0] = new Option("Selecione...", "");
			dwr.util.addOptions("colab", data);
		}

		function prepareInsertItem()
		{
			var viewDiv = window['gastoEmpresaItem'];
			viewDiv.href = '<@ww.url includeParams="none" value="/geral/gastoEmpresaItem/prepareInsert.action"/>';
			viewDiv.bind();
		}

		function voltarItem()
		{
			var viewDiv = window['gastoEmpresaItem'];
			viewDiv.href = '<@ww.url includeParams="none" value="/geral/gastoEmpresaItem/list.action"/>';
			viewDiv.bind();
		}

		function removeItem(id)
		{
			var viewDiv = window['gastoEmpresaItem'];
			viewDiv.href = '<@ww.url includeParams="none" value="/geral/gastoEmpresaItem/delete.action?gastoEmpresaItem.id="/>'+id;
			viewDiv.bind();
		}

		function prepareUpdateItem(id)
		{
			var viewDiv = window['gastoEmpresaItem'];
			viewDiv.href = '<@ww.url includeParams="none" value="/geral/gastoEmpresaItem/prepareUpdate.action?gastoEmpresaItem.id="/>'+id;
			viewDiv.bind();
		}
	</script>

	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#--Refatorar usar JQuery--#>
	<@ww.div id="gastoEmpresaItem" href="/geral/gastoEmpresaItem/list.action" cssClass="divInfo"/>
	<br>
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<li>
			<#include "../util/topFiltro.ftl" />
			<ul>
				<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" onClick="populaColaborador();"/>
				<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" onClick="populaColaborador();"/>
			</ul>
			<#include "../util/bottomFiltro.ftl" />
			<br>
		</li>
		<@ww.select label="Colaborador" name="gastoEmpresa.colaborador.id" id="colab" list="colaboradors" listKey="id" listValue="nomeComercial" headerValue="Selecione..." headerKey="" required="true"/>
		<@ww.textfield label="Mês/Ano" name="dataMesAno" id="mesAno" cssClass="mascaraMesAnoData"/>

		<@ww.hidden name="gastoEmpresa.id" />

	</@ww.form>

	<br>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}">	</button>
		<#if gastoEmpresa.id?exists>
			<button onclick="window.location='prepareClonar.action?gastoEmpresa.id=${gastoEmpresa.id}' " class="btnClonarInvestimentos" accesskey="c">
			</button>
		</#if>
		<button onclick="window.location='list.action'" class="btnVoltar" accesskey="V">
		</button>
	</div>
</body>
</html>