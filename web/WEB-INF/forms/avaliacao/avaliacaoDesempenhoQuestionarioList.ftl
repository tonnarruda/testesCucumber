<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />

<html>
<head>
<@ww.head/>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<title>Responder Avaliações de Desempenho</title>
	
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#include "../ftl/showFilterImports.ftl" />
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AvaliacaoDesempenhoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<script type="text/javascript">
		var empresaIds = new Array();
		<#if empresas?exists>
			<#list empresas as empresa>
				empresaIds.push(${empresa.id});
			</#list>
		</#if>
	
		$(function() {
			<#if exibeResultadoAutoavaliacao && colaboradorQuestionario?exists && msgResultadoAvaliacao?exists>
				$("<div>${msgResultadoAvaliacao}</div>").dialog({ title: '${colaboradorQuestionario.avaliacao.titulo}', width: 400 });
			</#if>
		});
		
		function populaAvaliador(avaliacaoDesempenhoId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			ColaboradorDWR.getAvaliadores(createListAvaliadores, avaliacaoDesempenhoId);
		}

		function createListAvaliadores(data)
		{
			DWRUtil.removeAllOptions("avaliador");
			DWRUtil.addOptions("avaliador", data);
		}

		function populaAvaliacaoDesempenho()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			AvaliacaoDesempenhoDWR.getAvaliacoesByEmpresaPermitidas(createListAvaliacaoDesmpenho, false, ($('#empresaId').val() == ""  ? empresaIds : [$('#empresaId').val()]) );
		}

		function createListAvaliacaoDesmpenho(data)
		{
			DWRUtil.removeAllOptions("avaliacao");
			DWRUtil.addOptions("avaliacao", data);
		}
	</script>

	<#assign validarCampos="return validaFormulario('form', new Array('avaliacao'), null)"/>
	<#assign funcaoAvaliacao=""/>

	<@authz.authorize ifAllGranted="ROLE_RESPONDER_AVALIACAO_DESEMP_POR_OUTRO_USUARIO">
		<#assign validarCampos="return validaFormulario('form', new Array('avaliacao', 'avaliador'), null)"/>
		<#assign funcaoAvaliacao="populaAvaliador(this.value);"/>
	</@authz.authorize>
		
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#assign desabilita="true"/>
	<#if empresas?exists && 1 < empresas?size>
		<#assign desabilita="false"/>
	</#if>
	
	<#include "../util/topFiltro.ftl" />
	
	<@ww.form name="form" action="avaliacaoDesempenhoQuestionarioList.action" onsubmit="${validarCampos}" method="POST">

		<@ww.select label="Empresa" name="empresaId" id="empresaId" listKey="id" listValue="nome" list="empresas" headerKey="" headerValue="Todas" cssClass="selectEmpresa" onchange="populaAvaliacaoDesempenho();" disabled="${desabilita}"/>
		<@ww.select label="Avaliação de Desempenho" required="true" name="avaliacaoDesempenho.id" id="avaliacao" list="avaliacaoDesempenhos" listKey="id" listValue="titulo" cssStyle="width: 500px;" headerKey="" headerValue="Selecione..." onchange="${funcaoAvaliacao}"/>
		<@authz.authorize ifAllGranted="ROLE_RESPONDER_AVALIACAO_DESEMP_POR_OUTRO_USUARIO">
			<@ww.select label="Avaliador" required="true" name="avaliador.id" id="avaliador" list="avaliadors" listKey="id" listValue="nome" cssStyle="width: 245px;" headerKey="" headerValue="Selecione..."/>
		</@authz.authorize>
		
		<@ww.select label="Situação" name="respondida" list=r"#{'0':'Todas','1':'Respondidas','2':'Não respondidas'}"/>
		
		<input type="submit" value="" class="btnPesquisar grayBGE" onclick="${validarCampos};">
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>

	<@display.table name="colaboradorQuestionarios" id="colaboradorQuestionario" class="dados">
		<@display.column title="Ações" class="acao">
			<#if colaboradorQuestionario.respondida>
				<a href="prepareResponderAvaliacaoDesempenho.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}"><img border="0" title="Editar respostas" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<a href="imprimirAvaliacaoDesempenhoRespondida.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}"><img border="0" title="Imprimir respostas" src="<@ww.url value="/imgs/printer.gif"/>"></a>
				<@frt.link verifyRole="ROLE_AVAL_DESEMP_DELETE_RESPOSTA" href="#" onclick="newConfirm('Confirma exclusão das respostas?', function(){window.location='deleteAvaliacao.action?colaboradorQuestionarioId=${colaboradorQuestionario.id}&avaliacaoDesempenho.id=${avaliacaoDesempenho.id}&respondida=${respondida}'});" imgTitle="Excluir respostas" imgName="deletar_avaliacao.gif"/>
			<#else>
				<a href="prepareResponderAvaliacaoDesempenho.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}"><img border="0" title="Responder" src="<@ww.url value="/imgs/folhaCheia.gif"/>"></a>
				<a href="imprimirQuestionario.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}"><img border="0" title="Imprimir questionário" src="<@ww.url value="/imgs/printer.gif"/>"></a>
				<!-- img border="0" title="Não há respostas a serem impressas" src="<@ww.url value="/imgs/printer.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);"/ -->
				<@frt.link verifyRole="ROLE_AVAL_DESEMP_DELETE_RESPOSTA" imgTitle="Não há respostas a serem excluídas" imgName="deletar_avaliacao.gif" opacity=true/>
			</#if>
		</@display.column>
		<@display.column property="colaborador.nome" title="Avaliado"/>
		<@display.column property="avaliacaoDesempenho.titulo" title="Título"/>
		<@display.column property="performanceFormatada" title="Performance Questionário" style="width:90px; text-align:right;"/>
		<@display.column property="performanceNivelCompetenciaFormatada" title="Performance Competência" style="width:90px; text-align:right;"/>
		<@display.column property="performanceFinal" title="Performance" style="width:90px; text-align:right;"/>
	</@display.table>
</body>
</html>