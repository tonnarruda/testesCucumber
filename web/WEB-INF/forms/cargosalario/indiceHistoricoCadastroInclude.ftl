
<#if !integradoAC>
	<#assign desabilita="false"/>
<#else>
	<#assign desabilita="true"/>
</#if>
<@ww.datepicker label="A partir de" name="indiceHistorico.data" id="dataHist" value="${data}"  disabled="${desabilita}" required="true" cssClass="mascaraData" liClass="liLeft"/>
<@ww.textfield label="Valor" name="indiceHistorico.valor" id="valor"  disabled="${desabilita}" required="true" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12"/>