<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
<#assign validarCampos="return validaFormularioEPeriodoMesAno('form', null, new Array('dataIni', 'dataFim'))"/>

<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<#include "../ftl/mascarasImports.ftl" />
<script type='text/javascript'>
	function populaColaborador()
	{
		DWRUtil.useLoadingMessage('Carregando...');
		var areasIds = getArrayCheckeds(document.forms[0], 'areasCheck');
		var estabelecimentosIds = getArrayCheckeds(document.forms[0], 'estabelecimentosCheck');
		if(areasIds.length == 0 && estabelecimentosIds.length == 0)
			DWRUtil.removeAllOptions("colaboradores");
		else
			ColaboradorDWR.getColaboradoresAreaEstabelecimento(createListColaborador, areasIds, estabelecimentosIds, <@authz.authentication operation="empresaId"/>);
	}

	function createListColaborador(data)
	{
		DWRUtil.removeAllOptions("colaboradores");
		document.getElementById("colaboradores").options[0] = new Option("Todos", "");
		DWRUtil.addOptions("colaboradores", data);
	}

	function imprimir()
	{
		var qtdSelect = qtdeChecksSelected(document.forms[0], 'areasCheck');
		var qtdSelectE = qtdeChecksSelected(document.forms[0], 'estabelecimentosCheck');

		if(qtdSelectE == 0)
		{
			jAlert("Nenhum Estabelecimento selecionado.");
		}
		else if(qtdSelect == 0 && document.getElementById("colaboradores").value == "")
		{
			jAlert("Nenhuma Área ou Colaborador selecionado.");
		}
		else
		{
			${validarCampos};
		}
	}

</script>
<@ww.head/>

	<title>Relatório de Investimentos da Empresa</title>

</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />
<@ww.form name="form" action="gerarRelatorioInvestimentos.action"  onsubmit="${validarCampos}" validate="true" method="POST">
	<div>Período (Mês/Ano)*</div>

	<@ww.textfield name="dataIni" id="dataIni" required="true" liClass="liLeft" cssClass="mascaraDataMesAno validaDataIni"/>
	<@ww.label value="a" liClass="liLeft"/>
	<@ww.textfield name="dataFim" id="dataFim" required="true" cssClass="mascaraDataMesAno validaDataFim"/>
	<br>
	<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" onClick="populaColaborador();"/>
	<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" onClick="populaColaborador();" />
	<br>
	<@ww.select label="Colaborador" id="colaboradores" name="colaborador.id" list="colaboradors" listKey="id" listValue="nomeComercial" headerValue="Todos" headerKey=""/>
</@ww.form>

<div class="buttonGroup">
	<button class="btnRelatorio" onclick="if(compararData('dataIni','dataFim')) imprimir()" accesskey="V">
	</button>
</div>

</body>
</html>