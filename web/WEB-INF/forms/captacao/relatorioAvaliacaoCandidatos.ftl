<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>

<@ww.head/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<#include "../ftl/mascarasImports.ftl" />

	<script type="text/javascript">

		function populaCargosByGrupo(frm, nameCheck)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var gruposIds = getArrayCheckeds(frm, nameCheck);
			CargoDWR.getCargoByGrupo(createListCargos, gruposIds);
		}

		function createListCargos(data)
		{
			addChecks('cargosCheck',data)
		}

		function filtrarOpt(value)
		{
			if(value == "1")
			{
				document.getElementById('divAreas').style.display = "";
				document.getElementById('divGrupos').style.display = "none";
			}
			else if(value == "2")
			{
				document.getElementById('divAreas').style.display = "none";
				document.getElementById('divGrupos').style.display = "";
			}
		}

		function validaForm()
		{
			var filtrarPorValue = document.getElementById('filtrarPor').value;

			var validaAreaCargo = '@areasCheck';

			if(filtrarPorValue == "2")
			{
				validaAreaCargo = '@cargosCheck';
			}

			return validaFormularioEPeriodo('form', new Array('filtrarPor', 'dataIni', 'dataFim'), new Array('dataIni','dataFim'));
		}


	</script>

	<#if dataCadIni?exists>
		<#assign dateIni = dataCadIni?date/>
	<#else>
		<#assign dateIni = ""/>
	</#if>
	<#if dataCadFim?exists>
		<#assign dateFim = dataCadFim?date/>
	<#else>
		<#assign dateFim = ""/>
	</#if>

	<title>Relatório de Avaliações de Candidatos</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<#assign formAction = "relatorioAvaliacaoCandidatos.action" />

	<@ww.form name="form" action="${formAction}" method="POST">
		Período:*<br>
		<@ww.datepicker name="dataCadIni" id="dataIni"  value="${dateIni}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="dataCadFim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" />

		<@ww.select id="filtrarPor" label="Filtrar Colaboradores por" name="filtrarPor" list=r"#{'1':'Área Organizacional', '2':'Grupo Ocupacional'}" onchange="filtrarOpt(this.value);"/>

			<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimento" list="estabelecimentosCheckList" />
			<li>
				<@ww.div id="divAreas">
					<ul>
						<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList"/>
					</ul>
				</@ww.div>
			</li>
			<li>
				<@ww.div id="divGrupos" cssStyle="display:none;">
					<ul>
						<@frt.checkListBox name="gruposCheck" id="gruposCheck" label="Grupos Ocupacionais" list="gruposCheckList" onClick="populaCargosByGrupo(document.forms[0],'gruposCheck');"/>
						<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos" list="cargosCheckList" />
					</ul>
				</@ww.div>
			</li>

	</@ww.form>

	<div class="buttonGroup">
		<button onclick="validaForm();" class="btnRelatorio">
		</button>
	</div>

	<script type="text/javascript">
		filtrarOpt(document.getElementById('filtrarPor').value);
	</script>
</body>
</html>