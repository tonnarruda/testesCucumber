<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/formataValores.js?version=${versao}"/>"></script>
	<#include "../ftl/mascarasImports.ftl" />
<@ww.head/>
<#if beneficio.id?exists>
	<title>Editar Benefício</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
	<#assign validarCampos="return validaFormulario('form', new Array('nome'), null)"/>
<#else>
	<title>Inserir Benefício</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
	<#assign validarCampos="return validaFormulario('form', new Array('dataHist','nome','valor','perColab','perDir','perInd'), new Array('dataHist'))"/>
</#if>

<#if historicoBeneficio?exists && historicoBeneficio.data?exists>
	<#assign data = historicoBeneficio.data/>
<#else>
	<#assign data = ""/>
</#if>

<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">

		<#if !beneficio.id?exists>
			<@ww.datepicker label="A partir de" name="historicoBeneficio.data" value="${data}" id="dataHist" required="true" cssClass="mascaraData" />
		</#if>

		<@ww.textfield label="Nome" name="beneficio.nome" id="nome" cssClass="inputNome" maxLength="100" required="true" />

		<#if !beneficio.id?exists>
			<@ww.textfield label="Valor" name="historicoBeneficio.valor" id="valor" required="true" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12"/>
			<@ww.textfield label="Percentual pago pelo Colaborador" onkeypress = "return(somenteNumeros(event,'{,}'));" name="historicoBeneficio.paraColaborador" id="perColab" required="true" maxLength="4" cssStyle="text-align:right; width:30px;"/>
			<@ww.textfield label="Percentual pago pelo Dependente Direto" onkeypress = "return(somenteNumeros(event,'{,}'));" name="historicoBeneficio.paraDependenteDireto" id="perDir" required="true" maxLength="4" cssStyle="text-align:right; width:30px;" />
			<@ww.textfield label="Percentual pago pelo Dependente Indireto" onkeypress = "return(somenteNumeros(event,'{,}'));" name="historicoBeneficio.paraDependenteIndireto" id="perInd" required="true" maxLength="4" cssStyle="text-align:right; width:30px;"/>
		</#if>

		<@ww.hidden label="Id" name="beneficio.id" />
		<@ww.hidden label="empresaId" name="beneficio.empresa.id" />
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="${validarCampos};"  class="btnGravar" accesskey="${accessKey}">
		</button>

	<#if beneficio.id?exists && historicoBeneficios?exists>
		</div>
		<br>
		<@display.table name="historicoBeneficios" id="historicoBeneficio" pagesize=10 class="dados" defaultsort=2 sort="list">
			<@display.column title="Ações" class="acao">
				<a href="../historicoBeneficio/prepareUpdate.action?historicoBeneficio.id=${historicoBeneficio.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='../historicoBeneficio/delete.action?historicoBeneficio.id=${historicoBeneficio.id}&beneficio.id=${beneficio.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			</@display.column>
			<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="text-align: center;width:80px;"/>
			<@display.column property="valor" title="Valor" format="{0, number, #,##0.00}" style="text-align: center;width:80px;"/>
			<@display.column property="paraColaborador" title="Percentual pago pelo Colaborador" format="{0,number,#,###.00}%" style="text-align: center;width:120px;"/>

		</@display.table>

	<div class="buttonGroup">
		<button onclick="window.location='../historicoBeneficio/prepareInsert.action?beneficio.id=${beneficio.id}'" class="btnInserir" accesskey="I">
		</button>
	</#if>

		<button onclick="window.location='list.action'" class="btnVoltar" accesskey="V">
		</button>
	</div>
</body>
</html>