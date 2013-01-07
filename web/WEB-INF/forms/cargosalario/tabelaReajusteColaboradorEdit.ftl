<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<#if tabelaReajusteColaborador.id?exists>
		<title>Editar Planejamento de Realinhamentos</title>
		<#assign formAction="update.action"/>
	<#else>
		<title>Inserir Planejamento de Realinhamentos</title>
		<#assign formAction="insert.action"/>
	</#if>

	<#assign validarCampos="return validaFormulario('form', new Array('titulo','data'), new Array('data'))"/>
	<#include "../ftl/mascarasImports.ftl" />
	
	<#if tabelaReajusteColaborador?exists && tabelaReajusteColaborador.data?exists>
		<#assign data = tabelaReajusteColaborador.data/>
	<#else>
		<#assign data = ""/>
	</#if>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}"  method="POST">
		<@ww.textfield label="Título" name="tabelaReajusteColaborador.nome" id="titulo" required="true" cssClass="inputNome" maxLength="100"/>
		<@ww.datepicker label="Data de Aplicação" name="tabelaReajusteColaborador.data" id="data"  value="${data}" cssClass="mascaraData" required="true"/>

		<#if tabelaReajusteColaborador.id?exists>
			<@ww.select label="Tipo do Reajuste" name="tabelaReajusteColaborador.TipoReajuste" id="tipoReajuste" list="tipoReajustes" cssStyle="width:150px;color:#999;" title="Não é permitida a alteração do tipo de reajuste" disabled=true />
			<@ww.hidden name="tabelaReajusteColaborador.tipoReajuste" value='C'/>
		<#else>
			<@ww.select label="Tipo do Reajuste" name="tabelaReajusteColaborador.TipoReajuste" id="tipoReajuste" list="tipoReajustes" cssStyle="width:150px;" headerKey="" headerValue="Selecione..." required=true />
		</#if>
		
		<@ww.textarea label="Observação" name="tabelaReajusteColaborador.observacao"/>
		<@ww.checkbox label="Dissídio" name="tabelaReajusteColaborador.dissidio" labelPosition="left"/>

		<@ww.hidden name="tabelaReajusteColaborador.id" />
		<@ww.hidden name="tabelaReajusteColaborador.aprovada" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar"></button>
		<button onclick="window.location='list.action'" class="btnVoltar"></button>
	</div>
</body>
</html>