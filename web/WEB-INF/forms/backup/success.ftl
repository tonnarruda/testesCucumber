<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css"/>');
		</style>
	
		<@ww.head/>
		<title>Backup do Banco de Dados</title>
	
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		<div>
			Backups armazenados na pasta ${backupPath}: <br /><br />
		
			<#if arquivos?exists && 0 < arquivos?size>
				<@display.table name="arquivos" id="arquivo" class="dados">
					<@display.column property="name" title="Nome do Arquivo"/>
				</@display.table>
			</#if>
			
			<br>			
			<button onclick="window.location='${urlVoltar}'" class="btnVoltar"></button>
		</div>
	</body>
</html>