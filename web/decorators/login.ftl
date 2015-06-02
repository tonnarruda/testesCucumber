<#assign ww=JspTaglibs["/WEB-INF/tlds/webwork.tld"] />
<html>
<head>
	<link rel="SHORTCUT ICON" href='<@ww.url includeParams="none" value="/imgs/inf.ico"/>'>
	<title>Login</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="<@ww.url includeParams="none" value="/css/app.css" />" rel="stylesheet" type="text/css" media="all"/>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-1.4.4.min.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.alerts.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/fortes.js"/>'></script>
	
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/jquery.alerts.css"/>');
	</style>
	${head}
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="document.forms[0].elements[0].focus();">
${body}

</body>
</html>