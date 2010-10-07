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
	
	<#if configuracaoCampoExtra.tipo == "texto">
		<@ww.textfield label="${configuracaoCampoExtra.titulo}" name="camposExtras.${configuracaoCampoExtra.nome}" cssStyle="width: 400px;" maxLength="250" />
	</#if>
	<#if configuracaoCampoExtra.tipo == "data">
		<@ww.datepicker label="${configuracaoCampoExtra.titulo}" name="camposExtras.${configuracaoCampoExtra.nome}" value="${data}" id="${configuracaoCampoExtra.nome}" cssClass="mascaraData" />
	</#if>
	<#if configuracaoCampoExtra.tipo == "valor">
		<@ww.textfield label="${configuracaoCampoExtra.titulo}" name="camposExtras.${configuracaoCampoExtra.nome}" cssClass="currency" cssStyle="width:85px; text-align:right;" />
	</#if>
	<#if configuracaoCampoExtra.tipo == "numero">
		<@ww.textfield label="${configuracaoCampoExtra.titulo}" name="camposExtras.${configuracaoCampoExtra.nome}" cssStyle="width: 79px;" onkeypress = "return(somenteNumeros(event,''));" maxLength="250" />
	</#if>
</#list>

<@ww.hidden name="habilitaCampoExtra"/>