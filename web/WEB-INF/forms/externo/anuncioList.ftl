<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />

<html>
<head>
<@ww.head/>

<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytagModuloExterno.css"/>');
	@import url('<@ww.url value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>');
	
	.dados a { color: blue; }
</style>

<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>

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
						<#if anuncio.candidatoCadastrado>
							<strong>${anuncio.titulo}</strong>  
							(Você já está concorrendo a esta vaga) <br />
							
							<#if (anuncio.solicitacao.solicitacaoAvaliacaos?size > 0)>
								<a href="javascript:;" onclick="$('#avaliacoes${anuncio.id}').dialog();">Responder às avaliações</a><br />
								
								<div id="avaliacoes${anuncio.id}" title="Selecione uma avaliação" style="display:none;">
									<#list anuncio.solicitacao.solicitacaoAvaliacaos as solicitacaoAvaliacao>
										<a title="Responder" href="prepareInsertAvaliacaoSolicitacao.action?anuncioId=${anuncio.id}&solicitacao.id=${anuncio.solicitacao.id}&colaboradorQuestionario.avaliacao.id=${solicitacaoAvaliacao.avaliacao.id}&candidato.id=${SESSION_CANDIDATO_ID}&moduloExterno=true">${solicitacaoAvaliacao.avaliacao.titulo}</a><br />
									</#list>
								</div>
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