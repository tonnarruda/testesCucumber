<html>
<head>
	<@ww.head/>
	<#if comissao.id?exists>
		<#assign edicao="true"/>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
	<#else>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
	</#if>
	<style type="text/css">#menuComissao a.ativaGeral{  border-bottom: 2px solid #5292C0;}</style>
	<#include "../ftl/mascarasImports.ftl" />

	<#assign dateIni = ""/>
	<#assign dateFim = ""/>
	<#if comissao.dataIni?exists>
		<#assign dateIni = comissao.dataIni?date/>
	</#if>
	<#if comissao.dataFim?exists>
		<#assign dateFim = comissao.dataFim?date/>
	</#if>

	<#assign validarCampos="return validaFormularioEPeriodo('form',new Array('eleicao','dataIni','dataFim'),new Array('dataIni','dataFim'))"/>

</head>
<body>
	<#if edicao?exists>
		<title>CIPA</title>
		<#include "comissaoLinks.ftl" />
    <#else>
		<title>Inserir Comissão</title>
    </#if>
<@ww.actionerror />
<@ww.form name="form" action="${formAction}" method="POST">
	Período do Mandato:*<br/>
	<@ww.datepicker name="comissao.dataIni" id="dataIni"  value="${dateIni}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
	<@ww.label value="a" liClass="liLeft" />
	<@ww.datepicker name="comissao.dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" />
	<@ww.select label="Eleição" id="eleicao" name="eleicao.id" required="true" list="eleicaos" listKey="id" listValue="descricaoFormatada" headerValue="Selecione..." headerKey=""/>

	<@ww.hidden name="comissao.id"/>
	<@ww.token/>
</@ww.form>


<div class="buttonGroup">
<button onclick="${validarCampos}" class="btnGravar" accesskey="${accessKey}"></button>
<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V"></button>
</div>
</body>
</html>