<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<@ww.head/>
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		</style>
		<title>Arquivos de Log </title>
	</head>
	<body>
		<@ww.actionmessage />
		<@ww.actionerror />
		<@display.table name="logs" id="row" class="dados">
			<@display.column title="Ações" class="acao">
				<a href="loadLogFile.action?filename=${row.name}">
					<img border="0" title="Detalhar Arquivo de Log" src="<@ww.url value="/imgs/log.png"/>">
				</a>
			</@display.column>
			<@display.column title="Nome">
				<a href="loadLogFile.action?filename=${row.name}">
					${row.name}
				</a>
			</@display.column>
			<@display.column title="Tamanho">
				${row.size} bytes
			</@display.column>
			<@display.column title="Modificado em" property="lastModified" format="{0,date,dd/MM/yyyy HH:mm}"/>
		</@display.table>
	</body>
	
</html>