<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<title>Análise de Tabela Salarial</title>
	<#assign formAction="analiseTabelaSalarialList.action"/>
	<#assign formActionPdf="relatorioAnaliseTabelaSalarial.action"/>
	
	<#include "../ftl/mascarasImports.ftl" />
	<#assign validarCampos="validaFormulario('form', new Array('data'), new Array('data'))"/>
	
	<#if data?exists>
		<#assign data = data?date/>
	<#else>
		<#assign data = ""/>
	</#if>
	
	<script type='text/javascript'>
		function populaCargos(frm, nameCheck, empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var gruposIds = getArrayCheckeds(frm, nameCheck);
			CargoDWR.getCargoByGrupo(createListCargos, gruposIds);
		}

		function createListCargos(data)
		{
			addChecks('cargosCheck',data)
		}
		
		function filtrarOpt()
		{
			value =	document.getElementById('optFiltro').value;
			if(value == '1')
			{
				oculta("divAreaOrganizacionals");
				exibe("divGruposCargos");
			}
			else if(value == '2')
			{
				exibe("divAreaOrganizacionals");
				oculta("divGruposCargos");
			}
		}
		
		function oculta(id_da_div)
		{
			document.getElementById(id_da_div).style.display = 'none';
		}
		function exibe(id_da_div)
		{
			document.getElementById(id_da_div).style.display = '';
		}
		
		function enviaForm(tipo)
		{
			if (tipo == '1') 
				document.form.action='${formAction}';
			else if (tipo == '2')
				document.form.action='${formActionPdf}';
				
			return ${validarCampos};
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="${formAction}" validate="true" method="POST">
		<@ww.datepicker label="Data" id="data" name="data" required="true" cssClass="mascaraData" value="${data}"/>
		
		<@ww.select id="optFiltro" label="Filtrar Por" name="filtro"
				list=r"#{'1':'Grupos Ocupacionais/Cargos','2':'Áreas Organizacionais'}" onchange="filtrarOpt();" />
		
		<span id="divGruposCargos" style="display:''">
			<@frt.checkListBox name="grupoOcupacionalsCheck" id="grupoOcupacionalsCheck" label="Grupos Ocupacionais" list="grupoOcupacionalsCheckList" width="600" onClick="populaCargos(document.form,'grupoOcupacionalsCheck', ${empresaId});"/>
			<@frt.checkListBox name="cargosCheck" label="Cargos" id="cargosCheck" list="cargosCheckList" width="600" />
		</span>
		
		<span id="divAreaOrganizacionals" style="display:none">
			<@frt.checkListBox name="areasCheck" label="Áreas Organizacionais" list="areasCheckList" width="600"/>
		</span>
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnAvancar" onclick="enviaForm('1');"> </button>
		<button class="btnImprimirPdf" onclick="enviaForm('2');"> </button>
	</div>
</body>
</html>