<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Descrição de Cargos</title>
	<#assign formAction="relatorioCargo.action"/>
	<#assign accessKey="I"/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>

	<script type="text/javascript">

		function populaCargos()
		{
			var tipoFiltro = $('#optFiltro').val();
			
			dwr.util.useLoadingMessage('Carregando...');
			
			if(tipoFiltro == '1') {
				var areaIds = getArrayCheckeds(document.forms[0], 'areasCheck');
				
				if ($('#exibirCargosVinculados').is(":checked")) {
					CargoDWR.getCargoByArea(areaIds, 'getNomeMercado', ${empresaSistema.id}, createListCargos);
				} else {
					CargoDWR.getCargoByArea(null, 'getNomeMercado', ${empresaSistema.id}, createListCargos);
				}
			} else {
				var grupoIds = getArrayCheckeds(document.forms[0], 'gruposCheck');
				
				if ($('#exibirCargosVinculados').is(":checked")) {
					CargoDWR.getCargoByGrupo(grupoIds, ${empresaSistema.id}, createListCargos);
				} else {
					CargoDWR.getCargoByGrupo(null, ${empresaSistema.id}, createListCargos);
				}
			}
		}

		function createListCargos(data)
		{
			addChecks('cargosCheck',data)
		}

		function filtrarOpt(){
			var tipoFiltro = $('#optFiltro').val();
			
			if(tipoFiltro == "1") {
				$('#divAreas').show();
				$('#divGrupos').hide();
				$("label[for='exibirCargosVinculados']").text('Exibir somente os cargos vinculados às áreas organizacionais acima.');
			} else if(tipoFiltro == "2") {
				$('#divAreas').hide();
				$('#divGrupos').show();
				$("label[for='exibirCargosVinculados']").text('Exibir somente os cargos vinculados aos grupos ocupacionais acima.');
			}
			
			populaCargos();
		}
		
		$(function() {
			$('#exibirCargosVinculados').bind('click', populaCargos);
			
			$('#exibirCargosVinculados').attr('checked',true);
		});
	</script>

</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />

	<#assign validarCampos="return validaFormulario('form', new Array('@cargosCheck'), null)"/>

	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.select id="optFiltro" label="Filtrar Por" name="filtro" list=r"#{'1':'Área Organizacional', '2':'Grupo Ocupacional'}" onchange="filtrarOpt();"/>
		<div id="divAreas">
			<@frt.checkListBox name="areasCheck" id="areasCheck" label="Área Organizacional" list="areasCheckList" onClick="populaCargos();" filtro="true" selectAtivoInativo="true"/>
		</div>
		<div id="divGrupos" style="display:none;">
			<@frt.checkListBox name="gruposCheck" id="gruposCheck" label="Grupos Ocupacionais" list="gruposCheckList" onClick="populaCargos();" filtro="true"  selectAtivoInativo="true"/>
		</div>
		<@ww.checkbox label="Exibir somente os cargos vinculados às áreas organizacionais acima." id="exibirCargosVinculados" name="" labelPosition="left"/>
		<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos *" list="cargosCheckList" filtro="true" selectAtivoInativo="true"/>
		<@ww.checkbox label="Exibir valores atuais das faixas salariais." id="exibirValorFaixaSalarial" name="exibirValorFaixaSalarial" labelPosition="left"/>
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio" onclick="${validarCampos};" accesskey="${accessKey}">
		</button>
	</div>
</body>
</html>