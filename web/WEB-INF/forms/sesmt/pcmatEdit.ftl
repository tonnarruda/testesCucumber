<html>
	<head>
		<title>PCMAT</title>

		<style type="text/css">
			#menuPcmat a#menuGeral{ color: #FFCB03; }
		</style>
	
		<#include "../ftl/mascarasImports.ftl" />

		<@ww.head/>

		<#if pcmat.id?exists>
			<#assign formAction="update.action"/>
		<#else>
			<#assign formAction="insert.action"/>
		</#if>
	
		<#if pcmat?exists && pcmat.APartirDe?exists>
			<#assign aPartirDe=pcmat.APartirDe?date />
		<#else>
			<#assign aPartirDe=""/>
		</#if>
	
		<#if pcmat?exists && pcmat.dataIniObra?exists>
			<#assign dataIniObra=pcmat.dataIniObra?date />
		<#else>
			<#assign dataIniObra=""/>
		</#if>
	
		<#if pcmat?exists && pcmat.dataFimObra?exists>
			<#assign dataFimObra=pcmat.dataFimObra?date />
		<#else>
			<#assign dataFimObra=""/>
		</#if>
	
		<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('aPartirDe','dataIniObra','dataFimObra'), new Array('aPartirDe','dataIniObra','dataFimObra'))"/>
	</head>
	<body>
		<#include "pcmatLinks.ftl"/>
		
		<@ww.actionmessage />
		<@ww.actionerror />
		
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="pcmat.id" />
			<@ww.hidden name="pcmat.obra.id" />
			<@ww.token/>

			<@ww.datepicker label="Data do PCMAT" name="pcmat.aPartirDe" id="aPartirDe" required="true" value="${aPartirDe}" cssClass="mascaraData"/>
			Período da obra:*<br>
			<@ww.datepicker name="pcmat.dataIniObra" id="dataIniObra" required="true" value="${dataIniObra}" cssClass="mascaraData validaDataIni" liClass="liLeft"/>
			<@ww.label value="a" liClass="liLeft"/>
			<@ww.datepicker name="pcmat.dataFimObra" id="dataFimObra" required="true" value="${dataFimObra}" cssClass="mascaraData validaDataFim"/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='listPcmats.action?obra.id=${pcmat.obra.id}'" class="btnVoltar"></button>
		</div>
	</body>
</html>
