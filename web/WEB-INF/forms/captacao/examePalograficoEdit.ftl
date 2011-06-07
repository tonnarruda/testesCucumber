<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Editar Exame Palográfico</title>
</head>

<#assign validarCampoUpload="return validaFormulario('formUpload', new Array('ocrTexto'), null)"/>
<#assign validarCampos="return validaFormulario('form', new Array('examePalografico'), null)"/>

<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<#if candidato?exists>
	
		<strong>Candidato:</strong> ${candidato.nome}<br />
		<strong>CPF:</strong> ${candidato.pessoal.cpf}
		<br /><br />
		
		<@ww.form name="formUpload" id="formUpload" action="prepareUpdateExamePalografico.action" method="POST" enctype="multipart/form-data">
			<@ww.hidden name="candidato.id" />
			<@ww.file label="Arquivo de texto gerado via OCR" name="ocrTexto" id="ocrTexto" accept="text/*"/>
			
			<button onclick="${validarCampoUpload}" class="btnCarregar"></button>
		</@ww.form>
		
		<br /><br />
		
		<@ww.form name="form" id="form" action="updateExamePalografico.action" method="POST">
			<@ww.textarea label="Exame Palográfico" id="examePalografico" name="candidato.examePalografico" cssStyle="width:100%"></@ww.textarea>
			<@ww.hidden name="candidato.id" />
			<@ww.hidden name="candidato.nome" />
		</@ww.form>
		
		<#if partesExamePalografico?exists>
			<strong>Resultado:</strong><br />
			<#list partesExamePalografico as parte>
	  			${parte_index + 1}ª sequência: ${parte.length()} caracteres &nbsp;&nbsp;&nbsp;&nbsp; ${parte} <br />
			</#list>
		</#if>

		<div class="buttonGroup">
			<button onclick="${validarCampos}" class="btnGravar"></button>
			<button onclick="window.location='prepareExamePalografico.action'" class="btnVoltar"></button>
		</div>
	</#if>
</body>
</html>