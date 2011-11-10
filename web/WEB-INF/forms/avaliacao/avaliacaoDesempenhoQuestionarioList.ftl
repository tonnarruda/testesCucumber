<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />

<html>
<head>
<@ww.head/>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>');
	</style>

	<title>Responder Avaliações de Desempenho</title>
	
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#include "../ftl/showFilterImports.ftl" />
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AvaliacaoDesempenhoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<script type="text/javascript">
	
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
			AvaliacaoDesempenhoDWR.getAvaliacoesByEmpresaPermitidas(createListAvaliacaoDesmpenho, $('#empresaId').val(), false);
		}

		function createListAvaliacaoDesmpenho(data)
		{
			DWRUtil.removeAllOptions("avaliacao");
			DWRUtil.addOptions("avaliacao", data);
		}
	</script>

	<#assign validarCampos="return validaFormulario('form', new Array('avaliacao'), null)"/>
	<#assign funcaoAvaliacao=""/>

	<@authz.authorize ifAllGranted="ROLE_RESPONDER_AVALIACAO_POR_OUTRO_USUARIO">
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

		<@ww.select label="Empresa" name="empresaId" id="empresaId" listKey="id" listValue="nome" list="empresas" headerKey="-1" headerValue="Todas" cssClass="selectEmpresa" onchange="populaAvaliacaoDesempenho();" disabled="${desabilita}"/>
		<@ww.select label="Avaliação de Desempenho" name="avaliacaoDesempenho.id" id="avaliacao" list="avaliacaoDesempenhos" listKey="id" listValue="titulo" cssStyle="width: 500px;" headerKey="" headerValue="Selecione..." onchange="${funcaoAvaliacao}"/>
		<@authz.authorize ifAllGranted="ROLE_RESPONDER_AVALIACAO_POR_OUTRO_USUARIO">
			<@ww.select label="Avaliador" name="avaliador.id" id="avaliador" list="avaliadors" listKey="id" listValue="nome" cssStyle="width: 245px;" headerKey="" headerValue="Selecione..."/>
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
			<#else>
				<a href="prepareResponderAvaliacaoDesempenho.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}"><img border="0" title="Responder" src="<@ww.url value="/imgs/folhaCheia.gif"/>"></a>
			</#if>
		</@display.column>
		<@display.column property="colaborador.nome" title="Avaliado"/>
		<@display.column property="performanceFormatada" title="Performance"/>
	</@display.table>
</body>
</html>