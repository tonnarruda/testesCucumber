<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>

	<title>Importar Cadastros</title>
	
	<script type="text/javascript">
	
	function validarForm()
	{ 
		valida = validaFormulario('form', new Array('origem', 'destino', '@cadastrosCheck'), null, true);
		if (valida)
		{
			origem = document.getElementById('origem');
			destino = document.getElementById('destino');
			
			if (origem.value == destino.value)
			{
				jAlert('Selecione empresas Origem e Destino diferentes.');
				valida = false;
			}
		}
		
		if(valida)
			document.form.submit();
		else
			return false;
	}
	
	</script>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="importarCadastros.action" method="POST">
		<@ww.select label="Origem" required="true" name="empresaOrigem.id" id="origem" listKey="id" listValue="nome" list="empresas" cssClass="selectEmpresa" headerValue="Selecione..." headerKey=""/>
		<@ww.select label="Destino" required="true" name="empresaDestino.id" id="destino" listKey="id" listValue="nome" list="empresas" cssClass="selectEmpresa" headerValue="Selecione..." headerKey=""/>
		
		<@frt.checkListBox name="cadastrosCheck" label="Cadastros" list="cadastrosCheckList" />
		
		<li>
			<fieldset class="fieldsetPadrao" style="width:510px;text-align:justify;">
				<legend>Atenção</legend>
				Os cargos possuem vínculos com <strong>Áreas Organizacionais, Conhecimentos, Habilidades e Atitudes</strong>. Se desejar manter esses vínculos após a importação, marque também essas opções.
				<br /><br />
				Cuidado ao importar o cadastro entre empresas, pois o mesmo poderá ficar <strong>duplicado</strong> caso aconteça mais de uma importação.
			</fieldset>
		</li>
		
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="return validarForm();" class="btnGravar"> </button>
	</div>

</body>
</html>