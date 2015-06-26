<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		
		.dados th:first-child { text-align: left; padding-left: 5px; }
	</style>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/nivelCompetencia.js?version=${versao}"/>"></script>

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
		});
		
		function enviarForm()
		{
			if ($('.checkCompetencia').size() == 0)
			{
				jAlert('Não existem competências cadastradas para o cargo.');
				return false;
			}
			else if ($('.checkNivel').size() == 0)
			{
				jAlert('Não existem níveis de competência cadastrados.');
				return false;
			}
		
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

	<title>Competências da Faixa Salarial</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<p><b>Cargo:</b> ${faixaSalarial.cargo.nome} &nbsp;&nbsp;&nbsp; <b>Faixa:</b> ${faixaSalarial.nome}</p>
	
	<@ww.form name="form" id="form" action="saveCompetenciasByFaixa.action" method="POST">
		<@ww.hidden name="faixaSalarial.id"/>
		
		<#assign i = 0/>
		<@display.table name="niveisCompetenciaFaixaSalariais" id="configuracaoNivelCompetencia" class="dados">
		
			<@display.column title="<input type='checkbox' id='checkAllCompetencia'/> Competência" >
				<@ww.hidden name="niveisCompetenciaFaixaSalariais[${i}].tipoCompetencia"/>
				<input type="checkbox"  id="competencia_${i}" name="niveisCompetenciaFaixaSalariais[${i}].competenciaId" value="${configuracaoNivelCompetencia.competenciaId}" class="checkCompetencia" />
				<label for="competencia_${i}">${configuracaoNivelCompetencia.competenciaDescricao}</label>
				
				<#if configuracaoNivelCompetencia.competenciaObservacao?exists && configuracaoNivelCompetencia.competenciaObservacao != "">
					<img id="competencia_${i}_obs" onLoad="toolTipCompetenciaObs(${i}, '${configuracaoNivelCompetencia.competenciaObservacao?j_string?replace('\"','$#-')?replace('\'','\\\'')}')" src="<@ww.url value='/imgs/help-info.gif'/>" width='16' height='16' style='margin-left: 0px;margin-top: 0px;vertical-align: top;'/>
				</#if>
				
			</@display.column>
			
			<#list nivelCompetencias as nivel>			
				<@display.column title="${nivel.descricao}" style="width: 100px; text-align: center;">
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
