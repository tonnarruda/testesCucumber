<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Gerar Resultado da ${tipoQuestionario.getDescricaoMaisc(questionario.tipo)}</title>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/questionario.css"/>');
	</style>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AspectoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/PerguntaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/populaEstabAreaCargo.js"/>"></script>

	<script type='text/javascript'>
		var empresaIds = new Array();
		<#if empresaIds?exists>
			<#list empresaIds as empresaId>
				empresaIds.push(${empresaId});
			</#list>
		</#if>
		
		
		$(function() {
			DWREngine.setAsync(true);
		
			var empresa = $('#empresa').val();
	
			populaCargosByArea(empresa);
			verificaCargoSemAreaRelacionada(empresa);
			
			$('#cargoSemArea').click(function() {
				if($(this).is(":checked"))
					addCheckCargoSemArea();
				else
					populaCargosByArea();
			});
		});
	
		function populaPerguntasPorAspecto(questionarioId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var apectosIds = getArrayCheckeds(document.forms[0],'aspectosCheck');
			PerguntaDWR.getPerguntasByAspecto(createListPerguntas, questionarioId, apectosIds);
		}
	
		function populaPesquisaAspecto(questionarioId)
		{
			DWREngine.setAsync(true);
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

	<#include "../ftl/mascarasImports.ftl" />

	<#if questionario.tipo == tipoQuestionario.getENTREVISTA()>
		<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('entrevista'), new Array('periodoIni','periodoFim'))"/>
	<#else>
		<#assign validarCampos="return validaFormulario('form', null, null)"/>
	</#if>

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
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<#if questionario?exists && questionario.dataInicio?exists && questionario.dataFim?exists>
			<@ww.div cssClass="divInformacao">
				<span>${tipoQuestionario.getDescricaoMaisc(questionario.tipo)}: <b>${questionario.titulo}</b></span>
				<#if questionario.dataInicio?exists && questionario.dataFim?exists>
					<span>Período: <b>${questionario.dataInicio} a ${questionario.dataFim}</b></span>
				</#if>
			</@ww.div>
		<br>
	</#if>

		<@ww.form name="form" action="imprimeResultado.action" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden id="empresa" name="empresaSistema.id"/>
		
			<#if questionario.tipo == tipoQuestionario.getENTREVISTA()>
				<@ww.select label="Modelo de Entrevista" required="true" name="questionario.id" id="entrevista" list="entrevistas" listKey="questionario.id" listValue="questionario.titulo" headerKey="" headerValue="Selecione..." onchange="populaPesquisaAspecto(this.value);"/>
				<@ww.datepicker label="Período" name="periodoIni" id="periodoIni" cssClass="mascaraData validaDataIni" liClass="liLeft" after="a" value="${periodoIniFormatado}"/>
				<@ww.datepicker label="" name="periodoFim" id="periodoFim" cssClass="mascaraData validaDataFim" value="${periodoFimFormatado}"/>
			<#else>
				<@ww.hidden name="questionario.id"/>
			</#if>

			<#if questionario.tipo != tipoQuestionario.getENTREVISTA()>
				<#assign populaPerguntasPorAspecto = "populaPerguntasPorAspecto(${questionario.id?string});" />
				
				<#assign descricaoAreas = "Empresa - Áreas Organizacionais" />
			<#else>
				<#assign populaPerguntasPorAspecto = "" />
				
				<#assign descricaoAreas = "Áreas Organizacionais" />
			</#if>

			<#if questionario.tipo != tipoQuestionario.getENTREVISTA()>
				<@frt.checkListBox label="Empresa - Estabelecimento" name="estabelecimentosCheck" id="estabelecimentosCheck" list="estabelecimentosCheckList"/>
			</#if>

			<@frt.checkListBox label="${descricaoAreas}" name="areasCheck" id="areasCheck" list="areaOrganizacionalsCheckList" onClick="populaCargosByArea();"/>
			<@ww.checkbox label="Considerar cargos não vinculados a nenhuma Área Organizacional" id="cargoSemArea" name="" labelPosition="left"/>
			<@frt.checkListBox label="Cargos" id="cargosCheck" name="cargosCheck" list="cargosCheckList"/>
			
			<@frt.checkListBox label="Exibir apenas os Aspectos" name="aspectosCheck" id="aspectosCheck" list="aspectosCheckList" onClick="${populaPerguntasPorAspecto}"/>
			<@frt.checkListBox label="Exibir apenas as Perguntas" name="perguntasCheck" id="perguntasCheck" list="perguntasCheckList"/>

			<#if questionario.tipo == tipoQuestionario.getPESQUISA()>
				<@ww.checkbox label="Exibir observação" id="exibirCabecalho" name="exibirCabecalho" labelPosition="left"/>
			</#if>
			
			<@ww.checkbox label="Exibir todas as respostas" id="exibirRespostas" name="exibirRespostas" labelPosition="left"/>
			<@ww.checkbox label="Exibir comentários" id="exibirComentarios" name="exibirComentarios" labelPosition="left"/>
			<@ww.checkbox label="Agrupar perguntas por aspecto" id="agruparPorAspectos" name="agruparPorAspectos" labelPosition="left"/>

			<#if questionario.tipo == tipoQuestionario.getENTREVISTA() || questionario.tipo == tipoQuestionario.getAVALIACAOTURMA()>
				<@ww.checkbox label="Exibir nomes dos colaboradores nas respostas e comentários" id="exibirNomesColaboradores" name="exibirNomesColaboradores" labelPosition="left"/>
			</#if>
			
				<@ww.hidden name="urlVoltar"/>
				<@ww.hidden name="turmaId"/>
				<@ww.hidden name="cursoId"/>
		</@ww.form>

		<div class="buttonGroup">
			<button class="btnRelatorio" onclick="${validarCampos};" accesskey="I"></button>
			<#-- Monta o botão de acordo com o destino pesquisa, avaliação, entrevista-->
			<#if urlVoltar?exists && urlVoltar != "menu">
				<button class="btnVoltar" onclick="window.location='${urlVoltar}'"></button>
			</#if>
		</div>
</body>
</html>