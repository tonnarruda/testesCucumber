<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>

<@ww.head/>
	
	<#if comTreinamento>
		<title>Colaboradores que fizeram o treinamento</title>
	<#else>
		<title>Colaboradores que não fizeram o treinamento</title>
	</#if>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CursoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	
	<style>
		.div{
			padding-top: 5px;
			padding-bottom: 5px;
		}
	</style>
	
	<script type='text/javascript'>
		var empresaIds = new Array();
		<#if empresas?exists>
			<#list empresas as empresa>
				empresaIds.push(${empresa.id});
			</#list>
		</#if>
		
		function populaCurso(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			CursoDWR.getCursosByEmpresa(createListCurso, empresaId);
		}
		
		function createListCurso(data)
		{
			addChecks('cursosCheck',data);
		}
		
		function populaEstabelecimento(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			EstabelecimentoDWR.getByEmpresas(createListEstabelecimento, empresaId, empresaIds);
		}
	
		function createListEstabelecimento(data)
		{
			addChecks('estabelecimentosCheck',data);
		}
		
		function populaArea(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresas(createListArea, empresaId, empresaIds, null);
		}

		function createListArea(data)
		{
			addChecks('areasCheck',data);
		}
		
		function validar()
		{
			var formValido = false;
			<#if comTreinamento>
				$('#inicio, #fim').css('background','#fff');
				
				if ( ($('#inicio').val() == "  /  /    " && $('#fim').val() != "  /  /    ") || ($('#inicio').val() != "  /  /    " && $('#fim').val() == "  /  /    ") ) 
				{
					jAlert("Informe as duas datas do período ou<br />deixe ambas em branco para ignorá-las.");
					$('#inicio, #fim').css('background','#ffeec2');
					return false;
				}
				
				formValido = validaFormularioEPeriodo('form', new Array('@cursosCheck'), new Array('inicio','fim'));
			<#else>
				formValido = validaFormulario('form', new Array('@cursosCheck'), null);
			</#if>

			if (formValido)
				processando('${urlImgs}');

			return formValido;
		}
		
		$(document).ready(function($)
		{
			var empresaValue = $('#empresaId').val();

			DWREngine.setAsync(false);
			
			populaCurso(empresaValue);
			populaArea(empresaValue);
			populaEstabelecimento(empresaValue);
			
			$('#cursosCheckToolTipHelp').qtip({
				content: 'Será gerado um relatório individual para cada curso selecionado em um único arquivo.'
			});
		});
	</script>
	
	<#include "../ftl/mascarasImports.ftl" />
</head>
<body>
	
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<#if dataIni?exists>
		<#assign inicio=dataIni?date />
	<#else>
		<#assign inicio="" />
	</#if>
	<#if dataFim?exists>
		<#assign fim=dataFim?date />
	<#else>
		<#assign fim="" />
	</#if>
	
	<#if comTreinamento>
		<#assign formAction = "relatorioColaboradorComTreinamento.action" />
	<#else>
		<#assign formAction = "relatorioColaboradorSemTreinamento.action" />
	</#if>
	
	<@ww.form name="form" action="${formAction}" method="POST">
		<#if comTreinamento>
			<label>Período de realização da turma:</label><br />
			<@ww.datepicker value="${inicio}" name="dataIni" id="inicio" cssClass="mascaraData validaDataIni" after="a" liClass="liLeft"/>
			<@ww.datepicker value="${fim}" name="dataFim" id="fim" cssClass="mascaraData validaDataFim"/>
		<#else>
			<@ww.div cssClass="div">
				Considerar colaboradores sem treinamentos há mais de
				<@ww.textfield id="meses" theme="simple" name="qtdMesesSemCurso" onkeypress="return(somenteNumeros(event,''));" maxLength="3" cssStyle="width:30px; text-align:right;" />
				meses.
			</@ww.div>
		</#if>

		<@ww.select label="Empresa" name="empresaId" id="empresaId" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="-1"  onchange="populaCurso(this.value);populaEstabelecimento(this.value);populaArea(this.value);" disabled="!compartilharColaboradores" cssStyle="width: 500px;"/>		
		<@frt.checkListBox name="cursosCheck" id="cursosCheck" label="Cursos*" list="cursosCheckList" filtro="true" tooltipHelp="true"/>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" filtro="true"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
		<@ww.select label="Situação do colaborador" name="situacao" id="situacao" list="situacoes" cssStyle="width: 500px;"/>
		<@ww.select name="aprovado" label="Considerar colaboradores" list="statusAprovacao" cssStyle="width: 500px;"/>
		
		<#if comTreinamento>
			<@ww.checkbox label="Exibir cargos no relatório" name="exibeCargo" id="exibeCargo" labelPosition="left"/>
			<@ww.checkbox label="Exibir carga horária efetiva" name="exibeCargaHorariaEfetiva" labelPosition="left"/>
		</#if>
		
		<@ww.hidden name="comTreinamento"/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button class="btnRelatorio" onclick="$('form[name=form]').attr('action', '${formAction}');return validar();"></button>
		<#if comTreinamento>
			<button class="btnRelatorioExportar" onclick="$('form[name=form]').attr('action', 'relatorioColaboradorComTreinamentoXls.action');return validar();"></button>
		</#if>
	</div>
</body>
</html>