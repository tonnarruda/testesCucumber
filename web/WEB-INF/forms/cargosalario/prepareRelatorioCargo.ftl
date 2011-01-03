<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Descrição de Cargos</title>
	<#assign formAction="relatorioCargo.action"/>
	<#assign accessKey="I"/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<script type="text/javascript">

		function populaCargosByGrupo(frm, nameCheck)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var gruposIds = getArrayCheckeds(frm, nameCheck);
			CargoDWR.getCargoByGrupo(createListCargos, gruposIds, <@authz.authentication operation="empresaId"/>);
		}

		function populaCargosByArea(frm, nameCheck)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var areasIds = getArrayCheckeds(frm, nameCheck);
			CargoDWR.getCargoByArea(createListCargos, areasIds, "getNomeMercado", <@authz.authentication operation="empresaId"/>);
		}

		function createListCargos(data)
		{
			addChecks('cargosCheck',data)
		}

		function filtrarOpt(){
			value =	document.getElementById('optFiltro').value;
			if(value == "1") {
				document.getElementById('divAreas').style.display = "";
				document.getElementById('divGrupos').style.display = "none";
			} else if(value == "2") {
				document.getElementById('divAreas').style.display = "none";
				document.getElementById('divGrupos').style.display = "";
			}
		}

	</script>

</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />

	<#assign validarCampos="return validaFormulario('form', new Array('@cargosCheck'), null)"/>

	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.select id="optFiltro" label="Filtrar Por" name="filtro" list=r"#{'1':'Área Organizacional', '2':'Grupo Ocupacional'}" onchange="filtrarOpt();"/>
		<div id="divAreas">
			<@frt.checkListBox name="areasCheck" id="areasCheck" label="Área Organizacional" list="areasCheckList" onClick="populaCargosByArea(document.forms[0],'areasCheck');"/>
		</div>
		<div id="divGrupos" style="display:none;">
			<@frt.checkListBox name="gruposCheck" id="gruposCheck" label="Grupos Ocupacionais" list="gruposCheckList" onClick="populaCargosByGrupo(document.forms[0],'gruposCheck');"/>
		</div>
		<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos *" list="cargosCheckList" />
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio" onclick="${validarCampos};" accesskey="${accessKey}">
		</button>
	</div>
</body>
</html>