<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		
		.dados th { text-align: left; padding-left: 5px; }
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
			
			<#list niveisCompetenciaFaixaSalariaisSalvos as nivelSalvo>
				var linha = $('tr').has('.checkCompetencia[value="${nivelSalvo.competenciaId}"]').has('input[type="hidden"][value="${nivelSalvo.tipoCompetencia}"]');
				linha.find('.checkCompetencia').attr('checked', 'true');
				linha.find('.checkNivel').removeAttr('disabled');
				linha.find('.checkNivel[value="${nivelSalvo.nivelCompetencia.id}"]').attr('checked', 'true');
			</#list>
			
			<#list niveisCompetenciaFaixaSalariaisSugeridos as nivelSugerido>
				var linhaSugerida = $('tr').has('.checkCompetencia[value="${nivelSugerido.competenciaId}"]').has('input[type="hidden"][value="${nivelSugerido.tipoCompetencia}"]');
				linhaSugerida.find('.checkNivel[value="${nivelSugerido.nivelCompetencia.id}"]').parent().css('background-color', '#7E9DB9');
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

	<title>Competências do Candidato</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<p><b>Candidato:</b> ${candidato.nome}</p>
	
	<@ww.form name="form" id="form" action="saveCompetenciasByCandidato.action" method="POST">
		<@ww.hidden name="candidato.id"/>
		<@ww.hidden name="faixaSalarial.id"/>
				
		<#assign i = 0/>
		<@display.table name="niveisCompetenciaFaixaSalariais" id="nivelCompetenciaFaixaSalarial" class="dados">
		
			<@display.column title="<input type='checkbox' id='checkAllCompetencia'/> Competência" >
				<@ww.hidden name="niveisCompetenciaFaixaSalariais[${i}].tipoCompetencia"/>
				<input type="checkbox" name="niveisCompetenciaFaixaSalariais[${i}].competenciaId" value="${nivelCompetenciaFaixaSalarial.competenciaId}" class="checkCompetencia" />
				${nivelCompetenciaFaixaSalarial.competenciaDescricao}
			</@display.column>
			
			<#list nivelCompetencias as nivel>			
				<@display.column title="${nivel.descricao}" style="width: 100px;">
					<input type="radio" disabled="disabled" class="checkNivel radio" name="niveisCompetenciaFaixaSalariais[${i}].nivelCompetencia.id" value="${nivel.id}" />
				</@display.column>
			</#list>
			
			<#assign i = i + 1/>
		</@display.table>
	</@ww.form>
	
	<div class="buttonGroup">
		<button class="btnGravar" onclick="enviarForm();"></button>
		<button class="btnVoltar" onclick=""></button>
	</div>
</body>
</html>
