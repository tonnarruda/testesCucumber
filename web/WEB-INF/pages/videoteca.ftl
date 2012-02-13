<#assign ww=JspTaglibs["/WEB-INF/tlds/webwork.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="Content-Language" content="pt-br" />
<meta http-equiv="Cache-Control" content="no-cache, no-store" />
<meta http-equiv="Pragma" content="no-cache, no-store" />
<meta http-equiv="Expires" content="0" />
<title>RH</title>
<head>
	<style>
		
	</style>
	
	<script type='text/javascript'>
		
	</script>

</head>
<body>
	<#assign usuarioId><@authz.authentication operation="id"/></#assign>

	<@ww.actionmessage />
	<@ww.actionerror />	
	Videoteca
</body>
</html>