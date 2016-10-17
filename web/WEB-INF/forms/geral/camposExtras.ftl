<#list configuracaoCampoExtras as configuracaoCampoExtra>
	<#assign data = ""/>
	
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
	</#if>
		
	<@ww.div id="wwgrp_${configuracaoCampoExtra.nome}" cssClass="campo">
		<#if configuracaoCampoExtra.tipo == "texto">
			<@ww.textfield id="${configuracaoCampoExtra.nome}" label="${configuracaoCampoExtra.titulo}" name="camposExtras.${configuracaoCampoExtra.nome}" cssStyle="width: 400px;" maxLength="250" />
		</#if>
		<#if configuracaoCampoExtra.tipo == "textolongo">
			<@ww.textarea id="${configuracaoCampoExtra.nome}" label="${configuracaoCampoExtra.titulo}" name="camposExtras.${configuracaoCampoExtra.nome}" cssStyle="width: 396px; height: 100px;" />
		</#if>
		<#if configuracaoCampoExtra.tipo == "data">
			<@ww.datepicker id="${configuracaoCampoExtra.nome}" label="${configuracaoCampoExtra.titulo}" name="camposExtras.${configuracaoCampoExtra.nome}" value="${data}" cssClass="mascaraData" />
		</#if>
		<#if configuracaoCampoExtra.tipo == "valor">
			<@ww.textfield id="${configuracaoCampoExtra.nome}" label="${configuracaoCampoExtra.titulo}" name="camposExtras.${configuracaoCampoExtra.nome}" cssClass="currency" cssStyle="width:85px; text-align:right;" onkeypress = "return(somenteNumeros(event,''));" maxLength="250"/>
		</#if>
		<#if configuracaoCampoExtra.tipo == "numero">
			<@ww.textfield id="${configuracaoCampoExtra.nome}" label="${configuracaoCampoExtra.titulo}" name="camposExtras.${configuracaoCampoExtra.nome}" cssStyle="width: 79px;" onkeypress = "return(somenteNumeros(event,''));" maxLength="250" />
		</#if>
	</@ww.div>
	
</#list>

<@ww.hidden name="habilitaCampoExtra"/>