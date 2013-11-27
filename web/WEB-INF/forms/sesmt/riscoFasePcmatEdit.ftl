<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<html>
	<head>
		<@ww.head/>
		<#if riscoFasePcmat.id?exists>
			<title>Editar Risco e Medidas de Segurança</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Risco e Medidas de Segurança</title>
			<#assign formAction="insert.action"/>
		</#if>
	
		<#assign validarCampos="return validaFormulario('form', new Array('risco','@medidasCheck'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="riscoFasePcmat.id" />
			<@ww.hidden name="riscoFasePcmat.fasePcmat.id" />
			<@ww.token/>

			<@ww.select label="Risco" name="riscoFasePcmat.risco.id" id="risco" list="riscos" listKey="id" listValue="descricao" headerValue="" headerKey="-1" required="true"/>
			<@frt.checkListBox label="Medidas de Segurança" name="medidasCheck" list="medidasCheckList" width="960"/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action?fasePcmat.id=${riscoFasePcmat.fasePcmat.id}'" class="btnVoltar"></button>
		</div>
	</body>
</html>
