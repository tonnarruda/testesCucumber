<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>

	<#include "../ftl/mascarasImports.ftl" />
	
	<#if documentoAnexo.id?exists>
		<title>Editar Anexo do Curso - ${nome}</title>
		<#assign formAction="updateCurso.action"/>
		<#assign voltar="listCurso.action?documentoAnexo.origem=${origemTmp}&documentoAnexo.origemId=${origemIdTmp}"/>
		
		<#assign buttonLabel="<u>A</u>tualizar"/>
		<#assign accessKey="A"/>
		<#assign validarCampos="return validaFormulario('form', new Array('descricao'), new Array('data'));"/>
	<#else>
		<title>Novo Anexo do Curso - ${nome}</title>
		<#assign formAction="insertCurso.action"/>
		<#assign voltar="listCurso.action?documentoAnexo.origem=${origemTmp}&documentoAnexo.origemId=${origemIdTmp}"/>
		
		<#assign buttonLabel="<u>I</u>nserir"/>
		<#assign accessKey="I"/>
		<#assign validarCampos="return validaFormulario('form', new Array('descricao','documento'), new Array('data'));"/>
	</#if>
	
	<#if documentoAnexo.id?exists && documentoAnexo.data?exists>
		<#assign data = documentoAnexo.data/>
	<#else>
		<#assign data = ""/>
	</#if>
	<#if documentoAnexo?exists && documentoAnexo.data?exists>
		<#assign data = documentoAnexo.data/>
	<#else>
		<#assign data = ""/>
	</#if>

</head>
<body>
<@ww.actionerror />
<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST" enctype="multipart/form-data">
	<@ww.textfield label="Descrição" name="documentoAnexo.descricao" id="descricao" required="true" cssClass="inputNome" maxLength="100"/>
	<@ww.datepicker label="Data" name="documentoAnexo.data" value="${data}" id="data" cssClass="mascaraData"/>

	<#if documentoAnexo.id?exists>
		<br>
		Anexo atual:
		<a href="../documentoAnexo/showDocumento.action?documentoAnexo.id=${documentoAnexo.id}" target="_blank">${documentoAnexo.descricao}</a>
		<br>
		<br>
	</#if>

	<#if documentoAnexo.id?exists>Novo </#if>
	Anexo:*
	<@ww.file name="documento" id="documento" />
	<@ww.textarea label="Observação" name="documentoAnexo.observacao" cssClass="inputNome"/>
	<@ww.hidden name="documentoAnexo.id" />
	<@ww.hidden name="documentoAnexo.origem" />
	<@ww.hidden name="documentoAnexo.origemId" />
	<@ww.hidden name="documentoAnexo.url" />
	<@ww.hidden name="nome" />
	<@ww.hidden name="origemTmp" />
	<@ww.hidden name="origemIdTmp" />
	<@ww.hidden name="solicitacaoId" />
	<@ww.hidden name="origemDocumento" />
	<@ww.hidden name="origemIdDocumento" />
	
</@ww.form>

<div class="buttonGroup">
	<button onclick="${validarCampos};" class="btnGravar"></button>
	<button onclick="window.location='${voltar}'" class="btnCancelar"></button>
</button>
</div>
</body>
</html>