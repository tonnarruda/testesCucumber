<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		</style>
	
		<@ww.head/>
		<title>Backup do Banco de Dados</title>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		<div>
			Backups armazenados no diretório: ${backupPath} <br /><br />
		
			<#if arquivos?exists && 0 < arquivos?size>
				<@display.table name="arquivos" id="arquivo" class="dados">
					<@display.column title="Ações" class="acao">
						<a onclick="loader(true)" href="enviarFileBox.action?filename=${arquivo.nome}"><img border="0" title="Enviar arquivo para Fortes Tecnologia." src="<@ww.url value="/imgs/upload.png"/>" /></a>
					</@display.column>
					<@display.column property="nome" title="Nome do Arquivo" />					
					<@display.column property="tamanho" title="Tamanho" style="text-align: right;" />					
					<@display.column property="data" title="Data de modificação" format="{0,date,dd/MM/yyyy}" style="text-align: center;"/>					
				</@display.table>
			</#if>
			
			<br>			
	
			<#if permiteDeleteSemCodigoAC>
				<button onclick="window.location='../geral/parametrosDoSistema/prepareDeleteSemCodigoAC.action'" class="btnVoltar"></button>
			</#if>
			
			<button onclick="loader(true);window.location='gerar.action';" class="btnBackup"/></button>
		</div>
	</body>
</html>