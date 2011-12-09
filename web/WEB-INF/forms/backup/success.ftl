<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
		<@ww.head/>
		<title>Backup do Banco de Dados</title>
	
	</head>
	<body>
		<@ww.actionerror />
		<div>
			Backup foi gerado com sucesso!
			<br><br>
			Backups armazenados na pasta ..\RH\backup_db:
			<br>			
			${arquivos}
			<button onclick="window.location='${urlVoltar}'" class="btnVoltar"></button>
		</div>
	</body>
</html>