<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Relatório de Projeção Salarial</title>
	<#assign formAction="gerarRelatorioProjecaoSalarial.action"/>
	<#assign accessKey="I"/>

	<#include "../ftl/mascarasImports.ftl" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<#if data?exists>
		<#assign data = data?date/>
	<#else>
		<#assign data = ""/>
	</#if>

	<script type="text/javascript">

		function populaCargosByGrupo(frm, nameCheck)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var gruposIds = getArrayCheckeds(frm, nameCheck);
			CargoDWR.getCargoByGrupo(createListCargos, gruposIds, <@authz.authentication operation="empresaId"/>);
		}

		function createListCargos(data)
		{
			addChecks('cargosCheck',data)
		}

		function filtrarOpt()
		{
			value =	document.getElementById('optFiltro').value;

			if(value == "1")
			{
				document.getElementById('divAreas').style.display = "";
				document.getElementById('divGrupos').style.display = "none";
			} else if(value == "2") {
				document.getElementById('divAreas').style.display = "none";
				document.getElementById('divGrupos').style.display = "";
			}
		}

		function validaCamposProjecaoSalarial()
		{
			value =	document.getElementById('optFiltro').value;

			if(value == "1")
			{
				return validaFormulario('form', new Array('data'), new Array('data'))
			}
			else if(value == "2")
			{
				return validaFormulario('form', new Array('@cargosCheck', 'data'), new Array('data'))
			}
		}

	</script>

</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />

	<@ww.form name="form" action="${formAction}" onsubmit="validaCamposProjecaoSalarial();" validate="true" method="POST">
		<@ww.datepicker label="Data da Projeção" id="data" name="data" required="true" cssClass="mascaraData" value="${data}"/>
		<@ww.select id="optReajuste" label="Considerar o Planejamento de Realinhamento"  name="tabelaReajusteColaborador.id" list='tabelaReajusteColaboradors' headerKey="" headerValue="Nenhum" listKey="id" listValue="nome"/>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" width="600" />
		<@ww.select id="optFiltro" label="Filtrar Colaboradores Por" name="filtro" list=r"#{'1':'Área Organizacional', '2':'Grupo Ocupacional/Cargo'}" onchange="filtrarOpt();"/>
		<div id="divAreas">
			<@frt.checkListBox name="areasCheck" id="areasCheck" label="Área Organizacional" list="areasCheckList" />
		</div>
		<div id="divGrupos" style="display:none;">
			<@frt.checkListBox name="gruposCheck" id="gruposCheck" label="Grupos Ocupacionais" list="gruposCheckList" onClick="populaCargosByGrupo(document.forms[0],'gruposCheck');"/>
			<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos" list="cargosCheckList" />
		</div>
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio" onclick="validaCamposProjecaoSalarial();" accesskey="${accessKey}">
		</button>
	</div>
</body>
</html>