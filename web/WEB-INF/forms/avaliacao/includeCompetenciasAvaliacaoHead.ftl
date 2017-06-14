<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	.dados th:first-child { text-align: left; padding-left: 5px; }
</style>

<script type="text/javascript">
	$(function() {
		<#if niveisCompetenciaFaixaSalariaisSalvos?exists>
			<#list niveisCompetenciaFaixaSalariaisSalvos as nivelSalvo>
				var linha = $('tr').has('.checkCompetencia[value="${nivelSalvo.competenciaId}"]').has('input[type="hidden"][value="${nivelSalvo.tipoCompetencia}"]');
				var nivelColaborador = linha.find('.checkNivel[value="${nivelSalvo.nivelCompetencia.id}"]');
				
				linha.find('.checkCompetencia').attr('checked', 'checked');
				linha.find('.checkNivel').removeAttr('disabled');
				linha.find('.ordem').val(${nivelSalvo.nivelCompetencia.ordem});
				
				nivelColaborador.parent().css('background-color','#E4F0FE');
				<#if colaboradorQuestionario.respondida || colaboradorQuestionario.respondidaParcialmente>
					nivelColaborador.attr('checked','checked');
				</#if>
				
				<#list niveisCompetenciaFaixaSalariais as nivelSugerido>
					if('${nivelSugerido.competenciaId}' == '${nivelSalvo.competenciaId}' && '${nivelSugerido.tipoCompetencia}' == '${nivelSalvo.tipoCompetencia}' && '${nivelSugerido.nivelCompetencia.id}' == '${nivelSalvo.nivelCompetencia.id}'){
						linha.find('.checkNivel[value="${nivelSalvo.nivelCompetencia.id}"]').parent().css('background-color','#A4E2DB');
					}
				</#list>
				
				<#if nivelSalvo.configuracaoNivelCompetenciaCriterios?exists>
					<#list nivelSalvo.configuracaoNivelCompetenciaCriterios as criterio >
						$(".checkCompetenciaCriterio[value=${criterio.criterioId}]").parent().parent().find(".ordem").val(${criterio.nivelCompetencia.ordem});
						$(".checkCompetenciaCriterio[value=${criterio.criterioId}]").parent().parent().find(".checkNivelCriterio[value='${criterio.nivelCompetencia.id}").attr("checked", "checked");
						$(".checkCompetenciaCriterio[value=${criterio.criterioId}]").parent().parent().find(".checkNivelCriterio").removeAttr("disabled");
						$(".checkCompetenciaCriterio[value=${criterio.criterioId}]").attr("checked", "checked");
						$(".checkCompetenciaCriterio[value=${criterio.criterioId}]").change(function() {calcularPerformance();});
					</#list>
				</#if>
			</#list>
		</#if>
		calcularPerformance();
	});
	
	//PELO AMOR DE DEUS SE BULIR NESTE MÉTODO VERIFICAR CÁCULO DO MÉTODO calculaPerformance DE ColaboradorRespostaManagerImpl 
	function calcularPerformance()
	{
		<#if exibirPerformance >
			var pontuacaoMaximaTotal = ${pontuacaoMaximaQuestionario};
			var notaCompetencias = 0; 
			var pontuacaoMaxCompetencia = 0;
			<#if !colaboradorQuestionario.avaliacao.id?exists || colaboradorQuestionario.avaliacao.avaliarCompetenciasCargo>
				var pontuacaoMaximaNivelCompetencia = ${pontuacaoMaximaNivelCompetencia};
			
				$('#configuracaoNivelCompetencia').find('.performance').text('-');
				
				$('#configuracaoNivelCompetencia').find('.checados:checkbox:checked').parent().parent().each(function(){
					pontuacaoMaximaTotal += $(this).find('#peso').val()*pontuacaoMaximaNivelCompetencia; 
					pontuacaoMaxCompetencia += $(this).find('#peso').val()*pontuacaoMaximaNivelCompetencia;
				});
				
				$('#configuracaoNivelCompetencia').find('.checados:checkbox:checked').parent().parent().each(function(){
					var niveisSelecionadosDosCriterios = $("input[competencia="+$(this).find('.checkCompetencia').attr("id").replace("competencia_","")+"]:checked").not(":disabled");
					var somaPescetualDosCriterios = 0;
					var somaOrdemDosCriterios = 0;
					var competencia = $(this);
					
					$(niveisSelecionadosDosCriterios).each(function(){
						somaPescetualDosCriterios += parseFloat($(this).attr("percentual"));
						somaOrdemDosCriterios += parseFloat($(competencia).find('.checkNivel[value='+$(this).val()+']').attr('ordem'));
					});
					
					var media = somaOrdemDosCriterios/niveisSelecionadosDosCriterios.length;
					var ordemValue = niveisSelecionadosDosCriterios.length > 0 ? media : $(this).find('.checkNivel:checked').attr('ordem');
					calculoPerformance = ($(this).find('#peso').val() * ordemValue ) / pontuacaoMaximaTotal;
					
					if(!isNaN(calculoPerformance)){
					 	notaCompetencias+=calculoPerformance;
					 	$(this).find('.performance').text((calculoPerformance * 100).toFixed(2) + "%");
					}
				});
				
			</#if>
			
			<#if colaboradorQuestionario.avaliacao.id?exists>
				var notaObtidaQuestionario = 0;
				 $('.perguntaResposta').each(function(){
				 	var peso = $(this).find("#pesoPergunta").val();
				 	var tipo = $(this).find("#tipo").val();
				 	if(tipo == 1)
				 		if($(this).find('.objetiva:checked').size() > 0)
				 			notaObtidaQuestionario += $(this).find('.objetiva:checked').attr('peso') * peso;
				 	
				 	if( tipo == '4')
			 			notaObtidaQuestionario += $(this).find('.nota').val()  * peso;

					if(tipo == 5){
						var pesoMultiplaEscolha = 0;
						$(this).find('.multiplaEscolha:checked').each(function(){
							pesoMultiplaEscolha += parseInt($(this).attr('peso'));
						});
						notaObtidaQuestionario += pesoMultiplaEscolha * peso;
					}
				 });
				
				var performanceQuestionario;
				if(pontuacaoMaximaTotal > 0)
					performanceQuestionario = notaObtidaQuestionario / pontuacaoMaximaTotal;
				else
					performanceQuestionario = 0;
		
				performanceMaxQuestionarioPermitido =  1 - (pontuacaoMaxCompetencia / pontuacaoMaximaTotal);
				if(performanceQuestionario > performanceMaxQuestionarioPermitido)
					performanceQuestionario = performanceMaxQuestionarioPermitido;
					
				if(performanceQuestionario < 0)
					performanceQuestionario = 0;
				
				$('#performanceQuestionario').text('Performance Questionário: ' + (performanceQuestionario * 100).toFixed(2) + "%" );
			</#if>

			<#if !colaboradorQuestionario.avaliacao.id?exists || colaboradorQuestionario.avaliacao.avaliarCompetenciasCargo>
				$('#performanceCompetencias').text('Performance Competencias: ' + (notaCompetencias* 100).toFixed(2) + "%" );
			</#if>
		</#if>
	}
		
	function setOrdem(i, ordem)
	{
		$('#ordem_' + i).val(ordem);
	}
	
	function setOrdemNivelCriterio(i, y, ordem)
	{
		$('#ordemNivel_criterio_' + i + '_' + y).val(ordem);
	}
</script>