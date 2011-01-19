<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<title>Enviar Anúncio BDS por E-mail</title>

<script language='javascript'>
		function prepareEnviarForm()
		{
			var campo = document.form.anuncioBDS;
			if(campo.value.trim() != "")
			{
				campo.style.background = "#FFF"; //Cor do campo validado que foi preenchido(depois de corrigido)
				if(verificaEmail() || verificaEmailBDS())
				{
					
					document.form.submit();
				}
				else
				{
					jAlert("Nenhum Destinatário Informado.");
				}
			}
			else
			{
				campo.style.background = "#FFEEC2";
				jAlert("Preencha campos indicados.");
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
	<@ww.form name="form" action="exportaBDS.action" validate="true" method="POST">
		<@ww.textfield label="Assunto" name="anuncioBDS" id="anuncioBDS" size="56" required="true"/>
		<@frt.checkListBox label="Enviar para as Empresas do Banco de Dados Solidário" name="empresasCheck" id="empresa" list="empresasCheckList" />
		<@ww.textfield label="Destinatário(s) Avulso(s) (Separe com ponto-e-vírgula)" name="emailAvulso" id="email" size="56"/>
		<@ww.hidden name="anexo" id="anexo"/>
		<@ww.hidden name="candidatosId"/>
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="prepareEnviarForm();" class="btnEnviar" accesskey="n">
		</button>
		<button onclick="window.location='../candidato/prepareBusca.action?BDS=true';" class="btnVoltar" accesskey="V">
		</button>
	</div>
</body>
</html>