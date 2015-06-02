<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		
		.dados th:first-child { text-align: left; padding-left: 5px; }
	</style>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/nivelCompetencia.js"/>"></script>

	<script type="text/javascript">
		$(function() {
			$('.checkCompetenciaConhecimento').click(function() {
				$(this).parent().parent().find(".checkNivelConhecimento").attr('disabled', !($(this).attr('checked'))).attr('checked',false);
			});
			
			$('.checkCompetenciaHabilidade').click(function() {
				$(this).parent().parent().find(".checkNivelHabilidade").attr('disabled', !($(this).attr('checked'))).attr('checked',false);
			});
			
			$('.checkCompetenciaAtitude').click(function() {
				$(this).parent().parent().find(".checkNivelAtitude").attr('disabled', !($(this).attr('checked'))).attr('checked',false);
			});
			
			$('#checkAllCompetenciaConhecimento').click(function() {
				$('.checkNivelConhecimento').attr('disabled', !($(this).attr('checked')));
				$('.checkCompetenciaConhecimento').attr('checked', $(this).attr('checked'));
			});
			
			$('#checkAllCompetenciaHabilidade').click(function() {
				$('.checkNivelHabilidade').attr('disabled', !($(this).attr('checked')));
				$('.checkCompetenciaHabilidade').attr('checked', $(this).attr('checked'));
			});
			
			$('#checkAllCompetenciaAtitude').click(function() {
				$('.checkNivelAtitude').attr('disabled', !($(this).attr('checked')));
				$('.checkCompetenciaAtitude').attr('checked', $(this).attr('checked'));
			});
			
			<#list niveisCompetenciaFaixaSalariaisSalvosConhecimento as nivelSalvo>
				var linhaConhecimento = $('tr').has('.checkCompetenciaConhecimento[value="${nivelSalvo.competenciaId}"]').has('input[type="hidden"][value="${nivelSalvo.tipoCompetencia}"]');
				linhaConhecimento.find('.checkCompetenciaConhecimento').attr('checked', 'true');
				linhaConhecimento.find('.checkNivelConhecimento').removeAttr('disabled');
				linhaConhecimento.find('.checkNivelConhecimento[value="${nivelSalvo.nivelCompetencia.id}"]').attr('checked', 'true');
			</#list>
			
			<#list niveisCompetenciaFaixaSalariaisSalvosHabilidade as nivelSalvo>
				var linhaHabilidade = $('tr').has('.checkCompetenciaHabilidade[value="${nivelSalvo.competenciaId}"]').has('input[type="hidden"][value="${nivelSalvo.tipoCompetencia}"]');
				linhaHabilidade.find('.checkCompetenciaHabilidade').attr('checked', 'true');
				linhaHabilidade.find('.checkNivelHabilidade').removeAttr('disabled');
				linhaHabilidade.find('.checkNivelHabilidade[value="${nivelSalvo.nivelCompetencia.id}"]').attr('checked', 'true');
			</#list>
			
			<#list niveisCompetenciaFaixaSalariaisSalvosAtitude as nivelSalvo>
				var linhaAtitude = $('tr').has('.checkCompetenciaAtitude[value="${nivelSalvo.competenciaId}"]').has('input[type="hidden"][value="${nivelSalvo.tipoCompetencia}"]');
				linhaAtitude.find('.checkCompetenciaAtitude').attr('checked', 'true');
				linhaAtitude.find('.checkNivelAtitude').removeAttr('disabled');
				linhaAtitude.find('.checkNivelAtitude[value="${nivelSalvo.nivelCompetencia.id}"]').attr('checked', 'true');
			</#list>
		});
		
		function enviarForm() {
			if ($('.checkCompetenciaConhecimento').size() == 0 && $('.checkCompetenciaHabilidade').size() == 0 && $('.checkCompetenciaAtitude').size() == 0)	{
				jAlert('Não existem competências cadastradas para o cargo.');
				return false;
			} else if ($('.checkNivelConhecimento').size() == 0 && $('.checkNivelHabilidade').size() == 0 && $('.checkNivelAtitude').size() == 0) {
				jAlert('Não existem níveis de competência cadastrados.');
				return false;
			}
		
			var linhasSemRadioMarcadoConhecimento = $('tr').has('.checkNivelConhecimento:enabled').not(':has(.checkNivelConhecimento:checked)');
			var linhasSemRadioMarcadoHabilidade = $('tr').has('.checkNivelHabilidade:enabled').not(':has(.checkNivelHabilidade:checked)');
			var linhasSemRadioMarcadoAtitude = $('tr').has('.checkNivelAtitude:enabled').not(':has(.checkNivelAtitude:checked)');
			
			if(linhasSemRadioMarcadoConhecimento.size() == 0 && linhasSemRadioMarcadoHabilidade.size() == 0 && linhasSemRadioMarcadoAtitude.size() == 0) {
				$('#form').submit();
				return true;
			}
				
			$('tr.even').css('background-color', '#EFEFEF');
			$('tr.odd').css('background-color', '#FFF');
		
			jAlert('Selecione os níveis para as competências indicadas.');
			linhasSemRadioMarcadoConhecimento.css('background-color', '#FFEEC2');
			linhasSemRadioMarcadoHabilidade.css('background-color', '#FFEEC2');
			linhasSemRadioMarcadoAtitude.css('background-color', '#FFEEC2');

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
		<@display.table name="niveisCompetenciaFaixaSalariaisConhecimento" id="configuracaoNivelCompetencia" class="dados">
		
			<@display.caption><div style="background-color: #999999;font-weight: bold; color: #FFF;">Conhecimento</div> </@display.caption>
			<@display.column title="<input type='checkbox' id='checkAllCompetenciaConhecimento'/> Competência" >
				<@ww.hidden name="niveisCompetenciaFaixaSalariaisConhecimento[${i}].tipoCompetencia"/>
				<input type="checkbox"  id="competenciaConhecimento_${i}" name="niveisCompetenciaFaixaSalariaisConhecimento[${i}].competenciaId" value="${configuracaoNivelCompetencia.competenciaId}" class="checkCompetenciaConhecimento" />
				<label for="competenciaConhecimento_${i}">${configuracaoNivelCompetencia.competenciaDescricao}</label>
				
				<#if configuracaoNivelCompetencia.competenciaObservacao?exists && configuracaoNivelCompetencia.competenciaObservacao != "">
					<img id="competenciaConhecimento_${i}_obs" onLoad="toolTipCompetenciaObs(${i}, '${configuracaoNivelCompetencia.competenciaObservacao?j_string?replace('\"','$#-')?replace('\'','\\\'')}')" src="<@ww.url value='/imgs/help-info.gif'/>" width='16' height='16' style='margin-left: 0px;margin-top: 0px;vertical-align: top;'/>
				</#if>
				
			</@display.column>
			
			<#list nivelCompetencias as nivel>			
				<@display.column title="${nivel.descricao}" style="width: 100px; text-align: center;">
					<input type="radio" disabled="disabled" class="checkNivelConhecimento radio" name="niveisCompetenciaFaixaSalariaisConhecimento[${i}].nivelCompetencia.id" value="${nivel.id}" />
				</@display.column>
			</#list>
			
			<#assign i = i + 1/>
		</@display.table>

		<#assign i = 0/>
		<@display.table name="niveisCompetenciaFaixaSalariaisHabilidade" id="configuracaoNivelCompetenciaHabilidade" class="dados">
		
			<@display.caption><div style="background-color: #999999;font-weight: bold; color: #FFF;">Habilidade</div> </@display.caption>
			<@display.column title="<input type='checkbox' id='checkAllCompetenciaHabilidade'/> Competência" >
				<@ww.hidden name="niveisCompetenciaFaixaSalariaisHabilidade[${i}].tipoCompetencia"/>
				<input type="checkbox"  id="competenciaHabilidade_${i}" name="niveisCompetenciaFaixaSalariaisHabilidade[${i}].competenciaId" value="${configuracaoNivelCompetenciaHabilidade.competenciaId}" class="checkCompetenciaHabilidade" />
				<label for="competenciaHabilidade_${i}">${configuracaoNivelCompetenciaHabilidade.competenciaDescricao}</label>
				
				<#if configuracaoNivelCompetenciaHabilidade.competenciaObservacao?exists && configuracaoNivelCompetenciaHabilidade.competenciaObservacao != "">
					<img id="competenciaHabilidade_${i}_obs" onLoad="toolTipCompetenciaObs(${i}, '${configuracaoNivelCompetenciaHabilidade.competenciaObservacao?j_string?replace('\"','$#-')?replace('\'','\\\'')}')" src="<@ww.url value='/imgs/help-info.gif'/>" width='16' height='16' style='margin-left: 0px;margin-top: 0px;vertical-align: top;'/>
				</#if>
				
			</@display.column>
			
			<#list nivelCompetencias as nivel>			
				<@display.column title="${nivel.descricao}" style="width: 100px; text-align: center;">
					<input type="radio" disabled="disabled" class="checkNivelHabilidade radio" name="niveisCompetenciaFaixaSalariaisHabilidade[${i}].nivelCompetencia.id" value="${nivel.id}" />
				</@display.column>
			</#list>
			
			<#assign i = i + 1/>
		</@display.table>

		<#assign i = 0/>
		<@display.table name="niveisCompetenciaFaixaSalariaisAtitude" id="configuracaoNivelCompetenciaAtitude" class="dados">
					
			<@display.caption><div style="background-color: #999999;font-weight: bold; color: #FFF;">Atitude</div> </@display.caption>
			<@display.column title="<input type='checkbox' id='checkAllCompetenciaAtitude'/> Competência" >
				<@ww.hidden name="niveisCompetenciaFaixaSalariaisAtitude[${i}].tipoCompetencia"/>
				<input type="checkbox"  id="competenciaAtitude_${i}" name="niveisCompetenciaFaixaSalariaisAtitude[${i}].competenciaId" value="${configuracaoNivelCompetenciaAtitude.competenciaId}" class="checkCompetenciaAtitude" />
				<label for="competenciaAtitude_${i}">${configuracaoNivelCompetenciaAtitude.competenciaDescricao}</label>
				
				<#if configuracaoNivelCompetenciaAtitude.competenciaObservacao?exists && configuracaoNivelCompetenciaAtitude.competenciaObservacao != "">
					<img id="competenciaAtitude_${i}_obs" onLoad="toolTipCompetenciaObs(${i}, '${configuracaoNivelCompetenciaAtitude.competenciaObservacao?j_string?replace('\"','$#-')?replace('\'','\\\'')}')" src="<@ww.url value='/imgs/help-info.gif'/>" width='16' height='16' style='margin-left: 0px;margin-top: 0px;vertical-align: top;'/>
				</#if>
				
			</@display.column>
			
			<#list nivelCompetencias as nivel>			
				<@display.column title="${nivel.descricao}" style="width: 100px; text-align: center;">
					<input type="radio" disabled="disabled" class="checkNivelAtitude radio" name="niveisCompetenciaFaixaSalariaisAtitude[${i}].nivelCompetencia.id" value="${nivel.id}" />
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
