<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Exportar dados para o Fortes Pessoal</title>
	
	<style>
		ol { padding: 10px; margin-bottom: 20px; background-color: #eee; list-style: decimal inside none; }
	</style>
	
	<#assign validarCampos="return validaFormulario('form', new Array('codigoAC'))"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form name="form" action="exportarEmpresaAC.action"  onsubmit="${validarCampos}" method="POST">
	
		<ol>
			<li>Crie uma nova empresa no Fortes Pessoal;</li>
			<li>Informe no campo abaixo o código gerado pelo Fortes Pessoal para a empresa criada;</li>
			<li><strong>Atenção!</strong> Informar o código incorreto causará inconsistências irreversíveis no banco de dados do Fortes Pessoal.</li>
		</ol>
		
		<@ww.hidden name="empresaId"/>
		<@ww.hidden name="grupoAC"/>
		<@ww.textfield label="Código AC" name="codigoAC" id="codigoAC" required="true" cssStyle="width: 35px;" maxLength="4" onkeypress="return somenteNumeros(event,'');"/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnAvancar"></button>
	</div>
</body>
</html>