<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<title>Enviar Anúncio por E-mail</title>

<script language='javascript'>
		function prepareEnviarForm()
		{
			if(verificaEmail() || verificaEmailBDS())
			{
				
				document.form.submit();
			}
			else
			{
				jAlert("Nenhum Destinatário Informado.");
			}
		}
		function verificaEmailBDS()
		{
			if(document.form.empresasCheck == undefined)
				return false;
			if(document.form.empresasCheck.length == undefined)
				return document.form.empresasCheck.checked;

			for(i = 0; i < document.form.empresasCheck.length; i++)
			{
				if(document.form.empresasCheck[i].checked)
					return true;
			}

			return false;
		}
		function verificaEmail()
		{
			if(document.form.emailAvulso.value.trim() != "")
				return true;

			return false;
		}
</script>
</head>
<body>

	<@ww.actionerror />
	<@ww.form name="form" action="enviaEmail.action" validate="true" method="POST">
		<@ww.textfield label="Assunto" name="anuncio.titulo" size="75"/>
		<@ww.textfield label="Destinatário(s) Avulso(s) (Separe com ponto-e-vírgula)" name="emailAvulso" id="email" size="75"/>
		<@ww.hidden name="anuncio.id" />
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="prepareEnviarForm();" class="btnEnviar" >
		</button>
		<button onclick="window.location='prepareUpdate.action?solicitacao.id=${anuncio.solicitacao.id}'" class="btnVoltar" accesskey="V">
		</button>
	</div>
</body>
</html>