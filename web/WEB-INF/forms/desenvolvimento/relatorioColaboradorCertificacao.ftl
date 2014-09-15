<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Colaboradores x Certificações</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<#assign formAction = "relatorioColaboradorCertificacao.action" />
	
	<#assign validarCampos="return validaFormulario('form', new Array('certificacao'), null)"/>
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
		<@ww.select name="certificacao.id" id="certificacao" list="certificacoes" listKey="id" required="true" listValue="nome" label="Certificação" headerKey="" headerValue="Selecione..."/>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" filtro="true"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true"/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio">
		</button>
	</div>
</body>
</html>