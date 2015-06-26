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
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	</style>
</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@display.table name="triggers" id="trigger" class="dados">
		<@display.column title="Ações" media="html" style="text-align:center; width:40px;">
			<a href="javascript:;" onclick="newConfirm('Confirma execução?', function(){window.location='executeJob.action?jobName=${trigger.name}&jobGroup=${trigger.group}&jobClass=${trigger.jobDetail.jobClass.name}'});"><img border="0" title="Executar" src="<@ww.url includeParams="none" value="/imgs/btnRight.gif"/>"></a>
		</@display.column>
		<@display.column property="jobGroup" title="Grupo" style="width: 70px; text-align: center;" />
		<@display.column property="jobName" title="Tarefa" />
		<@display.column property="nextFireTime" title="Próxima Execução" format="{0,date,dd/MM/yyyy HH:mm:ss}" style="width: 125px; text-align: center;" />
	</@display.table>
</body>
</html>