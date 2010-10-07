<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<#include "../ftl/mascarasImports.ftl" />
<@ww.head/>
<#if historicoColaboradorBeneficio.id?exists>
	<title>Editar Histórico de Benefício do Colaborador</title>
	<#assign formAction="update.action"/>
	<#assign buttonLabel="<u>A</u>tualizar"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Histórico de Benefício do Colaborador</title>
	<#assign formAction="insert.action"/>
	<#assign buttonLabel="<u>I</u>nserir"/>
	<#assign accessKey="N"/>
</#if>


	<#if historicoColaboradorBeneficio?exists && historicoColaboradorBeneficio.id?exists>
		<#assign data = historicoColaboradorBeneficio.dataMesAno/>
	<#else>
		<#assign data = ""/>
	</#if>


<#assign validarCampos="return validaFormulario('form', new Array('data'), new Array('data'))"/>
</head>
<body>
<@ww.actionerror />
<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}"  validate="true" method="POST">
    <@ww.label label="Colaborador" name="colaborador.nomeComercial" />
	<@ww.textfield label="A partir de (Mês/Ano)" name="historicoColaboradorBeneficio.dataMesAno" id="data" required="true" value="${data}" cssClass="mascaraDataMesAno"/>
	<@frt.checkListBox label="Benefícios" name="beneficiosCheck" list="beneficiosCheckList" />
	<font color="blue">* Os benefícios selecionados foram sugeridos de acordo com o último histórico</font>
	<@ww.hidden label="Id" name="historicoColaboradorBeneficio.id" />
	<@ww.hidden label="colaboradorId" name="colaborador.id" />
	<@ww.token/>
</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='list.action?colaborador.id=${colaborador.id}'" class="btnVoltar" accesskey="V">
		</button>
	</div>

</body>
</html>