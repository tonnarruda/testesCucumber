<html>
	<head>
		<@ww.head/>
		<#if composicaoSesmt.id?exists>
			<title>Editar Composição do SESMT</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Composição do SESMT</title>
			<#assign formAction="insert.action"/>
		</#if>
	
		<#if composicaoSesmt?exists && composicaoSesmt.id?exists && composicaoSesmt.data?exists>
			<#assign data=composicaoSesmt.data?date />
		<#else>
			<#assign data="" />
		</#if>
	
		<#include "../ftl/mascarasImports.ftl" />
	
		<#assign validarCampos="return validaFormulario('form', new Array('data'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="composicaoSesmt.id" />
			<@ww.hidden name="composicaoSesmt.empresa.id" />
			<@ww.token/>
			
			<@ww.datepicker label="A partir de" required="true" value="${data}" name="composicaoSesmt.data" id="data" cssClass="mascaraData"/>
			
			<@ww.textfield label="Qtd. de Técnicos de Segurança" id="qtdTecnicosSeguranca" name="composicaoSesmt.qtdTecnicosSeguranca" maxLength="2" onkeypress="return somenteNumeros(event,'');" cssStyle="width:40px; text-align:right;"/>
			<@ww.textfield label="Qtd. de Engenheiros de Segurança" id="qtdEngenheirosSeguranca" name="composicaoSesmt.qtdEngenheirosSeguranca" maxLength="2" onkeypress="return somenteNumeros(event,'');" cssStyle="width:40px; text-align:right;"/>
			<@ww.textfield label="Qtd. de Auxiliares de Enfermagem" id="qtdAuxiliaresEnfermagem" name="composicaoSesmt.qtdAuxiliaresEnfermagem" maxLength="2" onkeypress="return somenteNumeros(event,'');" cssStyle="width:40px; text-align:right;"/>
			<@ww.textfield label="Qtd. de Enfermeiros" id="qtdEnfermeiros" name="composicaoSesmt.qtdEnfermeiros" maxLength="2" onkeypress="return somenteNumeros(event,'');" cssStyle="width:40px; text-align:right;"/>
			<@ww.textfield label="Qtd. de Médicos" id="qtdMedicos" name="composicaoSesmt.qtdMedicos" maxLength="2" onkeypress="return somenteNumeros(event,'');" cssStyle="width:40px; text-align:right;"/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="javascript: executeLink('list.action');" class="btnVoltar"></button>
		</div>
	</body>
</html>
