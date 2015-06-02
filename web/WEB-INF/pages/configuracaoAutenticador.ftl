<html>
<head>
	<@ww.head/>
	<title>Licença de Uso</title>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/login.css" />');
		@import url('<@ww.url includeParams="none" value="/css/botoes.css" />');
		@import url('<@ww.url includeParams="none" value="/css/fortes.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/default.css"/>');
	</style>
	<script src='<@ww.url includeParams="none" value="/js/functions.js"/>'></script>
	<#assign validarCampos="return validaFormulario('form', new Array('servidorRemprot'), null)"/>


<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery-1.4.4.min.js"/>"></script>
<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.maskedinput-1.1.4.js"/>"></script>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/fortes.css"/>');
	</style>
</head>
<body>
<@ww.actionerror />
<@ww.actionmessage /> 
<@ww.form name="form" action="configAutenticador.action" onsubmit="${validarCampos}" validate="true" method="POST">
	<br><br><br>
	<table width="344px" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td class="topo" width="344px" height="71px" colspan="3">
			</td>
		</tr>
		<tr>
			<td class="corpo" colspan="3" style="padding-left:10px" height="71px" colspan="3" align="center">
			<font style="font-weight:bold; color:#9F6000;">Verifique a localização do serviço de autenticação.</font><br><br>
			Entre em contato com a Fortes pelo telefone (85)4005.1114 para obter mais informações.<br><br>
			</td>
		</tr>
		<tr>
			<td class="corpo" colspan="3" style="padding-left:10px">
					<@ww.textfield label="Endereço IP" name="servidorRemprot" id="servidorRemprot" maxLength="50" cssStyle="width:110px !important" />
  				<br>
			</td>
		</tr>
		<tr>
			<td width="109px" height="60px" class="logo"></td>
			<td width="170px" height="79px"  valign="top" class="rodape">
				<button type="button" onclick="window.location='login.action?demonstracao=true'" class="btnDemo" style="background-color:transparent"></button>
			</td> 
			<td class="rodape" valign="top" align="rigth">
				<button onclick="${validarCampos};" class="btnEnviarBlue" style="background-color:transparent"></button>
			</td>
		</tr>
	</table>
</@ww.form>

</body>
</html>