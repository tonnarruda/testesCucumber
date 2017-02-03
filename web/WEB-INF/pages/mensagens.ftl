<#assign ww=JspTaglibs["/WEB-INF/tlds/webwork.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<meta http-equiv="Content-Language" content="pt-br" />
	<meta http-equiv="Cache-Control" content="no-cache, no-store" />
	<meta http-equiv="Pragma" content="no-cache, no-store" />
	<meta http-equiv="Expires" content="0" />
	
	<title>Caixa de Mensagens de ${action.getDescricaoTipo(tipo)}</title>
	
	<style>
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		.dados a { text-decoration: none; font-family: Arial, Helvetica, sans-serif !important; color: #000 !important; }
		.dados td { font-size: 11px !important; }
		.dados td .tituloMensagem { font-size: 11px !important; width: 590px; white-space: nowrap; overflow: hidden; }
		.remetenteHora { color: #069; }
	</style>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UsuarioMensagemDWR.js?version=${versao}"/>'></script>
	
	<script type='text/javascript'>
		function marcarMensagemLida(usuarioMensagemId)
		{
			dwr.engine.setErrorHandler(errorUsuarioMensagem);
			UsuarioMensagemDWR.gravarMensagemLida(usuarioMensagemId, ${empresaId}, true, retorno);
		}
	
		function retorno(){}
		function errorUsuarioMensagem()	{}
		
	
	</script>
	
</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@authz.authorize ifAllGranted="ROLE_VISUALIZAR_MSG">
		<#if (action.getMensagens(tipo)?size > 0)>
			<@ww.form name="formMensagemUsuario" action="geral/usuarioMensagem/delete.action" method="post">
				<@ww.hidden name="tipo"/>
				<@ww.hidden name="fromTodasMensagens" value="true"/>
			
				<#assign exibirBotaoExcluir = false/>
			
				<@display.table name="getMensagens(tipo)" id="msg" class="dados">
					<#if !msg.lida>
						<#assign style="font-weight:bold;"/>
						<#assign status>Não</#assign>
					<#else>
						<#assign style=""/>
						<#assign status>Sim</#assign>
					</#if>
					
					<#if msg.usuarioMensagemId?exists>
						<@display.column title="<input type='checkbox' id='md' onclick='atualizaChecks(\"checkMensagem\", this.checked);' />" style="width: 30px; text-align: center;">
							<input type="checkbox" class="checkMensagem" value="${msg.usuarioMensagemId}" name="usuarioMensagemIds" />
						</@display.column>
					
						<@display.column title="Ações" media="html" style="text-align:center; width:50px;">
							<a href="javascript: popup('geral/usuarioMensagem/leituraUsuarioMensagemPopup.action?usuarioMensagem.empresa.id=${empresaId}&amp;usuarioMensagem.id=${msg.usuarioMensagemId}&amp;tipo=${tipo}', 400, 500)"><img border="0" title="Visualizar mensagem"  src="${contexto}/imgs/olho.jpg"></a>
							<a href="javascript: newConfirm('Confirma exclusão?', function(){window.location='geral/usuarioMensagem/delete.action?usuarioMensagem.id=${msg.usuarioMensagemId}&amp;fromTodasMensagens=true&amp;tipo=${tipo}'});"><img border="0" title="Excluir" src="${contexto}/imgs/delete.gif"/></a>
						</@display.column>
					
						<@display.column title="De" style="${style} text-align:center; width:70px;">
							${msg.remetente}
						</@display.column>
						
						<@display.column title="Data" style="${style} text-align:center; width:100px;">
							${msg.data?string("dd/MM/yyyy HH:mm")}
						</@display.column>
						
						<#assign exibirBotaoExcluir = true/>
					<#else>
						<#if msg.tipo == 'T'>
							<@display.column title="<input type='checkbox'/>" style="width: 30px; text-align: center;" />
							<@display.column title="Ações" media="html" style="text-align:center; width:50px;" />
							<@display.column title="De" style="${style} text-align:center; width:70px;"/>
							<@display.column title="Data" style="${style} text-align:center; width:100px;" />
						</#if>
						<#if msg.tipo == 'A' >
							<@display.column title="<input type='checkbox'/>" style="width: 30px; text-align: center;" />
							<@display.column title="Ações" media="html" style="text-align:center; width:50px;" />
							
							<@display.column title="De" style="${style} text-align:center; width:70px;">
								${msg.remetente}
							</@display.column>
							
							<@display.column title="Data" style="${style} text-align:center; width:100px;" >
								${msg.data?string("dd/MM/yyyy")}
							</@display.column>
						</#if>
					</#if>
					
					<@display.column title="Mensagem" style="${style}">
						<div class="tituloMensagem">
							<#if msg.link?exists && msg.link != "">
								<a href="${msg.link}" title="${msg.textoAbreviado}" <#if msg.usuarioMensagemId?exists> onclick="marcarMensagemLida(${msg.usuarioMensagemId});" </#if> style="text-decoration:underline; ${style}">
									${msg.textoAbreviado}
								</a>
							<#else>
								<a style="${style}">${msg.textoAbreviado}</a>
							</#if>
						</div>
					</@display.column>
					
					<#if msg.usuarioMensagemId?exists || msg.tipo == 'A' || msg.tipo == 'T'>
						<@display.column title="Lida" style="${style} text-align:center; width:40px;">
							${status}
						</@display.column>
					</#if>
				</@display.table>
			</@ww.form>
			
			<div class="buttonGroup">
				<#if exibirBotaoExcluir>
					<button onclick="javascript: newConfirm('Confirma exclusão das mensagens selecionadas?', function(){document.formMensagemUsuario.submit();});" class="btnExcluir"></button>
				</#if>
				<button onclick="javascript: location.href='index.action';" class="btnVoltar"></button>
			</div>
		<#else>
			Não há mensagens a serem exibidas
		</#if>
	</@authz.authorize>
</body>
</html>