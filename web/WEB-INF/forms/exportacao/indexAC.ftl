<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Exportar dados para o AC Pessoal</title>
	
	<#assign validarCampos="return validaFormulario('form', new Array('empresaId','grupaAC'))"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<#if usuarioLogado?exists && usuarioLogado.id == 1>
	
		<@ww.form name="form" action="exportarAC.action"  onsubmit="${validarCampos}" method="POST">
			<@ww.select label="Exportar empresa" name="empresaId" id="empresaId" listKey="id" listValue="nome" list="empresas" headerKey="" headerValue="Selecione..." cssStyle="width:150px"/>
			<@ww.select label="Para o AC" name="grupoAC" id="grupaAC" listKey="codigo" listValue="codigoDescricao" list="gruposACs" headerKey="" headerValue="Selecione..." cssStyle="width:150px"/>
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