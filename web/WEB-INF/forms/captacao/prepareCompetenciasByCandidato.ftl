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
			
			<#list niveisCompetenciaFaixaSalariaisSalvos as nivelSalvo>
				var linha = $('tr').has('.checkCompetencia[value="${nivelSalvo.competenciaId}"]').has('input[type="hidden"][value="${nivelSalvo.tipoCompetencia}"]');
				linha.find('.checkCompetencia').attr('checked', 'true');
				linha.find('.checkNivel').removeAttr('disabled');
				linha.find('.checkNivel[value="${nivelSalvo.nivelCompetencia.id}"]').attr('checked', 'true');
			</#list>
		});
		
		function enviarForm()
		{
		 	if ($('.checkNivel').size() == 0)
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
				
			$('tr.even').css('background-color', '#EFEFEF');
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
	
	
	<div id="legendas" style="float:right;">
		<span style='background-color: #ececec;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Níveis de Competência exigidos para o Cargo/Faixa Salarial
	</div>
	
	<b>Candidato:</b> ${candidato.nome}
	
	<div style="clear: both;"></div><br />
	
	<@ww.form name="form" id="form" action="../../captacao/nivelCompetencia/saveCompetenciasByCandidato.action" method="POST">
		<@ww.hidden name="candidato.id"/>
		<@ww.hidden name="faixaSalarial.id"/>
		<@ww.hidden name="solicitacao.id"/>
				
		<#assign i = 0/>
		<@display.table name="niveisCompetenciaFaixaSalariais" id="configuracaoNivelCompetencia" class="dados">
		
			<@display.column title="<input type='checkbox' id='checkAllCompetencia'/> Competência" >
				<@ww.hidden name="niveisCompetenciaFaixaSalariais[${i}].tipoCompetencia"/>
				<input type="checkbox" id="competencia_${i}" name="niveisCompetenciaFaixaSalariais[${i}].competenciaId" value="${configuracaoNivelCompetencia.competenciaId}" class="checkCompetencia" />
				<label for="competencia_${i}">${configuracaoNivelCompetencia.competenciaDescricao}</label>
			</@display.column>
			
			<#list nivelCompetencias as nivel>
				<#if configuracaoNivelCompetencia?exists && configuracaoNivelCompetencia.nivelCompetencia?exists && configuracaoNivelCompetencia.nivelCompetencia.id?exists && configuracaoNivelCompetencia.nivelCompetencia.id == nivel.id>
					<#assign class="nivelFaixa"/>
					<#assign bgcolor="background-color: #ececec;"/>
				<#else>
					<#assign class=""/>
					<#assign bgcolor=""/>
				</#if>
					
				<#-- <@display.column title="${nivel.descricao}" style="width: 100px; text-align: center;"> -->
				<@display.column title="${nivel.descricao}" style="${bgcolor} width: 100px; text-align: center;" class="${class}">
					<input type="radio" disabled="disabled" class="checkNivel radio" name="niveisCompetenciaFaixaSalariais[${i}].nivelCompetencia.id" value="${nivel.id}" />
				</@display.column>
			</#list>
			
			<#assign i = i + 1/>
		</@display.table>
	</@ww.form>
	
	<div class="buttonGroup">
		<button class="btnGravar" onclick="enviarForm();"></button>
		<button class="btnVoltar" onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}'"></button>
	</div>
</body>
</html>
