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
		<@ww.select label="Origem" id="origem" name="origemSituacao" list=r"#{'T':'Todos','RH':'RH', 'AC':'Fortes Pessoal'}" cssStyle="width: 96px;"/>
		<div>Período*:</div>
		<@ww.datepicker name="dataIni" id="dataIni" liClass="liLeft" value="${valueDataIni}"  cssClass="mascaraData"/>
		<@ww.label value="a" liClass="liLeft"/>
		<@ww.datepicker name="dataFim" id="dataFim"  value="${valueDataFim}" cssClass="mascaraData"/>
		<@frt.checkListBox label="Estabelecimentos*" name="estabelecimentosCheck" id="estabelecimentoCheck" list="estabelecimentosCheckList" filtro="true"/>
		<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areaCheck" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
		<@ww.select label="Agrupar por" name="agruparPor" list=r"#{'A':'Área Organizacional','M':'Mês / Ano','C':'Colaborador'}"/>
		<@ww.checkbox label="Imprimir desligados" id="imprimirDesligados" name="imprimirDesligados" labelPosition="left"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="$('form[name=form]').attr('action', 'relatorioSituacoes.action');${validarCampos};" class="btnRelatorio"></button>
		<button class="btnRelatorioExportar" onclick="$('form[name=form]').attr('action', 'relatorioSituacoesXLS.action');${validarCampos};"></button>
	</div>
</body>
</html>