<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Responder Avaliação de Desempenho</title>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/perguntasAvaliacao.js"/>'></script>
	
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
						linha.find('.checkNivel[value="${nivelSalvo.nivelCompetencia.id}"]').parent().css('border','2px solid #99ccff');
					</#list>
				</#if>
				
				<#list niveisCompetenciaFaixaSalariaisSugeridos as nivelSugerido>
					var linhaSugerida = $('tr').has('.checkCompetencia[value="${nivelSugerido.competenciaId}"]').has('input[type="hidden"][value="${nivelSugerido.tipoCompetencia}"]');
					linhaSugerida.find('.checkNivel[value="${nivelSugerido.nivelCompetencia.id}"]').parent().css('background-color', '#ececec');
				</#list>
			</#if>
		});
		
		function enviarForm()
		{
			var reavaliar = $('#reavaliarCompetenciasColaborador').is(':checked');
		
			var linhasSemRadioMarcado = $('tr').has('.checkNivel:enabled').not(':has(.checkNivel:checked)');
			if (!reavaliar || linhasSemRadioMarcado.size() == 0)
			{
				validaRespostas(null, null, true, true, false, false, true);
				return true;
			}
				
			$('tr.even').css('background-color', '#E4F0FE');
			$('tr.odd').css('background-color', '#FFF');
		
			jAlert('Selecione os níveis para as competências indicadas.');
			linhasSemRadioMarcado.css('background-color', '#FFEEC2');

			return false;
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
			
			<#if colaboradorQuestionario.avaliacao.avaliarCompetenciasCargo>
				<br />
				<fieldset>
					<legend>
						<@ww.checkbox id="reavaliarCompetenciasColaborador" name="reavaliarCompetenciasColaborador" theme="simple"/>
						<label for="reavaliarCompetenciasColaborador">Reavaliar as Competências do Colaborador para o Cargo</label>
					</legend>
				
					<br />
					
					<@ww.datepicker label="Data" id="data" name="configuracaoNivelCompetenciaColaborador.data" value="${data}" required="true" cssClass="mascaraData"/>
				
					<div id="legendas">
						<span style='background-color: #ececec;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Nível de Competência exigido para o Cargo/Faixa Salarial
						<br /><br />
						<span style='border: 2px solid #99ccff;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Nível de Competência do Colaborador
						<br /><br />
						<span style='background-color: #ececec; border: 2px solid #99ccff;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Nível de Competência do Colaborador coincide com o nível exigido para o Cargo/Faixa Salarial
					</div>
		
					<br /><br />
					
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
				</fieldset>
			</#if>
			
			<@ww.token/>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="enviarForm()" class="btnGravar"></button>
	<#else>
		<div class="buttonGroup">
	</#if>
			<button class="btnCancelar" onclick="window.location='avaliacaoDesempenhoQuestionarioList.action?avaliacaoDesempenho.id=${colaboradorQuestionario.avaliacaoDesempenho.id}'"></button>		
		</div>
</body>
</html>