<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Lista de Candidatos da Seleção</title>
	
	<#include "../ftl/mascarasImports.ftl" />
		
	<#if dataIni?exists>
		<#assign valueDataIni = dataIni?date/>
	<#else>
		<#assign valueDataIni = ""/>
	</#if>

	<#if dataFim?exists>
		<#assign valueDataFim = dataFim?date/>
	<#else>
		<#assign valueDataFim = ""/>
	</#if>

	<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('dataIni', 'dataFim'), new Array('dataIni', 'dataFim'))"/>
	
</head>

<body>
<@ww.actionmessage />
<@ww.form name="form" action="imprimirRelatorio.action" onsubmit="${validarCampos}"  validate="true" method="POST">

	<div>Período da Data de Realização da Etapa*:</div>
	<@ww.datepicker name="dataIni" id="dataIni" liClass="liLeft" value="${valueDataIni}"  cssClass="mascaraData validaDataIni"/>
	<@ww.label value="a" liClass="liLeft"/>
	<@ww.datepicker name="dataFim" id="dataFim"  value="${valueDataFim}" cssClass="mascaraData validaDataFim"/>

	<@frt.checkListBox name="etapaCheck" id="etapaCheck" label="Etapas" list="etapaSeletivaCheckList" filtro="true"/>
	<@ww.select label="Solicitações de Pessoal" name="statusSolicitacao" list=r"#{'T':'Abertas e Encerradas', 'A':'Abertas', 'E':'Encerradas'}"/>
	<@ww.select label="Situação dos Candidatos" name="situacaoCandidato" list=r"#{'I':'Aptos e Não Aptos','S':'Aptos','N':'Não Aptos'}" />
	<@ww.checkbox label="Imprimir observação" id="imprimirObservacao" name="imprimirObservacao" labelPosition="left" />
</@ww.form>


<div class="buttonGroup">
	<button onclick="${validarCampos};" class="btnRelatorio">
	</button>
</div>
</body>
</html>