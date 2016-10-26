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
	
	<#assign exibirPerformance=(mostrarPerformanceAvalDesempenho && !(avaliador.id == colaborador.id && !colaboradorQuestionario.avaliacaoDesempenho.exibeResultadoAutoAvaliacao)) />
	
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
		
		function setOrdem(i, ordem)
		{
			$('#ordem_' + i).val(ordem);
		}
		
		function setOrdemNivelCriterio(i, y, ordem)
		{
			$('#ordemNivel_criterio_' + i + '_' + y).val(ordem);
		}
		
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
	</script>
	
	<#if configuracaoNivelCompetenciaColaborador?exists && configuracaoNivelCompetenciaColaborador.data?exists>
		<#assign data=configuracaoNivelCompetenciaColaborador.data?date />
	<#else>
		<#assign data="" />
	</#if>
	
	<#assign respostasCompactas=colaboradorQuestionario.avaliacao.respostasCompactas />
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
				<br />
				
				<fieldset>
					<legend>Avaliar as Competências do Colaborador para o Cargo</legend><br />
					
					<div id="legendas">
						<#if colaboradorQuestionario.avaliacaoDesempenho.exibirNivelCompetenciaExigido >
							<span style='background-color: #BFC0C3;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Nível de Competência exigido para o Cargo/Faixa Salarial
							<br /><br />
						</#if>
						<span style='background-color: #E4F0FE;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Nível de Competência do Colaborador
						<br /><br />
						<span style='background-color: #A4E2DB;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Nível de Competência do Colaborador é igual ao nível exigido para o Cargo/Faixa Salarial
					</div>
		
					<br /><br />
					<#if exibirPerformance>
						<pre id="performanceCompetencias" style="text-align:right; font-weight: bold;">Performance Questionário: - </pre>
					</#if>
					<table id="configuracaoNivelCompetencia" class="dados">
						<thead>
							<tr>
								<th><input type="checkbox" id="checkAllCompetencia"> Competência/Comportamento para avaliação</th>
								<#list nivelCompetencias as nivel>
									<th>${nivel.descricao}</th>
								</#list>
								
								<#if exibirPerformance>
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
										<#if colaboradorQuestionario.avaliacaoDesempenho.exibirNivelCompetenciaExigido && configuracaoNivelCompetencia?exists && configuracaoNivelCompetencia.nivelCompetencia?exists && configuracaoNivelCompetencia.nivelCompetencia.id?exists && configuracaoNivelCompetencia.nivelCompetencia.id == nivel.id>
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
									
									<#if exibirPerformance>
										<td style="width: 100px; text-align: center;" class="${class}">
											<label class="performance">-</label>
										</td>
									</#if>
									
								</tr>
								
								<#assign y = 0/>
								<#list configuracaoNivelCompetencia.criteriosAvaliacaoCompetencia as criterio>
									<tr class="odd">
										<td style="padding: 5px 25px;">
											<input type="hidden" name="niveisCompetenciaFaixaSalariais[${i}].configuracaoNivelCompetenciaCriterios[${y}].nivelCompetencia.ordem" id="ordemNivel_criterio_${i}_${y}" class="ordem" value=""/>
											<input type="hidden" name="niveisCompetenciaFaixaSalariais[${i}].configuracaoNivelCompetenciaCriterios[${y}].criterioDescricao" value="${criterio.descricao}" />
											<input type="checkbox" id="competencia_${i}_criterio_${y}" name="niveisCompetenciaFaixaSalariais[${i}].configuracaoNivelCompetenciaCriterios[${y}].criterioId" value="${criterio.id}" class="checkCompetenciaCriterio" />
											<label for="competencia_${i}_criterio_${y}">${criterio.descricao}</label>
										</td>
										
										<#list nivelCompetencias as nivelCriterio>
											<td style="width: 100px; text-align: center;">
												<input type="radio" disabled="disabled" class="checkNivelCriterio radio" competencia="${i}" percentual="${nivelCriterio.percentualString}" name="niveisCompetenciaFaixaSalariais[${i}].configuracaoNivelCompetenciaCriterios[${y}].nivelCompetencia.id" value="${nivelCriterio.id}" onchange="setOrdemNivelCriterio(${i}, ${y}, ${nivelCriterio.ordem})"/>
											</td>
										</#list>
									</tr>
									
									<#assign y = y + 1/>
								</#list>
								
								<#assign i = i + 1/>
							</#list>
					
							<#if niveisCompetenciaFaixaSalariais?exists && niveisCompetenciaFaixaSalariais?size == 0>
								<tr>
									<td colspan="15">
										<div class="info"> 
										<ul>
											<#if existConfigCompetenciaAvaliacaoDesempenho>
												<li>Não existem níveis de competências configurados para avaliar.</li>
											<#else>
												<li>Não existem níveis de competências configurados para o cargo atual do colaborador.</li>
											</#if>
										</ul>
										</div>
									</td>
								</tr>
							</#if>
							
						</tbody>
					</table>
				</fieldset>
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