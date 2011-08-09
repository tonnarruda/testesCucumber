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
				
				<#list niveisCompetenciaFaixaSalariaisSugeridos as nivelSugerido>
					var linhaSugerida = $('tr').has('.checkCompetencia[value="${nivelSugerido.competenciaId}"]').has('input[type="hidden"][value="${nivelSugerido.tipoCompetencia}"]');
					linhaSugerida.find('.checkNivel[value="${nivelSugerido.nivelCompetencia.id}"]').parent().css('background-color', '#b8e2ff');
				</#list>
			});
			
			function enviarForm()
			{
				var linhasSemRadioMarcado = $('tr').has('.checkNivel:enabled').not(':has(.checkNivel:checked)');
				if(linhasSemRadioMarcado.size() == 0)
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
		
		<#include "../ftl/mascarasImports.ftl" />
	</head>
	<body>
		<@ww.actionmessage />
		<@ww.actionerror />
		
		<b>Colaborador:</b> ${colaborador.nome}<br>
		<b>Cargo:</b> ${faixaSalarial.cargo.nome}  &nbsp;&nbsp;  <b>Faixa Salarial:</b> ${faixaSalarial.nome}
		<div style="clear: both;"></div><br />
		
		<@ww.form name="form" action="" method="POST">
			<@ww.hidden name="colaborador.id" />
			
			<div id="legendas" style="float:right;">
				<span style='background-color: #b8e2ff;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Níveis de Competência definidos para a Faixa Salarial
			</div>
			
			<@ww.datepicker label="A partir de" name="data" id="data" cssClass="mascaraData" />
			
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
			<button onclick="window.location=''" class="btnVoltar"></button>
		</div>
	</body>
</html>
