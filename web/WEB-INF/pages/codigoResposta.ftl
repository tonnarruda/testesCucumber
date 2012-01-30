<html>
<head>
<@ww.head/>
	<title>Código de Resposta</title>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/login.css" />');
		@import url('<@ww.url includeParams="none" value="/css/botoes.css" />');
	</style>
	<script src='<@ww.url includeParams="none" value="/js/functions.js"/>'></script>
	<#assign validarCampos="return validaFormulario('form', new Array('codigoResposta'), null)"/>

</head>
<body>
<@ww.actionerror />
<@ww.form name="form" action="validaCodigoResposta.action" onsubmit="${validarCampos}" validate="true" method="POST">
	<br><br><br>
	<table width="344px" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td class="topo" width="344px" height="71px" colspan="3">
			</td>
		</tr>
		<tr>
			<td class="corpo" colspan="3" style="padding-left:10px" height="71px" colspan="3" align="center">
			<font style="font-weight:bold; color:yellow;">Verifique a licença do sistema</font><br><br>
			Entre em contato com a Fortes Informática pelo telefone (85)4005.1111 ou acesse o <a href=http://www.fortesinformatica.com.br/portaldocliente target=_blank><font style="color:yellow;">Portal do Cliente</font></a> para obter o código de resposta.<br><br>
			</td>
		</tr>
		<tr>
			<td class="corpo" colspan="3" style="padding-left:10px">
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