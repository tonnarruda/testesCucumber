<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Alteração de Promoção para Dissídio</title>
	
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url value="/css/formModal.css?version=${versao}"/>');
	</style>

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formModal.js?version=${versao}"/>'></script>
	
	<#assign showFilter = true/>
	<#include "../ftl/showFilterImports.ftl" />
	<#include "../ftl/mascarasImports.ftl" />
	
	<script type="text/javascript">
		$(function() {
			$('#tooltipHelp').qtip({
				content: 'A data inicial é obrigatória. Caso a data final não seja preenchida, as situações serão filtradas a partir da data inicial.'
			});
			
			$('#marcarTodos').click(function(e) {
				var marcado = $('#marcarTodos').attr('checked');
				$(".dados").find(":checkbox").attr('checked', marcado);
			});
			
			idsCheckedsInicial = $("tbody input:checkbox:checked:enabled").map(function(){
			    return parseInt($(this).val());
			});
			
			$('.pagelinks a').each(function(){
			    var pg = $(this).attr('href').match(/-p=(\d+)/);
				if(pg != null)
					var pagina = pg[1];
					
			    $(this).attr('href','javascript:;');			    
			    $(this).click(function(){pesquisar(pagina); });
			});
						
			<#if !sugerir>
				var prox = $("a[title='Próxima']");
				
				if(prox.length > 0)
				{
					jQuery.alerts.okButton = 'Sim';				
					jQuery.alerts.cancelButton = 'Não';				
					newConfirm('Registros dessa página gravados com sucesso.<br>Deseja ir para a Próxima página?', function(){pesquisar( Number($('#page').val()) + 1 );});
				}else
					jAlert('Página gravada com sucesso.');
			</#if>
		});
		
		function pesquisar(pagina)
		{
			$('#page').val(pagina);
			$("#form").attr('action', '?d-3220764-p=' + pagina);
			$("#form").submit();
		}
		
		function gravaDissidios()
		{
			$('#aplicaDissidio').val(true);
			
			//todos os marcados ao carregar a pg
			idsCheckedsFinal = $("tbody input:checkbox:checked:enabled").map(function(){
		    	return parseInt($(this).val());
			});				
			
			retiraDissidioIds = arrayDiff(idsCheckedsInicial, idsCheckedsFinal)
			aplicaDissidioIds = arrayDiff(idsCheckedsFinal, idsCheckedsInicial)
			
			if (retiraDissidioIds.length != 0 || aplicaDissidioIds.length != 0)
			{
				$(retiraDissidioIds).each(function(i, value){
					$("#form").append('<input type="hidden" name="retiraDissidioIds" value='+ value +'>');
				});

				$("#form").attr('action', '?d-3220764-p=' + $('#page').val() + '#fim');
				$("#form").submit();
			}
			else
				jAlert("Nenhuma Situação modificada nessa página.");			
		}
		
	</script>
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('dataIni','percentualDissidio'), new Array('dataIni','dataFim'), false)"/>
</head>

<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<#if dataIni?exists>
		<#assign valueDataIni = dataIni?date>
	<#else>
		<#assign valueDataIni = "">
	</#if>
	<#if dataFim?exists>
		<#assign valueDataFim = dataFim?date>
	<#else>
		<#assign valueDataFim = "">
	</#if>
	
	<@ww.form name="form" action="prepareAjusteDissidio.action" id="form" method="POST" onsubmit="${validarCampos}">

		<#include "../util/topFiltro.ftl" />
		
			<div>
				Período das situações:
				<img id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" />
			</div>
			<@ww.datepicker name="dataIni" id="dataIni" required="true" value="${valueDataIni}" cssClass="mascaraData validaDataIni" liClass="liLeft"/>
			<@ww.label value="a" liClass="liLeft"/>
			<@ww.datepicker name="dataFim" id="dataFim"  value="${valueDataFim}" cssClass="mascaraData validaDataFim"/>
			
			<br />
			Sugerir reajuste com diferença de até
			<@ww.textfield theme="simple" name="percentualDissidio" id="percentualDissidio" cssStyle="width:40px; text-align:right;" maxLength="5" onkeypress = "return(somenteNumeros(event,','));"/> 
			%*
			
			<br /><br />
			<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" filtro="true"/>
			<br />
			<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true" selectAtivoInativo="true" />
			<br />
			<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos" list="cargosCheckList" filtro="true" selectAtivoInativo="true" />
			
			<@ww.hidden name="page" id="page"/>
			<@ww.hidden name="aplicaDissidio" id="aplicaDissidio" value="false"/>
			
			<div class="buttonGroup">
				<button type="button" class="btnPesquisar grayBGE" onclick="pesquisar(1);">
			</div>
		<#include "../util/bottomFiltro.ftl" />
		
		<br/>
		
		<#assign valorAnterior = 0.0/>
		<#if historicoColaboradors?exists && 0 < historicoColaboradors?size>
			<div style="text-align:right;"><span style="padding:0 10px; background-color: #CDCD00;"></span>&nbsp Sugestão de Dissídio</div><br>
			
				<@display.table name="historicoColaboradors" id="historicoColaborador" class="dados" pagesize=30 excludedParams="*">
					
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
	
			<div class="buttonGroup">
				<button type="button" onclick="gravaDissidios();" class="btnGravar"></button>
			</div>
		</#if>	
	
	</@ww.form>
</body>
</html>