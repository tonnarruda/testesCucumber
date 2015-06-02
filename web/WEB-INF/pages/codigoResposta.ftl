<html>
<head>
<@ww.head/>
	<title>Código de Resposta</title>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/login.css" />');
		@import url('<@ww.url includeParams="none" value="/css/botoes.css" />');
		@import url('<@ww.url includeParams="none" value="/css/default.css"/>');
	</style>
	<script src='<@ww.url includeParams="none" value="/js/functions.js"/>'></script>
	<#assign validarCampos="return validaFormulario('form', new Array('codigoResposta'), null)"/>

</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />
<@ww.form name="form" action="validaCodigoResposta.action" onsubmit="${validarCampos}" validate="true" method="POST">
	<br><br><br>
	<table width="344" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td class="topo" height="71px" colspan="3"></td>
		</tr>
		<tr>
			<td class="corpo" style="padding:10px" height="71px" colspan="2" align="center">
				<font style="font-weight:bold; color:#9F6000;">Verifique a licença do sistema</font><br><br>
				Entre em contato com a Fortes pelo telefone<br> (85) 4005.1114 ou acesse o <a href=http://www.fortesinformatica.com.br/portaldocliente target=_blank><font style="color:#9F6000;">Portal do Cliente</font></a><br> para obter o código de resposta.<br>
			</td>
		</tr>
		<tr>
			<td class="corpo" colspan="2" style="padding:10px">
			<@ww.label label="CNPJ/CPF"             name="cnpj"              id="cnpj"/><br>
			<@ww.label label="Licenciado"             name="nome"              id="nome"/><br>
			<@ww.label label="Último Reset"           name="ultimoReset"       id="ultimoReset"/><br>
			<@ww.label label="Código Operacional"     name="codigoOperacional" id="codigoOperacional"/><br>
			<@ww.textfield label="Código de Resposta" name="codigoResposta"    id="codigoResposta" size="30"/>
			<@ww.hidden name="cnpj"/>
			<@ww.hidden name="nome"/>
			<@ww.token/>
  				<br>
			</td>
		</tr>
		<tr>
			<td width="109px" height="60px" class="logo"></td>
			<td class="rodape" valign="top" align="right">
				<button type="button" onclick="window.location='login.action?demonstracao=true'" class="btnDemo" style="background-color:transparent"></button>
				<button onclick="${validarCampos};" class="btnEnviarBlue" style="background-color:transparent" accesskey="E"></button>
			</td>
		</tr>
	</table>
</@ww.form>



</body>
</html>