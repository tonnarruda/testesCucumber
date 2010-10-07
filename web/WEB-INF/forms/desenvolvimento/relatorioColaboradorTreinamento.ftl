<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>

<@ww.head/>
	
	<#if comTreinamento>
		<title>Relatório de colaboradores que fizeram um treinamento</title>
	<#else>
		<title>Relatório de colaboradores que não fizeram um treinamento</title>
	</#if>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<#if comTreinamento>
		<#assign formAction = "relatorioColaboradorComTreinamento.action" />
	<#else>
		<#assign formAction = "relatorioColaboradorSemTreinamento.action" />
	</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('curso'), null)"/>
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
		<@ww.select name="curso.id" id="curso" list="cursos" listKey="id" required="true" listValue="nome" label="Curso" headerKey="" headerValue="Selecione..."/>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList"/>
	
		<#if comTreinamento>
			<@ww.select label="Aprovado" name="aprovado" list=r"#{'T':'Todos','S':'Sim','N':'Não'}" />
		</#if>
		<@ww.hidden name="comTreinamento"/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio">
		</button>
	</div>
</body>
</html>