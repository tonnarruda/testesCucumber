<html>
<head>
<@ww.head/>
	<title>Informações sobre o Anúncio</title>
	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/botoes.css"/>" media="screen" type="text/css">
	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>" media="screen" type="text/css">

	<style type="text/css">
		<#if moduloExterno?exists && moduloExterno>
			.btnEnviar { background-image:url(../imgs/btnEnviarExterno.gif) !important; width: 81px !important; }
			.btnVoltar { background-image:url(../imgs/btnVoltarExterno.gif) !important; width: 69px !important; }
			#loginCadastro p { text-align: justify; }
		</#if>
	</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/SolicitacaoDWR.js"/>'></script>
	<script type="text/javascript" src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
	<script type="text/javascript">
		function abrirMenuCandidatarse() {
			$('#loginCadastro').dialog({
				title: "Identificação",
				modal: true,
				width: 325,
				buttons: [
				    { text: "Criar meu cadastro", click: function() { window.location="prepareInsert.action?moduloExterno=true&empresaId=${anuncio.solicitacao.empresa.id}&solicitacao.id=${anuncio.solicitacao.id}"; } },
				    { text: "Efetuar o login", click: function() { window.location="prepareLogin.action?empresaId=${anuncio.solicitacao.empresa.id}&solicitacao.id=${anuncio.solicitacao.id}" } }
				]
			});
		}
	</script>
	
	<#assign empresaId=anuncio.solicitacao.empresa.id/>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<h3>${anuncio.titulo}</h3>
	
	<div class="item"><b>Descrição:</b></div>
	<pre>${anuncio.cabecalhoFormatado}</pre>
	
	<br /><br />

	<div class="buttonGroup">
		<#if SESSION_CANDIDATO_ID?exists>
			<button onclick="window.location='enviarCurriculo.action?anuncio.id=${anuncio.id}&solicitacao.id=${anuncio.solicitacao.id}'" class="btnQueroMeCandidatar" accesskey="E"></button>
		<#else>
			<button onclick="abrirMenuCandidatarse();" class="btnQueroMeCandidatar" accesskey="E"></button>
		</#if>
		<button onclick="exibirFormAnuncioEmail(${anuncio.id}, '${anuncio.titulo}');" class="btnEnviarParaAmigo" accesskey="A"></button>
		<button onclick="window.location='prepareListAnuncio.action?empresaId=${anuncio.solicitacao.empresa.id}'" class="btnVoltar" accesskey="V"></button>
	</div>
	
	<div id="loginCadastro" style="display:none">
		<p>Crie seu cadastro ou efetue o login no nosso sistema para efetivar a sua candidatura à essa vaga.</p>
	</div>
	
	<#include "enviarAnuncioEmailIncludes.ftl"/>
</body>
</html>