<#if faixaSalarialHistorico?exists && faixaSalarialHistorico.id?exists>
	<@ww.textfield label="A partir de" value="${data}" id="data"  cssClass="mascaraData" disabled="true" liClass="liLeft"/>
	<@ww.hidden name="faixaSalarialHistorico.data" />
<#else>
	<@ww.datepicker label="A partir de" name="faixaSalarialHistorico.data" id="data" cssClass="mascaraData" required="true" value="${data}" liClass="liLeft" onchange="calculaValor();" onblur="calculaValor();"/>
</#if>


<@ww.select label="Tipo" name="faixaSalarialHistorico.tipo" id="tipo" list="tipoAplicacaoIndices" onchange="alteraTipo(this.value, ${tipoAplicacaoIndice.getIndice()});"/>

<li>
	<@ww.div id="divValor" cssStyle="display:none;">
		<ul>
			<@ww.textfield label="Valor" name="faixaSalarialHistorico.valor" id="valor" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12" required="true"/>
		</ul>
	</@ww.div>
	<@ww.div id="divQuantidade">
		<ul>
			<@ww.select label="Índice" name="faixaSalarialHistorico.indice.id" id="indice" required="true" list="indices" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." liClass="liLeft" onchange="calculaValor();"/>
			<@ww.textfield label="Qtd." name="faixaSalarialHistorico.quantidade" id="quantidade" onkeypress="return(somenteNumeros(event,'{,}'));" onchange="calculaValor();" cssStyle="width: 30px;text-align:right;" maxLength="6" required="true" liClass="liLeft"/>
			<@ww.textfield label="Valor Atual" id="salarioCalculado" cssStyle="width:85px; text-align:right;" disabled="true" />
		</ul>
	</@ww.div>
</li>