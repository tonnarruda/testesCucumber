<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>

<@ww.head/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>

	<#include "../ftl/mascarasImports.ftl" />

	<script type="text/javascript">
		$(function () {
			<#if filtrarPor?exists>
				filtrarOpt(${filtrarPor});
				$('#filtrarPor option').attr('selected',${filtrarPor}-1);
			</#if>
		});
		
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
			var validaAreaCargo = '@areasCheck';
			if($('#filtrarPor').val() == "2")
				validaAreaCargo = '@cargosCheck';

			return validaFormularioEPeriodo('form', new Array('filtrarPor', 'dataIni', 'dataFim', validaAreaCargo), new Array('dataIni','dataFim'));
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

	<title>Avaliações de Candidatos</title>
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

			<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimento" list="estabelecimentosCheckList" filtro="true"/>
			<li>
				<@ww.div id="divAreas">
					<ul>
						<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais*" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
					</ul>
				</@ww.div>
			</li>
			<li>
				<@ww.div id="divGrupos" cssStyle="display:none;">
					<ul>
						<@frt.checkListBox name="gruposCheck" id="gruposCheck" label="Grupos Ocupacionais" list="gruposCheckList" onClick="populaCargosByGrupo(document.forms[0],'gruposCheck');" filtro="true" />
						<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos" list="cargosCheckList" filtro="true" selectAtivoInativo="true" />
					</ul>
				</@ww.div>
			</li>
		<@ww.select id="statusSolicitacao" label="Considerar Solicitações de Pessoal" name="statusSolicitacao" list=r"#{'T':'Abertas e Encerradas', 'A':'Abertas', 'E':'Encerradas'}"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="validaForm();" class="btnRelatorio">
		</button>
	</div>
</body>
</html>