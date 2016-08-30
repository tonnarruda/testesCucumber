<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>

<@ww.head/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

	<#include "../ftl/mascarasImports.ftl" />

	<script type="text/javascript">
		function validaForm()
		{
			return validaFormularioEPeriodo('form', new Array('dataIni', 'dataFim'), new Array('dataIni','dataFim'));
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

	<title>Indicações de Candidatos</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<#assign formAction = "relatorioCandidatosIndicadosPor.action" />

	<@ww.form name="form" action="${formAction}" method="POST">
		Período:*<br>
		<@ww.datepicker name="dataCadIni" id="dataIni"  value="${dateIni}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="dataCadFim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" />
		
		<@frt.checkListBox name="empresasCheck" id="empresasCheck" label="Empresas" list="empresasCheckList" filtro="true" selectAtivoInativo="true"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="validaForm();" class="btnRelatorio">
		</button>
	</div>
</body>
</html>