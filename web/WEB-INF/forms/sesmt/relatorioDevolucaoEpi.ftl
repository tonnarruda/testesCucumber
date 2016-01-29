<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<#include "../ftl/mascarasImports.ftl" />
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<title>EPIs Devolvidos</title>

	<#if dataIni?exists>
		<#assign dataIni = dataIni?date/>
	<#else>
		<#assign dataIni = ""/>
	</#if>
	<#if dataFim?exists>
		<#assign dataFim = dataFim?date/>
	<#else>
		<#assign dataFim = ""/>
	</#if>

	<script type='text/javascript'>
		function submeterAction(action){
			$('form[name=form]').attr('action', action);
			return validaFormularioEPeriodo('form', new Array('periodoIni'), new Array('periodoIni','periodoFim'));
		}
		
		function populaColaboradores(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var areasIds = getArrayCheckeds(document.forms[0], 'areasCheck');
			
			ColaboradorDWR.getColaboradoresByArea(createListcolaborador, areasIds, empresaId);
		}

		function createListcolaborador(data)
		{
			addChecksByMap('colaboradorCheck',data)
		}
		
	</script>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" id="form" action="relatorioEntregaEpi.action" method="POST" >
		Período:<br>
		<@ww.datepicker label="Início" name="dataIni" id="periodoIni" cssClass="mascaraData validaDataIni" liClass="liLeft" after="a" value="${dataIni}" required="true"/>
		<@ww.datepicker label="Fim" name="dataFim" id="periodoFim" cssClass="mascaraData validaDataFim" value="${dataFim}"/>
		<@ww.hidden id="empresa" name="empresa.id" value="${empresa.id}"/>
					
		<@frt.checkListBox name="epiCheck" label="EPIs" list="epiCheckList" filtro="true"/>
		<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areasCheckList" onClick="populaColaboradores($('#empresa').val());" filtro="true" selectAtivoInativo="true"/>
		<@frt.checkListBox name="colaboradorCheck" label="Colaboradores" list="colaboradorCheckList" filtro="true"/>
		<@ww.select label="Agrupar por" id="agruparPor" name="agruparPor" list=r"#{'E':'Epi','C':'Colaborador'}" />
		
		<@ww.checkbox label="Exibir devoluções de colaboradores desligados" id="exibirDesligados" name="exibirDesligados" labelPosition="left"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="return submeterAction('relatorioDevolucaoEpi.action');" class="btnRelatorio" ></button>
		<button onclick="return submeterAction('relatorioDevolucaoEpiXls.action');" class="btnRelatorioExportar"></button>
	</div>
</body>
</html>