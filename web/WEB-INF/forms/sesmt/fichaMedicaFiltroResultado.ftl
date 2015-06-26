<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Resultado da ${tipoQuestionario.getDescricaoMaisc(questionario.tipo)}</title>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/questionario.css?version=${versao}"/>');
	</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AspectoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/PerguntaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

	<#include "../ftl/mascarasImports.ftl" />

	<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('fichaMedica'), new Array('periodoIni','periodoFim'))"/>

	<#if periodoIni?exists>
		<#assign periodoIniFormatado = periodoIni?date/>
	<#else>
		<#assign periodoIniFormatado = "  /  /    "/>
	</#if>
	<#if periodoFim?exists>
		<#assign periodoFimFormatado = periodoFim?date/>
	<#else>
		<#assign periodoFimFormatado = "  /  /    "/>
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

		<@ww.form name="form" action="imprimeResultadoFichaMedica.action" onsubmit="${validarCampos}" method="POST">
			
			<@ww.select label="Modelo de ficha médica" required="true" name="questionario.id" id="fichaMedica" list="fichaMedicas" listKey="questionario.id" listValue="questionario.titulo" headerKey="" headerValue="Selecione..." onchange="populaPesquisaAspecto(this.value);"/>
			<@ww.datepicker label="Período" name="periodoIni" id="periodoIni" cssClass="mascaraData validaDataIni" liClass="liLeft" after="a" value="${periodoIniFormatado}"/>
			<@ww.datepicker label="" name="periodoFim" id="periodoFim" cssClass="mascaraData validaDataFim" value="${periodoFimFormatado}"/>

			<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areaOrganizacionalsCheckList" filtro="true" selectAtivoInativo="true"/>
			<@frt.checkListBox label="Exibir apenas os Aspectos" name="aspectosCheck" id="aspectosCheck" list="aspectosCheckList" filtro="true" />
			<@frt.checkListBox label="Exibir apenas as Perguntas" name="perguntasCheck" id="perguntasCheck" list="perguntasCheckList" filtro="true" />

			<@ww.checkbox label="Exibir todas as respostas" id="exibirRespostas" name="exibirRespostas" labelPosition="left"/>
			<@ww.checkbox label="Exibir comentários" id="exibirComentarios" name="exibirComentarios" labelPosition="left"/>
			<@ww.checkbox label="Agrupar perguntas por aspecto" id="agruparPorAspectos" name="agruparPorAspectos" labelPosition="left"/>

			<@ww.hidden name="urlVoltar"/>
			<@ww.hidden name="turmaId"/>
			<@ww.hidden name="cursoId"/>
		</@ww.form>

		<div class="buttonGroup">
			<button class="btnRelatorio" onclick="${validarCampos};" accesskey="I"></button>
			
			<@authz.authorize ifAllGranted="ROLE_CAD_FICHAMEDICA">
				<button class="btnVoltar" onclick="window.location='${urlVoltar}'"></button>
			</@authz.authorize>
		</div>
</body>
</html>