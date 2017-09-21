<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />

<html>
<head>
	<@ww.head/>
	
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytagModuloExterno.css"/>');
		a { color: blue; }
	</style>
	
	<#assign insert="prepareInsertDocumentoAnexo.action?documentoAnexo.origem=${documentoAnexo.origem}&documentoAnexo.origemId=${documentoAnexo.origemId}"/>
</head>
<body>

<@ww.actionerror/>
<@ww.actionmessage/>

<table width="100%">
	<tr>
		<td colspan="3">
			<strong>Documentos Anexos:</strong> 
		</td>
	</tr>
	
	<#if (documentoAnexos?exists && documentoAnexos?size > 0)>
		<tr>
			<td colspan="3" width="400px">
				<@display.table name="documentoAnexos" id="documentoAnexo" pagesize=10 class="dados" defaultsort=2 sort="list">
					<@display.column title="Ações" class="acao" style="width:30px">
						<a href="showDocumentoAnexo.action?documentoAnexo.id=${documentoAnexo.id}&documentoAnexo.origem=${documentoAnexo.origem}" title="Visualizar documento" target="_blank"><img border="0" title="Visualizar documento" src="<@ww.url value="/imgs/olho.jpg"/>"></a>
						<a href="prepareUpdateDocumentoAnexo.action?documentoAnexo.id=${documentoAnexo.id}&documentoAnexo.origem=${documentoAnexo.origem}&documentoAnexo.origemId=${documentoAnexo.origemId}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
						<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='deleteDocumentoAnexo.action?documentoAnexo.id=${documentoAnexo.id}&documentoAnexo.origem=${documentoAnexo.origem}&documentoAnexo.origemId=${documentoAnexo.origemId}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
					</@display.column>
					<@display.column property="descricao" style="width: 270px" title="Descrição"/>
				</@display.table>
			</td>
		</tr>
	<#else>
		<tr><td colspan="3"><div align='center'><br><br><b>Você ainda não enviou nenhum documento.</b></div></td></tr>
	</#if>
</table>

<div class="buttonGroup">
	<button onclick="window.location='${insert}'" class="btnInserir"></button>
	<button class="btnCancelar" onclick="window.location='${voltar}'"></button>
</button>

</body>
</html>