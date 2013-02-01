<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />

<html>
<head>
	<@ww.head/>
	
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytagModuloExterno.css"/>');
		@import url('<@ww.url value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>');
		
		.dados a, 
		#popup a { color: blue; }
		#popup ul li { margin: 5px 0px; list-style: disc; }
	</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/SolicitacaoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
	<script type='text/javascript'>
		function menuAvaliacoesSolicitacao(anuncioId, anuncioTitulo, solicitacaoId) 
		{
			$('#popup h5').text(anuncioTitulo);
			
			SolicitacaoDWR.findAvaliacoesNaoRespondidas(solicitacaoId, ${SESSION_CANDIDATO_ID}, 
														function(dados) {
															var lista = "";
															$(dados).each(  function(item, solicitacaoAvaliacao) {
																				lista += "<li><a href='prepareInsertAvaliacaoSolicitacao.action?anuncioId=" + anuncioId + "&solicitacao.id=" + solicitacaoAvaliacao.solicitacaoId + "&colaboradorQuestionario.avaliacao.id=" + solicitacaoAvaliacao.avaliacaoId + "&candidato.id=${SESSION_CANDIDATO_ID}&moduloExterno=true'>" + solicitacaoAvaliacao.avaliacaoTitulo + "</a></li>\n";
																			});
														
															$('#popup ul').html(lista);
														
															$('#popup').dialog({
																modal: true
															});
														});	
	    }
	</script>
</head>
<body>

<#if !SESSION_CANDIDATO_NOME?exists >
	<script>window.location='prepareLogin.action';</script>
</#if>

<table width="100%">
	<tr>
		<td width="75%">
			<#if SESSION_CANDIDATO_NOME?exists >
				<b>Bem vindo ${SESSION_CANDIDATO_NOME}!<br>
				CPF: ${SESSION_CANDIDATO_CPF}</b>
			</#if>
		</td>
		<td width="1%">&nbsp;</td>
		<td width="24%" align='right'>
			<#assign areaId = 0>
			<#assign primeira = true>
			<#-- pega o id do candidato na sessao para iniciar o update-->
			<a href="prepareUpdate.action?moduloExterno=true&empresaId=${SESSION_EMPRESA}&candidato.id=${SESSION_CANDIDATO_ID}">Editar Currículo</a><br>
			<a href="prepareUpdateSenha.action?moduloExterno=true&empresaId=${SESSION_EMPRESA}&candidato.id=${SESSION_CANDIDATO_ID}">Alterar Senha</a><br>
			<a href="logoutExterno.action?empresaId=${SESSION_EMPRESA}">Sair</a>
		</td>
	</tr>
	<tr><td colspan="3"><hr><b>Vagas Abertas:</b><br></td></tr>
	<#if (anuncios?exists && anuncios?size > 0)>
		<tr>
			<td colspan="3" width="400px">
				<@display.table name="anuncios" id="anuncio" class="dados" >
					<@display.column title="Título da Vaga">
						<#if anuncio.candidatoSolicitacao?exists && anuncio.candidatoSolicitacao.id?exists>
							<strong>${anuncio.titulo}</strong>  
							(Você já está concorrendo a esta vaga) <br />
							
							<#if (anuncio.qtdAvaliacoes > 0) && (anuncio.qtdAvaliacoes > anuncio.qtdAvaliacoesRespondidas)>
								<a href="javascript:;" onclick="menuAvaliacoesSolicitacao(${anuncio.id}, '${anuncio.titulo}', ${anuncio.solicitacao.id});">Responder as avaliações</a><br />
							</#if>
						<#else>
							<strong>${anuncio.titulo}</strong> <br />
							<a href="verAnuncio.action?anuncio.id=${anuncio.id}">Visualizar</a>
						</#if>
						
						<#assign primeira=false/>
					</@display.column>
					
					<@display.column property="solicitacao.quantidade" title="Vagas" style="text-align:center; width: 40px;"/>
				</@display.table>
			</td>
		</tr>
	<#else>
		<tr><td colspan="3"><div align='center'><br><br><b>Não há vagas no momento.</b></div></td></tr>
	</#if>
	
	</table>
	
	<#if msgAlert?exists && msgAlert != "">
		<script>
			jAlert('${msgAlert}');
		</script>
	</#if>

	<div id="popup" title="Responder Avaliações da Vaga" style="display:none;">
		<h5>Técnico de SESMT</h5>
		<ul>
			<li><a href="#">Avaliação A</a></li>
			<li><a href="#">Avaliação B</a></li>
		<ul>
	</div>

	<script language='javascript'>
		<#if sucessoEnvioCurriculo>
			jAlert('Agradecemos seu interesse. Seu currículo será analisado e, se aprovado, entraremos em contato.');
		</#if>	
		<#if sucessoRespostaAvaliacao>
			jAlert('Avaliação respondida com sucesso!');
		</#if>	
	</script>
</body>
</html>