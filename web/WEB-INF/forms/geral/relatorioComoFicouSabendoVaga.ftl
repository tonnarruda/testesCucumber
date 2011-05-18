<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>

<@ww.head/>

	<#include "../ftl/mascarasImports.ftl" />

	<script type="text/javascript">
		function validaForm()
		{
			return validaFormularioEPeriodo('form', new Array('dataIni','dataFim'), new Array('dataIni','dataFim'));
		}
	</script>

	<#if dataIni?exists>
		<#assign dateIni = dataIni?date/>
	<#else>
		<#assign dateIni = ""/>
	</#if>
	<#if dataFim?exists>
		<#assign dateFim = dataFim?date/>
	<#else>
		<#assign dateFim = ""/>
	</#if>

	<title>Relatório Como Ficou Sabendo da Vaga</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<#assign formAction = "imprimirRelatorioComoFicouSabendoVaga.action" />

	<@ww.form name="form" action="${formAction}" method="POST">
		Período:*<br>
		<@ww.datepicker name="dataCadIni" id="dataIni"  value="${dateIni}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="dataCadFim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="validaForm();" class="btnRelatorio">
		</button>
	</div>

	<script type="text/javascript">
		filtrarOpt(document.getElementById('filtrarPor').value);
	</script>
</body>
</html>