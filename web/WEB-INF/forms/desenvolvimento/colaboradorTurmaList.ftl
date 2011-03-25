<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>
	<title>Colaboradores Inscritos no curso de ${turma.curso.nome}, Turma - ${turma.descricao}</title>
	
    <#include "../ftl/mascarasImports.ftl" />
    <#include "../ftl/showFilterImports.ftl" />
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	
	<#include "../util/topFiltro.ftl" />
        <@ww.form name="formBusca" id="formBusca" action="list.action" method="POST">
            <@ww.select label="Empresa" name="empresaId" id="empresaId" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="-1"/>
            <@ww.hidden name="turma.id"/>
            
            <input type="submit" value="" class="btnPesquisar" >
        </@ww.form>
    <#include "../util/bottomFiltro.ftl" />
    <br>
	
	<#if colaboradorTurmas.size()?exists>
		<b>${colaboradorTurmas.size()} Colaboradores Inscritos</b>
	</#if>

	<#if turma?exists && turma.id?exists>
		<@display.table name="colaboradorTurmas" id="colaboradorTurma" class="dados">
			<@display.column title="Ações" class="acao">

			<@authz.authorize ifAllGranted="ROLE_RESPONDER_AVALIACAO_POR_OUTRO_USUARIO">
				<#if turma.avaliacaoTurma.questionario.id?exists>
					<#if colaboradorTurma.respondeuAvaliacaoTurma == true>
						<a href="../../pesquisa/colaboradorResposta/prepareResponderQuestionarioPorOutroUsuario.action?questionario.id=${turma.avaliacaoTurma.questionario.id}&colaborador.id=${colaboradorTurma.colaborador.id}&turmaId=${turma.id}&voltarPara=../../desenvolvimento/colaboradorTurma/list.action?turma.id=${turma.id}"><img border="0" title="Revisar as respostas de avaliação da turma deste colaborador" src="<@ww.url value="/imgs/page_edit.gif"/>"></a>
					<#else>
						<a href="../../pesquisa/colaboradorResposta/prepareResponderQuestionarioPorOutroUsuario.action?questionario.id=${turma.avaliacaoTurma.questionario.id}&colaborador.id=${colaboradorTurma.colaborador.id}&turmaId=${turma.id}&voltarPara=../../desenvolvimento/colaboradorTurma/list.action?turma.id=${turma.id}"><img border="0" title="Responder a avaliação da turma por este colaborador" src="<@ww.url value="/imgs/page_new.gif"/>"></a>
					</#if>
				</#if>
			</@authz.authorize>			
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='../colaboradorTurma/delete.action?colaboradorTurma.id=${colaboradorTurma.id}&colaboradorTurma.colaborador.id=${colaboradorTurma.colaborador.id}&turma.id=${turma.id}&planoTreinamento=${planoTreinamento?string}&empresaId=' + jQuery('#empresaId').val()});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			</@display.column>

			<@display.column property="colaborador.nome" title="Nome" style="width: 300px;"/>
			<@display.column property="colaborador.matricula" title="Matrícula" style="width: 80px;"/>
			<@display.column property="colaborador.historicoColaborador.estabelecimento.nome" title="Estabelecimento" style="width: 100px;"/>
			<@display.column property="colaborador.empresa.nome" title="Empresa" style="width: 120px;"/>
			<@display.column property="colaborador.areaOrganizacional.descricao" title="Área" style="width: 200px;"/>

		</@display.table>
	</#if>

		<div class="buttonGroup">
			<#if turma?exists && turma.id?exists>
				<button class="btnIncluirColaboradores" onclick="window.location='../colaboradorTurma/prepareInsert.action?turma.id=${turma.id}&turma.curso.id=${turma.curso.id}&planoTreinamento=${planoTreinamento?string}'"></button>
				<button class="btnIncluirColaboradoresNota" onclick="window.location='../colaboradorTurma/prepareInsertNota.action?turma.id=${turma.id}&turma.curso.id=${turma.curso.id}&planoTreinamento=${planoTreinamento?string}'"></button>
			</#if>

			<#if planoTreinamento>
				<#assign urlVoltar="../turma/filtroPlanoTreinamento.action"/>
			<#else>
				<#assign urlVoltar="../turma/list.action?curso.id=${turma.curso.id}"/>
			</#if>
			<button class="btnVoltar" onclick="window.location='${urlVoltar}'"></button>
		</div>
</body>
</html>