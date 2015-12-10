<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<@ww.head/>
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
			
			.dados th:first-child { text-align: left; padding-left: 5px; }
			#boxgrafico { width: 966px; border: 1px solid #BEBEBE; margin-top: 15px; padding-top: 10px; }
			#grafico { width: 640px; height: 440px; margin: 0px auto; }
		</style>

		<style type="text/css" media="print">
			* { color: #333; }
			#topDiv, #menuDropDown, .buttonGroup { display: none; }
			#waDiv { margin: 0; left: 0; }
			#waDivTitulo { color: #333; background-color: #fff; border: 1px solid #333; }
			.waDivFormulario { border: none; }
			#legendas span, .nivelFaixa { border: 1px solid #333; }
			.dados, #boxgrafico { border: none; }
			.dados, .dados th, #boxgrafico { border-bottom: 1px solid #333; }
			.dados * { background-color: #fff; }
		</style>
		
		<!--[if lte IE 8]><script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.highlighter.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.spider.js"/>'></script>
		<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
		<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/nivelCompetencia.js?version=${versao}"/>"></script>
		
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/NivelCompetenciaDWR.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
		
		<script type="text/javascript">
			var niveis = [];
			var competencias = [];
			
			var labels = [];
			var compFaixa = [];
			var compColab = [];
			
			function changes()
			{
				$('.checkCompetencia.changed').change(function() {
					$(this).parent().parent().find(".checkNivel").attr('disabled', !($(this).attr('checked')));
				});
				
				$('.checkCompetenciaCriterio').change(function() {
					$(this).parent().parent().find(".checkNivelCriterio").attr('disabled', !($(this).attr('checked')));
					calculaNivelDaCompetenciaPeloPercentual( $(this).parent().parent().find(".checkNivelCriterio:eq(0)").attr('competencia') );
				});
				$('.checkCompetenciaCriterio').change();
				
				$('.checkNivelCriterio').change(function(){
					calculaNivelDaCompetenciaPeloPercentual($(this).attr("competencia"));
				});
				
				$('#checkAllCompetencia').click(function() {
					$('.checkNivel,.checkNivelCriterio').attr('disabled', !($(this).attr('checked')));
					$('.checkCompetencia.changed,.checkCompetenciaCriterio').attr('checked', $(this).attr('checked')).change();
				});
				
				$('.checkCompetencia, #checkAllCompetencia, .checkNivel, .checkCompetenciaCriterio').change(atualizarGrafico);
			}
			
			$(function() {
				<#if niveisCompetenciaFaixaSalariaisSalvos?exists>
					<#list niveisCompetenciaFaixaSalariaisSalvos as nivelSalvo>
						var linha = $('tr').has('.checkCompetencia[value="${nivelSalvo.competenciaId}"]').has('input[type="hidden"][value="${nivelSalvo.tipoCompetencia}"]');
						linha.find('.checkCompetencia').attr('checked', 'true');
						linha.find('.checkNivel').removeAttr('disabled');
						linha.find('.checkNivel[value="${nivelSalvo.nivelCompetencia.id}"]').attr('checked', 'true');
						linha.find('.ordem').val(${nivelSalvo.nivelCompetencia.ordem});
						
						<#if nivelSalvo.configuracaoNivelCompetenciaCriterios?exists>
							<#list nivelSalvo.configuracaoNivelCompetenciaCriterios as criterio >
								$(".checkCompetenciaCriterio[value=${criterio.criterioId}]").parent().parent().find(".checkNivelCriterio[value='${criterio.nivelCompetencia.id}").attr("checked", "checked");
								$(".checkCompetenciaCriterio[value=${criterio.criterioId}]").attr("checked", "checked").change();
							</#list>
						</#if>
					</#list>
				</#if>
				 
				niveis[0] = 'Indefinido';
				<#list nivelCompetencias as nivel>
					niveis[${nivel.ordem}] = '${nivel.descricao}';
				</#list>

				<#assign j = 1/>
				<#list niveisCompetenciaFaixaSalariais as comp>
					<#if comp.competenciaDescricao?exists>
						competencias[${j}] = '${comp.competenciaDescricao?j_string?replace('\"','&quot;')?replace('\'','\\\'')}';
						<#assign j = j + 1/>
					</#if>
				</#list>
				
				changes();
				atualizarGrafico();
				
				<#if !niveisCompetenciaFaixaSalariais?exists || niveisCompetenciaFaixaSalariais?size == 0>
					montaAlerta();
				</#if>
				
				<#if configuracaoNivelCompetenciaColaborador?exists && configuracaoNivelCompetenciaColaborador.colaboradorQuestionario?exists && configuracaoNivelCompetenciaColaborador.colaboradorQuestionario.id?exists>
					$('#data').attr("disabled","disabled");
					$('#data_button').hide();
				</#if>
			});
			
			function calculaNivelDaCompetenciaPeloPercentual(competenciaId){
				var niveisSelecionadosDosCriterios = $("input[competencia="+competenciaId+"]:checked").not(":disabled");
				var somaPescetualDosCriterios = 0;
				$(niveisSelecionadosDosCriterios).each(function(){
					somaPescetualDosCriterios += parseInt($(this).attr("percentual"));
				});
				var media = somaPescetualDosCriterios/niveisSelecionadosDosCriterios.length;
				var niveis = $("#competencia_"+competenciaId).parent().parent().find(".checkNivel");
				
				var percentualFinal = 0;
				$(niveis).filter(function() {
				  return $(this).attr("percentual") <= media;
				}).each(function() {
				  var value = parseFloat($(this).attr("percentual"));
				  percentualFinal = (value > percentualFinal) ? value : percentualFinal;
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
				
				$("#competencia_"+competenciaId).parent().parent().find(".checkNivel[percentual="+percentualFinal+"]").attr("checked","checked").change();
				$("#competencia_"+competenciaId).change();
			};
			
			var options = {
				series:{
					spider:{
						show: 'auto', 
						active: true,
						legs: { 
							data: labels,
							legScaleMax: 1,
							legScaleMin: 0,
							legStartAngle: 0,
							font: "11px Arial",
							fillStyle: "Black"
						},
						highlight: { opacity: 0.5, mode: "area" },
						spiderSize: 0.8,
						lineWidth: 0,
						pointSize: 6,
						scaleMode : "all",
						connection: { width: 3 }
					}
				},
				grid: { hoverable: true, clickable: false, tickColor: "rgba(0,0,0,0.2)", ticks: ${nivelCompetencias?size}, mode: "radar" },
				legend : { margin : 2, noColumns: 1, labelBoxBorderColor : '#FFF' }
			};
			
			function atualizarGrafico()
			{
				labels = [];
				compFaixa = [];
				compColab = [];
			
				$('.base').remove();
				$('.checkCompetencia:checked').each(function() {
					var seq = $(this).parent().next().text().match(/\d+/g)[0];
					var check = $(this).parent().parent().find('.checkNivel:checked');
					var nivelColab = check.attr('nivelcolaborador');
					var nivelFaixa = check.attr('nivelfaixa');
					
					if (nivelFaixa || nivelColab)
					{
						labels.push({ label: seq });
						compFaixa.push([ seq, nivelFaixa || 0 ]);
						compColab.push([ seq, nivelColab || 0 ]);
					}
				});
				
				options.series.spider.legs.data = labels;
				
				var data = [
					{ label: "Competências exigidas pelo cargo/faixa", color:"orange", data: compFaixa, spider: {show: true} },
					{ label: "Competências do colaborador", color:"lightblue", data: compColab, spider: {show: true} }
				];
				
				if($('.checkCompetencia').is(':checked')){
					plot = $.plot($("#grafico"), data , options);
				}
				
				$("#grafico").bind("plothover", graficohover);
			}
			
			var serie = null;
			var datapoint = null;
			function graficohover(event, pos, item) 
			{
				if (item) 
				{
					if (serie != item.serie || datapoint != item.datapoint) 
					{
						serie = item.serie; 
						datapoint = item.datapoint;
						$("#tooltip").remove();
						showTooltip(pos.pageX, pos.pageY, item.value);
					}
				} else 
				{
					$("#tooltip").remove();
					serie = null;
					datapoint = null;            
				}
			}
			
			function showTooltip(x, y, contents) 
			{
				$("<div id='tooltip'>" + competencias[ contents[0] ] + ': ' + niveis[ contents[1] ] + "</div>").css({
					position: "absolute",
					display: "none",
					top: y + 5,
					left: x + 5,
					border: "1px solid #fdd",
					padding: "2px",
					"background-color": "#fee",
					opacity: 0.80
				}).appendTo("body").fadeIn(200);
			}
			
			function enviarForm()
			{
				<#if configuracaoNivelCompetenciaColaborador?exists && !configuracaoNivelCompetenciaColaborador.id?exists>
					if (!validaFormulario('form', new Array('avaliador'), new Array(), true))
					{
						jAlert('Selecione um avaliador.');
						return false;
					}
				</#if>

				if (!validaFormulario('form', new Array('data'), new Array('data'), true))
				{
					jAlert('Informe uma data correta.');
					return false;
				}
				
				var linhasSemRadioMarcado = $('tr').has('.checkNivel:enabled').not(':has(.checkNivel:checked)');
				linhasSemRadioMarcado = $.merge(linhasSemRadioMarcado, $('tr').has('.checkNivelCriterio:enabled').not(':has(.checkNivelCriterio:checked)'));
				if (linhasSemRadioMarcado.size() == 0)
				{
					$('#form').submit();
					return true;
				}
					
				$('tr.even').css('background-color', '#EFEFEF');
				$('tr.odd').css('background-color', '#FFF');
			
				jAlert('Selecione os níveis para as competências ou critérios indicados.');
				linhasSemRadioMarcado.css('background-color', '#FFEEC2');
	
				return false;
			}
			
			function montaAlerta()
			{
				var content = '<tbody>';
				content +=  '<tr><td colspan="7">';
				content +=  ' <div class="info"> ';
				content +=	' <ul>';
				content +=	' 	<li>Não existem competências configuradas para ${faixaSalarial.descricao} na data informada.</li>';
				content +=	' </ul>';
				content +=	'</div>';
				content +=  '</tr></td>';
				content += '</tbody>';
				
				$('#configuracaoNivelCompetencia').append(content);			
			}
			
			var configChecked;
			var nivelChecked =  new Object();
			var onLoad =  new Object();
			var dadosNiveis;
			function repopulaConfiguracaoNivelCompetencia()
			{
				var hoje = new Date();
				var partesData =$('#data').val().split('/');
				var data = new Date(partesData[2],partesData[1]-1,partesData[0]); 
				
				if(data.getTime() > hoje.getTime())
				{
					jAlert('Não é possível inserir uma competência para o colaborador com uma data futura.');
					partesData = hoje.toISOString().substring(0, 10).split('-');					
					$('#data').val(partesData[2] + '/' + partesData[1] + '/' + partesData[0]);
				} 
			
				configChecked = $('.checkCompetencia:checked').map(function(){
					if($(this).parent().parent().find(".checkNivel").is(":checked")){
						id = $(this).val();
						nivelChecked[id] = $(this).parent().parent().find(".checkNivel:checked").val(); 
						return id;
					}
				}).get();
				
				$('#configuracaoNivelCompetencia thead').remove();
				$('#configuracaoNivelCompetencia tbody').remove();
				
				NivelCompetenciaDWR.findNiveisCompetencia($('#data').val(), ${faixaSalarial.id}, ${empresaSistema.id}, function(dataNiveis){dadosNiveis = dataNiveis;});				
				NivelCompetenciaDWR.findCompetenciaByFaixaSalarialAndData(repopulaConfiguracaoNivelCompetenciaByDados, $('#data').val(), ${faixaSalarial.id});
			}

			function repopulaConfiguracaoNivelCompetenciaByDados(dados)
			{
				var content = '<thead>';
				content += '<tr>';
				content += '<th><input type="checkbox" id="checkAllCompetencia"></th>';
				content += '<th style="width: 20px; text-align: center;">#</th>';
				content += '<th>Competência/Critério para avaliação</th>';
				
				if(dadosNiveis != '')
					for (var propNivel in dadosNiveis)
						content += '<th>' + dadosNiveis[propNivel]["descricao"] + '</th>';
				
				content += '</tr>';
				content += '</thead>';
			
				if(dados == '')	{
					montaAlerta();
					$('.base').remove();
					return;
				}
				
				var contador = 0;
				content += '<tbody>';
				for (var prop in dados)
				{
					criteriosAvaliacaoCompetencia = dados[prop]["criteriosAvaliacaoCompetencia"];
					
					content += '<tr class="even">';
				    
				    id = dados[prop]["competenciaId"];
				    marcado = $.inArray(id + '', configChecked) > -1;
				    
				    content += '		<td style="width: 20px;">';
				    content += '		<input type="hidden" name="niveisCompetenciaFaixaSalariais[' + contador + '].tipoCompetencia" value="' + dados[prop]["tipoCompetencia"] + '" id="form_niveisCompetenciaFaixaSalariais_' + contador + '__tipoCompetencia" />';
					content += '		<input type="hidden" name="niveisCompetenciaFaixaSalariais[' + contador + '].nivelCompetencia.ordem" class="ordem" value="' + dados[prop]["ordem"] + '" id="ordem_' + contador + '" />';					
					content += '		<input type="checkbox" id="competencia_' + contador + '" name="niveisCompetenciaFaixaSalariais[' + contador + '].competenciaId" value="' + id + '" ';
					
					if(criteriosAvaliacaoCompetencia.length > 0)
						content += '	onclick="return false;" class="checkCompetencia" disabled="disabled" ';
					else 
						content += '	class="checkCompetencia changed" ';
					
					if(marcado)
						content += ' checked=true';
											
					content += '     />';
					content += '	</td>';
					content += '	<td style="width: 20px; text-align: center;">';
					content += '	' + (contador + 1);
					content += '	</td>';
					content += '	<td>';
					content += '		<label for="competencia_' + contador + '">' + dados[prop]["competenciaDescricao"] + '</label>';

					if(dados[prop]["competenciaObservacao"] != "")
					{
						src = '<@ww.url value="/imgs/help-info.gif"/>';
						onLoad[contador] = dados[prop]["competenciaObservacao"];						
						content += '	<img id="competencia_' + contador + '_obs" src="' + src + '" width="16" height="16" style="margin-left: 0px;margin-top: 0px;vertical-align: top;"/>';
					}
										
					content += '	</td>';
					
					if(dadosNiveis != '')
					{
						for (var propNivel in dadosNiveis)
						{
							content += '<td style=" width: 100px; text-align: center;';
	
							if(dados[prop]["nivelCompetencia"]["id"] == dadosNiveis[propNivel]["id"])
								content += 'background-color: #BFC0C3;" class="nivelFaixa';
								
							content += '">	<input type="radio" disabled="disabled" name="niveisCompetenciaFaixaSalariais[' + contador + '].nivelCompetencia.id" value="' + dadosNiveis[propNivel]["id"] + '" nivelcolaborador="' + dadosNiveis[propNivel]["ordem"] + '" nivelfaixa="' + dados[prop]["nivelCompetencia"]["ordem"] + '" ';
							
							if(criteriosAvaliacaoCompetencia.length > 0)
								content += ' onclick="return false;"  class="checkNivel radio"'; 
							else
								content += 'class="checkNivel changed radio"'; 
							
							content += ' percentual="' + dadosNiveis[propNivel]["percentual"] + '" onchange="setOrdem(' + contador + ', ' + dadosNiveis[propNivel]["ordem"] + ')"';
							
							if(nivelChecked[id] == dadosNiveis[propNivel]["id"])
								content += ' checked=true ';
							
							if(!marcado)
								content += ' disabled="disabled"';
							
							content += '/></td>';
						}
					}
					
					content += '</tr>';
					
					if(criteriosAvaliacaoCompetencia.length > 0)
					{
						var contadorCriterio = 0;
						for (var propCriterios in criteriosAvaliacaoCompetencia)
						{
							content +=	'	<tr class="odd">';
							content +=	'		<td >';
							content +=	'		</td>';
							content +=	'		<td style="text-align: center;">';
							content +=	'			<input type="hidden" name="niveisCompetenciaFaixaSalariais[' + contador + '].configuracaoNivelCompetenciaCriterios[' + contadorCriterio + '].criterioDescricao" value="' + criteriosAvaliacaoCompetencia[propCriterios]["descricao"] + '" />';
							content +=	'			<input type="checkbox" id="competencia_' + contador + '_criterio_' + contadorCriterio + '" name="niveisCompetenciaFaixaSalariais[' + contador + '].configuracaoNivelCompetenciaCriterios[' + contadorCriterio + '].criterioId" value="' + criteriosAvaliacaoCompetencia[propCriterios]["id"] + '" class="checkCompetenciaCriterio" />';
							content +=	'		</td>';
							content +=	'		<td><label for="competencia_' + contador + '_criterio_' + contadorCriterio + '">' + criteriosAvaliacaoCompetencia[propCriterios]["descricao"] + '</label></td>';
							
											for (var propNivel in dadosNiveis)
											{
												content +=	'<td style="width: 100px; text-align: center;">';
												content +=	'	<input type="radio" disabled="disabled" class="checkNivelCriterio radio" competencia="' + contador + '" percentual="' + dadosNiveis[propNivel]["percentual"] + '" name="niveisCompetenciaFaixaSalariais[' + contador + '].configuracaoNivelCompetenciaCriterios[' + contadorCriterio + '].nivelCompetencia.id" value="' + dadosNiveis[propNivel]["id"] + '" />';
												content +=	'</td>';
											}
							content +=	'	</tr>';
									
							contadorCriterio++;	
						}
					}
					
					contador++;
				}
				content += '</tbody>';
				
				$('#configuracaoNivelCompetencia').append(content);
				
				$('#checkAllCompetencia').attr('checked', false);

				$('.checkCompetencia .changed').change(function() {
					$(this).parent().parent().find(".checkNivel").attr('disabled', !($(this).attr('checked')));
				});
			
				if(onLoad[1] != null)
				{
					$.each(onLoad, function(key, value) {
	    				toolTipCompetenciaObs(key, value);
					});
				}

				changes();
				atualizarGrafico();
			}
			
			function setOrdem(i, ordem)
			{
				$('#ordem_' + i).val(ordem);
			}
		</script>
		
		<title>Competências do Colaborador</title>
		
		<#if configuracaoNivelCompetenciaColaborador?exists && configuracaoNivelCompetenciaColaborador.data?exists>
			<#assign data = configuracaoNivelCompetenciaColaborador.data?date/>
		<#else>
			<#assign data = ""/>
		</#if>
				
		<#include "../ftl/mascarasImports.ftl" />
	</head>
	<body>
		<@ww.actionmessage />
		<@ww.actionerror />

		<#if configuracaoNivelCompetenciaColaborador?exists && configuracaoNivelCompetenciaColaborador.configuracaoNivelCompetenciaFaixaSalarial?exists && configuracaoNivelCompetenciaColaborador.configuracaoNivelCompetenciaFaixaSalarial.id?exists>
			<#assign configuracaoNivelCompetenciaFaixaSalarialId = configuracaoNivelCompetenciaColaborador.configuracaoNivelCompetenciaFaixaSalarial.id/>
		<#else>
			<#assign configuracaoNivelCompetenciaFaixaSalarialId = configuracaoNivelCompetenciaFaixaSalarial.id/>
		</#if>
		
		<#if configuracaoNivelCompetenciaColaborador?exists && configuracaoNivelCompetenciaColaborador.colaboradorQuestionario?exists && configuracaoNivelCompetenciaColaborador.colaboradorQuestionario.id?exists>
			<#assign colaboradorQuestionarioId = configuracaoNivelCompetenciaColaborador.colaboradorQuestionario.id/>
		<#else>
			<#assign colaboradorQuestionarioId = ""/>
		</#if>
		
		<#if configuracaoNivelCompetenciaColaborador?exists && configuracaoNivelCompetenciaColaborador.colaboradorQuestionario?exists && configuracaoNivelCompetenciaColaborador.colaboradorQuestionario.avaliacaoDesempenho?exists && configuracaoNivelCompetenciaColaborador.colaboradorQuestionario.avaliacaoDesempenho.titulo?exists>
			<#assign avDesempenhoId = configuracaoNivelCompetenciaColaborador.colaboradorQuestionario.avaliacaoDesempenho.id/>
			<#assign avDesempenhoTitulo = configuracaoNivelCompetenciaColaborador.colaboradorQuestionario.avaliacaoDesempenho.titulo/>
		<#else>
			<#assign avDesempenhoId = ""/>
			<#assign avDesempenhoTitulo = "-"/>
		</#if>

		<#if configuracaoNivelCompetenciaColaborador?exists && configuracaoNivelCompetenciaColaborador.avaliador?exists && configuracaoNivelCompetenciaColaborador.avaliador.id?exists>
			<#assign avaliadorId = configuracaoNivelCompetenciaColaborador.avaliador.id/>
			<#assign avaliadorNome = configuracaoNivelCompetenciaColaborador.avaliador.nome/>
		<#else>
			<#assign avaliadorId = ""/>
			<#assign avaliadorNome = "Anônimo"/>
		</#if>
		
		<b>Colaborador:</b> ${colaborador.nome}<br>
		<b>Cargo:</b> ${faixaSalarial.cargo.nome}  &nbsp;&nbsp;  <b>Faixa Salarial:</b> ${faixaSalarial.nome}<br>
		<#if configuracaoNivelCompetenciaColaborador?exists && 	configuracaoNivelCompetenciaColaborador.id?exists>
			<b>Avaliação de Desempenho:</b> ${avDesempenhoTitulo} <br>
			<b>Avaliador:</b> ${avaliadorNome}<br>
		</#if>
		<div style="clear: both;"></div><br />
		
			<@ww.form name="form" id="form" action="saveCompetenciasColaborador.action" method="POST">
			
			<div id="legendas" style="float:right;">
				<span style='background-color: #BFC0C3;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Níveis de Competência exigidos para o Cargo/Faixa Salarial
			</div>
			
			<@ww.datepicker label="A partir de" name="configuracaoNivelCompetenciaColaborador.data" value="${data}" id="data" cssClass="mascaraData" onchange="repopulaConfiguracaoNivelCompetencia();"/>
			<#if configuracaoNivelCompetenciaColaborador?exists && configuracaoNivelCompetenciaColaborador.colaboradorQuestionario?exists && configuracaoNivelCompetenciaColaborador.colaboradorQuestionario.avaliacaoDesempenho?exists>
				<@ww.hidden name="configuracaoNivelCompetenciaColaborador.data" value="${data}"/>
			</#if>
			
			<#if configuracaoNivelCompetenciaColaborador?exists && 	configuracaoNivelCompetenciaColaborador.id?exists>
				<@ww.hidden name="configuracaoNivelCompetenciaColaborador.avaliador.id" id="avaliador" value="${avaliadorId}"/>
			<#else>
				<@ww.select label="Avaliador" id="avaliador" name="configuracaoNivelCompetenciaColaborador.avaliador.id" list="colaboradores"  listKey="id" listValue="nome"  headerKey="-1" headerValue="Selecione..." />
			</#if>
			
			<@ww.hidden name="configuracaoNivelCompetenciaColaborador.id" />
			<@ww.hidden name="configuracaoNivelCompetenciaColaborador.faixaSalarial.id" value="${faixaSalarial.id}"/>
			<@ww.hidden name="configuracaoNivelCompetenciaColaborador.colaborador.id" value="${colaborador.id}"/>
			<@ww.hidden name="configuracaoNivelCompetenciaColaborador.colaboradorQuestionario.id" value="${colaboradorQuestionarioId}"/>
			<@ww.hidden name="configuracaoNivelCompetenciaColaborador.colaboradorQuestionario.avaliacaoDesempenho.id" value="${avDesempenhoId}"/>
			<@ww.hidden name="configuracaoNivelCompetenciaColaborador.configuracaoNivelCompetenciaFaixaSalarial.id" value="${configuracaoNivelCompetenciaFaixaSalarialId}"/>
			
			<@ww.hidden name="colaborador.id" />
			<br />
			
			<div id="niveisCompetenciaFaixaSalariais">
				<table id="configuracaoNivelCompetencia" class="dados">
					<thead>
						<tr>
							<th><input type="checkbox" id="checkAllCompetencia"></th>
							<th style="width: 20px; text-align: center;">#</th>
							<th>Competência/Critério para avaliação</th>
							<#list nivelCompetencias as nivel>
								<th>${nivel.descricao}</th>
							</#list>
						</tr>
					</thead>
					<tbody>
						<#assign i = 0/>
						<#list niveisCompetenciaFaixaSalariais as configuracaoNivelCompetencia>
							<#assign hasCriterios = (configuracaoNivelCompetencia.criteriosAvaliacaoCompetencia.size() >0) />
							<tr class="even">
								<td style="width: 20px;">
									<@ww.hidden name="niveisCompetenciaFaixaSalariais[${i}].tipoCompetencia"/>
									<#-- não utilizar decorator no hidden abaixo -->
									<input type="hidden" name="niveisCompetenciaFaixaSalariais[${i}].nivelCompetencia.ordem" id="ordem_${i}" class="ordem" value=""/>
									
									<input type="checkbox" id="competencia_${i}" name="niveisCompetenciaFaixaSalariais[${i}].competenciaId"
									<#if hasCriterios > onclick="return false;" class="checkCompetencia" disabled="disabled"<#else> class="checkCompetencia changed" </#if>
									value="${configuracaoNivelCompetencia.competenciaId}" />
								</td>
								
								<td style="text-align: center;">${i+1}</td>
								<td>
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
										<#assign nivelPercentual="${nivel.percentual}"/>
									<#else>
										<#assign nivelPercentual=""/>
									</#if>
									
									<td style="${bgcolor} width: 100px; text-align: center;" class="${class}">
										<input type="radio" disabled="disabled" percentual="${nivelPercentual}" 
										<#if hasCriterios > onclick="return false;"  class="checkNivel radio" <#else>  class="checkNivel changed radio" </#if>
										name="niveisCompetenciaFaixaSalariais[${i}].nivelCompetencia.id" value="${nivel.id}" nivelcolaborador="${nivel.ordem}" nivelfaixa="${configuracaoNivelCompetencia.nivelCompetencia.ordem}" onchange="setOrdem(${i}, ${nivel.ordem})"/>
									</td>
								</#list>
							</tr>
							
							<#assign y = 0/>
							<#list configuracaoNivelCompetencia.criteriosAvaliacaoCompetencia as criterio>
								<tr class="odd">
									<td >
										
									</td>
									
									<td style="text-align: center;">
										<input type="hidden" name="niveisCompetenciaFaixaSalariais[${i}].configuracaoNivelCompetenciaCriterios[${y}].criterioDescricao" value="${criterio.descricao}" />
										<input type="checkbox" id="competencia_${i}_criterio_${y}" name="niveisCompetenciaFaixaSalariais[${i}].configuracaoNivelCompetenciaCriterios[${y}].criterioId" value="${criterio.id}" class="checkCompetenciaCriterio" />
									</td>
									<td><label for="competencia_${i}_criterio_${y}">${criterio.descricao}</label></td>
									
									<#list nivelCompetencias as nivelCriterio>
										<td style="width: 100px; text-align: center;">
											<input type="radio" disabled="disabled" class="checkNivelCriterio radio" competencia="${i}" percentual="${nivelCriterio.percentual}" name="niveisCompetenciaFaixaSalariais[${i}].configuracaoNivelCompetenciaCriterios[${y}].nivelCompetencia.id" value="${nivelCriterio.id}" />
										</td>
									</#list>
								</tr>
								
								<#assign y = y + 1/>
							</#list>
							
							<#assign i = i + 1/>
						</#list>
					</tbody>
				</table>	
			
			</div>
			
			<@ww.token/>
		</@ww.form>
	
		<div id="boxgrafico">
			<div id="grafico"></div>
		</div>
		
		<div class="buttonGroup">
			<#if niveisCompetenciaFaixaSalariais?exists && 0 < niveisCompetenciaFaixaSalariais?size>
				<button onclick="enviarForm();" class="btnGravar"></button>
			<#else>
				<button class="btnGravarDesabilitado"></button>
			</#if>
			<button onclick="window.location='listCompetenciasColaborador.action?colaborador.id=${colaborador.id}';" class="btnVoltar"></button>
			<button onclick="window.print();" class="btnImprimir"></button>
		</div>
	</body>
</html>
