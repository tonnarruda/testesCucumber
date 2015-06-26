<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Desligamentos</title>

	<#include "../ftl/mascarasImports.ftl" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/populaEstabAreaCargo.js?version=${versao}"/>"></script>
	
	<script type="text/javascript">
		
		var empresaIds = new Array();
		
		<#if empresaIds?exists>
			<#list empresaIds as empresaId>
				empresaIds.push(${empresaId});
			</#list>
		</#if>

		function getAgruparPorMotivo()
		{
			elementLista = document.getElementById('listaColaboradores');
			elementAgrupar = document.getElementById('agruparPorMotivo');
			
			display = "none";
			
			if (elementLista.value == 'true')
				display="";
			
			elementAgrupar.style.display = display;
		}
		
		function submeterAction(action)
		{
			$('form[name=form]').attr('action', action);
			return validaFormularioEPeriodo('form', new Array('dataIni', 'dataFim', '@estabelecimentosCheck'), new Array('dataIni', 'dataFim'));
		}

		$(document).ready(function($)
		{
			DWREngine.setAsync(false);
		
			getAgruparPorMotivo();	
			
			var empresa = $('#empresa').val();
	
			populaAreaComCargoVinculado(empresa);
			populaEstabelecimento(empresa);
			populaCargosByAreaVinculados(empresa);
			
			$('#cargosVinculadosAreas').click(function() {
				populaCargosByAreaVinculados();
			});
			
			$('#cargosVinculadosAreas').attr('checked', true);;
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
</head>

<body>

	<@ww.actionmessage />

	<@ww.form name="form" action="relatorioMotivoDemissao.action" validate="true" method="POST">
		<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="0" onchange="changeEmpresa(this.value);" disabled="!compartilharColaboradores"/>
		<div>Período*:</div>
		<@ww.datepicker name="dataIni" id="dataIni" liClass="liLeft" value="${valueDataIni}"  cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft"/>
		<@ww.datepicker name="dataFim" id="dataFim"  value="${valueDataFim}" cssClass="mascaraData validaDataFim"/>
		
		<@frt.checkListBox label="Estabelecimentos*" name="estabelecimentosCheck" id="estabelecimentoCheck" list="estabelecimentosCheckList" filtro="true"/>
		<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areaCheck" list="areasCheckList" onClick="populaCargosByAreaVinculados()" filtro="true" selectAtivoInativo="true"/>
		<@ww.checkbox label="Exibir somente os cargos vinculados às áreas organizacionais acima." id="cargosVinculadosAreas" name="" labelPosition="left"/>
		<@frt.checkListBox label="Cargo" name="cargosCheck" list="cargosCheckList" filtro="true" selectAtivoInativo="true"/>
		
		<@ww.select label="Colocação do Colaborador" name="vinculo" id="vinculo" list="vinculos" headerKey="" headerValue="Todas" cssStyle="width: 180px;" />
		<@ww.select label="Exibir lista de Colaboradores" id="listaColaboradores" onchange="getAgruparPorMotivo()" name="listaColaboradores" list=r"#{true:'Sim',false:'Não'}" cssStyle="width: 96px;"/>
		
		<span id="agruparPorMotivo">
			<@ww.select label="Agrupar Por" name="agruparPor" list=r"#{'N':'Nenhum','A':'Área Organizacional', 'E':'Estabelecimento', 'M':'Motivo'}" cssStyle="width: 140px;"/>
			<@ww.select label="Exibir observação" name="exibirObservacao" list=r"#{true:'Sim',false:'Não'}" cssStyle="width: 76px;"/>
		</span>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="return submeterAction('relatorioMotivoDemissao.action');" class="btnRelatorio"></button>
		<button onclick="return submeterAction('relatorioMotivoDemissaoXLS.action');" class="btnRelatorioExportar"></button>
	</div>
	
</body>
</html>