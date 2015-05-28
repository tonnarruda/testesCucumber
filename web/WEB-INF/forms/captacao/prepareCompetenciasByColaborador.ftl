<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<@ww.head/>
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css"/>');
			
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
		<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>
		<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/nivelCompetencia.js"/>"></script>
		
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/NivelCompetenciaDWR.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
		
		<script type="text/javascript">
			var niveis = [];
			var competencias = [];
			
			var labels = [];
			var compFaixa = [];
			var compColab = [];
			
			$(function() {
				$('.checkCompetencia').click(function() {
					$(this).parent().parent().find(".checkNivel").attr('disabled', !($(this).attr('checked')));
				});
				
				$('#checkAllCompetencia').click(function() {
					$('.checkNivel').attr('disabled', !($(this).attr('checked')));
					$('.checkCompetencia').attr('checked', $(this).attr('checked'));
				});
				
				<#if niveisCompetenciaFaixaSalariaisSalvos?exists>
					<#list niveisCompetenciaFaixaSalariaisSalvos as nivelSalvo>
						var linha = $('tr').has('.checkCompetencia[value="${nivelSalvo.competenciaId}"]').has('input[type="hidden"][value="${nivelSalvo.tipoCompetencia}"]');
						linha.find('.checkCompetencia').attr('checked', 'true');
						linha.find('.checkNivel').removeAttr('disabled');
						linha.find('.checkNivel[value="${nivelSalvo.nivelCompetencia.id}"]').attr('checked', 'true');
					</#list>
				</#if>
				 
				niveis[0] = 'Indefinido';
				<#list nivelCompetencias as nivel>
					niveis[${nivel.ordem}] = '${nivel.descricao}';
				</#list>

				<#assign j = 1/>
				<#list niveisCompetenciaFaixaSalariais as comp>
					competencias[${j}] = '${comp.competenciaDescricao?j_string?replace('\"','&quot;')?replace('\'','\\\'')}';
					<#assign j = j + 1/>
				</#list>
				
				$('.checkCompetencia, #checkAllCompetencia, .checkNivel').click(atualizarGrafico);
				
				atualizarGrafico();
				
				<#if !niveisCompetenciaFaixaSalariais?exists || niveisCompetenciaFaixaSalariais?size == 0>
					montaAlerta();
				</#if>
				
			});
			
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
				
				if($('.checkCompetencia').is(':checked'))
					plot = $.plot($("#grafico"), data , options);
				
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
				if (linhasSemRadioMarcado.size() == 0)
				{
					$('#form').submit();
					return true;
				}
					
				$('tr.even').css('background-color', '#EFEFEF');
				$('tr.odd').css('background-color', '#FFF');
			
				jAlert('Selecione os níveis para as competências indicadas.');
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
			function repopulaConfiguracaoNivelCompetencia()
			{
				configChecked = $('.checkCompetencia:checked').map(function(){
					if($(this).parent().parent().find(".checkNivel").is(":checked")){
						id = $(this).val();
						nivelChecked[id] = $(this).parent().parent().find(".checkNivel:checked").val(); 
						return id;
					}
				}).get();
			
				$('#configuracaoNivelCompetencia tbody').remove();
				NivelCompetenciaDWR.findCompetenciaByFaixaSalarialAndData(repopulaConfiguracaoNivelCompetenciaByDados, ${faixaSalarial.id}, $('#data').val());
			}



			function repopulaConfiguracaoNivelCompetenciaByDados(dados)
			{
				if(dados == '')	{
					montaAlerta();
					$('.base').remove();
					return;
				}
				
				var contador = 0;
				var content = '<tbody>';
				for (var prop in dados)
				{
					if (contador % 2 != 0)
						content += '<tr class="even">';
					else
						content += '<tr class="odd">';
				    
				    id = dados[prop]["competenciaId"];
				    marcado = $.inArray(id + '', configChecked) > -1;
				    
				    content += '	<td style="width: 20px;">';
				    content += '		<input type="hidden" name="niveisCompetenciaFaixaSalariais[' + contador + '].tipoCompetencia" value="' + dados[prop]["tipoCompetencia"] + '" id="form_niveisCompetenciaFaixaSalariais_' + contador + '__tipoCompetencia" />';
					content += '		<input type="checkbox" id="competencia_' + contador + '" name="niveisCompetenciaFaixaSalariais[' + contador + '].competenciaId" value="' + id + '" class="checkCompetencia" ';
					
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
					
					<#list nivelCompetencias as nivel>
						content += '<td style=" width: 100px; text-align: center;';

						if(dados[prop]["nivelCompetencia"]["id"] == ${nivel.id})
							content += 'background-color: #ececec;" class="nivelFaixa';
							
						content += '">	<input type="radio" class="checkNivel radio" name="niveisCompetenciaFaixaSalariais[' + contador + '].nivelCompetencia.id" value="${nivel.id}" nivelcolaborador="${nivel.ordem}" nivelfaixa="' + dados[prop]["nivelCompetencia"]["ordem"] + '" ';
						
						if(nivelChecked[id] == ${nivel.id})
							content += ' checked=true ';
						
						if(!marcado)
							content += ' disabled="disabled"';
						
						content += '/></td>';
					</#list>
					content += '</tr>';
					contador++;
				}
				content += '</tbody>';
				
				$('#configuracaoNivelCompetencia').append(content);
				
				$('#checkAllCompetencia').attr('checked', false);
				
				$('.checkCompetencia').click(function() {
					$(this).parent().parent().find(".checkNivel").attr('disabled', !($(this).attr('checked')));
				});
			
				$('.checkCompetencia, #checkAllCompetencia, .checkNivel').click(atualizarGrafico);
				
				$.each(onLoad, function(key, value) {
    				toolTipCompetenciaObs(key, value);
				})

				atualizarGrafico();
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
				<span style='background-color: #ececec;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Níveis de Competência exigidos para o Cargo/Faixa Salarial
			</div>
			
			<@ww.datepicker label="A partir de" name="configuracaoNivelCompetenciaColaborador.data" value="${data}" id="data" cssClass="mascaraData" onchange="repopulaConfiguracaoNivelCompetencia();"/>
			
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
			
			<@ww.hidden name="colaborador.id" />
			<br />
			
			<div id="niveisCompetenciaFaixaSalariais">
				<#assign i = 0/>
				<@display.table name="niveisCompetenciaFaixaSalariais" id="configuracaoNivelCompetencia" class="dados">
				
					<@display.column title="<input type='checkbox' id='checkAllCompetencia'/>" style="width: 20px;">
						<@ww.hidden name="niveisCompetenciaFaixaSalariais[${i}].tipoCompetencia"/>
						<input type="checkbox" id="competencia_${i}" name="niveisCompetenciaFaixaSalariais[${i}].competenciaId" value="${configuracaoNivelCompetencia.competenciaId}" class="checkCompetencia" />
					</@display.column>
	
					<@display.column title="#" style="width: 20px; text-align: center;">
						${i+1}
					</@display.column>
	
					<@display.column title="Competência" >
						<label for="competencia_${i}">${configuracaoNivelCompetencia.competenciaDescricao}</label>
						<#if configuracaoNivelCompetencia.competenciaObservacao?exists && configuracaoNivelCompetencia.competenciaObservacao != "">
							<img id="competencia_${i}_obs" onLoad="toolTipCompetenciaObs(${i}, '${configuracaoNivelCompetencia.competenciaObservacao?j_string?replace('\"','&quot;')?replace('\'','\\\'')}')" src="<@ww.url value='/imgs/help-info.gif'/>" width='16' height='16' style='margin-left: 0px;margin-top: 0px;vertical-align: top;'/>
						</#if>
					</@display.column>
					
					<#list nivelCompetencias as nivel>
						
						<#if configuracaoNivelCompetencia?exists && configuracaoNivelCompetencia.nivelCompetencia.id == nivel.id>
							<#assign class="nivelFaixa"/>
							<#assign bgcolor="background-color: #ececec;"/>
						<#else>
							<#assign class=""/>
							<#assign bgcolor=""/>
						</#if>
						
						<@display.column title="${nivel.descricao}" style="${bgcolor} width: 100px; text-align: center;" class="${class}">
							<input type="radio" disabled="disabled" class="checkNivel radio" name="niveisCompetenciaFaixaSalariais[${i}].nivelCompetencia.id" value="${nivel.id}" nivelcolaborador="${nivel.ordem}" nivelfaixa="${configuracaoNivelCompetencia.nivelCompetencia.ordem}"/>
						</@display.column>
					</#list>
					
					<#assign i = i + 1/>
				</@display.table>
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
