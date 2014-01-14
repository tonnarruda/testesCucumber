<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Resumo de Afastamentos</title>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	
	<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('inicio','fim'), new Array('inicio','fim'))"/>

	<#if colaboradorAfastamento.inicio?exists >
		<#assign inicio = colaboradorAfastamento.inicio?date/>
	<#else>
		<#assign inicio = ""/>
	</#if>
	<#if colaboradorAfastamento.fim?exists>
		<#assign fim = colaboradorAfastamento.fim?date/>
	<#else>
		<#assign fim = ""/>
	</#if>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" id="form" action="relatorioResumoAfastamentos.action" method="POST">
		Período:*<br>
		<@ww.datepicker name="colaboradorAfastamento.inicio" id="inicio" value="${inicio}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="colaboradorAfastamento.fim" id="fim" value="${fim}" cssClass="mascaraData validaDataFim" />

		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimento" list="estabelecimentosCheckList" />
		<@frt.checkListBox name="areasCheck" label="Área Organizacional" list="areasCheckList"/>
		<@frt.checkListBox name="motivosCheck" label="Motivo de Afastamento" list="motivosCheckList"/>
		
		<@ww.select label="Ordenar por" name="ordenarPor" id="ordenarPor" list=r"#{'N':'Nome do colaborador','D':'Qtde de dias de afastamento (decrescente)'}" cssStyle="width:500px"/>
		<@ww.select label="Caso um período de afastamento passe de um mês a outro, os dias deverão ser totalizados" name="totalizarDiasPor" id="totalizarDiasPor" list=r"#{'I':'No mês de início do período','D':'Distribuídos nos meses correspondentes'}" cssStyle="width:500px"/>
		<@ww.checkbox label="Agrupar por área organizacional" id="agruparPorArea" name="agruparPorArea" labelPosition="left"/>
		
		<div class="buttonGroup">
			<button class="btnRelatorio" onclick="${validarCampos}"></button>
		</div>
	</@ww.form>
</body>
</html>