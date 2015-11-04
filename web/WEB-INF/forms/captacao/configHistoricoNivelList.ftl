<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>

	<title>Niveis de Competencia</title>
	
	<script type="text/javascript">
		$(function() {
			$('.configHistoricoNivel').click(function(){
				$(this).parent().parent().find(".campos").attr('disabled', !($(this).attr('checked')));
			});
			
			$('#checkAllNiveis').click(function() {
				tabela = $(this).parent().parent().parent().parent();
				tabela.find('.configHistoricoNivel').attr('checked', ($(this).attr('checked')));
				tabela.find(".campos").attr('disabled', !($(this).attr('checked')));
			});
			
			<#if configHistoricoNivels?exists && 0 < configHistoricoNivels?size>
				$('#checkAllNiveis').attr('checked', 'true');
				$('#configHistoricoNivel').find('.configHistoricoNivel').attr('checked', 'true');
				$('#configHistoricoNivel').find(".campos").attr('disabled', false);
			</#if>
		});
		
		function gerarPercentual()
		{
			percentualUnitario = 100/$('.percentual').size();
			percentualAcumulado = 0;
			$('.percentual').each(function(){
				percentualAcumulado = percentualAcumulado +  percentualUnitario;
				$(this).val(percentualAcumulado);
			});
		}
	</script>
	
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<#assign i = 0/>
	<@display.table name="configHistoricoNivels" id="configHistoricoNivel" class="dados">
		<@display.column title="<input type='checkbox' id='checkAllNiveis'/>" style="width:50px; text-align:center;" >
			<input type="checkbox" id="configHistoricoNivel_${i}" name="configHistoricoNivels[${i}].id" value="${configHistoricoNivel.id}" class="configHistoricoNivel" />
		</@display.column>
		<@display.column property="nivelCompetencia.descricao" title="Descrição"/>
		<@display.column title="Peso" style="width: 50px; text-align: center;">
			<input type="text" disabled="disabled" name="configHistoricoNivels[${i}].ordem" size="4" maxlength="4" value="${configHistoricoNivel.ordem}" class="campos" style="width:40px; text-align:right; border: 1px solid #BEBEBE;" onkeypress="return(somenteNumeros(event,''));">
		</@display.column>
		<@display.column title="Percentual Mínimo (%)" style="width: 150px; text-align: center;">
			<input type="text" disabled="disabled" name="configHistoricoNivels[${i}].percentual" size="5" maxlength="5" value="${configHistoricoNivel.percentualFormatado}" class="campos percentual" style="width:50px; text-align:right; border: 1px solid #BEBEBE;" onkeypress="return(somenteNumeros(event,','));">
		</@display.column>
		<#assign i = i + 1/>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnGravar" onclick="window.location='insert.action'"></button>
		<button class="btnDividirPercentualIgualmente" onclick="newConfirm('Tem certeza que deseja dividir o percentual mínimo igualmente?</br>Isso acarretará na alteração de todos os percentuais mínimo dos níveis de competência.', gerarPercentual());"></button>
		<button onclick="window.location='../nivelCompetenciaHistorico/list.action'" class="btnVoltar"></button>
	</div>
</body>
</html>
