<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<title>Resultado das Avaliações de Desempenho e Acompanhamento do Período de Experiência</title>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/questionario.css"/>');
	</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AspectoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/PerguntaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<#include "../ftl/mascarasImports.ftl" />

	<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('avaliacaoExperiencia'), new Array('periodoIni','periodoFim'))"/>

	<#if periodoIni?exists>
		<#assign periodoIniFormatado = periodoIni?date/>
	<#else>
		<#assign periodoIniFormatado = ""/>
	</#if>
	<#if periodoFim?exists>
		<#assign periodoFimFormatado = periodoFim?date/>
	<#else>
		<#assign periodoFimFormatado = ""/>
	</#if>
	
	<script type='text/javascript'>
		function populaPesquisaAspecto(questionarioId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			PerguntaDWR.getPerguntas(createListPerguntas, questionarioId);
			AspectoDWR.getAspectosId(createListAspectos, questionarioId);
		}
		
		function createListPerguntas(data)
		{
			addChecks('perguntasCheck',data)
		}

		function createListAspectos(data)
		{
			addChecks('aspectosCheck',data)
		}
	</script>
	
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

		<@ww.form name="form" action="imprimeResultado.action" onsubmit="${validarCampos}" method="POST">
			<@ww.select label="Modelo de Avaliação" required="true" name="avaliacaoExperiencia.id" id="avaliacaoExperiencia" list="avaliacaoExperiencias" listKey="id" listValue="titulo" headerKey="" headerValue="Selecione..." onchange="populaPesquisaAspecto(this.value);"/>
			<@ww.datepicker label="Período" name="periodoIni" id="periodoIni" cssClass="mascaraData validaDataIni" liClass="liLeft" after="a" value="${periodoIniFormatado}"/>
			<@ww.datepicker label="" name="periodoFim" id="periodoFim" cssClass="mascaraData validaDataFim" value="${periodoFimFormatado}"/>

			<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areasCheckList"/>
			<@frt.checkListBox label="Exibir apenas os Aspectos" name="aspectosCheck" id="aspectosCheck" list="aspectosCheckList" />
			<@frt.checkListBox label="Exibir apenas as Perguntas" name="perguntasCheck" id="perguntasCheck" list="perguntasCheckList"/>

			<@ww.checkbox label="Exibir observação" id="exibirCabecalho" name="exibirCabecalho" labelPosition="left"/>
			<@ww.checkbox label="Exibir todas as respostas" id="exibirRespostas" name="exibirRespostas" labelPosition="left"/>
			<@ww.checkbox label="Exibir comentários" id="exibirComentarios" name="exibirComentarios" labelPosition="left"/>
			<@ww.checkbox label="Agrupar perguntas por aspecto" id="agruparPorAspectos" name="agruparPorAspectos" labelPosition="left"/>

			<@ww.hidden name="urlVoltar"/>
			<@ww.hidden name="turmaId"/>
			<@ww.hidden name="cursoId"/>
		</@ww.form>

		<div class="buttonGroup">
			<button class="btnRelatorio" onclick="${validarCampos};" accesskey="I"></button>
		</div>
</body>
</html>