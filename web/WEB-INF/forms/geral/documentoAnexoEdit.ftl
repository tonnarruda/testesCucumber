<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>

	<#include "../ftl/mascarasImports.ftl" />
	
	<#if documentoAnexo.id?exists>
		<#if documentoAnexo.origem?string == "C">
			<title>Editar Documento do Candidato - ${nome}</title>
			<#assign formAction="updateCandidato.action"/>
			<#if solicitacaoId?exists>
				<#assign voltar="list.action?documentoAnexo.origem=${origemTmp}&documentoAnexo.origemId=${origemIdTmp}&solicitacaoId=${solicitacaoId}"/>
			<#else>
				<#assign voltar="list.action?documentoAnexo.origem=${origemTmp}&documentoAnexo.origemId=${origemIdTmp}"/>
			</#if>
		</#if>
		<#if documentoAnexo.origem?string == "D">
			<title>Editar Documento do Colaborador - ${nome}</title>
			<#assign formAction="updateColaborador.action"/>
			<#assign voltar="list.action?documentoAnexo.origem=${origemTmp}&documentoAnexo.origemId=${origemIdTmp}"/>
		</#if>
		
		<#assign buttonLabel="<u>A</u>tualizar"/>
		<#assign accessKey="A"/>
		<#assign validarCampos="return validaFormulario('form', new Array('descricao','data'), new Array('data'));"/>
	<#else>
		<#if documentoAnexo.origem?string == "C">
			<title>Novo Documento do Candidato - ${nome}</title>
			<#assign formAction="insertCandidato.action"/>
			<#if solicitacaoId?exists>
				<#assign voltar="list.action?documentoAnexo.origem=${origemTmp}&documentoAnexo.origemId=${origemIdTmp}&solicitacaoId=${solicitacaoId}"/>
			<#else>
				<#assign voltar="list.action?documentoAnexo.origem=${origemTmp}&documentoAnexo.origemId=${origemIdTmp}"/>
			</#if>
		</#if>
		<#if documentoAnexo.origem?string == "D">
			<title>Novo Documento do Colaborador - ${nome}</title>
			<#assign formAction="insertColaborador.action"/>
			<#assign voltar="list.action?documentoAnexo.origem=${origemTmp}&documentoAnexo.origemId=${origemIdTmp}"/>
		</#if>
		
		<#assign buttonLabel="<u>I</u>nserir"/>
		<#assign accessKey="I"/>
	
		<#assign validarCampos="return validaFormulario('form', new Array('descricao','data','documento'), new Array('data'));"/>
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
	<@ww.datepicker label="Data" name="documentoAnexo.data" value="${data}" id="data" required="true" cssClass="mascaraData"/>

	<#if documentoAnexo.id?exists>
		<br>
		Documento atual:
		<a href="../documentoAnexo/showDocumento.action?documentoAnexo.id=${documentoAnexo.id}" target="_blank">${documentoAnexo.descricao}</a>
		<br>
		<br>
	</#if>

	<#if documentoAnexo.id?exists>Novo </#if>
	Documento :
	<@ww.file name="documento" id="documento" />
	<#if documentoAnexo.origem?string == "C">
		<@ww.select label="Etapa Seletiva" name="documentoAnexo.etapaSeletiva.id" list="etapaSeletivas" listKey="id" listValue="nome" headerKey="" headerValue="Não informado"/>
	</#if>
	<@ww.textarea label="Observação" name="documentoAnexo.observacao" cssClass="inputNome"/>
	<@ww.hidden name="documentoAnexo.id" />
	<@ww.hidden name="documentoAnexo.origem" />
	<@ww.hidden name="documentoAnexo.origemId" />
	<@ww.hidden name="documentoAnexo.url" />
	<@ww.hidden name="nome" />
	<@ww.hidden name="origemTmp" />
	<@ww.hidden name="origemIdTmp" />
	<@ww.hidden name="solicitacaoId" />
	
</@ww.form>

<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar">
		</button>
		<button onclick="window.location='${voltar}'" class="btnCancelar">
		</button>
</button>
</div>
</body>
</html>