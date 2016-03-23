<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />

<html>
<head>
<@ww.head/>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<title>Respostas da Avaliação de Desempenho (${avaliacaoDesempenho.titulo})</title>
	
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign showFilter = true/>
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
			<#if exibeResultadoAutoAvaliacao && colaboradorQuestionario?exists && msgResultadoAvaliacao?exists>
				$("<div>${msgResultadoAvaliacao}</div>").dialog({ title: '${colaboradorQuestionario.avaliacaoDesempenho.titulo}', width: 400 });
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
	</script>

	<#assign validarCampos="return validaFormulario('form', new Array('avaliacao'), null)"/>
	<#assign funcaoAvaliacao=""/>

	<@authz.authorize ifAllGranted="ROLE_RESPONDER_AVALIACAO_DESEMP_POR_OUTRO_USUARIO">
		<#assign validarCampos="return validaFormulario('form', new Array('avaliador'), null)"/>
		<#assign funcaoAvaliacao="populaAvaliador(this.value);"/>
	</@authz.authorize>
		
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />
	
	<@ww.form name="form" action="avaliacaoDesempenhoRespostasList.action?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}" onsubmit="${validarCampos}" method="POST">

		<@authz.authorize ifAllGranted="ROLE_RESPONDER_AVALIACAO_DESEMP_POR_OUTRO_USUARIO">
			<@ww.select label="Avaliador" required="true" name="avaliador.id" id="avaliador" list="avaliadors" listKey="id" listValue="nome" cssStyle="width: 245px;" headerKey="" headerValue="Selecione..."/>
		</@authz.authorize>
		
		<input type="submit" value="" class="btnPesquisar grayBGE" onclick="${validarCampos};">
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>

	<div id="legendas" align="right">
	<p>
		<span style="background-color: #009900;">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Respondida&nbsp;&nbsp;
		<span style="background-color: #002EB8;">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Respondida Parcialmente&nbsp;&nbsp;
	</P>
	</div>

	<@display.table name="colaboradorQuestionarios" id="colaboradorQuestionario" class="dados">
		<@display.column title="Ações" class="acao">
			<#if colaboradorQuestionario.respondida || colaboradorQuestionario.respondidaParcialmente>
				<a href="imprimirAvaliacaoDesempenhoRespondida.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}"><img border="0" title="Imprimir respostas" src="<@ww.url value="/imgs/printer.gif"/>"></a>
			<#else>
				<a href="imprimirQuestionario.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}"><img border="0" title="Imprimir questionário" src="<@ww.url value="/imgs/printer.gif"/>"></a>
			</#if>
		</@display.column>
		
		<#assign style=""/>
		<#if colaboradorQuestionario?exists && colaboradorQuestionario.respondidaParcialmente>
			<#assign style="color:#002EB8;"/>
		<#else>
			<#if colaboradorQuestionario?exists && colaboradorQuestionario.respondida>
				<#assign style="color:#009900;"/>
			</#if>
		</#if>
		
		<@display.column property="colaborador.nome" title="Avaliado" style="${style}"/>
		<@display.column property="avaliacaoDesempenho.titulo" title="Título" style="${style}"/>
		<@display.column property="performanceFormatada" title="Performance Questionário" style="${style} width:90px; text-align:right;"/>
		<@display.column property="performanceNivelCompetenciaFormatada" title="Performance Competência" style="${style} width:90px; text-align:right;"/>
		<@display.column property="performanceFinal" title="Performance" style="${style} width:90px; text-align:right;"/>
	</@display.table>
</body>
</html>