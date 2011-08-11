<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<@ww.head/>
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css"/>');
			
			.dados th:first-child { text-align: left; padding-left: 5px; }
		</style>
		
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
			
				<@display.column title="<input type='checkbox' id='checkAllCompetencia'/> Competência" >
					<@ww.hidden name="niveisCompetenciaFaixaSalariais[${i}].tipoCompetencia"/>
					<input type="checkbox" id="competencia_${i}" name="niveisCompetenciaFaixaSalariais[${i}].competenciaId" value="${configuracaoNivelCompetencia.competenciaId}" class="checkCompetencia" />
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
	
		<div class="buttonGroup">
			<button onclick="enviarForm();" class="btnGravar"></button>
			<button onclick="window.location='listCompetenciasColaborador.action?colaborador.id=${colaborador.id}';" class="btnVoltar"></button>
		</div>
	</body>
</html>
