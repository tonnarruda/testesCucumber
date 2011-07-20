<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<#if historicoFuncao.id?exists>
	<title>Editar Histórico da Função - ${cargoTmp.nome} / ${funcao.nome}</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Histórico da Função - ${cargoTmp.nome} / ${funcao.nome}</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<#if historicoFuncao.data?exists>
		<#assign data = historicoFuncao.data>
	<#else>
		<#assign data = "">
</#if>
	<#include "../ftl/mascarasImports.ftl" />

<#assign validarCampos="return validaFormulario('form', new Array('dataHist','descricao'), new Array('dataHist'))"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.datepicker label="A partir de" name="historicoFuncao.data" id="dataHist" required="true" value="${data}" cssClass="mascaraData"/>
		<@ww.textarea label="Descrição das Atividades" name="historicoFuncao.descricao" id="descricao" required="true"/>
		<@frt.checkListBox label="Exames (PCMSO)" name="examesChecked" id="exame" list="examesCheckList" />
		<@frt.checkListBox label="EPIs (PPRA)" name="episChecked" id="epi" list="episCheckList" />

		<@ww.hidden name="funcao.id" />
		<@ww.hidden name="historicoFuncao.id" />
		<@ww.hidden name="historicoFuncao.funcao.id" />
		<@ww.hidden name="cargoTmp.id" />
		<@ww.hidden name="veioDoSESMT" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar"></button>
		<button onclick="window.location='../funcao/prepareUpdate.action?funcao.id=${funcao.id}&cargoTmp.id=${cargoTmp.id}&veioDoSESMT=${veioDoSESMT?string}'" class="btnCancelar"></button>
	</div>
</body>
</html>