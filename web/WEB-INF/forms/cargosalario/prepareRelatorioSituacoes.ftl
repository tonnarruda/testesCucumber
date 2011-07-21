<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Relatório de Situações</title>

	<#include "../ftl/mascarasImports.ftl" />

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

	<#assign validarCampos="return validaFormulario('form', new Array('dataIni', 'dataFim', '@estabelecimentosCheck'), new Array('dataIni', 'dataFim'))"/>
	
</head>

<body>
	<@ww.actionmessage />

	<@ww.form name="form" action="relatorioSituacoes.action" onsubmit="${validarCampos}" method="POST">
		<@ww.select label="Origem" id="origem" name="origemSituacao" list=r"#{'T':'Todos','RH':'RH', 'AC':'AC Pessoal'}" cssStyle="width: 96px;"/>
		<div>Período*:</div>
		<@ww.datepicker name="dataIni" id="dataIni" liClass="liLeft" value="${valueDataIni}"  cssClass="mascaraData"/>
		<@ww.label value="a" liClass="liLeft"/>
		<@ww.datepicker name="dataFim" id="dataFim"  value="${valueDataFim}" cssClass="mascaraData"/>
		<@frt.checkListBox label="Estabelecimentos*" name="estabelecimentosCheck" id="estabelecimentoCheck" list="estabelecimentosCheckList" />
		<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areaCheck" list="areasCheckList" />
		
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio">
		</button>
	</div>
</body>
</html>