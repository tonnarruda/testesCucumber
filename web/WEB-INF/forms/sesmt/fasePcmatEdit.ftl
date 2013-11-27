<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<html>
	<head>
		<@ww.head/>
		<#if fasePcmat.id?exists>
			<title>Editar Fase</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Fase</title>
			<#assign formAction="insert.action"/>
		</#if>
	
		<#assign validarCampos="return validaFormulario('form', new Array('fase','ordem','@riscosCheck'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="fasePcmat.id" />
			<@ww.hidden name="fasePcmat.pcmat.id" />
			<@ww.token/>

			<@ww.textfield label="Ordem" name="fasePcmat.ordem" id="ordem" maxLength="3" cssStyle="width:30px;" required="true"/>
			<@ww.select label="Fase" name="fasePcmat.fase.id" id="fase" list="fases" listKey="id" listValue="descricao" headerValue="" headerKey="-1" required="true"/>
			<@frt.checkListBox label="Riscos" name="riscosCheck" list="riscosCheckList"/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action?pcmat.id=${fasePcmat.pcmat.id}'" class="btnVoltar"></button>
		</div>
	</body>
</html>
