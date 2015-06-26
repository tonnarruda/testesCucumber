<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
<@ww.head/>
	<title>Avaliadores e Avaliados</title>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	</style>
	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/avaliadoresAvaliados.css?version=${versao}"/>" media="screen" type="text/css">

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/avaliadoresAvaliados.js?version=${versao}"/>"></script>

	<#assign edicao="true"/>

</head>
<body>
	<div id="abas">
		<div id="aba1"><a href="javascript: abas(1, '', ${edicao})">Avaliadores</a></div>
		<div id="aba2"><a href="javascript: abas(2, '', ${edicao})">Avaliados</a></div>
	</div>

	<div id="content1">
		<b>Avaliadores da Avaliação</b>
	</div>

	<div id="content2" style="display: none;">
		<b>Avaliados da Avaliação</b>
	</div>

	<@ww.hidden id="aba" name="aba" value="1"/>

	<div style="text-align: right;">
		<button id='voltar' disabled="disabled" onclick="abas(-1, 'V', ${edicao});" class="btnVoltarDesabilitado" accesskey="V">
		</button>
		<button id='avancar' onclick="abas(-1, 'A', ${edicao});" class="btnAvancar" accesskey="A">
		</button>
	</div>
</body>
</html>