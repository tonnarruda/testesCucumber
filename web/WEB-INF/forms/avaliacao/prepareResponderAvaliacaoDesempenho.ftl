<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Responder Avaliação de Desempenho</title>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/perguntasAvaliacao.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/nivelCompetencia.js?version=${versao}"/>"></script>

	<script type="text/javascript">
		$(function() {
			<#if !colaboradorQuestionario.avaliacao.id?exists || colaboradorQuestionario.avaliacao.avaliarCompetenciasCargo>
				$('.pergunta').change(function(){
					calcularPerformance();
				});
				
				$('.checkNivel').change(function(){
					calcularPerformance();
				});
				
				$('.checkCompetencia.changed').change(function() {
					$(this).parent().parent().find(".checkNivel").attr('disabled', !($(this).attr('checked')));
					calcularPerformance();
				});
				
				$('.checkCompetenciaCriterio').change(function() {
					$(this).parent().parent().find(".checkNivelCriterio").attr('disabled', !($(this).attr('checked')));
					calculaNivelDaCompetenciaPeloPercentual( $(this).parent().parent().find(".checkNivelCriterio:eq(0)").attr('competencia') );
					calcularPerformance();
				});
				
				$('.checkNivelCriterio').change(function(){
					calculaNivelDaCompetenciaPeloPercentual($(this).attr("competencia"));
				});
				
				$('#checkAllCompetencia').click(function() {
					$('.checkNivel,.checkNivelCriterio').attr('disabled', !($(this).attr('checked')));
					$('.checkCompetencia.changed,.checkCompetenciaCriterio').attr('checked', $(this).attr('checked')).change();
					calcularPerformance();
				});
			<#else>
				$('.pergunta').change(function(){calcularPerformance();});
				$('.checkNivel').change(function(){calcularPerformance();});
				$('.checkCompetencia.changed').change(function() {calcularPerformance();});
				$('.checkCompetenciaCriterio').change(function() {calcularPerformance();});
				$('#checkAllCompetencia').click(function() {calcularPerformance();});
			</#if>
			
			calcularPerformance();
		});
		
		function calculaNivelDaCompetenciaPeloPercentual(competenciaId){
			var niveisSelecionadosDosCriterios = $("input[competencia="+competenciaId+"]:checked").not(":disabled");
			var somaPescetualDosCriterios = 0;
			$(niveisSelecionadosDosCriterios).each(function(){
				somaPescetualDosCriterios += parseFloat($(this).attr("percentual"));
			});
			var media = somaPescetualDosCriterios/niveisSelecionadosDosCriterios.length;
			var niveis = $("#competencia_"+competenciaId).parent().parent().find(".checkNivel");
			
			var percentualFinal = 0;
			$(niveis).filter(function() {
			  return parseFloat($(this).attr("percentual")) <= media;
			}).each(function() {
			  var value = $(this).attr("percentual");
			  percentualFinal = ( parseFloat(value) > parseFloat(percentualFinal) ) ? value : percentualFinal;
			});
			
			if(niveisSelecionadosDosCriterios.length > 0) {
				$("#competencia_"+competenciaId).attr("checked", "checked");
				$("#competencia_"+competenciaId).removeAttr("disabled");
				$("#competencia_"+competenciaId).parent().parent().find(".checkNivel").removeAttr("disabled");
			} else {
				$("#competencia_"+competenciaId).removeAttr("checked");
				$("#competencia_"+competenciaId).attr("disabled", "disabled");
				$("#competencia_"+competenciaId).parent().parent().find(".checkNivel").attr("disabled", "disabled");
			}
			
			if(percentualFinal == 0)
				$("#competencia_"+competenciaId).parent().parent().find(".checkNivel[percentual=0.0]").attr("checked","checked").change();
			else
				$("#competencia_"+competenciaId).parent().parent().find(".checkNivel[percentual="+percentualFinal+"]").attr("checked","checked").change();	
		};
		
		function enviarForm(validarRespostas)
		{
			$('tr').has('.checkCompetencia:disabled').has('.checkCompetencia:checked').find('.checkCompetencia').removeAttr("disabled");
			var linhasSemRadioMarcado = $('tr').has('.checkNivel:enabled').not(':has(.checkNivel:checked)');
			linhasSemRadioMarcado = $.merge(linhasSemRadioMarcado, $('tr').has('.checkNivelCriterio:enabled').not(':has(.checkNivelCriterio:checked)'));

			if (linhasSemRadioMarcado.size() == 0){
				
				$('#respondidaParcialmente').val(!validarRespostas);
				
				if(validarRespostas)
					validaRespostas(null, null, true, true, false, false, true);
				else
					document.form.submit();
				
				return true;
			}
				
			$('tr.even').css('background-color', '#EFEFEF');
			$('tr.odd').css('background-color', '#FFF');
		
			jAlert('Selecione os níveis para as competências ou comportamentos indicados.');
			linhasSemRadioMarcado.css('background-color', '#FFEEC2');

			return false;
		}
	</script>
	
	<#if configuracaoNivelCompetenciaColaborador?exists && configuracaoNivelCompetenciaColaborador.data?exists>
		<#assign data=configuracaoNivelCompetenciaColaborador.data?date />
	<#else>
		<#assign data="" />
	</#if>
	
	<#assign respostasCompactas=colaboradorQuestionario.avaliacao.respostasCompactas />
	<#assign exibirPerformance=(mostrarPerformanceAvalDesempenho && !(avaliador.id == colaborador.id && !colaboradorQuestionario.avaliacaoDesempenho.exibeResultadoAutoAvaliacao)) />
	
	<#if !colaboradorQuestionario.avaliacao.id?exists || colaboradorQuestionario.avaliacao.avaliarCompetenciasCargo>
		<#include "includeCompetenciasAvaliacaoHead.ftl" />
	</#if>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<b>${colaboradorQuestionario.avaliacaoDesempenho.titulo}</b><br/>
	<b>Avaliador: ${avaliador.nome}</b><br/>
	<b>Avaliado: ${colaborador.nome}</b>
	<#if colaboradorQuestionario.avaliacao?exists && colaboradorQuestionario.avaliacao.cabecalho?exists>
		<pre><h4>${colaboradorQuestionario.avaliacao.cabecalho}</h4></pre>
	</#if>
	<#if exibirPerformance && colaboradorQuestionario.avaliacao.id?exists >
		<pre id="performanceQuestionario" style="text-align:right; font-weight: bold;">Performance Questionário: - </pre>
	</#if>
	
		<@ww.form name="form" action="responderAvaliacaoDesempenho.action" method="POST">
			<#if (perguntas?exists && perguntas?size > 0 ) >
				<#include "includePerguntasAvaliacao.ftl" />
			<#else>
				<@ww.hidden name="colaboradorQuestionario.colaborador.id" />
				<@ww.hidden name="colaboradorQuestionario.id" />
			</#if>
			<@ww.hidden name="avaliacaoDesempenho.id" />
			<@ww.hidden name="colaboradorQuestionario.avaliacaoDesempenho.id" />
			<@ww.hidden name="colaboradorQuestionario.avaliador.id" />
			<@ww.hidden name="colaboradorQuestionario.avaliacaoDesempenho.permiteAutoAvaliacao"/>
			<@ww.hidden name="colaboradorQuestionario.avaliacaoDesempenho.exibeResultadoAutoAvaliacao"/>
			<@ww.hidden name="colaboradorQuestionario.avaliacao.avaliarCompetenciasCargo"/>
			<@ww.hidden name="colaboradorQuestionario.configuracaoNivelCompetenciaColaborador.id"/>
			<@ww.hidden name="colaboradorQuestionario.pesoAvaliador"/>
			<@ww.hidden name="colaboradorQuestionario.respondidaEm"/>
			<@ww.hidden name="colaboradorQuestionario.respondidaParcialmente" id="respondidaParcialmente" />
			<@ww.hidden name="autoAvaliacao" />

			<#if !colaboradorQuestionario.avaliacao.id?exists || colaboradorQuestionario.avaliacao.avaliarCompetenciasCargo>
				<#include "includeCompetenciasAvaliacaoBody.ftl" />
			</#if>
			
			<@ww.token/>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="enviarForm(true);" class="btnGravar"></button>
			<@authz.authorize ifAllGranted="ROLE_MOV_AVALIACAO_GRAVAR_PARCIALMENTE">
				<#if !colaboradorQuestionario.respondida>
					<button onclick="enviarForm(false);" class="btnGravarParcialmente"></button>
				</#if>
			</@authz.authorize>
			
			<#if autoAvaliacao>
				<button class="btnCancelar" onclick="window.location='../modelo/minhasAvaliacoesList.action'"></button>
			<#else>
				<button class="btnCancelar" onclick="window.location='avaliacaoDesempenhoQuestionarioList.action?avaliacaoDesempenho.id=${colaboradorQuestionario.avaliacaoDesempenho.id}'"></button>		
			</#if>
		</div>
</body>
</html>