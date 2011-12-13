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
			Backups armazenados na pasta > ${backupPath} <br /><br />
		
			<#if arquivos?exists && 0 < arquivos?size>
				<@display.table name="arquivos" id="arquivo" class="dados">
					<@display.column title="Nome do Arquivo / Tamanho / Data de modificação">${arquivo}</@display.column>					
				</@display.table>
			</#if>
			
			<br>			
			<button onclick="window.location='../geral/parametrosDoSistema/prepareDeleteSemCodigoAC.action'" class="btnVoltar"></button>
			<button onclick="window.location='gerar.action';" class="btnBackup"/></button>
		</div>
	</body>
</html>