<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>

	<title>Relatório de Ocorrências</title>
	<#assign formAction="buscaOcorrencia.action"/>
	<#assign accessKey="F"/>

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

	<#include "../ftl/mascarasImports.ftl" />
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/OcorrenciaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>
	<script type='text/javascript'>
		var empresaIds = new Array();
		<#if empresaIds?exists>
			<#list empresaIds as empresaId>
				empresaIds.push(${empresaId});
			</#list>
		</#if>
		
		function populaColaboradores(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var areasIds = getArrayCheckeds(document.forms[0], 'areaCheck');
			ColaboradorDWR.getColaboradoresByAreaEmpresas(createListcolaborador, areasIds, empresaId, empresaIds);
		}

		function createListcolaborador(data)
		{
			addChecks('colaboradorCheck',data)
		}

		function valuePonto(ponto)
		{
			if(ponto.checked)
				document.getElementById("ponto").value=true;
			else
				document.getElementById("ponto").value=false;
		}

		function mudaPonto(detalhe)
		{
			if(detalhe.checked == true)
				document.getElementById("detalhe").value = true;
			else
				document.getElementById("detalhe").value= false;
		}
		
		function populaEstabelecimento(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			EstabelecimentoDWR.getByEmpresas(createListEstabelecimento, empresaId, empresaIds);
		}

		function createListEstabelecimento(data)
		{
			addChecks('estabelecimentoCheck',data);
		}
		
		function populaOcorrencia(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			OcorrenciaDWR.getByEmpresas(createListOcorrencia, empresaId, empresaIds);
		}

		function createListOcorrencia(data)
		{
			addChecks('ocorrenciaCheck',data);
		}
		
		function populaArea(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresas(function(data){createListArea(data, empresaId);
												}, empresaId, empresaIds);
		}

		function createListArea(data, empresaId)
		{
			addChecks('areaCheck', data, "populaColaboradores(" + empresaId + ");");
		}
		
		$(document).ready(function($)
		{
			var empresa = $('#empresa').val();
			populaChecks(empresa);
		});
		
		function populaChecks(empresa)
		{
			populaArea(empresa);
			populaEstabelecimento(empresa);
			populaColaboradores(empresa);
			populaOcorrencia(empresa);
		}
	</script>
</head>
<body>
	<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('@estabelecimentoCheck','@colaboradorCheck','@ocorrenciaCheck','dataPrevIni','dataPrevFim'), new Array('dataPrevIni','dataPrevFim'))"/>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="" onchange="populaChecks(this.value);" disabled="!compartilharColaboradores"/>

		<div>Período*:</div>
		<@ww.datepicker name="dataIni" id="dataPrevIni" liClass="liLeft" value="${valueDataIni}" cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft"/>
		<@ww.datepicker name="dataFim" id="dataPrevFim" value="${valueDataFim}" cssClass="mascaraData validaDataFim"/>
		
		<@frt.checkListBox name="estabelecimentoCheck" label="Estabelecimento*" list="estabelecimentoCheckList" width="400" height="120" />
		<@frt.checkListBox name="areaCheck" label="Área Organizacional" list="areaCheckList" width="400" height="120" onClick="populaColaboradores($('#empresa').val());"/>
		<@frt.checkListBox name="colaboradorCheck" label="Colaborador*" list="colaboradorCheckList" width="400" height="120"/>
		<@frt.checkListBox name="ocorrenciaCheck" label="Ocorrência*" list="ocorrenciaCheckList" width="400" height="120"/>

		<@ww.checkbox label="Detalhado"  id="detalhes" onchange="mudaPonto(this);" labelPosition="left" name="det"/>
		<@ww.hidden id = "detalhe" name = "detalhamento" value = "true"/>
		<@ww.hidden  id ="ponto" name = "ponto" value = "false" />

	</@ww.form>


	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio"></button>
		<button onclick="window.location='list.action'" class="btnVoltar" ></button>
	</div>
<br><br>

</body>
</html>