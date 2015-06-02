<html>
<head>
	<@ww.head/>
	<title>Licença de Uso</title>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/login.css" />');
		@import url('<@ww.url includeParams="none" value="/css/botoes.css" />');
		@import url('<@ww.url includeParams="none" value="/css/fortes.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/default.css"/>');
		
		.errorMessage { width: 320px; margin: 0px auto; }
		.errorMessage ul { padding: 0px; }
		.errorMessage li {  font-size: 13px; padding: 5px; color: red; }
		form ul { padding: 0px; }
	</style>
	<script src='<@ww.url includeParams="none" value="/js/functions.js"/>'></script>
</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />
<@ww.form name="form" action="validaTicket.action" validate="true" method="POST">
	<br><br><br>
	
	<table width="344px" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td class="topo" height="71px" colspan="2">
			</td>
		</tr>
		<tr>
			<td class="corpo" colspan="2" style="padding:10px" height="71px" align="center">
				Aguarde a confirmação de liberação do técnico.<br />
				Ticket n&ordm; <span style="font-size:16px;color:#ff0;">${ticket}</span>
			</td>
		</tr>
		<tr>
			<td width="109px" height="60px" class="logo"></td>
			<td class="rodape" valign="top" align="right">
				<@ww.hidden name="ticket" value="${ticket}"/>
				<@ww.hidden name="nome" value="${nome}"/>
				<@ww.hidden name="cnpj" value="${cnpj}"/>
				<button type="submit" class="btnOK" style="background-color:transparent"></button>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<a href="login.action">Editar dados da lincença de uso</a>
			</td>
		</tr>
	</table>
</@ww.form>

</body>
</html>