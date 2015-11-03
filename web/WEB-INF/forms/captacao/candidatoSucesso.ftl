<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign ww=JspTaglibs["/WEB-INF/tlds/webwork.tld"] />
<html>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="Content-Language" content="pt-br" />
	<meta http-equiv="Cache-Control" content="no-cache, no-store" />
	<meta http-equiv="Pragma" content="no-cache, no-store" />
	<meta http-equiv="Expires" content="0" />

	<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/candidato.css?version=${versao}"/>');
	#cv {
		overflow:auto;
		padding: 10px;
		width:720px;
		height:350px;
		border-left:2px solid #808080;
		border-top:2px solid #808080;
		border-bottom:2px solid #fff;
		border-right:2px solid #fff;
		background:#FFF;
	}
	#msg {
		text-align: center;
		font:15px sans-serif;
		font-weight: Bold;
		color: #002EB8
	}
	</style>

	<script type='text/javascript'>
		$(function() {
			$("#cv").load('<@ww.url includeParams="none" value="/captacao/candidato/verCurriculo.action?candidato.id=${candidato.id}"/>');
		});
	</script>
	<@ww.head />
</head>
<body>
	<@ww.actionmessage />

	<@ww.div id="cv"/>

	<br>
	<div class="buttonGroup">
		<span style="float:left;">
			<button onclick="window.location='list.action'" class="btnVoltar" accesskey="V">
			</button>

			<button onclick="window.location='prepareUpdate.action?candidato.id=${candidato.id}'" class="btnEditar" accesskey="E">
			</button>
			<button class="btnImprimirPdf" onclick="window.location='imprimirCurriculo.action?candidato.id=${candidato.id}'">
			</button>
			<button onclick="window.location='prepareInsertCurriculoPlus.action?candidato.id=${candidato.id}'" class="btnInserirCurriculoEscaneado" accesskey="E">
			</button>
		</span>

		<span style="float: right;">
			<button onclick="window.location='../../captacao/solicitacao/verSolicitacoes.action?candidato.id=${candidato.id}'" class="btnSolicitacoes" accesskey="S"> </button>
		</span>
	</div>
	<br>
	<br>
</body>
</html>