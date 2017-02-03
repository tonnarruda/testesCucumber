<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>

<@ww.head/>
	<title>Relatório de Investimentos de T&D</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/TurmaDWR.js?version=${versao}"/>'></script>

	<script type="text/javascript">
		function getTurmasByFiltro()
		{
			dwr.util.useLoadingMessage('Carregando...');
			var cursoIds = getArrayCheckeds(document.forms[0], 'cursosCheck');
			TurmaDWR.getTurmasByCursos(cursoIds, populaTurmas);
		}

		function populaTurmas(data)
		{
			addChecks('turmasCheck', data);
		}
	</script>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign validarCampos="return validaFormulario('form', new Array('inicio','fim','@turmasCheck'), new Array('inicio','fim'))"/>
	
	<#if dataIni?exists>
		<#assign inicio=dataIni?date />
	<#else>
		<#assign inicio="" />
	</#if>
	<#if dataFim?exists>
		<#assign fim=dataFim?date />
	<#else>
		<#assign fim="" />
	</#if>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" action="imprimirRelatorioInvestimento.action" onsubmit="${validarCampos}" method="POST">
		<@ww.datepicker label="Período" required="true" value="${inicio}" name="dataIni" id="inicio" cssClass="mascaraData validaDataIni" after="a" liClass="liLeft"/>
		<@ww.datepicker label="" value="${fim}" name="dataFim" id="fim" cssClass="mascaraData validaDataFim"/>

		<@frt.checkListBox name="cursosCheck" id="cursosCheck" label="Cursos" list="cursosCheckList" onClick="getTurmasByFiltro();" width="600" filtro="true"/>
		<@frt.checkListBox name="turmasCheck" id="turmasCheck" label="Cursos / Turmas *" list="turmasCheckList" width="600" filtro="true"/>
		<@ww.select id="realizada" label="Turmas Realizadas" name="realizada" list=r"#{'T':'Todas','S':'Sim','N':'Não'}" />
		<@ww.checkbox label="Detalhar custos" name="exibirCustoDetalhado" labelPosition="left"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio"></button>
	</div>
</body>
</html>