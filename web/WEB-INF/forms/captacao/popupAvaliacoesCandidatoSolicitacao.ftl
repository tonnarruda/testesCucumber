<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />

<@display.table name="colaboradorQuestionarios" id="colaboradorQuestionario" class="dados" >
	<@display.column title="Ações" style="width:50px;text-align:center;">
		<#if colaboradorQuestionario.id?exists>
			<a href="javascript: executeLink('prepareUpdateAvaliacaoSolicitacao.action?solicitacao.id=${solicitacao.id}&colaboradorQuestionario.id=${colaboradorQuestionario.id}&candidato.id=${colaboradorQuestionario.candidato.id}');"><img border="0" title="Editar Respostas" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){executeLink('deleteAvaliacaoSolicitacao.action?solicitacao.id=${solicitacao.id}&colaboradorQuestionario.id=${colaboradorQuestionario.id}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		<#else>
			<a href="javascript: executeLink('prepareInsertAvaliacaoSolicitacao.action?solicitacao.id=${solicitacao.id}&colaboradorQuestionario.avaliacao.id=${colaboradorQuestionario.avaliacao.id}&candidato.id=${colaboradorQuestionario.candidato.id}');"><img border="0" title="Responder Avaliação" src="<@ww.url value="/imgs/page_new.gif"/>"></a>
		</#if>
	</@display.column>
	
	<@display.column property="avaliacao.titulo" title="Avaliação" />
</@display.table>

<div style="text-align:right;">
	<button onclick="$('#popup').dialog('close');" class="btnFechar"></button>
</div>