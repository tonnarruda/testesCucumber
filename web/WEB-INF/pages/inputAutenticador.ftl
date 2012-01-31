<html>
<head>
	<@ww.head/>
	<title>Licença de Uso</title>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/login.css" />');
		@import url('<@ww.url includeParams="none" value="/css/botoes.css" />');
	</style>
	<script src='<@ww.url includeParams="none" value="/js/functions.js"/>'></script>
	<#assign validarCampos="return validaFormulario('form', new Array('cnpj', 'nome'), null)"/>


<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery-1.4.4.min.js"/>"></script>
<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.maskedinput-1.1.4.js"/>"></script>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/fortes.css"/>');
	</style>
</head>
<body>
<@ww.actionerror />
<@ww.form name="form" action="codigoOperacional.action" onsubmit="${validarCampos}" validate="true" method="POST">
	<br><br><br>
	<table width="344px" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td class="topo" width="344px" height="71px" colspan="3">
			</td>
		</tr>
		<tr>
			<td class="corpo" colspan="3" style="padding-left:10px" height="71px" colspan="3" align="center">
			<font style="font-weight:bold; color:yellow;">Verifique a licença do sistema</font><br><br>
			Entre em contato com a Fortes Informática pelo telefone (85)4005.1111 ou acesse o <a href=http://www.fortesinformatica.com.br/portaldocliente target=_blank><font style="color:yellow;">Portal do Cliente</font></a> para obter mais informações.<br><br>
			</td>
		</tr>
		<tr>
			<td class="corpo" colspan="3" style="padding-left:10px">
					<@ww.textfield label="CNPJ/CPF" name="cnpj" id="cnpj" maxlength="14" cssStyle="width:110px !important" /><br>
					<@ww.textfield label="Nome/Denominação Social do Licenciado" name="nome" id="nome" cssStyle="width:300px !important"/>
  				<br>
			</td>
		</tr>
		<tr>
			<td width="109px" height="79px" class="logo"></td>
			<td width="170px" height="79px" class="rodape"></td>
			<td class="rodape" valign="top" align="rigth">
				<button onclick="${validarCampos};" class="btnEnviarBlue" style="background-color:transparent" accesskey="E"></button>
			</td>
		</tr>
	</table>
</@ww.form>

</body>
</html>