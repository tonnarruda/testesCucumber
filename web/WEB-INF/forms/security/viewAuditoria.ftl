<html>
	<head>
		<link type="text/css" rel="stylesheet" href="<@ww.url value="/css/syntaxhighlighter/shCore.css"/>"/>
		<link type="text/css" rel="stylesheet" href="<@ww.url value="/css/syntaxhighlighter/shThemedefault.css?version=${versao}"/>"/>
		<script src='<@ww.url includeParams="none" value="/js/syntaxhighlighter/shCore.js"/>'></script>
		<script src='<@ww.url includeParams="none" value="/js/syntaxhighlighter/shBrushPlain.js"/>'></script>
		
		<style type="text/css">
			@import url('<@ww.url includeParams="none" value="/css/botoes.css?version=${versao}"/>');
			* {
				font-family:verdana, sans-serif;
				font-size: 12px;
			}
			h3 {
				margin: 0;
				float: right;
				color:white;
				padding-right: 2px;
			}
			td {
				padding: 4px;
				background-color:#F8F8F8;
			}
			.tdLabel {
				text-align:right;
				background-color: #999999;
				width:12%;
			}
			.tdDados {
				background-color:white;
			}
			.waDivTitulo {
				background-color:#999999;
				color:white;
				font-weight:bold;
				padding:8px 15px;
			}
			.waDivFormulario {
				background-color:#FFFFFF;
				border:1px solid #BEBEBE;
				color:#5C5C5A;
				padding:15px;
			}
		</style>
	<title>Auditoria do Sistema</title>
	
	</head>
	<body>
		<div class="waDivTitulo">Informações</div>
		<div class="waDivFormulario">
			<table width="100%">
				<tr>
					<td class="tdLabel">
						<h3>Data</h3>
					</td>
					<td>
						${auditoriaView.data}
					</td>
				</tr>
				<tr>
					<td class="tdLabel">
						<h3>Usuário</h3>
					</td>
					<td>
						${auditoriaView.usuario.nomeFormatado}
					</td>
				</tr>
				<tr>
					<td class="tdLabel">
						<h3>Módulo</h3>
					</td>
					<td>
						${auditoriaView.entidade}
					</td>
				</tr>
				<tr>
					<td class="tdLabel">
						<h3>Operação</h3>
					</td>
					<td>
						${auditoriaView.operacao}
					</td>
				</tr>
				<tr>
					<td class="tdLabel">
						<h3>Chave</h3>
					</td>
					<td>
						${auditoriaView.chave}
					</td>
				</tr>
				<tr>
					<td class="tdLabel">
						<h3>Dados</h3>
					</td>
					<td class="tdDados">
						<div style="height: 350px;overflow-x: auto;">
							<pre id="log" class="brush:plain; wrap-lines: true">${auditoriaView.dados}</pre>
						</div>
						<script type="text/javascript">
							SyntaxHighlighter.config.clipboardSwf = '<@ww.url includeParams="none" value="/js/syntaxhighlighter/clipboard.swf"/>';
							SyntaxHighlighter.all();
						</script>
					</td>
				</tr>
			</table>
		</div>
		<div class="buttonGroup">
			<button onclick="window.close();" class="btnFechar" accesskey="V">
			</button>
		</div>
	</body>
</html>