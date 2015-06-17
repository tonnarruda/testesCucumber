<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		
		.dados th:first-child { text-align: left; padding-left: 5px; }
	</style>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CompetenciaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

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
			<#if niveisCompetenciaFaixaSalariaisSalvosConhecimento?exists>
				<#list niveisCompetenciaFaixaSalariaisSalvosConhecimento as nivelSalvo>
					var linhaConhecimento = $('tr').has('.checkCompetenciaConhecimento[value="${nivelSalvo.competenciaId}"]').has('input[type="hidden"][value="${nivelSalvo.tipoCompetencia}"]');
					linhaConhecimento.find('.checkCompetenciaConhecimento').attr('checked', 'true');
					linhaConhecimento.find('.checkNivelConhecimento').removeAttr('disabled');
					linhaConhecimento.find('.checkNivelConhecimento[value="${nivelSalvo.nivelCompetencia.id}"]').attr('checked', 'true');
				</#list>
			</#if>
			
			<#if niveisCompetenciaFaixaSalariaisSalvosHabilidade?exists>
				<#list niveisCompetenciaFaixaSalariaisSalvosHabilidade as nivelSalvo>
					var linhaHabilidade = $('tr').has('.checkCompetenciaHabilidade[value="${nivelSalvo.competenciaId}"]').has('input[type="hidden"][value="${nivelSalvo.tipoCompetencia}"]');
					linhaHabilidade.find('.checkCompetenciaHabilidade').attr('checked', 'true');
					linhaHabilidade.find('.checkNivelHabilidade').removeAttr('disabled');
					linhaHabilidade.find('.checkNivelHabilidade[value="${nivelSalvo.nivelCompetencia.id}"]').attr('checked', 'true');
				</#list>
			</#if>
			
			<#if niveisCompetenciaFaixaSalariaisSalvosAtitude?exists>
				<#list niveisCompetenciaFaixaSalariaisSalvosAtitude as nivelSalvo>
					var linhaAtitude = $('tr').has('.checkCompetenciaAtitude[value="${nivelSalvo.competenciaId}"]').has('input[type="hidden"][value="${nivelSalvo.tipoCompetencia}"]');
					linhaAtitude.find('.checkCompetenciaAtitude').attr('checked', 'true');
					linhaAtitude.find('.checkNivelAtitude').removeAttr('disabled');
					linhaAtitude.find('.checkNivelAtitude[value="${nivelSalvo.nivelCompetencia.id}"]').attr('checked', 'true');
				</#list>
			</#if>
			
			<#if configuracaoNivelCompetenciaFaixaSalarial?exists && configuracaoNivelCompetenciaFaixaSalarial.id?exists>
				$('#data').attr("disabled","disabled");
				$('#data_button').hide();
				$(":checkbox").attr("disabled","disabled");
			<#else>
				$('#data').removeAttr("disabled");
				$('#data_button').show();
				$(":checkbox").removeAttr("disabled");
			</#if>
		});
		
		function toolTipCompetenciaObs(elementoId, obs)
		{
			$("#"+elementoId).qtip({
				content: '<div style="text-align:justify">' + obs.split('$#-').join("\"") + '</div>',
				style: { width: 400 }
			});
		}
		
		function enviarForm() 
		{		
			if (!validaFormulario('form', new Array('data'), new Array('data'), true))
			{
				jAlert('Informe uma data correta.');
				return false;
			}
			
			if ($('.checkCompetenciaConhecimento').size() == 0 && $('.checkCompetenciaHabilidade').size() == 0 && $('.checkCompetenciaAtitude').size() == 0)	{
				jAlert('Não existem competências cadastradas para o cargo.');
				return false;
			} else if ($('.checkNivelConhecimento').size() == 0 && $('.checkNivelHabilidade').size() == 0 && $('.checkNivelAtitude').size() == 0) {
				jAlert('Não existem níveis de competência cadastrados.');
				return false;
			}
		
			if(!$('#configuracaoNivelCompetenciaFaixaSalarialId').val())
				CompetenciaDWR.findVinculosCompetencia(pendeciasCompetencias, ${faixaSalarial.id}, $('#data').val());
			else
				pendeciasCompetencias();
		}
				
		function pendeciasCompetencias(data)
		{
			if(data){   
				jAlert(data);
			}else{
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
		}
	
	</script>

	<title>Competências da Faixa Salarial</title>
			
	<#if configuracaoNivelCompetenciaFaixaSalarial?exists && configuracaoNivelCompetenciaFaixaSalarial.data?exists>
		<#assign data = configuracaoNivelCompetenciaFaixaSalarial.data?date/>
	<#else>
		<#assign data = "  /  /    "/>
	</#if>
	
	<#include "../ftl/mascarasImports.ftl" />
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<p><b>Cargo:</b> ${faixaSalarial.cargo.nome} &nbsp;&nbsp;&nbsp; <b>Faixa:</b> ${faixaSalarial.nome}</p>
	
	<@ww.form name="form" id="form" action="saveCompetenciasByFaixaSalarial.action" method="POST">
		<@ww.hidden name="faixaSalarial.id"/>
		<@ww.hidden name="configuracaoNivelCompetenciaFaixaSalarial.id" id="configuracaoNivelCompetenciaFaixaSalarialId"/>
		<@ww.hidden name="configuracaoNivelCompetenciaFaixaSalarial.faixaSalarial.id" value="${faixaSalarial.id}"/>
		
		<@ww.datepicker label="A partir de" name="configuracaoNivelCompetenciaFaixaSalarial.data" value="${data}" id="data" cssClass="mascaraData" required="true"/>
		<#if edicao>
			<@ww.hidden name="configuracaoNivelCompetenciaFaixaSalarial.data" value="${data}"/>
		</#if>
		<br />
		
		<#assign i = 0/>
		<@display.table name="niveisCompetenciaFaixaSalariaisConhecimento" id="configuracaoNivelCompetencia" class="dados">
		
			<@display.caption><div style="background-color: #999999;font-weight: bold; color: #FFF;">Conhecimento</div> </@display.caption>
			<@display.column title="<input type='checkbox' id='checkAllCompetenciaConhecimento'/> Competência" >
				<@ww.hidden name="niveisCompetenciaFaixaSalariaisConhecimento[${i}].tipoCompetencia"/>
				<input type="checkbox" id="competenciaConhecimento_${i}" name="niveisCompetenciaFaixaSalariaisConhecimento[${i}].competenciaId" value="${configuracaoNivelCompetencia.competenciaId}" class="checkCompetenciaConhecimento" />
				<label for="competenciaConhecimento_${i}">${configuracaoNivelCompetencia.competenciaDescricao}</label>
				
				<#if edicao>
					<@ww.hidden name="niveisCompetenciaFaixaSalariaisConhecimento[${i}].competenciaId" value="${configuracaoNivelCompetencia.competenciaId}"/>
				</#if>
				<#if configuracaoNivelCompetencia.competenciaObservacao?exists && configuracaoNivelCompetencia.competenciaObservacao != "">
					<img id="competenciaConhecimento_${i}_obs" onLoad="toolTipCompetenciaObs(this.id, '${configuracaoNivelCompetencia.competenciaObservacao?j_string?replace('\"','$#-')?replace('\'','\\\'')}')" src="<@ww.url value='/imgs/help-info.gif'/>" width='16' height='16' style='margin-left: 0px;margin-top: 0px;vertical-align: top;'/>
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
				
				<#if edicao>
					<@ww.hidden name="niveisCompetenciaFaixaSalariaisHabilidade[${i}].competenciaId" value="${configuracaoNivelCompetenciaHabilidade.competenciaId}"/>
				</#if>
				<#if configuracaoNivelCompetenciaHabilidade.competenciaObservacao?exists && configuracaoNivelCompetenciaHabilidade.competenciaObservacao != "">
					<img id="competenciaHabilidade_${i}_obs" onLoad="toolTipCompetenciaObs(this.id, '${configuracaoNivelCompetenciaHabilidade.competenciaObservacao?j_string?replace('\"','$#-')?replace('\'','\\\'')}')" src="<@ww.url value='/imgs/help-info.gif'/>" width='16' height='16' style='margin-left: 0px;margin-top: 0px;vertical-align: top;'/>
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
				
				<#if edicao>
					<@ww.hidden name="niveisCompetenciaFaixaSalariaisAtitude[${i}].competenciaId" value="${configuracaoNivelCompetenciaAtitude.competenciaId}"/>
				</#if>
				<#if configuracaoNivelCompetenciaAtitude.competenciaObservacao?exists && configuracaoNivelCompetenciaAtitude.competenciaObservacao != "">
					<img id="competenciaAtitude_${i}_obs" onLoad="toolTipCompetenciaObs(this.id, '${configuracaoNivelCompetenciaAtitude.competenciaObservacao?j_string?replace('\"','$#-')?replace('\'','\\\'')}')" src="<@ww.url value='/imgs/help-info.gif'/>" width='16' height='16' style='margin-left: 0px;margin-top: 0px;vertical-align: top;'/>
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
		<button class="btnVoltar" onclick="window.location='../../captacao/nivelCompetencia/listCompetenciasFaixaSalarial.action?faixaSalarial.id=${faixaSalarial.id}'"></button>
	</div>
</body>
</html>
