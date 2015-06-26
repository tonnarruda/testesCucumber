<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>" type="text/css">
<@ww.head/>
	<title>Clonar Investimentos da Empresa - por Colaborador</title>
	<#assign validarCampos="return validaFormulario('form', null, null)"/>
	<#include "../ftl/mascarasImports.ftl" />
	<script language='javascript'>
		function clonarInvest()
		{
			if(document.getElementById("dataClone").value == "  /    " || document.getElementById("dataClone").value.length != 7)
			{
				jAlert("Informe Mes/Ano no formato mm/aaaa.");
				document.getElementById("dataClone").focus();
			}
			else
			{
				${validarCampos};
			}
		}
	</script>
</head>
<body>

	<@ww.actionerror />
	<@ww.form name="form" action="clonar.action" onsubmit="${validarCampos}" method="POST">
		<@ww.textfield label="Clonar para (Mês/Ano)" name="dataClone" id="dataClone" cssClass="mascaraMesAnoData"/>

		<@ww.label label="Mês/Ano" name="dataMesAno"/>
		<@ww.label label="Colaborador" name="gastoEmpresa.colaborador.nome" />
		<@ww.hidden name="gastoEmpresa.id" />
		<br>

		<@display.table name="itens" id="gastoEmpresaItem" class="dados" >
			<@display.column property="gasto.nome" title="Gastos não Eventuais (Importáveis)"/>
			<@display.column property="valor" title="Valor"/>
		</@display.table>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="javascript:clonarInvest();" class="btnClonar" accesskey="C" />
		<button onclick="window.location='list.action'" class="btnVoltar" accesskey="V" />
	</div>
</body>
</html>