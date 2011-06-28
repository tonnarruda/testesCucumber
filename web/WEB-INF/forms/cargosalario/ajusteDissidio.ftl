<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Ajuste de Situação (Dissídio)</title>
	
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>
	
	<#assign showFilter = true/>
	<#include "../ftl/showFilterImports.ftl" />
	<#include "../ftl/mascarasImports.ftl" />
	
	<script type="text/javascript">
		$(function() {
			$('#marcarTodos').click(function(e) {
				var marcado = $('#marcarTodos').attr('checked');
				$(".dados").find(":checkbox").attr('checked', marcado);
			});
			
			$('.pagelinks a').each(function(){
				$(this).attr('href', $(this).attr('href').replace('sugerir=false','sugerir=true'))
			});
			
			idsCheckedsInicial = $("tbody input:checkbox:checked:enabled").map(function(){
			    return parseInt($(this).val());
			});
			
			<#if !sugerir>
				var prox = $("a[title='Próxima']");
				
				if(prox.length > 0)
				{
					jQuery.alerts.okButton = 'Sim';				
					jQuery.alerts.cancelButton = 'Não';				
					newConfirm('Registros dessa página gravados com sucesso.<br>Deseja ir para a Próxima página?', function(){window.location=prox.attr('href');});
				}else
					jAlert('Página gravada com sucesso.');
			</#if>
		});
		
		function prepareEnviarForm() {
			//todos os marcados ao carregar a pg
			idsCheckedsFinal = $("tbody input:checkbox:checked:enabled").map(function(){
		    	return parseInt($(this).val());
			});				
			
			retiraDissidioIds = arrayDiff(idsCheckedsInicial, idsCheckedsFinal)
			aplicaDissidioIds = arrayDiff(idsCheckedsFinal, idsCheckedsInicial)
			
			if (retiraDissidioIds.length != 0 || aplicaDissidioIds.length != 0)
			{
				pagina = 1;
				var pg = window.location.toString().match(/-p=(\d+)/);
				if(pg != null)
					pagina = pg[1];

				$("#formHistoricos").append('<input type="hidden" name="page" value='+ pagina +'>');
				
				$(retiraDissidioIds).each(function(i, value){
					$("#formHistoricos").append('<input type="hidden" name="retiraDissidioIds" value='+ value +'>');
				});
				
				$("#formHistoricos").submit();
			}
			else
				jAlert("Nenhuma Situação modificada nessa página.");
		}
	</script>
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign validarCampos="return validaFormulario('form', new Array('dataBase','percentualDissidio'), new Array('dataBase'))"/>
</head>

<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<#if dataBase?exists>
			<#assign data = dataBase?date>
		<#else>
			<#assign data = "">
	</#if>
	

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="form" action="prepareAjusteDissidio.action" id="form"  onsubmit="${validarCampos}" method="POST">
			<@ww.datepicker label="Situações a partir de" name="dataBase" id="dataBase" required="true" value="${data}" cssClass="mascaraData"/>
			<br />
			Sugerir reajuste com diferença de até
			<@ww.textfield theme="simple" name="percentualDissidio" id="percentualDissidio" cssStyle="width:40px; text-align:right;" maxLength="5" onkeypress = "return(somenteNumeros(event,','));"/> 
			%*
			
			<div class="buttonGroup">
				<input type="submit" value="" class="btnPesquisar grayBGE" onclick="${validarCampos};">
			</div>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	
	<br/>
	
	<#assign valorAnterior = 0.0/>
	<#if historicoColaboradors?exists && 0 < historicoColaboradors?size>
		<div style="text-align:right;"><span style="padding:0 10px; background-color: #CDCD00;"></span>&nbsp Sugestão de Dissídio</div><br>
		
		<@ww.form name="formHistoricos" id="formHistoricos" action="setDissidio.action" method="POST">
			<@ww.hidden name="dataBase" value="${data}"/>
			<@ww.hidden name="percentualDissidio"/>
			<@ww.hidden name="sugerir"/>
			
			<@display.table name="historicoColaboradors" id="historicoColaborador" class="dados" pagesize=30 >
				
				<#assign destacar = ""/>
				<#assign checked=""/>
				<#assign disabled=""/>
				
				<#if sugerir && historicoColaborador.diferencaSalarialEmPorcentam?exists &&  historicoColaborador.diferencaSalarialEmPorcentam != 0 && historicoColaborador.diferencaSalarialEmPorcentam <= percentualDissidio>
					<#assign destacar = "destacar"/>
					<#assign checked="checked"/>
				</#if>
				
				<#if  historicoColaborador.motivo?exists>
					<#if historicoColaborador.motivo == 'D'>
						<#assign destacar = ""/>
						<#assign checked="checked"/>
					</#if>
					<#if historicoColaborador.motivo == 'C'>
						<#assign disabled = "disabled"/>
					</#if>
				</#if>
				
				<@display.column title="<input type='checkbox' id='marcarTodos'/>" style="width: 30px; text-align: center;" class="${destacar}">
					<input type="checkbox" ${checked} ${disabled} value="${historicoColaborador.id?string?replace(".", "")?replace(",","")}" name="historicoColaboradorIds" />
				</@display.column>
			
				<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width:60px;" class="${destacar}"/>
				<@display.column property="colaborador.nome" title="Colaborador" class="${destacar}"/>
				<@display.column property="descricaoTipoSalario" title="Tipo" style="width:100px;" class="${destacar}"/>
				<@display.column property="salario" title="Salário" format="{0, number, #,##0.00}" style="text-align:right; width:80px;" class="salario ${destacar}"/>
				<@display.column property="salarioVariavel" title="Salário Anterior" format="{0, number, #,##0.00}" style="text-align:right; width:80px;" class="salario ${destacar}" />

				<@display.column title="Diferença" style="text-align:right; width:80px;" class="salario ${destacar}">
					<#if historicoColaborador.salario?exists>
						<#if historicoColaborador.salarioVariavel == 0.00>
							- 
						<#else>
							${historicoColaborador.salario - historicoColaborador.salarioVariavel}
						</#if>
					<#else>
						(sem hist.)
					</#if>
				</@display.column>

				<@display.column title="Diferença (%)" style="text-align:right; width:80px;" class="salario ${destacar}">
					<#if historicoColaborador.diferencaSalarialEmPorcentam?exists>
						${historicoColaborador.diferencaSalarialEmPorcentam}
					<#else>
						-
					</#if>
				</@display.column>

				<@display.column property="motivoDescricao" title="Situação"  class="${destacar}"/>
			</@display.table>
			<a name="fim">
		</@ww.form>

		<div class="buttonGroup">
			<button type="button" onclick="prepareEnviarForm();" class="btnGravar"></button>
		</div>
	</#if>	
</body>
</html>