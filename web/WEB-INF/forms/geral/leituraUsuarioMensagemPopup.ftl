<html>
<head>
<@ww.head/>

<link rel="stylesheet" href="<@ww.url value='/webwork/css_xhtml/styles.css' encode='false' includeParams='none'/>" type="text/css"/>
<link rel="stylesheet" href="<@ww.url value='/webwork/jscalendar/calendar-blue.css' encode='false' includeParams='none'/>" type="text/css"/>


<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-1.4.4.min.js"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.alerts.js"/>'></script>

<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/formataValores.js?version=${versao}"/>"></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UsuarioMensagemDWR.js?version=${versao}"/>'></script>
<script src='<@ww.url includeParams="none" value="/js/functions.js?version=${versao}"/>'></script>

<script type='text/javascript'>

	function marcarMensagemLida(check, usuarioMensagemId, empresaId)
	{
		dwr.engine.setErrorHandler(errorUsuarioMensagem);

		if (check.checked)
			UsuarioMensagemDWR.gravarMensagemLida(usuarioMensagemId, empresaId, true, retorno);
		else
			UsuarioMensagemDWR.gravarMensagemLida(usuarioMensagemId, empresaId, false, retorno);

	}

	function retorno()
	{
		window.opener.location.reload();
	}

	function errorUsuarioMensagem()
	{
		jAlert("Ocorreu um erro na operação.");
	}

</script>

<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	@import url('<@ww.url includeParams="none" value="/css/default.css?version=${versao}"/>');
	@import url('<@ww.url includeParams="none" value="/css/fortes.css?version=${versao}"/>');
	@import url('<@ww.url includeParams="none" value="/css/botoes.css?version=${versao}"/>');
	@import url('<@ww.url includeParams="none" value="/css/jquery.alerts.css"/>');
</style>
</head>
<body>

<#if !noMessages?exists || !noMessages>

<div class="waDivTituloX">Visualizar Mensagem</div>
<div class="waDivFormularioX">

	<@ww.form name="form" action="update.action" method="POST" cssStyle="width: 100%" >
		<table bgcolor="#DDDDDD" cellpadding="5" cellspacing="1" style="width: 100%; font-size:10px;">
			<tr>
				<td bgcolor="#ffffff"><b>De:</b></td>
				<td bgcolor="#ffffff">${usuarioMensagem.mensagem.remetente}</td>
			</tr>
			<tr>
				<td bgcolor="#ffffff"><b>Data:</b></td>
				<td bgcolor="#ffffff">
					${usuarioMensagem.mensagem.data}
				</td>
			</tr>
			<tr>
				<td colspan="2" bgcolor="#ffffff"><b>Mensagem:</b></td>
			</tr>
			<tr>
				<td colspan="2" bgcolor="#ffffff">${usuarioMensagem.mensagem.texto?string?replace('\n','<br>')}</td>
			</tr>
			<tr>
				<td colspan="2" bgcolor="#ffffff">
	            	<@ww.checkbox label="" name="usuarioMensagem.lida" id="lida" onclick="marcarMensagemLida(this, ${usuarioMensagem.id}, ${usuarioMensagem.empresa.id});" labelPosition="left" cssStyle="border:1px solid #FFFFFF; float:left; height:16px;"/><label style="float:left; font-size:10px;" for="lida">&nbsp;Mensagem Lida</label>
				</td>
			</tr>
		</table>
		<br>

		<@ww.hidden name="tipo" />
		<@ww.hidden name="usuarioMensagem.id" />
		<@ww.hidden name="usuarioMensagem.usuario.id" />
		<@ww.hidden name="usuarioMensagem.mensagem.id" />
		<@ww.hidden name="usuarioMensagem.empresa.id" />

		<@ww.hidden name="usuarioMensagemProximoId" />
		<@ww.hidden name="usuarioMensagemAnteriorId" />

	<@ww.token/>
	</@ww.form>
</#if>

	<script>
		<#if !usuarioMensagemAnteriorId?exists>
			function getAnterior()
			{
				return false;
			}
		<#else>
			function getAnterior()
			{
				validaFormulario('formAnterior',null,null);
			}
		</#if>

		<#if !usuarioMensagemProximoId?exists>
			function getProximo()
			{
				return false;
			}
		<#else>
			function getProximo()
			{
				validaFormulario('formProximo',null,null);
			}
		</#if>
	</script>

	<@ww.form name="formProximo" action="leituraUsuarioMensagemPopup.action"  method="POST">
		<@ww.hidden name="tipo" />
		<@ww.hidden name="usuarioMensagem.id" />
		<@ww.hidden name="usuarioMensagem.usuario.id" />
		<@ww.hidden name="usuarioMensagem.empresa.id" />
		<@ww.hidden name="usuarioMensagemProximoId" />
		<@ww.hidden name="usuarioMensagemAnteriorId" />
		<@ww.hidden name="navegacao" value="proximo" />
	</@ww.form>
	<@ww.form name="formAnterior" action="leituraUsuarioMensagemPopup.action"  method="POST">
		<@ww.hidden name="tipo" />
		<@ww.hidden name="usuarioMensagem.id" />
		<@ww.hidden name="usuarioMensagem.usuario.id" />
		<@ww.hidden name="usuarioMensagem.empresa.id" />
		<@ww.hidden name="usuarioMensagemProximoId" />
		<@ww.hidden name="usuarioMensagemAnteriorId" />
		<@ww.hidden name="navegacao" value="anterior" />
	</@ww.form>
	<@ww.form name="formDelete" action="delete.action" method="POST">
		<@ww.hidden name="tipo" />
		<@ww.hidden name="usuarioMensagem.id" />
		<@ww.hidden name="usuarioMensagemProximoId" />
		<@ww.hidden name="usuarioMensagemAnteriorId" />
		<@ww.hidden name="fromPopup" value="true" />
	</@ww.form>

	<#assign btnExcluir="btnExcluir" />
	<#assign acaoExcluir="newConfirm('Confirma exclusão?', function(){validaFormulario('formDelete', null); window.opener.location.reload();});" />


<#if usuarioMensagemAnteriorId?exists>
	<#assign btnAnterior = "btnAnterior"/>
<#else>
	<#assign btnAnterior = "btnAnteriorDesabilitado"/>
</#if>
<#if usuarioMensagemProximoId?exists>
	<#assign btnProxima = "btnProxima"/>
<#else>
	<#assign btnProxima = "btnProximaDesabilitado"/>
</#if>


<#-- Se não existir nenhuma mensagem -->
<#if noMessages?exists && noMessages>
<div style="background-color:#ffffff;">
	<@ww.actionmessage />
	<#assign btnExcluir="btnExcluirDesabilitado" />
	<#assign acaoExcluir="return false;" />
</#if>

	<div class="buttonGroup">
		<button onclick="getAnterior();" class="${btnAnterior}" accesskey="A">
		</button>
		<button onclick="getProximo();" class="${btnProxima}" accesskey="P">
		</button>
		<button onclick="${acaoExcluir}" class="${btnExcluir}" accesskey="E">
		</button>
		<button onclick="window.close();" class="btnFechar" accessKey="F">
		</button>
	</div>

</div>

</body>
</html>