<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<title>Cliente</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="clientes" id="cliente" class="dados">
		
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?cliente.id=${cliente.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?cliente.id=${cliente.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		
		<@display.column property="nome" title="Nome" />
		<@display.column property="enderecoInterno" title="Endereço Interno" style="width:220px;" />
		<@display.column property="enderecoExterno" title="Endereço Externo" style="width:220px;"/>
		<@display.column property="versao" title="Versão" />
		<@display.column property="dataAtualizacao" title="Data da Atualização" format="{0,date,dd/MM/yyyy}" style="width: 80px;" />
		<#if usuarioFortes>
			<@display.column title="Senha">
				<#if cliente.senhaFortes?exists && cliente.senhaFortes?trim != "">
					<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${cliente.senhaFortes?j_string}');return false">...</span>
				</#if>
			</@display.column>
		</#if>
		<@display.column title="Módulos">
			<#if cliente.modulosAdquiridos?exists && cliente.modulosAdquiridos?trim != "">
				<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${cliente.modulosAdquiridos?j_string}');return false">...</span>
			</#if>
		</@display.column>
		<@display.column title="Cont. Geral">
			<#if cliente.contatoGeral?exists && cliente.contatoGeral?trim != "">
				<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${cliente.contatoGeral?j_string}');return false">...</span>
			</#if>
		</@display.column>
		<@display.column title="Cont. TI">
			<#if cliente.contatoTI?exists && cliente.contatoTI?trim != "">
				<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${cliente.contatoTI?j_string}');return false">...</span>
			</#if>
		</@display.column>
		<@display.column title="Obs.">
			<#if cliente.observacao?exists && cliente.observacao?trim != "">
				<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${cliente.observacao?j_string}');return false">...</span>
			</#if>
		</@display.column>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
	</div>
</body>
</html>
