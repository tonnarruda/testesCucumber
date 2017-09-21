<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>

	<#include "../ftl/mascarasImports.ftl" />
	
	<title>${titulo}</title>
	
	<#if solicitacaoId?exists>
		<#assign addSolicitacaoId = "&solicitacaoId=${solicitacaoId}"/>
	<#else>
		<#assign addSolicitacaoId = ""/>
	</#if>
	
	<#if colaboradorId?exists>
		<#assign addcolaboradorId = "&colaboradorId=${colaboradorId}"/>
	<#else>
		<#assign addcolaboradorId = ""/>
	</#if>
	
	<#if origem?exists>
		<#assign addOrigem = "&origem=${origem}"/>
		<#assign addOrigemChar = "${origem}"/>
	<#else>
		<#assign addOrigem = ""/>
		<#assign addOrigemChar = "${documentoAnexo.origem}"/>
	</#if>
	
	<#assign voltar="list.action?documentoAnexo.origem=${addOrigemChar}&documentoAnexo.origemId=${documentoAnexo.origemId}${addcolaboradorId}${addSolicitacaoId}"/>
	
	<#if documentoAnexo.id?exists>
		<#assign formAction="update.action"/>
		<#assign buttonLabel="<u>A</u>tualizar"/>
		<#assign accessKey="A"/>
		<#assign validarCampos="return validaFormulario('form', new Array('descricao','data'), new Array('data'));"/>
	<#else>
		<#assign formAction="insert.action"/>
		<#assign buttonLabel="<u>I</u>nserir"/>
		<#assign accessKey="I"/>
		<#assign validarCampos="return validaFormulario('form', new Array('descricao','data','documento'), new Array('data'));"/>
	</#if>
	
	<#if documentoAnexo.id?exists && documentoAnexo.data?exists>
		<#assign data = documentoAnexo.data/>
	<#else>
		<#assign data = ""/>
	</#if>

</head>
<body>
<@ww.actionerror />
<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST" enctype="multipart/form-data">
	<@ww.textfield label="Descrição" name="documentoAnexo.descricao" id="descricao" required="true" cssClass="inputNome" maxLength="100"/>
	<@ww.datepicker label="Data" name="documentoAnexo.data" value="${data}" id="data" required="true" cssClass="mascaraData"/>

	<#if documentoAnexo.id?exists>
		<br>
		Documento atual:
		<a href="../documentoAnexo/showDocumento.action?documentoAnexo.id=${documentoAnexo.id}&documentoAnexo.origem=${documentoAnexo.origem}${addcolaboradorId}${addOrigem}" target="_blank">${documentoAnexo.descricao}</a>
		<br><br>
	</#if>

	<#if documentoAnexo.id?exists>Novo </#if>
	Documento :

	<@ww.file name="documento" id="documento" />

	<#if documentoAnexo.origem?string == "C">
		<@ww.select label="Etapa Seletiva" name="documentoAnexo.etapaSeletiva.id" list="etapaSeletivas" listKey="id" listValue="nome" headerKey="" headerValue="Não informado"/>
	</#if>
	
	<@ww.select label="Tipo do Documento" name="documentoAnexo.tipoDocumento.id" id="tipoDocumentoId" listKey="id" listValue="descricao" list="tipoDocumentos" headerKey="" headerValue="Nenhum"/>
	<@ww.textarea label="Observação" name="documentoAnexo.observacao" cssClass="inputNome"/>
	
	<@ww.hidden name="documentoAnexo.id" />
	<@ww.hidden name="documentoAnexo.origem" />
	<@ww.hidden name="documentoAnexo.origemId" />
	<@ww.hidden name="documentoAnexo.url" />
	<@ww.hidden name="solicitacaoId" />
	<@ww.hidden name="colaboradorId" />
	<@ww.hidden name="origem" />
	
</@ww.form>

<div class="buttonGroup">
	<button onclick="${validarCampos};" class="btnGravar"></button>
	<button onclick="window.location='${voltar}'" class="btnCancelar"></button>
</button>
</div>
</body>
</html>