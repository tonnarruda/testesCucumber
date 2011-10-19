<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
		<@ww.head/>
		<title>Backup do Banco de Dados</title>
	
	</head>
	<body>
		<@ww.actionerror />
		<div>
			Erro ao gerar backup, entre em contato com o suporte!
			<br><br>
			Backups armazenados na pasta ..\RH\backup_db:
			<br>			
			${arquivos}
		</div>
	</body>
</html>