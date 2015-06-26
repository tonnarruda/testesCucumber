<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Empresas</title>
</head>
<body>
	<@ww.actionerror/>
	<@ww.actionmessage/>
	<@display.table name="empresas" id="empresa" class="dados" defaultsort=2 >
		<@display.column title="Ações" style="text-align:center; width: 80px" media="html">
			<a href="prepareUpdate.action?empresa.id=${empresa.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			
			<#assign msgDelete ="Confirma exclusão?"/>
			<#if usuarioFortes>
				<#assign msgDelete ="Deseja realmente excluir a empresa?\\n Isso acarretará a perda de todos os dados referente à empresa.\\n Sugiro efetuar um backup do banco de dados antes da confirmação."/>
			</#if>
			
			<a href="#" onclick="newConfirm('${msgDelete}', function(){window.location='delete.action?empresa.id=${empresa.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		
		</@display.column>
		<@display.column property="nome" title="Nome"/>
		<@display.column property="razaoSocial" title="Razão Social"/>
	</@display.table>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
		</button>
	</div>

</body>
</html>