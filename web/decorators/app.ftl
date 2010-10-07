<#assign ww=JspTaglibs["/WEB-INF/tlds/webwork.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<link rel="SHORTCUT ICON" href='<@ww.url includeParams="none" value="/imgs/fortes.ico"/>'>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="Content-Language" content="pt-br" />
	<meta http-equiv="Cache-Control" content="no-cache, no-store" />
	<meta http-equiv="Pragma" content="no-cache, no-store" />
	<meta http-equiv="Expires" content="0" />
	<title>${title}</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-1.3.2.min.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.numberformatter-1.1.0.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/functions.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/fortes.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/mooTools/mootools-1.2.4.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/MenuMatic_0.68.3.js"/>'></script>

	<style type="text/css">
		<#if pgInicial?exists && pgInicial>
			@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
		</#if>

		@import url('<@ww.url includeParams="none" value="/css/default.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/fortes.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/botoes.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/MenuMatic.css"/>');
	</style>

	<!--[if IE]>
	<style type="text/css" media="screen">
	body
	{
		behavior: url(<@ww.url includeParams="none" value="/css/csshover2.htc"/>);
	}
	.smenuDiv
	{
		width: 100%;
	}
	#mainDiv
	{

	}

	.liLeft
	{
		margin-right:0px;
	}

	span.botao
	{
		padding:2px 0 1px 0;
	}
	fieldset
	{
		padding: 10px;
	}

	</style>
	<![endif]-->

	${head}
</head>
<body>
	<div id="topDiv">
		<div id="userDiv">
			<span class="saudacao">
				Bem-vindo(a)&nbsp;
			</span>
			<span class="nomeUsuario"><@authz.authentication operation="nome"/>&nbsp;</span>
			<span class="nomeEmpresa">(<@authz.authentication operation="empresaNome"/>)&nbsp;&nbsp;</span>
			<br>
			<span class="nomeEmpresa"><@authz.authentication operation="ultimoLogin"/>&nbsp;&nbsp;</span>
		</div>
		<div id="userDiv1">
			<img src='<@ww.url includeParams="none" value="/imgs/topo_img_right.jpg"/>' border='0' align='absMiddle' />
		</div>
		<div id="logoDiv"><a href="<@ww.url value='/'/>"><img src='<@ww.url includeParams="none" value="/imgs/topo_ico.jpg"/>' border='0'/></a></div>
		
		<#if REG_MSG?exists && REG_MSG != "">
			<span id="msgAutenticacao">&nbsp &nbsp &nbsp ${REG_MSG}</span>
		</#if>
	</div>

	<@authz.authentication operation="menuFormatado"/>
	<div style="clear: both"></div>
	<div id="waDiv">
		<br>
		<#if !pgInicial?exists>
			<#if title != "">
				<div id="waDivTitulo">${title}</div>
			</#if>
			<div class="waDivFormulario">
		</#if>
			${body}
		<#if !pgInicial?exists>
			</div>
		</#if>
		<br><br>
	</div>

	<!-- Create a MenuMatic Instance -->
	<script type="text/javascript" >
		window.addEvent('domready', function() {			
			var myMenu = new MenuMatic();
		});		
	</script>

</body>
</html>