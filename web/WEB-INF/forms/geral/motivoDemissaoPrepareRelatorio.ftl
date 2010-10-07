<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Desligamentos</title>

	<#include "../ftl/mascarasImports.ftl" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<script type="text/javascript">
		
		var empresaIds = new Array();
		
		<#if empresaIds?exists>
			<#list empresaIds as empresaId>
				empresaIds.push(${empresaId});
			</#list>
		</#if>
		
		function populaEstabelecimento(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			EstabelecimentoDWR.getByEmpresas(createListEstabelecimento, empresaId, empresaIds);
		}

		function createListEstabelecimento(data)
		{
			addChecks('estabelecimentosCheck',data);
		}
		
		function populaArea(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresas(createListArea, empresaId, empresaIds);
		}

		function createListArea(data)
		{
			addChecks('areasCheck',data, 'populaCargosByArea();');
		}
		
		function populaCargosByArea()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var areasIds = getArrayCheckeds(document.forms[0],'areasCheck');
			if(areasIds.length == 0)
			{
				var empresaId = jQuery('#empresa').val();
				CargoDWR.getByEmpresas(createListCargosByArea, empresaId, empresaIds);
			}
			else
				CargoDWR.getCargoByArea(createListCargosByArea, areasIds, "getNomeMercadoComEmpresa");
		}

		function createListCargosByArea(data)
		{
			addChecks('cargosCheck',data);
		}
		
		function populaCargo(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			CargoDWR.getByEmpresas(createListCargoByEmpresa, empresaId, empresaIds);
		}

		function createListCargoByEmpresa(data)
		{
			addChecks('cargosCheck',data);
		}

		function getAgruparPorMotivo()
		{
			elementLista = document.getElementById('listaColaboradores');
			elementAgrupar = document.getElementById('agruparPorMotivo');
			
			display = "none";
			
			if (elementLista.value == 'true')
				display="";
			
			elementAgrupar.style.display = display;
		}
		
		jQuery(document).ready(function($)
		{
			getAgruparPorMotivo();	
			var empresa = jQuery('#empresa').val();
			
			populaArea(empresa);
			populaEstabelecimento(empresa);
			populaCargo(empresa);
		});
	</script>

	<#if dataIni?exists>
		<#assign valueDataIni = dataIni?date/>
	<#else>
		<#assign valueDataIni = ""/>
	</#if>

	<#if dataFim?exists>
		<#assign valueDataFim = dataFim?date/>
	<#else>
		<#assign valueDataFim = ""/>
	</#if>

	<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('dataIni', 'dataFim', '@estabelecimentosCheck'), new Array('dataIni', 'dataFim'))"/>	
</head>

<body>

	<@ww.actionmessage />

	<@ww.form name="form" action="relatorioMotivoDemissao.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="0" onchange="populaEstabelecimento(this.value);populaArea(this.value);populaCargo(this.value);"/>
		<div>Período*:</div>
		<@ww.datepicker name="dataIni" id="dataIni" liClass="liLeft" value="${valueDataIni}"  cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft"/>
		<@ww.datepicker name="dataFim" id="dataFim"  value="${valueDataFim}" cssClass="mascaraData validaDataFim"/>
		<@frt.checkListBox label="Estabelecimentos*" name="estabelecimentosCheck" id="estabelecimentoCheck" list="estabelecimentosCheckList" />
		<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areaCheck" list="areasCheckList" onClick="populaCargosByArea();" />
		<@frt.checkListBox label="Cargo" name="cargosCheck" list="cargosCheckList" />

		<@ww.select label="Exibir lista de Colaboradores" id="listaColaboradores" onchange="getAgruparPorMotivo()" name="listaColaboradores" list=r"#{true:'Sim',false:'Não'}" cssStyle="width: 96px;"/>
		
		<span id="agruparPorMotivo">
			<@ww.select label="Agrupar por motivo" name="agruparPorMotivo" list=r"#{true:'Sim',false:'Não'}" cssStyle="width: 76px;"/>
			<@ww.select label="Exibir observação" name="exibirObservacao" list=r"#{true:'Sim',false:'Não'}" cssStyle="width: 76px;"/>
		</span>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio"></button>
	</div>
	
</body>
</html>