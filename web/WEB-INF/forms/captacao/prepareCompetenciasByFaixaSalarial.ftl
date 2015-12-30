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
		
		function clicked(check, classe){
			check.parent().parent().find("." + classe).attr('disabled', !(check.attr('checked'))).attr('checked',false);
			check.parent().parent().find(".peso").attr('disabled', !(check.attr('checked')));
		}
		
		function clickedAll(check, classeNivel, classeCompetencia){
			$('.' + classeNivel).attr('disabled', !(check.attr('checked')));
			$('.' + classeCompetencia).attr('checked', check.attr('checked'));
			$('.' + classeCompetencia).parent().parent().find(".peso").each(function(){
				$(this).attr('disabled', !(check.attr('checked')));
			});
		}
		
		function marca(classeCompetencia, classeNivel, nivelSalvoCompetenciaId, nivelSalvoTipoCompetencia, nivelSalvoNivelCompetenciaId){
			var linha = $('tr').has('.' + classeCompetencia + '[value=' + nivelSalvoCompetenciaId + ']').has('input[type="hidden"][value='+ nivelSalvoTipoCompetencia +']');
			linha.find('.' + classeCompetencia).attr('checked', 'true');
			linha.find('.' + classeNivel).removeAttr('disabled');
			linha.find('.' + classeNivel + '[value=' + nivelSalvoNivelCompetenciaId + ']').attr('checked', 'true');
			linha.find('.peso').removeAttr('disabled');
		}
		
		$(function() {
			$('.checkCompetenciaConhecimento').click(function(){
				clicked ($(this), 'checkNivelConhecimento');
			});
			
			$('.checkCompetenciaHabilidade').click(function() {
				clicked ($(this), 'checkNivelHabilidade');
			});
			
			$('.checkCompetenciaAtitude').click(function() {
				clicked ($(this), 'checkNivelAtitude');
			});
			
			$('#checkAllCompetenciaConhecimento').click(function() {
				clickedAll($(this), 'checkNivelConhecimento', 'checkCompetenciaConhecimento');
			});
			
			$('#checkAllCompetenciaHabilidade').click(function() {
				clickedAll($(this), 'checkNivelHabilidade', 'checkCompetenciaHabilidade');
			});
			
			$('#checkAllCompetenciaAtitude').click(function() {
				clickedAll($(this), 'checkNivelAtitude', 'checkCompetenciaAtitude');
			});
			
			<#if niveisCompetenciaFaixaSalariaisSalvosConhecimento?exists>
				<#list niveisCompetenciaFaixaSalariaisSalvosConhecimento as nivelSalvo>
					marca('checkCompetenciaConhecimento', 'checkNivelConhecimento', ${nivelSalvo.competenciaId}, '${nivelSalvo.tipoCompetencia}', ${nivelSalvo.nivelCompetencia.id});
				</#list>
			</#if>
			
			<#if niveisCompetenciaFaixaSalariaisSalvosHabilidade?exists>
				<#list niveisCompetenciaFaixaSalariaisSalvosHabilidade as nivelSalvo>
					marca('checkCompetenciaHabilidade', 'checkNivelHabilidade', ${nivelSalvo.competenciaId}, '${nivelSalvo.tipoCompetencia}', ${nivelSalvo.nivelCompetencia.id});
				</#list>
			</#if>
			
			<#if niveisCompetenciaFaixaSalariaisSalvosAtitude?exists>
				<#list niveisCompetenciaFaixaSalariaisSalvosAtitude as nivelSalvo>
					marca('checkCompetenciaAtitude', 'checkNivelAtitude', ${nivelSalvo.competenciaId}, '${nivelSalvo.tipoCompetencia}', ${nivelSalvo.nivelCompetencia.id});
				</#list>
			</#if>
			
			<#if configuracaoNivelCompetenciaFaixaSalarial?exists && configuracaoNivelCompetenciaFaixaSalarial.id?exists>
				$(":checkbox").attr("disabled","disabled");
			<#else>
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
				$('tr.even').css('background-color', '#EFEFEF');
				$('tr.odd').css('background-color', '#FFF');
			
				var linhasSemRadioMarcado = $('tr').has('.radio:enabled').not(':has(.radio:checked)');
				
				var	checkCompetencia = $('tr').has('.checkCompetencia:enabled');
				<#if edicao>
					checkCompetencia = $('td').has('input[type=checkbox]:disabled').parent();
				</#if>
				
				var existeCampoSemPeso = false;
				checkCompetencia.find(".peso").each(function(){
					if(!$(this).val()){
						$(this).parent().parent().css('background-color', '#FFEEC2');
						existeCampoSemPeso = true;
					}
				});
				
				if(!existeCampoSemPeso && linhasSemRadioMarcado.size() == 0){
					$('#form').submit();
					return true;
				}
			
				linhasSemRadioMarcado.css('background-color', '#FFEEC2');
				jAlert('Confira os pesos e os níveis para as competências indicadas.');

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
		<@ww.hidden name="configuracaoNivelCompetenciaFaixaSalarial.faixaSalarial.descricao" value="${faixaSalarial.descricao}"/>
		
		<#if nivelCompetenciaHistoricoId?exists>
			<@ww.hidden name="configuracaoNivelCompetenciaFaixaSalarial.nivelCompetenciaHistorico.id" value="${nivelCompetenciaHistoricoId}"/>
		</#if>
		
		<@ww.textfield label="A partir de" name="configuracaoNivelCompetenciaFaixaSalarial.data" value="${data}" id="data" cssClass="mascaraData" disabled="true"/>
		<@ww.hidden name="configuracaoNivelCompetenciaFaixaSalarial.data" value="${data}"/>
		<br />
		
		<#assign i = 0/>
		<@display.table name="niveisCompetenciaFaixaSalariaisConhecimento" id="configuracaoNivelCompetencia" class="dados">
		
			<@display.caption><div style="background-color: #999999;font-weight: bold; color: #FFF;">Conhecimento</div> </@display.caption>
			<@display.column title="<input type='checkbox' id='checkAllCompetenciaConhecimento'/> Competência" >
				<@ww.hidden name="niveisCompetenciaFaixaSalariaisConhecimento[${i}].tipoCompetencia"/>
				<input type="checkbox" id="competenciaConhecimento_${i}" name="niveisCompetenciaFaixaSalariaisConhecimento[${i}].competenciaId" value="${configuracaoNivelCompetencia.competenciaId}" class="checkCompetenciaConhecimento checkCompetencia" />
				<label for="competenciaConhecimento_${i}">${configuracaoNivelCompetencia.competenciaDescricao}</label>
				
				<#if edicao>
					<@ww.hidden name="niveisCompetenciaFaixaSalariaisConhecimento[${i}].competenciaId" value="${configuracaoNivelCompetencia.competenciaId}"/>
				</#if>
				<#if configuracaoNivelCompetencia.competenciaObservacao?exists && configuracaoNivelCompetencia.competenciaObservacao != "">
					<img id="competenciaConhecimento_${i}_obs" onLoad="toolTipCompetenciaObs(this.id, '${configuracaoNivelCompetencia.competenciaObservacao?j_string?replace('\"','$#-')?replace('\'','\\\'')}')" src="<@ww.url value='/imgs/help-info.gif'/>" width='16' height='16' style='margin-left: 0px;margin-top: 0px;vertical-align: top;'/>
				</#if>
				
			</@display.column>
				
				
			<@display.column title="Peso" style="width: 50px; text-align: center;">
				<#if !edicao>
					<input type="text" disabled="disabled" name="niveisCompetenciaFaixaSalariaisConhecimento[${i}].pesoCompetencia" size="4" maxlength="4" value="${configuracaoNivelCompetencia.pesoCompetencia}" class="peso" style="width:40px; text-align:right; border: 1px solid #BEBEBE;" onkeypress="return(somenteNumeros(event,''));">
				<#else>
					${configuracaoNivelCompetencia.pesoCompetencia}
					<@ww.hidden name="niveisCompetenciaFaixaSalariaisConhecimento[${i}].pesoCompetencia" value="${configuracaoNivelCompetencia.pesoCompetencia}"/>
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
				<input type="checkbox"  id="competenciaHabilidade_${i}" name="niveisCompetenciaFaixaSalariaisHabilidade[${i}].competenciaId" value="${configuracaoNivelCompetenciaHabilidade.competenciaId}" class="checkCompetenciaHabilidade checkCompetencia" />
				<label for="competenciaHabilidade_${i}">${configuracaoNivelCompetenciaHabilidade.competenciaDescricao}</label>
				
				<#if edicao>
					<@ww.hidden name="niveisCompetenciaFaixaSalariaisHabilidade[${i}].competenciaId" value="${configuracaoNivelCompetenciaHabilidade.competenciaId}"/>
				</#if>
				<#if configuracaoNivelCompetenciaHabilidade.competenciaObservacao?exists && configuracaoNivelCompetenciaHabilidade.competenciaObservacao != "">
					<img id="competenciaHabilidade_${i}_obs" onLoad="toolTipCompetenciaObs(this.id, '${configuracaoNivelCompetenciaHabilidade.competenciaObservacao?j_string?replace('\"','$#-')?replace('\'','\\\'')}')" src="<@ww.url value='/imgs/help-info.gif'/>" width='16' height='16' style='margin-left: 0px;margin-top: 0px;vertical-align: top;'/>
				</#if>
				
			</@display.column>
			
			<@display.column title="Peso" style="width: 50px; text-align: center;">
				<#if !edicao>
					<input type="text" disabled="disabled" name="niveisCompetenciaFaixaSalariaisHabilidade[${i}].pesoCompetencia" size="4" maxlength="4" value="${configuracaoNivelCompetenciaHabilidade.pesoCompetencia}" class="peso" style="width:40px; text-align:right; border: 1px solid #BEBEBE;" onkeypress="return(somenteNumeros(event,''));">
				<#else>
					${configuracaoNivelCompetenciaHabilidade.pesoCompetencia}
					<@ww.hidden name="niveisCompetenciaFaixaSalariaisHabilidade[${i}].pesoCompetencia" value="${configuracaoNivelCompetenciaHabilidade.pesoCompetencia}"/>
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
				<input type="checkbox"  id="competenciaAtitude_${i}" name="niveisCompetenciaFaixaSalariaisAtitude[${i}].competenciaId" value="${configuracaoNivelCompetenciaAtitude.competenciaId}" class="checkCompetenciaAtitude checkCompetencia" />
				<label for="competenciaAtitude_${i}">${configuracaoNivelCompetenciaAtitude.competenciaDescricao}</label>
				
				<#if edicao>
					<@ww.hidden name="niveisCompetenciaFaixaSalariaisAtitude[${i}].competenciaId" value="${configuracaoNivelCompetenciaAtitude.competenciaId}"/>
				</#if>
				<#if configuracaoNivelCompetenciaAtitude.competenciaObservacao?exists && configuracaoNivelCompetenciaAtitude.competenciaObservacao != "">
					<img id="competenciaAtitude_${i}_obs" onLoad="toolTipCompetenciaObs(this.id, '${configuracaoNivelCompetenciaAtitude.competenciaObservacao?j_string?replace('\"','$#-')?replace('\'','\\\'')}')" src="<@ww.url value='/imgs/help-info.gif'/>" width='16' height='16' style='margin-left: 0px;margin-top: 0px;vertical-align: top;'/>
				</#if>
				
			</@display.column>

			<@display.column title="Peso" style="width: 50px; text-align: center;">
				<#if !edicao>
					<input type="text" disabled="disabled" name="niveisCompetenciaFaixaSalariaisAtitude[${i}].pesoCompetencia" size="4" maxlength="4" value="${configuracaoNivelCompetenciaAtitude.pesoCompetencia}" class="peso" style="width:40px; text-align:right; border: 1px solid #BEBEBE;" onkeypress="return(somenteNumeros(event,''));">
				<#else>
					${configuracaoNivelCompetenciaAtitude.pesoCompetencia}
					<@ww.hidden name="niveisCompetenciaFaixaSalariaisAtitude[${i}].pesoCompetencia" value="${configuracaoNivelCompetenciaAtitude.pesoCompetencia}"/>
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
