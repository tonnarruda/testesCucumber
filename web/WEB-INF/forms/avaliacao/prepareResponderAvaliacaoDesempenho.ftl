<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Responder Avaliação de Desempenho</title>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/perguntasAvaliacao.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/nivelCompetencia.js"/>"></script>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		.dados th:first-child { text-align: left; padding-left: 5px; }
	</style>
	
	<script type="text/javascript">
		$(function() {
			<#if colaboradorQuestionario.avaliacao.avaliarCompetenciasCargo>
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
						var nivelColaborador = linha.find('.checkNivel[value="${nivelSalvo.nivelCompetencia.id}"]');
						
						linha.find('.checkCompetencia').attr('checked', 'checked');
						linha.find('.checkNivel').removeAttr('disabled');
						linha.find('.ordem').val(${nivelSalvo.nivelCompetencia.ordem});
						
						nivelColaborador.parent().css('background-color','#E4F0FE');
						<#if colaboradorQuestionario.respondida>
							nivelColaborador.attr('checked','checked');
						</#if>
						
						<#list niveisCompetenciaFaixaSalariais as nivelSugerido>
							if('${nivelSugerido.competenciaId}' == '${nivelSalvo.competenciaId}' && '${nivelSugerido.tipoCompetencia}' == '${nivelSalvo.tipoCompetencia}' && '${nivelSugerido.nivelCompetencia.id}' == '${nivelSalvo.nivelCompetencia.id}'){
								linha.find('.checkNivel[value="${nivelSalvo.nivelCompetencia.id}"]').parent().css('background-color','#A4E2DB');
							}
						</#list>

					</#list>
				</#if>
			</#if>
		});
		
		function enviarForm()
		{
			var linhasSemRadioMarcado = $('tr').has('.checkNivel:enabled').not(':has(.checkNivel:checked)');
			if (linhasSemRadioMarcado.size() == 0)
			{
				validaRespostas(null, null, true, true, false, false, true);
				return true;
			}
				
			$('tr.even').css('background-color', '#EFEFEF');
			$('tr.odd').css('background-color', '#FFF');
		
			jAlert('Selecione os níveis para as competências indicadas.');
			linhasSemRadioMarcado.css('background-color', '#FFEEC2');

			return false;
		}
		
		function setOrdem(i, ordem)
		{
			$('#ordem_' + i).val(ordem);
		}
	</script>
	
	<#if configuracaoNivelCompetenciaColaborador?exists && configuracaoNivelCompetenciaColaborador.data?exists>
		<#assign data=configuracaoNivelCompetenciaColaborador.data?date />
	<#else>
		<#assign data="" />
	</#if>
	
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<b>${colaboradorQuestionario.avaliacaoDesempenho.titulo}</b><br/>
	<b>Avaliador: ${avaliador.nome}</b><br/>
	<b>Avaliado: ${colaborador.nome}</b>
	<#if colaboradorQuestionario.avaliacao?exists && colaboradorQuestionario.avaliacao.cabecalho?exists>
		<pre><h4>${colaboradorQuestionario.avaliacao.cabecalho}</h4></pre>
	</#if>
	
	<#if perguntas?exists && 0 < perguntas?size>
		<@ww.form name="form" action="responderAvaliacaoDesempenho.action" method="POST">
			<#include "includePerguntasAvaliacao.ftl" />
			<@ww.hidden name="avaliacaoDesempenho.id" />
			<@ww.hidden name="colaboradorQuestionario.avaliacaoDesempenho.id" />
			<@ww.hidden name="colaboradorQuestionario.avaliador.id" />
			<@ww.hidden name="colaboradorQuestionario.avaliacaoDesempenho.permiteAutoAvaliacao"/>
			<@ww.hidden name="colaboradorQuestionario.avaliacao.exibeResultadoAutoavaliacao"/>
			<@ww.hidden name="colaboradorQuestionario.avaliacao.avaliarCompetenciasCargo"/>
			<@ww.hidden name="autoAvaliacao" />
			
			<#if colaboradorQuestionario.avaliacao.avaliarCompetenciasCargo>
				<br />
				<fieldset>
					<legend>Reavaliar as Competências do Colaborador para o Cargo</legend><br />
					
					<div id="legendas">
						<span style='background-color: #BFC0C3;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Nível de Competência exigido para o Cargo/Faixa Salarial
						<br /><br />
						<span style='background-color: #E4F0FE;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Nível de Competência do Colaborador
						<br /><br />
						<span style='background-color: #A4E2DB;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Nível de Competência do Colaborador é igual ao nível exigido para o Cargo/Faixa Salarial
					</div>
		
					<br /><br />
					
					<#assign i = 0/>
					<@display.table name="niveisCompetenciaFaixaSalariais" id="configuracaoNivelCompetencia" class="dados">
						<@display.column title="<input type='checkbox' id='checkAllCompetencia'/> Competência" >
							<@ww.hidden name="niveisCompetenciaFaixaSalariais[${i}].tipoCompetencia"/>
							<#-- não utilizar decorator no hidden abaixo -->
							<input type="hidden" name="niveisCompetenciaFaixaSalariais[${i}].nivelCompetencia.ordem" id="ordem_${i}" class="ordem" value=""/>
							
							<input type="checkbox" id="competencia_${i}" name="niveisCompetenciaFaixaSalariais[${i}].competenciaId" value="${configuracaoNivelCompetencia.competenciaId}" class="checkCompetencia" />
							<label for="competencia_${i}">${configuracaoNivelCompetencia.competenciaDescricao}</label>
							
							<#if configuracaoNivelCompetencia.competenciaObservacao?exists && configuracaoNivelCompetencia.competenciaObservacao != "">
								<img id="competencia_${i}_obs" onLoad="toolTipCompetenciaObs(${i}, '${configuracaoNivelCompetencia.competenciaObservacao?j_string?replace('\"','$#-')?replace('\'','\\\'')}')" src="<@ww.url value='/imgs/help-info.gif'/>" width='16' height='16' style='margin-left: 0px;margin-top: 0px;vertical-align: top;'/>
							</#if>
							
						</@display.column>
						
						<#list nivelCompetencias as nivel>
						
							<#if configuracaoNivelCompetencia?exists && configuracaoNivelCompetencia.nivelCompetencia?exists && configuracaoNivelCompetencia.nivelCompetencia.id?exists && configuracaoNivelCompetencia.nivelCompetencia.id == nivel.id>
								<#assign class="nivelFaixa"/>
								<#assign bgcolor="background-color: #BFC0C3;"/>
							<#else>
								<#assign class=""/>
								<#assign bgcolor=""/>
							</#if>
									
							<@display.column title="${nivel.descricao}" style="${bgcolor} width: 100px; text-align: center;" class="${class}">
								<input type="radio" disabled="disabled" class="checkNivel radio" name="niveisCompetenciaFaixaSalariais[${i}].nivelCompetencia.id" value="${nivel.id}" onchange="setOrdem(${i}, ${nivel.ordem})"/>
							</@display.column>
						</#list>
						
						<#assign i = i + 1/>
						
					</@display.table>
					
					<#if niveisCompetenciaFaixaSalariais?exists && niveisCompetenciaFaixaSalariais?size == 0>
						<ul style="height:20px; margin-top: 5px; margin-left: 20px;">
							Não existem níveis de competências configurados para o cargo atual do colaborador.
						</ul>
					</#if>
					
				</fieldset>
			</#if>
			
			<@ww.token/>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="enviarForm()" class="btnGravar"></button>
			<#if autoAvaliacao>
				<button class="btnCancelar" onclick="window.location='../modelo/minhasAvaliacoesList.action'"></button>
			<#else>
				<button class="btnCancelar" onclick="window.location='avaliacaoDesempenhoQuestionarioList.action?avaliacaoDesempenho.id=${colaboradorQuestionario.avaliacaoDesempenho.id}'"></button>		
			</#if>
		</div>
	<#else>
		<div class="buttonGroup">
			<button class="btnCancelar" onclick="window.location='avaliacaoDesempenhoQuestionarioList.action?avaliacaoDesempenho.id=${colaboradorQuestionario.avaliacaoDesempenho.id}'"></button>		
		</div>
	</#if>
</body>
</html>