<#assign ww=JspTaglibs["/WEB-INF/tlds/webwork.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="Content-Language" content="pt-br" />
<meta http-equiv="Cache-Control" content="no-cache, no-store" />
<meta http-equiv="Pragma" content="no-cache, no-store" />
<meta http-equiv="Expires" content="0" />
<title>RH</title>
<head>
	<style>
		@import url('<@ww.url value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>');
				
		a:link {text-decoration: none}
		a:visited {text-decoration: none}
		a:hover {text-decoration: underline; color: #2471A7; }
		a:active {text-decoration: none}
		
		.dados td { font-size: 11px; }
		
		/* cria link sobre a parte "FECHAR" do splash do Chat */ 
		#fecharSplash {
			 left: 507px;
			 top: 10px;
			 color: white;
			 font-weight: bold;
			 position: absolute; 
			 cursor: pointer; 
			 border: none; 
			 text-decoration: none; 
		}
		
		#atualizacao { display: none; padding: 10px; margin-bottom: 15px; background-color: #FFB; border: 1px solid #FC6; }
		#atualizacao img { margin-right: 5px; }
		
		.column { width: 50%; float: left; }
		.left .portlet { margin: 0 0.5em 1em 0; }
		.right .portlet { margin: 0 0 1em 0.5em; }
		.portlet-header { margin: 0.3em; padding: 3px; }
		.portlet-header .ui-icon { float: right; }
		.portlet-content { padding: 0.4em; height: 180px; overflow: auto; }
		.ui-sortable-placeholder { background: transparent; border: 1px dotted black; visibility: visible !important; height: 220px !important; }
		.ui-sortable-placeholder * { visibility: hidden; }
		
		.remetenteHora { color: #069; }
	</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UtilDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UsuarioMensagemDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.cookie.js"/>'></script>
	
	<script type='text/javascript'>
		$(function () {
			$( "#splash" ).dialog({
				autoOpen: false,
				modal: true,
				zIndex: 99999,
				minWidth: 578,
				create: function (event, ui) { $(".ui-dialog-titlebar").hide(); },
			  	close: function() {
			  		if ( $('#naoExibirMsg').is(':checked') ) 
			  			$.cookie("pgInicialSplashChat", false, { expires: 30 }); 
			  	}
			});
			
			if($.cookie("pgInicialSplashChat") != 'false')
			{
				$("#splash").dialog("open");
			}
			
			$( ".column" ).sortable({
				connectWith: ".column",
				handle: ".portlet-header"
			});
			
			$( ".portlet" ).addClass( "ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" )
				.find( ".portlet-header" )
					.addClass( "ui-widget-header ui-corner-all" )
					.prepend( "<span class='ui-icon ui-icon-minusthick'></span>")
					.end()
				.find( ".portlet-content" );
	
			$( ".portlet-header .ui-icon" ).click(function() {
				$( this ).toggleClass( "ui-icon-minusthick" ).toggleClass( "ui-icon-plusthick" );
				$( this ).parents( ".portlet:first" ).find( ".portlet-content" ).toggle();
			});
	
			$( ".column" ).disableSelection();
			
			<@authz.authorize ifAllGranted="ROLE_UTI_HISTORICO_VERSAO">
				UtilDWR.findUltimaVersaoPortal(function(retorno) {
					var resposta = jQuery.parseJSON(retorno);
					if (resposta.sucesso == '1')
					{
						$('#versaoPortal').text(resposta.versao);
						$('#atualizacao').show();
					}
				});
		});
		
		function marcarMensagemLida(usuarioMensagemId)
		{
			DWREngine.setErrorHandler(errorUsuarioMensagem);
			UsuarioMensagemDWR.gravarMensagemLida(retorno, usuarioMensagemId, ${empresaId}, true);
		}
	
		function retorno()
		{
			window.opener.location.reload();
		}
	
		function errorUsuarioMensagem() 
		{
		
		}
		
		function gravarPosicoes()
		{
			var ordem = [];
			var minimizadas = [];
			
			$("input[name='caixa']").each(function() {
				ordem.push( $(this).val() );
			});

			$(".portlet").has(".ui-icon-plusthick").find("[name='caixa']").each(function() {
				minimizadas.push( $(this).val() );
			});
		
			UsuarioDWR.gravarLayoutCaixasMensagens(<@authz.authentication operation="id"/>, ordem, minimizadas, function() {  });
		}
	</script>

</head>
<body>
	<#assign usuarioId><@authz.authentication operation="id"/></#assign>

	<@ww.actionmessage />
	<@ww.actionerror />	
	
	<div id="atualizacao">
		<a title="Acessar o Portal do Cliente" href="http://www.grupofortes.com.br/portaldocliente" target="_blank">
			<img border="0" src="<@ww.url value="/imgs/softwareUpdate.png"/>" align="absMiddle"/>
			Versão <span id="versaoPortal"></span> disponível. Clique aqui para acessar o Portal do Cliente e realizar o download.
		</a>
	</div>
	
	<#if avaliacaos?exists && 0 < avaliacaos?size>
		<div class="waDivTituloX">Aviso!</div>
		<div class="waDivFormularioX">
			<p>
				<strong>Caro cliente,</strong><br>
				Existe uma inconsistência no cadastro de Modelo de Avaliação, foi criado um novo vínculo entre o "Modelo de Avaliação" e "Períodos de Acompanhamento de Experiência".<br>
				Clique nos "Modelo de Avaliação" abaixo e edite o campo "Períodos de Acompanhamento de Experiência":<br><br>
				<#list avaliacaos as avaliacao>
					<a href="avaliacao/modelo/prepareUpdate.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${avaliacao.tipoModeloAvaliacao}&telaInicial=true">${avaliacao.titulo}</a><br>
				</#list>
			</p>
		</div>
		<br />
	</#if>
	
	<form id="formMensagens">
		<@authz.authorize ifAllGranted="ROLE_VISUALIZAR_MSG">
			<#if mensagems?exists>
				<div class="caixas">
					<div class="column left">
						<#assign i = 1/>
						
						<#list mensagems?keys as tipo>
							<div class="portlet">
								<input type="hidden" name="caixa" value="${tipo}"/>
								<div class="portlet-header">${action.getDescricaoTipo(tipo)}</div>
								<div class="portlet-content">
									<table width="100%" class="dados" style="border:none;">
										<tbody>
											<#assign j=0/>
											<#list action.getMensagens(tipo) as msg>
												<#if j%2==0>
													<#assign class="odd"/>
												<#else>
													<#assign class="even"/>
												</#if>
		
												<#if !msg.lida>
													<#assign style="font-weight: bold;"/>
													<#assign status="Não">
												<#else>
													<#assign style=""/>
													<#assign status="Sim">
												</#if>
											
												<tr class="${class}">
													<#if msg.usuarioMensagemId?exists>
														<td width="40" align="center">
															<a href="javascript: popup('geral/usuarioMensagem/leituraUsuarioMensagemPopup.action?usuarioMensagem.empresa.id=4&amp;usuarioMensagem.id=${msg.usuarioMensagemId}', 400, 500)"><img border="0" title="Visualizar mensagem"  src="/fortesrh/imgs/olho.jpg"></a>
															<a href="javascript: newConfirm('Confirma exclusão?', function(){window.location='geral/usuarioMensagem/delete.action?usuarioMensagem.id=${msg.usuarioMensagemId}'});"><img border="0" title="Excluir" src="/fortesrh/imgs/delete.gif"/></a>
														</td>
													</#if>
													<td>
														<#if msg.remetente?exists && msg.data?exists>
															<span class="remetenteHora">${msg.remetente} - ${msg.data?string("dd/MM/yyyy HH:mm")}</span><br />
														</#if>
														
														<#if msg.link?exists && msg.link != "">
															<a href="${msg.link}" title="${msg.textoAbreviado}" <#if msg.usuarioMensagemId?exists> onclick="marcarMensagemLida(${msg.usuarioMensagemId});" </#if> style="${style}">
																${msg.textoAbreviado}
															</a>
														<#else>
															${msg.textoAbreviado}
														</#if>
													</td>
												</tr>
												<#assign j=j+1/>
											</#list>
											
											<#if (action.getMensagens(tipo)?size < 1)>
												<tr>
													<td>Não há mensagens disponíveis</td>
												</tr>
											</#if>
										</tbody>
									</table>
								</div>
							</div>
	
							<#-- Passa para a coluna da esquerda -->
							<#if i == (mensagems?keys?size/2)?int>
								</div>
								<div class="column right">
							</#if>
							
							<#assign i = i+1/>
						</#list>
					</div>
				</div>
			</#if>
		</@authz.authorize>
		
		<br clear="all"/>
		
		<@authz.authorize ifAllGranted="ROLE_VISUALIZAR_PENDENCIA_AC">
			<#if integradoAC && pendenciaACs?exists>
				<div class="waDivTituloX">Pendências com o AC Pessoal</div>
				<div class="waDivFormularioX">
				<#if pendenciaACs?size < 1>
					<span>Nenhuma pendência</span>
				<#else>
					<@display.table name="pendenciaACs" id="pendenciaAC" class="dados" defaultsort=2 sort="list">
						<@display.column property="pendencia" title="Pendência" />
						<@display.column property="detalhes" title="Detalhes" />
						<@display.column property="status" title="Status" />
					</@display.table>
				</#if>
				</div>
			</#if>
		</@authz.authorize>
	</form>
	
	<br clear="all"/>
	
	<@authz.authorize ifAllGranted="ROLE_VISUALIZAR_PENDENCIA_AC">
		<#if integradoAC && pendenciaACs?exists>
			<div class="waDivTituloX">Pendências com o AC Pessoal</div>
			<div class="waDivFormularioX">
			<#if pendenciaACs?size < 1>
				<span>Nenhuma pendência</span>
			<#else>
				<@display.table name="pendenciaACs" id="pendenciaAC" class="dados" defaultsort=2 sort="list">
					<@display.column property="pendencia" title="Pendência" />
					<@display.column property="detalhes" title="Detalhes" />
					<@display.column property="status" title="Status" />
				</@display.table>
			</#if>
			</div>
		</#if>
	</@authz.authorize>
	
	<div id="splash">
		<a id="fecharSplash" title="Fechar" href="javascript:;" onclick="$('#splash').dialog('close');">
			FECHAR
		</a>
		<a href="http://www.fortesinformatica.com.br/i/mails/chat_grupoFortes/html_chat.html" target="_blank" >
			<img border="0" title="Acesse o novo chat"  src="<@ww.url includeParams="none" value="/imgs/splashChat.jpg"/>">
		</a>
		<input type="checkbox" id="naoExibirMsg" name="naoExibirMsg"/>
		<label for="naoExibirMsg">Não exibir esta mensagem novamente</label>
	</div>

	<script type="text/javascript">
		<#if idiomaIncorreto?exists && !idiomaIncorreto>
			jAlert('O idioma do sistema está incorreto, favor alterar para Português-Brasileiro .');
		</#if>
	</script>
</body>
</html>