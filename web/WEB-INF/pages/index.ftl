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
	
		function errorUsuarioMensagem(){
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
	
	<@authz.authorize ifAllGranted="ROLE_VISUALIZAR_MSG">
		<#if mensagems?exists>
			<div class="caixas">
				<div class="column left">
					<#assign i = 1/>
					<#assign qtd = mensagems?keys?size/>
					
					<#if questionarios?exists || avaliacoesDesempenhoPendentes?exists>
						<#assign qtd = qtd + 1/>
						<div class="portlet">
							<div class="portlet-header">Pesquisas/Avaliações Disponíveis</div>
							<div class="portlet-content">
								<#if colaborador?exists && colaborador.id?exists>
									<#list questionarios as questionario>
										<p><a href="pesquisa/colaboradorResposta/prepareResponderQuestionario.action?questionario.id=${questionario.id}&colaborador.id=${colaborador.id}&tela=index&validarFormulario=true">${questionario.titulo}</a></p>
									</#list>
								</#if>
								
								<#if colaborador?exists && colaborador.id?exists>
									<#list avaliacoesDesempenhoPendentes as avaliacaoDesempenho>
										<p>
										<a href="avaliacao/desempenho/prepareResponderAvaliacaoDesempenho.action?colaboradorQuestionario.id=${avaliacaoDesempenho.id}">${avaliacaoDesempenho.avaliacaoDesempenho.titulo} (${avaliacaoDesempenho.colaborador.nome}) (${avaliacaoDesempenho.avaliacaoDesempenho.periodoFormatado})</a>
										</p>
									</#list>
								</#if>
						
								<#if (questionarios?size < 1 && avaliacoesDesempenhoPendentes?size < 1)>
									<span>Não existem questionários disponíveis</span>
								</#if>
							</div>
						</div>
					</#if>
					
					<#if colaboradorQuestionariosTeD?exists>
						<#assign qtd = qtd + 1/>
						<div class="portlet">
							<div class="portlet-header">Avaliações de T&D</div>
							<div class="portlet-content">
								<#if colaborador?exists && colaborador.id?exists>
									<#list colaboradorQuestionariosTeD as colaboradorQuestionarioTeD>
										<a href="pesquisa/colaboradorResposta/prepareResponderQuestionario.action?colaborador.id=${colaborador.id}&questionario.id=${colaboradorQuestionarioTeD.questionario.id}&turmaId=${colaboradorQuestionarioTeD.turma.id}&voltarPara=../../index.action">
											${colaboradorQuestionarioTeD.questionario.titulo} (Curso ${colaboradorQuestionarioTeD.turma.curso.nome}, turma ${colaboradorQuestionarioTeD.turma.descricao})
										</a>
										<br />
									</#list>
								</#if>
								
								<#if colaboradorQuestionariosTeD?size < 1>
									<span>Não existem Avaliações de T&D disponíveis</span>
								</#if>
							</div>
						</div>
					</#if>
					
					<#list mensagems?keys as tipo>
						<div class="portlet">
							<div class="portlet-header">${action.getDescricaoTipo(tipo)}</div>
							<div class="portlet-content">
								<table width="100%" class="dados" style="border:none;">
									<tbody>
										<#assign j=0/>
										<#list action.getMensagens(tipo) as uMsg>
											<#if j%2==0>
												<#assign class="odd"/>
											<#else>
												<#assign class="even"/>
											</#if>
	
											<#if !uMsg.lida>
												<#assign style="font-weight: bold;"/>
												<#assign status="Não">
											<#else>
												<#assign style=""/>
												<#assign status="Sim">
											</#if>
										
											<tr class="${class}">
												<td width="40" align="center">
													<a href="javascript: popup('geral/usuarioMensagem/leituraUsuarioMensagemPopup.action?usuarioMensagem.empresa.id=4&amp;usuarioMensagem.id=${uMsg.id}', 400, 500)"><img border="0" title="Visualizar mensagem"  src="/fortesrh/imgs/olho.jpg"></a>
													<a href="javascript: newConfirm('Confirma exclusão?', function(){window.location='geral/usuarioMensagem/delete.action?usuarioMensagem.id=${uMsg.id}'});"><img border="0" title="Excluir" src="/fortesrh/imgs/delete.gif"/></a>
												</td>
												<td>
													<span class="remetenteHora">${uMsg.mensagem.remetente} - ${uMsg.mensagem.data?string("dd/MM/yyyy HH:mm")}</span><br />
													<#if uMsg.mensagem.link?exists && uMsg.mensagem.link != "">
														<a href="${uMsg.mensagem.link}" title="${uMsg.mensagem.textoAbreviado}"  onclick="marcarMensagemLida(${uMsg.id});" style="${style}">
															${uMsg.mensagem.textoAbreviado}
														</a>
													<#else>
														${uMsg.mensagem.textoAbreviado}
													</#if>
												</td>
											</tr>
											<#assign j=j+1/>
										</#list>
									</tbody>
								</table>
							</div>
						</div>

						<#-- Passa para a coluna da esquerda -->
						<#if i == (qtd/2)?int - 1>
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
		<#if pendenciaACs?exists>
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
	
	
	
	
	
	
	<#-- 
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
		<br>
	</#if>

	<#if questionarios?exists || avaliacoesDesempenhoPendentes?exists>
		<div class="waDivTituloX">Pesquisas/Avaliações Disponíveis</div>
		<div class="waDivFormularioX">

			<#if colaborador?exists && colaborador.id?exists>
				<#list questionarios as questionario>
					<p><a href="pesquisa/colaboradorResposta/prepareResponderQuestionario.action?questionario.id=${questionario.id}&colaborador.id=${colaborador.id}&tela=index&validarFormulario=true">${questionario.titulo}</a></p>
				</#list>
			</#if>
			
			<#if colaborador?exists && colaborador.id?exists>
				<#list avaliacoesDesempenhoPendentes as avaliacaoDesempenho>
					<p>
					<a href="avaliacao/desempenho/prepareResponderAvaliacaoDesempenho.action?colaboradorQuestionario.id=${avaliacaoDesempenho.id}">${avaliacaoDesempenho.avaliacaoDesempenho.titulo} (${avaliacaoDesempenho.colaborador.nome}) (${avaliacaoDesempenho.avaliacaoDesempenho.periodoFormatado})</a>
					</p>
				</#list>
			</#if>

			<#if questionarios?size < 1 && avaliacoesDesempenhoPendentes?size < 1>
				<span>Não existem questionários disponíveis</span>
			</#if>
		</div>
	</#if>
	<br>

	<#if colaboradorQuestionariosTeD?exists>
		<div class="waDivTituloX">Avaliações de T&D</div>
		<div class="waDivFormularioX">

			<#if colaborador?exists && colaborador.id?exists>
				<#list colaboradorQuestionariosTeD as colaboradorQuestionarioTeD>
					<a href="pesquisa/colaboradorResposta/prepareResponderQuestionario.action?colaborador.id=${colaborador.id}&questionario.id=${colaboradorQuestionarioTeD.questionario.id}&turmaId=${colaboradorQuestionarioTeD.turma.id}&voltarPara=../../index.action">
						${colaboradorQuestionarioTeD.questionario.titulo} (Curso ${colaboradorQuestionarioTeD.turma.curso.nome}, turma ${colaboradorQuestionarioTeD.turma.descricao})
					</a>
					<br />
				</#list>
			</#if>
			
			<#if colaboradorQuestionariosTeD?size < 1>
				<span>Não existem Avaliações de T&D disponíveis</span>
			</#if>
		</div>
	</#if>
	<br>
	
	<@authz.authorize ifAllGranted="ROLE_VISUALIZAR_MSG">
		<#if mensagems?exists>
			<div class="waDivTituloX">Caixa de Mensagens</div>
			<div class="waDivFormularioX">
			<#if mensagems?size < 1>
				<span>Nenhuma mensagem</span>
			<#else>
				<@ww.form name="formMensagemUsuario" action="geral/usuarioMensagem/delete.action" method="POST">
					<@display.table name="mensagems" id="mensagem" class="dados" sort="list">
						<#if !mensagem.lida>
							<#assign style="font-weight: bold;"/>
							<#assign status="Não">
						<#else>
							<#assign status="Sim">
							<#assign style=""/>
						</#if>
	
						<@display.column title="<input type='checkbox' id='md' onclick='atualizaChecks(\"checkMensagem\", this.checked);' />" style="width: 30px; text-align: center;">
							<input type="checkbox" class="checkMensagem" value="${mensagem.id}" name="usuarioMensagemIds" />
						</@display.column>
						
						<@display.column title="Ações" style="width: 40px;text-align:center">
							<a href="javascript: popup('<@ww.url includeParams="none" value="geral/usuarioMensagem/leituraUsuarioMensagemPopup.action?usuarioMensagem.id=${mensagem.id}&usuarioMensagem.empresa.id=${empresaId}"/>', 400, 500)"><img border="0" title="Visualizar mensagem"  src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>"></a>
							<a href="javascript: newConfirm('Confirma exclusão?', function(){window.location='geral/usuarioMensagem/delete.action?usuarioMensagem.id=${mensagem.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
						</@display.column>
	
						<@display.column property="mensagem.remetente" title="De" style='${style} width: 80px;' />
						<@display.column property="mensagem.data" title="Data" format="{0,date,dd/MM/yyyy HH:mm}" style='${style} width: 100px;' />
						<@display.column title="Mensagem" style='${style}'>
							<#if mensagem.mensagem.link?exists && mensagem.mensagem.link != "">
								<a href="${mensagem.mensagem.link}" title="${mensagem.mensagem.textoAbreviado}"  onclick="marcarMensagemLida(${mensagem.id});">${mensagem.mensagem.textoAbreviado}</a>
							<#else>
								${mensagem.mensagem.textoAbreviado}
							</#if>
						</@display.column>

						<@display.column title="Lida" style='${style}' >
							${status}
						</@display.column>	
					</@display.table>
				</@ww.form>
				<div class="buttonGroup">
					<button onclick="javascript: newConfirm('Confirma exclusão das mensagens selecionadas?', function(){document.formMensagemUsuario.submit();});" class="btnExcluir"></button>
				</div>
			</#if>
			</div>
		</#if>
		<br>
	</@authz.authorize>
	
	<@authz.authorize ifAllGranted="ROLE_VISUALIZAR_SOLICITACAO_PESSOAL">
		<#if solicitacaos?exists && 0 < solicitacaos?size >
			<div class="waDivTituloX">Existem solicitações de pessoal aguardando liberação</div>
			<div class="waDivFormularioX">
				<a href="<@ww.url includeParams="none" value="/captacao/solicitacao/list.action"/>">Clique aqui para visualizar</a>
			</div>
		</#if>
		<br>

		<#if candidatoSolicitacaos?exists && 0 < candidatoSolicitacaos?size>
			<div class="waDivTituloX">Existem currículos aguardando aprovação para participar de seleção (Triagem do módulo externo)</div>
			<div class="waDivFormularioX">
				<#list candidatoSolicitacaos as candidatoSolicitacao>
					<a href="<@ww.url includeParams="none" value="/captacao/candidatoSolicitacao/listTriagem.action?solicitacao.id=${candidatoSolicitacao.solicitacao.id}"/>">${candidatoSolicitacao.solicitacao.descricao}</a><br>
				</#list>
			</div>
		</#if>
		<br>
	</@authz.authorize>

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
	-->
	
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
		<@authz.authorize ifAllGranted="ROLE_VISUALIZAR_MSG">
			<#if primeiraExecucao?exists && primeiraExecucao && possuiMensagem?exists && possuiMensagem>
				jAlert('Você tem mensagens não lidas.');
			</#if>
		</@authz.authorize>
			
		<#if idiomaIncorreto?exists && !idiomaIncorreto>
			jAlert('O idioma do sistema está incorreto, favor alterar para Português-Brasileiro .');
		</#if>
	</script>
</body>
</html>