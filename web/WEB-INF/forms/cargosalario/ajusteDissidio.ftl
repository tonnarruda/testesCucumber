<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Considerar situações como dissídio</title>
	
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>
	
	<#include "../ftl/showFilterImports.ftl" />
	<#include "../ftl/mascarasImports.ftl" />
	
	<script type="text/javascript">
		$(function() {
			$('#marcarTodos').click(function(e) {
				var marcado = $('#marcarTodos').attr('checked');
				$(".dados").find(":checkbox").attr('checked', marcado);
			});
		});
		
		function prepareEnviarForm() {
			if ($(".dados * td").find(":checkbox:checked").size() > 0)
				$("#formHistoricos").submit();
			else
				jAlert("Nenhuma situação selecionada.");
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
			Reajustes até    
			<@ww.textfield theme="simple" name="percentualDissidio" id="percentualDissidio" cssStyle="width:40px; text-align:right;" maxLength="5" onkeypress = "return(somenteNumeros(event,','));"/> 
			%*
			
			<div class="buttonGroup">
				<input type="submit" value="" class="btnPesquisar grayBGE" onclick="${validarCampos};">
			</div>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	
	<br/>
	
	<#if historicoColaboradors?exists && 0 < historicoColaboradors?size>
		<br>
		<@ww.form name="formHistoricos" id="formHistoricos" action="setDissidio.action" validate="true" method="POST">
			<@display.table name="historicoColaboradors" id="historicoColaborador" class="dados" >
			
				<@display.column title="<input type='checkbox' id='marcarTodos'/>" style="width: 30px; text-align: center;">
					<input type="checkbox" value="${historicoColaborador.id?string?replace(".", "")?replace(",","")}" name="historicoColaboradorIds" />
				</@display.column>
			
				<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width:60px;"/>
				<@display.column property="colaborador.nome" title="Colaborador"/>
				<@display.column property="estabelecimento.nome" title="Estabelecimento"/>
				<@display.column property="areaOrganizacional.descricao" title="Área"/>
				<@display.column property="faixaSalarial.descricao" title="Cargo"/>
				<@display.column property="descricaoTipoSalario" title="Tipo" style="width:100px;"/>
				<@display.column property="salarioCalculado" title="Salário" format="{0, number, #,##0.00}" style="text-align:right; width:80px;"/>
				<@display.column property="motivoDescricao" title="Situação" />
			</@display.table>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="prepareEnviarForm();" class="btnAplicar"></button>
		</div>
	</#if>	
</body>
</html>