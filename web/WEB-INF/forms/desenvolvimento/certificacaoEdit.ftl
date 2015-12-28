<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
	<@ww.head/>
		<#if certificacao.id?exists>
			<title>Editar Certificação</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Certificação</title>
			<#assign formAction="insert.action"/>
		</#if>
		<#assign validarCampos="return validaFormulario('form', new Array('nome'), null)"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.textfield label="Nome" id="nome" name="certificacao.nome"  cssStyle="width:500px" maxLength="100" required="true"/>
			<#if exibirPeriodicidade>
				<@ww.textfield label="Periodicidade em meses" name="certificacao.periodicidade" id="periodicidade" cssStyle="width:30px; text-align:right;" maxLength="4" onkeypress = "return(somenteNumeros(event,''));"/>
				<@ww.select label="Certificação pré-requisita" name="certificacao.certificacaoPreRequisito.id" list="certificacoes" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." cssStyle="width: 500px;" disabled=bloquearEdicao />
			<#else>
				<@ww.hidden name="certificacao.periodicidade" />
				<@ww.hidden name="certificacao.certificacaoPreRequisito.id" />	
			</#if>
			
        	<@frt.checkListBox label="Cursos" name="cursosCheck" list="cursosCheckList" filtro="true" readonly =bloquearEdicao/>
	        
	        <#if exibirPeriodicidade>
        		<@frt.checkListBox label="Avaliações Práticas" name="avaliacoesPraticasCheck" list="avaliacoesPraticasCheckList" filtro="true" readonly =bloquearEdicao />
	        </#if>
			
			<@ww.hidden name="certificacao.id" />
			<@ww.token/>
		</@ww.form>
		
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnCancelar"></button>
		</div>
	</body>
</html>