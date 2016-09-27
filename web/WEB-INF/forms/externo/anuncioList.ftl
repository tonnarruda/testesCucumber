<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />

<html>
<head>
	<@ww.head/>
	
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytagModuloExterno.css"/>');
		@import url('<@ww.url value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>');
		
		table a { color: blue; }
		table.dados a { margin-right: 10px; }
		#popup ul li { margin: 5px 0px; list-style: disc; }
		#popupEnvioAmigo li { margin: 5px 0px; list-style: none; }
		h5 { margin: 2px 0px; }
	</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/SolicitacaoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
	<script type='text/javascript'>
		<#if SESSION_CANDIDATO_ID?exists>
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
		</#if>
	</script>
</head>
<body>

<table width="100%">
	<tr>
		<td colspan="3">
			<strong>Vagas Abertas:</strong> 
			
			<#if !SESSION_CANDIDATO_ID?exists && empresaId?exists>
				<div style="float:right">
					<a href="logoutExterno.action?empresaId=${empresaId}">Efetuar login</a>
				</div>
			</#if>
		</td>
	</tr>
	
	<#if (anuncios?exists && anuncios?size > 0)>
		<tr>
			<td colspan="3" width="400px">
				<@display.table name="anuncios" id="anuncio" class="dados" >
					<@display.column title="Título da Vaga">
						<#if anuncio.candidatoSolicitacao?exists && anuncio.candidatoSolicitacao.id?exists>
							<strong>${anuncio.titulo}</strong>  
							(Você já está concorrendo a esta vaga) <br />
							
							<#if (anuncio.qtdAvaliacoes > 0) && (anuncio.qtdAvaliacoes > anuncio.qtdAvaliacoesRespondidas)>
								<a href="javascript:;" onclick="menuAvaliacoesSolicitacao(${anuncio.id}, '${anuncio.titulo}', ${anuncio.solicitacao.id});">Responder às avaliações</a>
							</#if>
						<#else>
							<strong>${anuncio.titulo}</strong> <br />
							<a href="verAnuncio.action?anuncio.id=${anuncio.id}">Visualizar</a>
						</#if>
						
						<a href="javascript:;" onclick="exibirFormAnuncioEmail(${anuncio.id}, '${anuncio.titulo}');">Enviar para um amigo</a>
					</@display.column>
					
					<@display.column property="solicitacao.quantidade" title="Vagas" style="text-align:center; width: 40px;"/>
					<@display.column property="dataPrevisaoEncerramento" title="Previsão de encerramento" format="{0,date,dd/MM/yyyy}" style="text-align:center; width:70px;"/>
					<@display.column property="solicitacao.empresa.nome" title="Empresa" style="text-align:center; width: 40px;"/>
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
		<h5></h5>
		<ul></ul>
	</div>

	<#include "enviarAnuncioEmailIncludes.ftl"/>

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