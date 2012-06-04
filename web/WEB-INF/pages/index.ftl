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
		div.odd
		{
			background-color: #FFF;
			font-family: Arial, Helvetica, sans-serif;
			font-size:13px;
			padding: 10px;
		}
		div.even
		{
			background-color: #E4F0FE;
			font-family: Arial, Helvetica, sans-serif;
			font-size:13px;
			padding: 10px;
		}
		div.even a, div.odd a
		{
			font-weight: bold;
			text-decoration: none;
			color: #454C54;
		}
		
		a, a:visited {
		text-decoration:none;
		color: #454C54;
		font-family: Arial, Helvetica, sans-serif;
		font-size:13px;
		}
		
		a:link {text-decoration: none}
		a:visited {text-decoration: none}
		a:hover {text-decoration: underline; 
		color: #2471A7;
		}
		a:active {text-decoration: none}
		
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
	</style>
	
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
					<p><a href="pesquisa/colaboradorResposta/prepareResponderQuestionario.action?colaborador.id=${colaborador.id}&questionario.id=${colaboradorQuestionarioTeD.questionario.id}&turmaId=${colaboradorQuestionarioTeD.turma.id}&voltarPara=../../index.action">${colaboradorQuestionarioTeD.questionario.titulo}</a></p>
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