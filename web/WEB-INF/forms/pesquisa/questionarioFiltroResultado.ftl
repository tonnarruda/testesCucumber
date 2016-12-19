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
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/populaEstabAreaCargo.js?version=${versao}"/>"></script>

	<script type='text/javascript'>
		var empresaIds = new Array();
		<#if empresaIds?exists>
			<#list empresaIds as empresaId>
				empresaIds.push(${empresaId});
			</#list>
		</#if>
		
		$(function() {
			DWREngine.setAsync(true);
			
			<#if empresas?exists && 1 < empresas?size >
				$('#empresa').val('');
			</#if>
			
			<#if questionario.tipo == tipoQuestionario.getPESQUISA() >
				populaCargosByAreaVinculados();
			<#else>
				populaCargosByArea();
			</#if>
			
			verificaCargoSemAreaRelacionada($('#empresa').val());
			
			$('#cargoSemArea').click(function() {
				<#if questionario.tipo == tipoQuestionario.getPESQUISA() >
					populaCargosByAreaVinculados();
				<#else>
					populaCargosByArea();
				</#if>
			});
		});
	
		function populaEstabelecimentos() {
			DWRUtil.useLoadingMessage('Carregando...');
			var empresaCheckIds = getArrayCheckeds(document.forms[0], 'empresasCheck');
			if(empresaCheckIds.length > 0)
				EstabelecimentoDWR.getByEmpresas(createListEstabelecimento, null, empresaCheckIds);
			else
				EstabelecimentoDWR.getByEmpresas(createListEstabelecimento, null, empresaIds);
		}
	
		function createListEstabelecimento(data)
		{
			addChecks('estabelecimentosCheck',data);
		}
	
		function populaAreas()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var empresaCheckIds = getArrayCheckeds(document.forms[0], 'empresasCheck');
			populaCargosByAreaVinculados();

			if(empresaCheckIds.length > 0)
				AreaOrganizacionalDWR.getByEmpresas(createListAreas, null, empresaCheckIds, null);
			else
				AreaOrganizacionalDWR.getByEmpresas(createListAreas, null, empresaIds, null);
				
		}
		
		function createListAreas(data)
		{
			<#if questionario.tipo == tipoQuestionario.getPESQUISA() >
				addChecks('areasCheck', data, 'populaCargosByAreaVinculados()');
			<#else>
				addChecks('areasCheck', data, 'populaCargosByArea()');
			</#if>
			
		}
		
		function populaCargosByAreaVinculados()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var areasIds = getArrayCheckeds(document.forms[0],'areasCheck');
			var empresasIds = getArrayCheckeds(document.forms[0],'empresasCheck');
			
			if ($('#cargoSemArea').is(":checked"))
			{
				if(areasIds.length == 0)
					CargoDWR.getByEmpresas(createListCargosByArea, 0, empresasIds);
				else {
					CargoDWR.getCargoByAreaMaisSemAreaRelacionada(createListCargosByArea, areasIds, "getNomeMercado", 0);
				}
			}
			else
				if (areasIds.length == 0)
					CargoDWR.getByEmpresas(createListCargosByArea, 0, empresasIds);
				else
					CargoDWR.getCargoByArea(createListCargosByArea, areasIds, "getNomeMercadoComEmpresa", 0);
				
		}
		
		function createListCargosByArea(data)
		{
			addChecks('cargosCheck',data);
		}
	
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
		
			<#if questionario.tipo == tipoQuestionario.getENTREVISTA()>
				<@ww.select label="Modelo de Entrevista" required="true" name="questionario.id" id="entrevista" list="entrevistas" listKey="questionario.id" listValue="questionario.titulo" headerKey="" headerValue="Selecione..." onchange="populaPesquisaAspecto(this.value);"/>
				<label>Período que ocorreu o desligamento:</label>
				<div style="margin-top: -14px;">
					<@ww.datepicker label="" name="periodoIni" id="periodoIni" cssClass="mascaraData validaDataIni" liClass="liLeft" after="a" value="${periodoIniFormatado}"/>
					<@ww.datepicker label="" name="periodoFim" id="periodoFim" cssClass="mascaraData validaDataFim" value="${periodoFimFormatado}"/>
				</div>
			<#else>
				<@ww.hidden name="questionario.id"/>
			</#if>
			
			<#if questionario.tipo == tipoQuestionario.getPESQUISA() || questionario.tipo == tipoQuestionario.getAVALIACAOTURMA()>
				<@frt.checkListBox label="Empresas" name="empresasCheck" id="empresasCheck" list="empresasCheckList" onClick="populaEstabelecimentos(); populaAreas();" filtro="true"/>
			</#if> 

			<#if questionario.tipo != tipoQuestionario.getENTREVISTA()>
				<#assign populaPerguntasPorAspecto = "populaPerguntasPorAspecto(${questionario.id?string});" />
				
				<#assign descricaoAreas = "Empresa - Áreas Organizacionais" />
			<#else>
				<#assign populaPerguntasPorAspecto = "" />
				
				<#assign descricaoAreas = "Áreas Organizacionais" />
			</#if>

			
			<@frt.checkListBox label="Empresa - Estabelecimento" name="estabelecimentosCheck" id="estabelecimentosCheck" list="estabelecimentosCheckList" filtro="true"/>
			

			<#if questionario.tipo == tipoQuestionario.getPESQUISA() >
				<@frt.checkListBox label="${descricaoAreas}" name="areasCheck" id="areasCheck" list="areaOrganizacionalsCheckList" onClick="populaCargosByAreaVinculados();" filtro="true" selectAtivoInativo="true"/>
			<#else>
				<@frt.checkListBox label="${descricaoAreas}" name="areasCheck" id="areasCheck" list="areaOrganizacionalsCheckList" onClick="populaCargosByArea();" filtro="true" selectAtivoInativo="true"/>
			</#if>

			<@ww.checkbox label="Considerar cargos não vinculados a nenhuma Área Organizacional" id="cargoSemArea" name="" labelPosition="left"/>
			
			<@frt.checkListBox label="Cargos" id="cargosCheck" name="cargosCheck" list="cargosCheckList" filtro="true" selectAtivoInativo="true"/>
			
			<@frt.checkListBox label="Exibir apenas os Aspectos" name="aspectosCheck" id="aspectosCheck" list="aspectosCheckList" onClick="${populaPerguntasPorAspecto}" filtro="true"/>
			<@frt.checkListBox label="Exibir apenas as Perguntas" name="perguntasCheck" id="perguntasCheck" list="perguntasCheckList" filtro="true"/>

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
				<@ww.hidden id="empresa" value="${empresaSistema.id}"/>
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