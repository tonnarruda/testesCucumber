<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Responder Avaliação de Desempenho</title>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/perguntasAvaliacao.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/nivelCompetencia.js?version=${versao}"/>"></script>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		.dados th:first-child { text-align: left; padding-left: 5px; }
	</style>
	
	<script type="text/javascript">
		$(function() {
			<#if colaboradorQuestionario.avaliacao.avaliarCompetenciasCargo>
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

				<#if niveisCompetenciaFaixaSalariaisSalvos?exists>
					<#list niveisCompetenciaFaixaSalariaisSalvos as nivelSalvo>
						var linha = $('tr').has('.checkCompetencia[value="${nivelSalvo.competenciaId}"]').has('input[type="hidden"][value="${nivelSalvo.tipoCompetencia}"]');
						var nivelColaborador = linha.find('.checkNivel[value="${nivelSalvo.nivelCompetencia.id}"]');
						
						linha.find('.checkCompetencia').attr('checked', 'checked');
						linha.find('.checkNivel').removeAttr('disabled');
						linha.find('.ordem').val(${nivelSalvo.nivelCompetencia.ordem});
						
						nivelColaborador.parent().css('background-color','#E4F0FE');
						<#if colaboradorQuestionario.respondida>
							nivelColaborador.attr('checked','checked');
						</#if>
						
						<#list niveisCompetenciaFaixaSalariais as nivelSugerido>
							if('${nivelSugerido.competenciaId}' == '${nivelSalvo.competenciaId}' && '${nivelSugerido.tipoCompetencia}' == '${nivelSalvo.tipoCompetencia}' && '${nivelSugerido.nivelCompetencia.id}' == '${nivelSalvo.nivelCompetencia.id}'){
								linha.find('.checkNivelCriterio[value="${nivelSalvo.nivelCompetencia.id}"]').parent().css('background-color','#A4E2DB');
							}
						</#list>
						
						<#if nivelSalvo.configuracaoNivelCompetenciaCriterios?exists>
							<#list nivelSalvo.configuracaoNivelCompetenciaCriterios as criterio >
								$(".checkCompetenciaCriterio[value=${criterio.criterioId}]").parent().parent().find(".checkNivelCriterio[value='${criterio.nivelCompetencia.id}").attr("checked", "checked");
								$(".checkCompetenciaCriterio[value=${criterio.criterioId}]").attr("checked", "checked").change();
							</#list>
						</#if>
					</#list>
				</#if>

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
			
			console.log(media);
			var percentualFinal = 0;
			$(niveis).filter(function() {
			  return parseFloat($(this).attr("percentual")) <= media;
			}).each(function() {
			  var value = $(this).attr("percentual");
			  percentualFinal = ( parseFloat(value) > parseFloat(percentualFinal) ) ? value : percentualFinal;
			});
			
			console.log(percentualFinal);
			
			if(niveisSelecionadosDosCriterios.length > 0) {
				$("#competencia_"+competenciaId).attr("checked", "checked");
				$("#competencia_"+competenciaId).removeAttr("disabled");
				$("#competencia_"+competenciaId).parent().parent().find(".checkNivel").removeAttr("disabled");
			} else {
				$("#competencia_"+competenciaId).removeAttr("checked");
				$("#competencia_"+competenciaId).attr("disabled", "disabled");
				$("#competencia_"+competenciaId).parent().parent().find(".checkNivel").attr("disabled", "disabled");
			}
			
			$("#competencia_"+competenciaId).parent().parent().find(".checkNivel[percentual="+percentualFinal+"]").attr("checked","checked").change();
		};
		
		function enviarForm()
		{
			var linhasSemRadioMarcado = $('tr').has('.checkNivel:enabled').not(':has(.checkNivel:checked)');
			linhasSemRadioMarcado = $.merge(linhasSemRadioMarcado, $('tr').has('.checkNivelCriterio:enabled').not(':has(.checkNivelCriterio:checked)'));

			if (linhasSemRadioMarcado.size() == 0)
			{
				validaRespostas(null, null, true, true, false, false, true);
				return true;
			}
				
			$('tr.even').css('background-color', '#EFEFEF');
			$('tr.odd').css('background-color', '#FFF');
		
			jAlert('Selecione os níveis para as competências ou critérios indicados.');
			linhasSemRadioMarcado.css('background-color', '#FFEEC2');

			return false;
		}
		
		function setOrdem(i, ordem)
		{
			$('#ordem_' + i).val(ordem);
		}
		
		function calcularPerformance()
		{
			<#if mostrarPerformanceAvalDesempenho>
				var pontuacaoMaximaTotal = ${pontuacaoMaximaQuestionario};
				var notaCompetencias = 0; 
				<#if colaboradorQuestionario.avaliacao.avaliarCompetenciasCargo>
					var pontuacaoMaximaNivelCompetencia = ${pontuacaoMaximaNivelCompetencia};
				
					$('#configuracaoNivelCompetencia').find('.performance').text('-');
					
					$('#configuracaoNivelCompetencia').find('.checados:checkbox:checked').parent().parent().each(function(){
						pontuacaoMaximaTotal += $(this).find('#peso').val()*pontuacaoMaximaNivelCompetencia; 
					});
					
					$('#configuracaoNivelCompetencia').find('.checados:checkbox:checked').parent().parent().each(function(){
						 calculoPerformance = ($(this).find('#peso').val() * $(this).find('.checkNivel:checked').attr('ordem')) / pontuacaoMaximaTotal;
						 if(!isNaN(calculoPerformance)){
						 	notaCompetencias+=calculoPerformance;
						 	$(this).find('.performance').text((calculoPerformance * 100).toFixed(2) + "%");
						 }
					});
					$('#performanceCompetencias').text('Performance Competencia: ' + (notaCompetencias* 100).toFixed(2) + "%" );
				</#if>
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
				$('#performanceQuestionario').text('Performance Questionário: ' + (performanceQuestionario * 100).toFixed(2) + "%" );
			</#if>
		}
	</script>
	
	<#if configuracaoNivelCompetenciaColaborador?exists && configuracaoNivelCompetenciaColaborador.data?exists>
		<#assign data=configuracaoNivelCompetenciaColaborador.data?date />
	<#else>
		<#assign data="" />
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
	<#if mostrarPerformanceAvalDesempenho>
		<pre id="performanceQuestionario" style="text-align:right; font-weight: bold;">Performance Questionário: - </pre>
	</#if>
	
	<#if perguntas?exists && 0 < perguntas?size>
		<@ww.form name="form" action="responderAvaliacaoDesempenho.action" method="POST">
			<#include "includePerguntasAvaliacao.ftl" />
			<@ww.hidden name="avaliacaoDesempenho.id" />
			<@ww.hidden name="colaboradorQuestionario.avaliacaoDesempenho.id" />
			<@ww.hidden name="colaboradorQuestionario.avaliador.id" />
			<@ww.hidden name="colaboradorQuestionario.avaliacaoDesempenho.permiteAutoAvaliacao"/>
			<@ww.hidden name="colaboradorQuestionario.avaliacao.exibeResultadoAutoavaliacao"/>
			<@ww.hidden name="colaboradorQuestionario.avaliacao.avaliarCompetenciasCargo"/>
			<@ww.hidden name="colaboradorQuestionario.configuracaoNivelCompetenciaColaborador.id"/>
			<@ww.hidden name="colaboradorQuestionario.respondidaEm"/>
			<@ww.hidden name="autoAvaliacao" />
			
			<#if colaboradorQuestionario.avaliacao.avaliarCompetenciasCargo>
				<br />
				
				<fieldset>
					<legend>Reavaliar as Competências do Colaborador para o Cargo</legend><br />
					
					<div id="legendas">
						<span style='background-color: #BFC0C3;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Nível de Competência exigido para o Cargo/Faixa Salarial
						<br /><br />
						<span style='background-color: #E4F0FE;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Nível de Competência do Colaborador
						<br /><br />
						<span style='background-color: #A4E2DB;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Nível de Competência do Colaborador é igual ao nível exigido para o Cargo/Faixa Salarial
					</div>
		
					<br /><br />
					<#if mostrarPerformanceAvalDesempenho>
						<pre id="performanceCompetencias" style="text-align:right; font-weight: bold;">Performance Questionário: - </pre>
					</#if>
					<table id="configuracaoNivelCompetencia" class="dados">
						<thead>
							<tr>
								<th><input type="checkbox" id="checkAllCompetencia"> Competência/Critério para avaliação</th>
								<#list nivelCompetencias as nivel>
									<th>${nivel.descricao}</th>
								</#list>
								
								<#if mostrarPerformanceAvalDesempenho>
									<th>Performance(%)</th>
								</#if>
							</tr>
						</thead>
						<tbody>
							<#assign i = 0/>
							<#list niveisCompetenciaFaixaSalariais as configuracaoNivelCompetencia>
								<#assign hasCriterios = (configuracaoNivelCompetencia.criteriosAvaliacaoCompetencia.size() >0) />
								<tr class="even">
									<td>
										<@ww.hidden name="niveisCompetenciaFaixaSalariais[${i}].tipoCompetencia"/>
										<@ww.hidden name="niveisCompetenciaFaixaSalariais[${i}].pesoCompetencia" id="peso"/>
										<#-- não utilizar decorator no hidden abaixo -->
										<input type="hidden" name="niveisCompetenciaFaixaSalariais[${i}].nivelCompetencia.ordem" id="ordem_${i}" class="ordem" value=""/>
										
										<input type="checkbox" id="competencia_${i}" name="niveisCompetenciaFaixaSalariais[${i}].competenciaId"
										<#if hasCriterios > onclick="return false;" disabled="disabled" class="checkCompetencia checados"<#else> class="checkCompetencia changed checados" </#if>
										value="${configuracaoNivelCompetencia.competenciaId}" />
										
										<label for="competencia_${i}">${configuracaoNivelCompetencia.competenciaDescricao}</label>
										
										<#if configuracaoNivelCompetencia.competenciaObservacao?exists && configuracaoNivelCompetencia.competenciaObservacao != "">
											<img id="competencia_${i}_obs" onLoad="toolTipCompetenciaObs(${i}, '${configuracaoNivelCompetencia.competenciaObservacao?j_string?replace('\"','$#-')?replace('\'','\\\'')}')" src="<@ww.url value='/imgs/help-info.gif'/>" width='16' height='16' style='margin-left: 0px;margin-top: 0px;vertical-align: top;'/>
										</#if>
																
									</td>
									
									<#list nivelCompetencias as nivel>
										<#if configuracaoNivelCompetencia?exists && configuracaoNivelCompetencia.nivelCompetencia?exists && configuracaoNivelCompetencia.nivelCompetencia.id?exists && configuracaoNivelCompetencia.nivelCompetencia.id == nivel.id>
											<#assign class="nivelFaixa"/>
											<#assign bgcolor="background-color: #BFC0C3;"/>
										<#else>
											<#assign class=""/>
											<#assign bgcolor=""/>
										</#if>
										
										<#if nivel.percentual?exists>
											<#assign nivelPercentual="${nivel.percentualString}"/>
										<#else>
											<#assign nivelPercentual=""/>
										</#if>
										
										<td style="${bgcolor} width: 100px; text-align: center;" class="${class}">
											<input type="radio" disabled="disabled" percentual="${nivelPercentual}" ordem="${nivel.ordem}"
											<#if hasCriterios > onclick="return false;"  class="checkNivel radio" <#else>  class="checkNivel changed radio" </#if>
											name="niveisCompetenciaFaixaSalariais[${i}].nivelCompetencia.id" value="${nivel.id}" onchange="setOrdem(${i}, ${nivel.ordem})"/>
										</td>
									</#list>
									
									<#if mostrarPerformanceAvalDesempenho>
										<td style="width: 100px; text-align: center;" class="${class}">
											<label class="performance">-</label>
										</td>
									</#if>
									
								</tr>
								
								
								<#assign y = 0/>
								<#list configuracaoNivelCompetencia.criteriosAvaliacaoCompetencia as criterio>
									<tr class="odd">
										<td style="padding: 5px 25px;">
											<input type="hidden" name="niveisCompetenciaFaixaSalariais[${i}].configuracaoNivelCompetenciaCriterios[${y}].criterioDescricao" value="${criterio.descricao}" />
											<input type="checkbox" id="competencia_${i}_criterio_${y}" name="niveisCompetenciaFaixaSalariais[${i}].configuracaoNivelCompetenciaCriterios[${y}].criterioId" value="${criterio.id}" class="checkCompetenciaCriterio" />
											<label for="competencia_${i}_criterio_${y}">${criterio.descricao}</label>
										</td>
										
										<#list nivelCompetencias as nivelCriterio>
											<td style="width: 100px; text-align: center;">
												<input type="radio" disabled="disabled" class="checkNivelCriterio radio" competencia="${i}" percentual="${nivelCriterio.percentualString}" name="niveisCompetenciaFaixaSalariais[${i}].configuracaoNivelCompetenciaCriterios[${y}].nivelCompetencia.id" value="${nivelCriterio.id}" />
											</td>
										</#list>
									</tr>
									
									<#assign y = y + 1/>
								</#list>
								
								<#assign i = i + 1/>
							</#list>
						</tbody>
					</table>
					
					<#if niveisCompetenciaFaixaSalariais?exists && niveisCompetenciaFaixaSalariais?size == 0>
						<ul style="height:20px; margin-top: 5px; margin-left: 20px;">
							Não existem níveis de competências configurados para o cargo atual do colaborador.
						</ul>
					</#if>
					
				</fieldset>
			</#if>
			
			<@ww.token/>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="enviarForm()" class="btnGravar"></button>
			<#if autoAvaliacao>
				<button class="btnCancelar" onclick="window.location='../modelo/minhasAvaliacoesList.action'"></button>
			<#else>
				<button class="btnCancelar" onclick="window.location='avaliacaoDesempenhoQuestionarioList.action?avaliacaoDesempenho.id=${colaboradorQuestionario.avaliacaoDesempenho.id}'"></button>		
			</#if>
		</div>
	<#else>
		<div class="buttonGroup">
			<button class="btnCancelar" onclick="window.location='avaliacaoDesempenhoQuestionarioList.action?avaliacaoDesempenho.id=${colaboradorQuestionario.avaliacaoDesempenho.id}'"></button>		
		</div>
	</#if>
</body>
</html>