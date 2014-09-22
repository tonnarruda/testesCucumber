<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<#include "../ftl/mascarasImports.ftl" />
	<title>EPIs Entregues</title>

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


</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#assign validarCampos="return validaFormulario('form', new Array('periodoIni'), new Array('periodoIni'))"/>
	<@ww.form name="form" action="relatorioEntregaEpi.action" onsubmit="${validarCampos}" method="POST" >
		Período:<br>
		<@ww.datepicker label="Início" name="dataIni" id="periodoIni" cssClass="mascaraData validaDataIni" liClass="liLeft" after="a" value="${dataIni}" required="true"/>
		<@ww.datepicker label="Fim" name="dataFim" id="periodoFim" cssClass="mascaraData validaDataFim" value="${dataFim}"/>
		
		<@frt.checkListBox name="epiCheck" label="EPIs" list="epiCheckList" filtro="true"/>
		<@frt.checkListBox name="colaboradorCheck" label="Colaboradores" list="colaboradorCheckList" filtro="true"/>
		<@ww.select label="Agrupar por" id="agruparPor" name="agruparPor" list=r"#{'E':'Epi','C':'Colaborador'}" />
		
		<@ww.checkbox label="Exibir entregas a colaboradores desligados" id="exibirDesligados" name="exibirDesligados" labelPosition="left"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnRelatorio"></button>
	</div>
</body>
</html>