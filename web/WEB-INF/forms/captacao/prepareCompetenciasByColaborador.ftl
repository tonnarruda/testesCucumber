<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<@ww.head/>
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css"/>');
			
			.dados th:first-child { text-align: left; padding-left: 5px; }
			#grafico { width: 640px; height: 440px; margin: 0px auto; }
		</style>
		
		<!--[if lte IE 8]><script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.highlighter.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.spider.js"/>'></script>
		
		<script type="text/javascript">
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
				
				<#list niveisCompetenciaFaixaSalariaisSugeridos as nivelSugerido>
					var linhaSugerida = $('tr').has('.checkCompetencia[value="${nivelSugerido.competenciaId}"]').has('input[type="hidden"][value="${nivelSugerido.tipoCompetencia}"]');
					linhaSugerida.find('.checkNivel[value="${nivelSugerido.nivelCompetencia.id}"]').parent().css('background-color', '#ececec');
				</#list>
				
				
				var d1 = [ [0,3], [1,4], [2,4], [3,3], [4,3], [5,4], [6,4], [7,3], [8,3] ];
				var d2 = [ [0,4], [1,4], [2,3], [3,5], [4,4], [5,4], [6,5], [7,3], [8,4] ];
				
				var options = {
					series:{
						spider:{
							active: true,
							legs: { 
								data: [{label: "1"},{label: "2"},{label: "3"},{label: "4"},{label: "5"},{label: "6"},{label: "7"},{label: "8"},{label: "9"}],
								legScaleMax: 1,
								legScaleMin:0.8,
								legStartAngle: 0,
								font: "11px Arial",
								fillStyle: "Black"
							},
							highlight: { opacity: 0.5, mode: 'area' },
							spiderSize: 0.8,
							lineWidth: 0,
							pointSize: 6,
							scaleMode : "all",
							connection: { width: 3 }
						}
					},
					grid: { 
						hoverable: true, 
						clickable: false, 
						tickColor: "rgba(0,0,0,0.2)",
						ticks: ${nivelCompetencias?size},
						mode: "radar" 
					},
					legend : {
						margin : 2,
						noColumns: 1,
						labelBoxBorderColor : '#FFF',
					}
				};
				
				var data = [
					{ label: "Competências exigidas pelo cargo/faixa", color:"orange", data: d1, spider: {show: true, lineWidth: 12} },
					{ label: "Competências do colaborador", color:"lightblue", data: d2, spider: {show: true} },
				];
				
				var plot = $.plot($("#grafico"), data , options);
			});
			
			function enviarForm()
			{
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
					
				$('tr.even').css('background-color', '#E4F0FE');
				$('tr.odd').css('background-color', '#FFF');
			
				jAlert('Selecione os níveis para as competências indicadas.');
				linhasSemRadioMarcado.css('background-color', '#FFEEC2');
	
				return false;
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
		
		<b>Colaborador:</b> ${colaborador.nome}<br>
		<b>Cargo:</b> ${faixaSalarial.cargo.nome}  &nbsp;&nbsp;  <b>Faixa Salarial:</b> ${faixaSalarial.nome}
		<div style="clear: both;"></div><br />
		
		<@ww.form name="form" id="form" action="saveCompetenciasColaborador.action" method="POST">
			
			<div id="legendas" style="float:right;">
				<span style='background-color: #ececec;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Níveis de Competência exigidos para o Cargo/Faixa Salarial
			</div>
			
			<@ww.datepicker label="A partir de" name="configuracaoNivelCompetenciaColaborador.data" value="${data}" id="data" cssClass="mascaraData" />
			
			<@ww.hidden name="configuracaoNivelCompetenciaColaborador.id" />
			<@ww.hidden name="configuracaoNivelCompetenciaColaborador.faixaSalarial.id" value="${faixaSalarial.id}"/>
			<@ww.hidden name="configuracaoNivelCompetenciaColaborador.colaborador.id" value="${colaborador.id}"/>
			<@ww.hidden name="colaborador.id" />
			<br />
			
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
				</@display.column>
				
				<#list nivelCompetencias as nivel>			
					<@display.column title="${nivel.descricao}" style="width: 100px; text-align: center;">
						<input type="radio" disabled="disabled" class="checkNivel radio" name="niveisCompetenciaFaixaSalariais[${i}].nivelCompetencia.id" value="${nivel.id}" />
					</@display.column>
				</#list>
				
				<#assign i = i + 1/>
			</@display.table>
			
			<@ww.token/>
		</@ww.form>
	
		<div id="grafico"></div>
		
		<div class="buttonGroup">
			<#if niveisCompetenciaFaixaSalariais?exists && 0 < niveisCompetenciaFaixaSalariais?size>
				<button onclick="enviarForm();" class="btnGravar"></button>
			<#else>
				<button class="btnGravarDesabilitado"></button>
			</#if>
			<button onclick="window.location='listCompetenciasColaborador.action?colaborador.id=${colaborador.id}';" class="btnVoltar"></button>
		</div>
	</body>
</html>
