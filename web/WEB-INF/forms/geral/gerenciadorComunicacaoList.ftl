<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<title>Gerenciador de Comunicação</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="gerenciadorComunicacaos" id="gerenciadorComunicacao" class="dados" defaultsort=2 >
		<@display.column title="Ações" class="acao" style="width:40px;">
			<a href="prepareUpdate.action?gerenciadorComunicacao.id=${gerenciadorComunicacao.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="javascript:;" onclick="javascript:newConfirm('Confirma exclusão?', function(){window.location='delete.action?gerenciadorComunicacao.id=${gerenciadorComunicacao.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="operacaoGrupo" title="Grupo"/>
		<@display.column property="operacaoDescricao" title="Notificar quando"/>
		<@display.column property="meioComunicacaoDescricao" title="Através de"/>
		<@display.column property="enviarParaDescricao" title="Para"/>
		<@display.column property="destinatarioFormatado" title="Destinatário(s)"/>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
	</div>
</body>
</html>
