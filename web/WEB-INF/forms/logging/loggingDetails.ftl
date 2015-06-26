<html>
	<head>
		<@ww.head/>
		<title>Arquivos de Log [${filename}] </title>
		
		<link type="text/css" rel="stylesheet" href="<@ww.url value="/css/syntaxhighlighter/shCore.css"/>"/>
		<link type="text/css" rel="stylesheet" href="<@ww.url value="/css/syntaxhighlighter/shThemedefault.css?version=${versao}"/>"/>
		
		<script src='<@ww.url includeParams="none" value="/js/syntaxhighlighter/shCore.js"/>'></script>
		<script src='<@ww.url includeParams="none" value="/js/syntaxhighlighter/shBrushPlain.js"/>'></script>
		
	</head>
	<body>
		<@ww.actionerror />
		<div style="height: 450px;overflow-x: auto;">
			<pre id="log" class="brush:plain; wrap-lines: true">${content}</pre>
		</div>
		<div class="buttonGroup">
			<button onclick="window.location='list.action'" class="btnVoltar" accesskey="V">
			</button>
		</div>
		<script type="text/javascript">
			SyntaxHighlighter.config.clipboardSwf = '<@ww.url includeParams="none" value="/js/syntaxhighlighter/clipboard.swf"/>';
			SyntaxHighlighter.all();
		</script>
	</body>
</html>