<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Relatório de Projeção Salarial do Colaborador</title>
	<#assign formAction="gerarRelatorioProjecaoSalarial.action"/>
	<#assign accessKey="I"/>

	<#include "../ftl/mascarasImports.ftl" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>

	<#if data?exists>
		<#assign data = data?date/>
	<#else>
		<#assign data = ""/>
	</#if>

	<script type="text/javascript">

		function populaCargosByGrupo(frm, nameCheck)
		{
			dwr.util.useLoadingMessage('Carregando...');
			var gruposIds = getArrayCheckeds(frm, nameCheck);
			CargoDWR.getCargoByGrupo(gruposIds, <@authz.authentication operation="empresaId"/>, createListCargos);
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
		<@ww.select id="optReajuste" label="Planejamento de Realinhamento por Colaborador"  name="tabelaReajusteColaborador.id" list='tabelaReajusteColaboradors' headerKey="" headerValue="Nenhum" listKey="id" listValue="nome" cssStyle ="width:320px;"/>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" width="600" filtro="true"/>
		<@ww.select id="optFiltro" label="Filtrar Colaboradores Por" name="filtro" list=r"#{'1':'Área Organizacional', '2':'Grupo Ocupacional/Cargo'}" onchange="filtrarOpt();"/>
		<div id="divAreas">
			<@frt.checkListBox name="areasCheck" id="areasCheck" label="Área Organizacional" list="areasCheckList" width="600" filtro="true" selectAtivoInativo="true"/>
		</div>
		<div id="divGrupos" style="display:none;">
			<@frt.checkListBox name="gruposCheck" id="gruposCheck" label="Grupos Ocupacionais" list="gruposCheckList" onClick="populaCargosByGrupo(document.forms[0],'gruposCheck');" width="600" filtro="true"/>
			<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos*" list="cargosCheckList" width="600" filtro="true" selectAtivoInativo="true"/>
		</div>
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio" onclick="$('form[name=form]').attr('action', 'gerarRelatorioProjecaoSalarial.action');validaCamposProjecaoSalarial();" accesskey="${accessKey}"></button>
		<button class="btnRelatorioExportar" onclick="$('form[name=form]').attr('action', 'gerarRelatorioProjecaoSalarialXLS.action');validaCamposProjecaoSalarial();"></button>		
	</div>
</body>
</html>