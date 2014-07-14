<#assign ww=JspTaglibs["/WEB-INF/tlds/webwork.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<meta http-equiv="Content-Language" content="pt-br" />
	<meta http-equiv="Cache-Control" content="no-cache, no-store" />
	<meta http-equiv="Pragma" content="no-cache, no-store" />
	<meta http-equiv="Expires" content="0" />
	
	<title>Tarefas Agendadas</title>
	
	<style>
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>
</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@display.table name="triggers" id="trigger" class="dados">
		<@display.column property="jobGroup" title="Grupo" style="width: 70px; text-align: center;" />
		<@display.column property="jobName" title="Tarefa" />
		<@display.column property="nextFireTime" title="Próxima Execução" format="{0,date,dd/MM/yyyy HH:mm:ss}" style="width: 125px; text-align: center;" />
	</@display.table>
</body>
</html>