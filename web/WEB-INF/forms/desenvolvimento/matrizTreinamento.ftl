<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
	<@ww.head/>
		<title>Matriz de Treinamentos</title>
	
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/FaixaSalarialDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
		<script type="text/javascript">	
			function populaCargosByArea()
			{
				DWRUtil.useLoadingMessage('Carregando...');
				var areasIds = getArrayCheckeds(document.forms[0],'areasCheck');
				CargoDWR.getByAreaDoHistoricoColaborador(createListCargos, areasIds);
			}
	
			function createListCargos(data)
			{
				addChecks('cargosCheck',data, 'populaFaixas();')
			}
			
			function populaFaixas()
			{
				var cargosIds = getArrayCheckeds(document.forms[0],'cargosCheck');
				if(cargosIds.length == 1)
				{
					document.getElementById("listCheckBoxfaixaSalarialsCheck").style.backgroundColor='#FFF';
					FaixaSalarialDWR.getByCargo(createListFaixas, cargosIds[0]);
				}
				else if(cargosIds.length > 1)
				{
					document.getElementById("listCheckBoxfaixaSalarialsCheck").style.backgroundColor='#DEDEDE';
					FaixaSalarialDWR.getByCargo(createListFaixas, "");
				}
			}
	
			function createListFaixas(data)
			{
				addChecks('faixaSalarialsCheck', data)
			}
		</script>


		<#assign validarCampos="return validaFormulario('form', new Array('@areasCheck','@cargosCheck'), null)"/>
	</head>
	<body>
		<@ww.actionmessage />
		<@ww.actionerror />
		
		<@ww.form name="form" action="imprimirMatrizTreinamento.action" onsubmit="${validarCampos}" validate="true" method="POST">
			<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais*" list="areasCheckList" onClick="populaCargosByArea();" filtro="true" selectAtivoInativo="true"/>
			<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos*" list="cargosCheckList" onClick="populaFaixas();" filtro="true" selectAtivoInativo="true"/>
			<@frt.checkListBox name="faixaSalarialsCheck" id="faixaSalarialsCheck" label="Faixas Salariais (disponível se for selecionado apenas um Cargo)" list="faixaSalarialsCheckList" filtro="true"/>
		</@ww.form>
		
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnRelatorio"></button>
		</div>
	</body>
</html>