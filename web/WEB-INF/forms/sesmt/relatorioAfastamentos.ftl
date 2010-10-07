<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Relatório de Afastamentos</title>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign validarCampos="return validaFormularioEPeriodo('form', null, new Array('inicio','fim'))"/>

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

	<@ww.form name="form" id="form" action="relatorioAfastamentos.action" method="POST">
		Período:<br>
		<@ww.datepicker name="colaboradorAfastamento.inicio" id="inicio"  value="${inicio}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="colaboradorAfastamento.fim" id="fim" value="${fim}" cssClass="mascaraData validaDataFim" />

		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimento" list="estabelecimentosCheckList" />

		<@ww.textfield label="Colaborador" name="nomeBusca" id="nomeBusca" cssStyle="width: 260px;"/>
		<@ww.select label="Motivo" name="colaboradorAfastamento.afastamento.id" id="tipo" required="true" list="afastamentos" listKey="id" listValue="descricao" headerKey="" headerValue="Todos"/>
		
		<@ww.select label="Ordenar por" name="ordenaColaboradorPorNome" id="ordenaColaboradorPorNome" list=r"#{true:'Nome',false:'Data'}" required="true"/>

		<div class="buttonGroup">
			<button class="btnRelatorio" onclick="${validarCampos}">
			</button>
		</div>
	</@ww.form>
</body>
</html>