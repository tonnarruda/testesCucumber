<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />

<html>
<head>
	<@ww.head/>
	
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytagModuloExterno.css"/>');
		a { color: blue; }
	</style>
	
	<#if documentoAnexo.id?exists>
		<title>Editar Documento</title>
		<#assign formAction="updateDocumentoAnexo.action"/>
		
		<#assign buttonLabel="<u>A</u>tualizar"/>
		<#assign accessKey="A"/>
		<#assign validarCampos="return validaFormulario('form', new Array('descricao'));"/>
	<#else>
		<title>Novo Documento</title>
		<#assign formAction="insertDocumentoAnexo.action"/>
		
		<#assign buttonLabel="<u>I</u>nserir"/>
		<#assign accessKey="I"/>
		<#assign validarCampos="return validaFormulario('form', new Array('descricao','documento'));"/>
	</#if>
</head>
<body>

<@ww.actionerror/>
<@ww.actionmessage/>

<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST" enctype="multipart/form-data">
	<@ww.textfield label="Descrição" name="documentoAnexo.descricao" id="descricao" required="true" cssClass="inputNome" maxLength="100"/>

	<#if documentoAnexo.id?exists>
		<br />
		Documento atual:
		<a href="showDocumentoAnexo.action?documentoAnexo.id=${documentoAnexo.id}" target="_blank">${documentoAnexo.descricao}</a>
		<br /><br />
	</#if>

	<#if documentoAnexo.id?exists>Novo </#if>
	Documento :
	<@ww.file name="documento" id="documento" />
	
	<@ww.hidden name="documentoAnexo.id" />
	<@ww.hidden name="documentoAnexo.observacao" />
	<@ww.hidden name="documentoAnexo.tipoDocumento.id" />
	<@ww.hidden name="documentoAnexo.etapaSeletiva.id" />
	<@ww.hidden name="documentoAnexo.origem" />
	<@ww.hidden name="documentoAnexo.origemId" />
	<@ww.hidden name="documentoAnexo.url" />
	<@ww.hidden name="documentoAnexo.data" />
	<@ww.hidden name="documentoAnexo.moduloExterno" />
</@ww.form>

<div class="buttonGroup">
	<button onclick="${validarCampos};" class="btnGravar"></button>
	<button onclick="window.location='listDocumentosAnexos.action'" class="btnCancelar"></button>
</button>

</body>
</html>