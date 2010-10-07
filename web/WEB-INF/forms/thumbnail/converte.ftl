<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
		<@ww.head/>
		<title>Converte todas as imagens para thumbnail</title>
	
	</head>
	<body>
		<@ww.actionmessage />
		<@ww.actionerror />
		<@ww.form name="form" action="processa.action" method="POST" id="form">
			<p>Converte todas as imagens para thumbnail (miniatura) contidas no banco de dados.</p>
			<input id="processa" type="submit" 
				value="Converte Tudo"
				style="height: 40px; font-weight: bold; font-size: 18px;"
				class="grayBGE">
		</@ww.form>
	</body>
</html>