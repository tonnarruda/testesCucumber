<html>
<head>
	<@ww.head/>
	<#if engenheiroResponsavel.id?exists>
		<title>Editar Engenheiro do Trabalho</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
	<#else>
		<title>Inserir Engenheiro do Trabalho</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
	</#if>

	<#assign inicio = ""/>
	<#if engenheiroResponsavel?exists && engenheiroResponsavel.inicio?exists>
		<#assign inicio = engenheiroResponsavel.inicio/>
	</#if>
	
	<#assign fim = ""/>
	<#if engenheiroResponsavel?exists && engenheiroResponsavel.fim?exists>
		<#assign fim = engenheiroResponsavel.fim/>
	</#if>
		
	<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('nome','inicio','crea'), new Array('inicio','fim'))"/>

	<#include "../ftl/mascarasImports.ftl" />
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield id="nome" label="Nome" name="engenheiroResponsavel.nome" cssClass="inputNome" maxLength="100" required="true"/>
		
		Período:<br>
		<@ww.datepicker id="inicio" theme="simple" label="" name="engenheiroResponsavel.inicio" required="true" value="${inicio}" cssClass="mascaraData validaDataIni"/>
		a
		<@ww.datepicker id="fim" theme="simple" label="" name="engenheiroResponsavel.fim" value="${fim}" cssClass="mascaraData validaDataFim"/>
		<br>
		<@ww.textfield id="crea" label="CREA" name="engenheiroResponsavel.crea" maxLength="20" required="true" />
		<@ww.textfield id="nit" label="NIT" name="engenheiroResponsavel.nit" maxLength="15" />
		<@ww.hidden name="engenheiroResponsavel.id"/>
		<@ww.token/>
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnGravar" accesskey="${accessKey}">
		</button>
		<button onclick="javascript: executeLink('list.action');" class="btnCancelar" accesskey="V">
		</button>
	</div>
</body>
</html>