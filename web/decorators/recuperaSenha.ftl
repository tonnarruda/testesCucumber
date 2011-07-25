<#assign ww=JspTaglibs["/WEB-INF/tlds/webwork.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="Content-Language" content="pt-br" />
<meta http-equiv="Cache-Control" content="no-cache, no-store" />
<meta http-equiv="Pragma" content="no-cache, no-store" />
<meta http-equiv="Expires" content="0" />
<title>${title}</title>
<script src='<@ww.url includeParams="none" value="/js/jQuery/jquery-1.4.4.min.js"/>'></script>
<script src='<@ww.url includeParams="none" value="/js/functions.js"/>'></script>
<script src='<@ww.url includeParams="none" value="/js/fortes.js"/>'></script>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/default.css"/>');
	@import url('<@ww.url includeParams="none" value="/css/fortes.css"/>');
	@import url('<@ww.url includeParams="none" value="/css/botoes.css"/>');
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
	<div id="waDiv">
		<br>
		<#if title != "">
			<div id="waDivTitulo">${title}</div>
		</#if>
		<div class="waDivFormulario">
			${body}
		</div>
		<br><br>
	</div>
</body>
</html>