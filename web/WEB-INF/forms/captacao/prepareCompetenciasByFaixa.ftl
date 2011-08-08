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
		});
		
		function enviarForm()
		{
			var linhasSemRadioMarcado = $('tr').has('.checkNivel:enabled').not(':has(.checkNivel:checked)');
			if(linhasSemRadioMarcado.size() == 0)
				$('#form').submit();
				
			$('tr.even').css('background-color', '#E4F0FE');
			$('tr.odd').css('background-color', '#FFF');
		
			jAlert('Selecione os níveis para as competências indicadas.');
			linhasSemRadioMarcado.css('background-color', '#FFEEC2');
		}
	</script>

	<title>Competências da Faixa Salarial</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<p><b>Cargo:</b> ${faixaSalarial.cargo.nome} &nbsp;&nbsp;&nbsp; <b>Faixa:</b> ${faixaSalarial.nome}</p>
	
	<@ww.form name="form" id="form" action="saveCompetenciasByFaixa.action" method="POST">
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
		<button class="btnVoltar" onclick="window.location='../../cargosalario/faixaSalarial/list.action?cargo.id=${faixaSalarial.cargo.id}'"></button>
	</div>
</body>
</html>
