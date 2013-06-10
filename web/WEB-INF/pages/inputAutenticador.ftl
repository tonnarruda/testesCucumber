<html>
<head>
	<@ww.head/>
	<title>Licença de Uso</title>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/login.css" />');
		@import url('<@ww.url includeParams="none" value="/css/botoes.css" />');
	</style>
	<script src='<@ww.url includeParams="none" value="/js/functions.js"/>'></script>
	<#assign validarCampos="return validaFormulario('form', new Array('cnpj', 'nome'), null, true)"/>


	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/fortes.css"/>');
	</style>
	
	<script type="text/javascript">
		function enviaTicket() {
			if (validaFormulario('form', new Array('cnpj', 'nome'), null, true)) {
				var nome = removerAcento($('#nome').val());
				window.location='geraTicket.action?cnpj='+$('#cnpj').val()+'&nome='+ nome;
			} else {
				return false;
			}
		}
	</script>
</head>
<body>
<@ww.actionerror />
<@ww.form name="form" action="codigoOperacional.action" onsubmit="${validarCampos}" validate="true" method="POST">
	<br><br><br>
	<table width="344px" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td class="topo" height="71px" colspan="2">
			</td>
		</tr>
		<tr>
			<td class="corpo" colspan="2" style="padding:10px" height="71px" align="center">
				<font style="font-weight:bold; color:yellow;">Verifique a licença do sistema</font><br><br>
				Entre em contato com a Fortes pelo telefone<br> (85) 4005.1114 ou acesse o <a href=http://www.fortesinformatica.com.br/portaldocliente target=_blank><font style="color:yellow;">Portal do Cliente</font></a><br> para obter mais informações.<br>
			</td>
		</tr>
		<tr>
			<td class="corpo" colspan="2" style="padding:10px">
				<@ww.textfield label="CNPJ/CPF" name="cnpj" id="cnpj" maxlength="14" cssStyle="width:110px !important" /><br>
				<@ww.textfield label="Nome/Denominação Social do Licenciado" name="nome" id="nome" cssStyle="width:320px !important"/>
  				<br>
			</td>
		</tr>
		<tr>
			<td width="109px" height="79px" class="logo"></td>
			<td class="rodape" valign="top" align="left">
				<button type="button" onclick="window.location='login.action?demonstracao=true'" class="btnDemo" style="background-color:transparent;margin-right:5px;"></button>
				<button type="button" onclick="javascript:enviaTicket();" class="btnGerarTicket" style="background-color:transparent;margin-right:5px;"></button>
				<#-- <button onclick="${validarCampos};" class="btnEnviarBlue" style="background-color:transparent"></button> -->
			</td>
		</tr>
	</table>
</@ww.form>

</body>
</html>