<html>
<head>
<@ww.head/>
	<title>Informações sobre o Anúncio</title>
	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/botoes.css"/>" media="screen" type="text/css">

	<#if moduloExterno?exists && moduloExterno>
		<style type="text/css">
			.btnEnviar{
				background-image:url(../imgs/btnEnviarExterno.gif) !important;
				width: 81px !important;
			}
			.btnVoltar
			{
				background-image:url(../imgs/btnVoltarExterno.gif) !important;
				width: 69px !important;
			}
			input,textarea,select,fieldset {
				border: 1px solid #BFB6B3 !important;
			}
		</style>
	</#if>

</head>
<body>

	<@ww.actionerror />
	<h3>${anuncio.titulo}</h3>
	<span class="item"><b>Descrição:</b></span><br>
	<pre>${anuncio.cabecalhoFormatado}</pre><br><br>

	<div class="buttonGroup">
		<button onclick="window.location='enviarCurriculo.action?candidato.id=&anuncio.id=${anuncio.id}&solicitacao.id=${anuncio.solicitacao.id}'" class="btnQueroMeCandidatar" accesskey="E">
		</button>
		<button onclick="window.location='prepareListAnuncio.action'" class="btnVoltar" accesskey="V">
		</button>
	</div>
</body>
</html>