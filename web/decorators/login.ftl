<#assign ww=JspTaglibs["/WEB-INF/tlds/webwork.tld"] />
<html>
<head>
	<link rel="SHORTCUT ICON" href='<@ww.url includeParams="none" value="/imgs/fortes.ico"/>'>
	<title>Login</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="<@ww.url includeParams="none" value="/css/app.css" />" rel="stylesheet" type="text/css" media="all"/>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery-1.3.2.min.js"/>"></script>
	${head}
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="document.forms[0].elements[0].focus();">
${body}


<script type="text/javascript" >
	jQuery(function($){
		var versaoNavegador = $.browser.version;

		if (jQuery.browser.msie && versaoNavegador < "7.0") 
		{
			$('body').prepend("<div id=\"asn-warning\" style=\"position:absolute; display:none; left: 0px; border-bottom: solid 1px #DFDDCB; top:0px; margin: 0px; padding: 5px 0px; width: 100%; color: #4F4D3B; background: #FFFCDF; text-align: center;\">Você está usando um navegador em uma versão não homologada para o Fortes RH. Sugerimos o uso do Mozilla Firefox 3 ou superior.<a href=\"javascript://\" id=\"asn-close\" style=\"color: #4F4D3B; text-decoration: underline; font: normal 8pt/14px 'Trebuchet MS', Arial, Helvetica;\">[x]</a></div>");
			$('#asn-warning').fadeIn(1000);
			$('#asn-close').click(function(){ $('#asn-warning').fadeOut(400); });
		}
	}); 
</script>

</body>
</html>