<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<#if tabelaReajusteColaborador.id?exists>
		<title>Editar Planejamento de Realinhamentos</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
	<#else>
		<title>Inserir Planejamento de Realinhamentos</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="A"/>
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
		<@ww.textarea label="Observação" name="tabelaReajusteColaborador.observacao"/>
		<@ww.checkbox label="Dissídio" name="tabelaReajusteColaborador.dissidio" labelPosition="left"/>

		<@ww.hidden name="tabelaReajusteColaborador.id" />
		<@ww.hidden name="tabelaReajusteColaborador.aprovada" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='list.action'" class="btnVoltar" accesskey="V">
		</button>
	</div>
</body>
</html>