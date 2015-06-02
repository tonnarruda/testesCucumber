<html>
	<head>
		<title>PCMAT</title>

		<style type="text/css">
			#menuPcmat a#menuGeral{ border-bottom: 2px solid #5292C0; }
		</style>
	
		<#include "../ftl/mascarasImports.ftl" />

		<@ww.head/>

		<#if pcmat.id?exists>
			<#assign formAction="update.action"/>
			<#assign mostraBtnGravar = ultimoPcmatId == pcmat.id/>
		<#else>
			<#assign mostraBtnGravar = true/>
			<#assign formAction = "insert.action"/>
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
	
		<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('aPartirDe','dataIniObra','dataFimObra','qtdFuncionarios'), new Array('aPartirDe','dataIniObra','dataFimObra'))"/>
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
			<@ww.textfield label="Nº máximo de funcionários" name="pcmat.qtdFuncionarios" id="qtdFuncionarios" cssStyle="width:40px; text-align:right;" maxLength="5" onkeypress="return somenteNumeros(event,',');" required="true"/>
			<@ww.textarea label="Objetivo" name="pcmat.objetivo" id="objetivo"/>
			
			<fieldset>
				<legend>Textos introdutórios</legend>
				<@ww.textarea label="Condições de Trabalho" name="pcmat.textoCondicoesTrabalho" id="textoCondicoesTrabalho" liClass="liLeft"/>
				<@ww.textarea label="Áreas de Vivência" name="pcmat.textoAreasVivencia" id="textoAreasVivencia"/>
				<@ww.textarea label="Atividades de Segurança" name="pcmat.textoAtividadesSeguranca" id="textoAtividadesSeguranca" liClass="liLeft"/>
				<@ww.textarea label="EPIs" name="pcmat.textoEpis" id="textoEpis"/>
				<@ww.textarea label="EPCs" name="pcmat.textoEpcs" id="textoEpcs" liClass="liLeft"/>
				<@ww.textarea label="Sinalização" name="pcmat.textoSinalizacao" id="textoSinalizacao"/>
			</fieldset>
		</@ww.form>
	
		<div class="buttonGroup">
			<#if mostraBtnGravar>
				<button onclick="${validarCampos};" class="btnGravar"></button>
			</#if>
			
			<button onclick="window.location='listPcmats.action?obra.id=${pcmat.obra.id}'" class="btnVoltar"></button>
		</div>
	</body>
</html>
