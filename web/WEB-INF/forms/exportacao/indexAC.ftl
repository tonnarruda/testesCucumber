<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Exportar dados para o AC Pessoal</title>
	
	<#assign validarCampos="return validaFormulario('form', new Array('empresaId'))"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<#if usuarioLogado?exists && usuarioLogado.id == 1>
	
		<@ww.form name="form" action="exportarAC.action"  onsubmit="${validarCampos}" method="POST">
			<@ww.select label="Empresa" name="empresaId" id="empresaId" listKey="id" listValue="nome" list="empresas" headerKey="" headerValue="Selecione..." cssClass="selectEmpresa"/>
		</@ww.form>
		
		<div class="buttonGroup">
			<button onclick="${validarCampos}" class="btnExportar"></button>
		</div>
		
	<#else>
		Usuário sem permissão de acesso.<br />
		Uso exclusivo do suporte técnico. 
	</#if>
</body>
</html>