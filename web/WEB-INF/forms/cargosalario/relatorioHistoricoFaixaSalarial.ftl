<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<title>Históricos das Faixas Salariais</title>
	
	
	<script type='text/javascript'>
		function filtrarOpt()
		{
			value =	document.getElementById('optFiltro').value;
			if(value == '1')  // Grupo Ocupacional
			{
				$('#cargoSemVinculo').click(function() {
					if($(this).is(":checked"))
						addCheckCargoSemGrupo();
					else
						populaCargosByGrupo();
				});
				
				oculta("divAreaOrganizacionals");
				exibe("divGruposCargos");
				populaCargosByGrupo();
				$("label[for='cargoSemVinculo']").text('Considerar cargos não vinculados a nenhuma grupo ocupacional');
			}
			else if(value == '2') // Area Organizacional
			{
				$('#cargoSemVinculo').click(function() {
					if($(this).is(":checked"))
						addCheckCargoSemArea();
					else
						populaCargosByArea();
				});
			
				exibe("divAreaOrganizacionals");
				oculta("divGruposCargos");
				populaCargosByArea();
				$("label[for='cargoSemVinculo']").text('Considerar cargos não vinculados a nenhuma área organizacional');
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
		
		function populaCargosByGrupo()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var gruposIds = getArrayCheckeds(document.forms[0], 'grupoOcupacionalsCheck');
			CargoDWR.getCargoByGrupo(createListCargos, gruposIds, ${empresaId});
			
			if($('#cargoSemGrupo').is(":checked"))
				addCheckCargoSemGrupo();
		}
		
		function addCheckCargoSemGrupo()	
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var gruposIds = getArrayCheckeds(document.forms[0], 'grupoOcupacionalsCheck');
			CargoDWR.getCargoByGruposMaisSemGruposRelacionado(createListCargos, gruposIds, ${empresaId});
		}
		
		function addCheckCargoSemArea()	
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var areasIds = getArrayCheckeds(document.forms[0],'areasCheck');
			CargoDWR.getCargoByAreaMaisSemAreaRelacionada(createListCargos, areasIds, "getNomeMercado", ${empresaId});
		}
		
		function populaCargosByArea()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var areasIds = getArrayCheckeds(document.forms[0],'areasCheck');
			CargoDWR.getCargoByArea(createListCargos, areasIds, "getNomeMercado", ${empresaId});
			
			if($('#cargoSemArea').is(":checked"))
				addCheckCargoSemArea();
		}
		
		function createListCargos(data)
		{
			addChecks('cargosCheck',data);
		}
		
		$(function() {
			$('#cargoSemVinculo').click(function() {
				if($(this).is(":checked"))
					addCheckCargoSemGrupo();
				else
					populaCargosByGrupo();
			});
			populaCargosByGrupo();
		});
		
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="relatorioHistoricoFaixaSalarial.action" validate="true" method="POST">
		
		<@ww.select id="optFiltro" label="Filtrar Por" name="filtro"
				list=r"#{'1':'Grupos Ocupacionais/Cargos','2':'Áreas Organizacionais'}" onchange="filtrarOpt();" />
		
		<span id="divGruposCargos" style="display:''">
			<@frt.checkListBox name="grupoOcupacionalsCheck" id="grupoOcupacionalsCheck" label="Grupos Ocupacionais" list="grupoOcupacionalsCheckList" width="600" onClick="populaCargosByGrupo();" filtro="true"/>
		</span>
		
		<span id="divAreaOrganizacionals" style="display:none">
			<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" width="600" onClick="populaCargosByArea();" filtro="true" selectAtivoInativo="true"/>
		</span>
		<@ww.checkbox label="Considerar cargos não vinculados a nenhuma Grupo Ocupacional" id="cargoSemVinculo" name = "" labelPosition="left"/>

		<@frt.checkListBox name="cargosCheck" label="Cargos*" id="cargosCheck" list="cargosCheckList" width="600" filtro="true" selectAtivoInativo="true"/>
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnImprimirPdf" onclick="validaFormulario('form', new Array('@cargosCheck'), null);"> </button>
	</div>
</body>
</html>