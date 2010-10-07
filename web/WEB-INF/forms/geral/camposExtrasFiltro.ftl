<#include "../ftl/mascarasImports.ftl" />

<#list configuracaoCampoExtras as configuracaoCampoExtra>
	
	<#assign data = ""/>
	<#assign dataFim = ""/>
	
	<#if camposExtras?exists>
		<#if camposExtras.data1?exists && configuracaoCampoExtra.nome?exists && configuracaoCampoExtra.nome == "data1">
			<#assign data = camposExtras.data1?date/>
		</#if>
		<#if camposExtras.data2?exists && configuracaoCampoExtra.nome?exists && configuracaoCampoExtra.nome == "data2">
			<#assign data = camposExtras.data2?date/>
		</#if>
		<#if camposExtras.data3?exists && configuracaoCampoExtra.nome?exists && configuracaoCampoExtra.nome == "data3">
			<#assign data = camposExtras.data3?date/>
		</#if>
		
		<#if camposExtras.data1Fim?exists && configuracaoCampoExtra.nome?exists && configuracaoCampoExtra.nome == "data1">
			<#assign dataFim = camposExtras.data1Fim?date/>
		</#if>
		<#if camposExtras.data2Fim?exists && configuracaoCampoExtra.nome?exists && configuracaoCampoExtra.nome == "data2">
			<#assign dataFim = camposExtras.data2Fim?date/>
		</#if>
		<#if camposExtras.data3Fim?exists && configuracaoCampoExtra.nome?exists && configuracaoCampoExtra.nome == "data3">
			<#assign dataFim = camposExtras.data3Fim?date/>
		</#if>
	</#if>
	
	<#if configuracaoCampoExtra.tipo == "texto">
		<@ww.textfield label="${configuracaoCampoExtra.titulo}" name="camposExtras.${configuracaoCampoExtra.nome}" cssStyle="width: 400px;" maxLength="250" />
	</#if>
	
	<#if configuracaoCampoExtra.tipo == "data">
		${configuracaoCampoExtra.titulo}: <br>
		<@ww.datepicker label="" name="camposExtras.${configuracaoCampoExtra.nome}" value="${data}" id="${configuracaoCampoExtra.nome}" theme="simple" cssClass="mascaraData" />
		a
		<@ww.datepicker label="" name="camposExtras.${configuracaoCampoExtra.nome}Fim" value="${dataFim}" id="${configuracaoCampoExtra.nome}Fim" theme="simple" cssClass="mascaraData" />
		<br>
	</#if>
	
	<#if configuracaoCampoExtra.tipo == "valor">
		${configuracaoCampoExtra.titulo}: <br>
		<@ww.textfield label="" name="camposExtras.${configuracaoCampoExtra.nome}" theme="simple" cssClass="currency" cssStyle="width:85px; text-align:right;" />
		a
		<@ww.textfield label="" name="camposExtras.${configuracaoCampoExtra.nome}Fim" theme="simple" cssClass="currency" cssStyle="width:85px; text-align:right;" /><br>
	</#if>
	
	<#if configuracaoCampoExtra.tipo == "numero">
		${configuracaoCampoExtra.titulo}: <br>
		<@ww.textfield label="" name="camposExtras.${configuracaoCampoExtra.nome}" theme="simple" cssStyle="width: 79px;" onkeypress = "return(somenteNumeros(event,''));" maxLength="250" />
		a
		<@ww.textfield label="" name="camposExtras.${configuracaoCampoExtra.nome}Fim" theme="simple" cssStyle="width: 79px;" onkeypress = "return(somenteNumeros(event,''));" maxLength="250" />
	</#if>
	
</#list>

<@ww.hidden name="habilitaCampoExtra"/>
<@ww.hidden name="naoApague" cssClass="naoApague"/>