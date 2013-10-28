<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>

<@ww.head/>
	
	<#if comTreinamento>
		<title>Colaboradores que fizeram o treinamento</title>
	<#else>
		<title>Colaboradores que não fizeram o treinamento</title>
	</#if>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript'>
		var empresaIds = new Array();
		<#if empresas?exists>
			<#list empresas as empresa>
				empresaIds.push(${empresa.id});
			</#list>
		</#if>
		
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
			AreaOrganizacionalDWR.getByEmpresas(createListArea, empresaId, empresaIds);
		}

		function createListArea(data)
		{
			addChecks('areasCheck',data);
		}
		
		function validar()
		{
			<#if comTreinamento>
				$('#inicio, #fim').css('background','#fff');
				
				if ( ($('#inicio').val() == "  /  /    " && $('#fim').val() != "  /  /    ") || ($('#inicio').val() != "  /  /    " && $('#fim').val() == "  /  /    ") ) 
				{
					jAlert("Informe as duas datas do período ou<br />deixe ambas em branco para ignorá-las.");
					$('#inicio, #fim').css('background','#ffeec2');
					return false;
				}
				
				return validaFormularioEPeriodo('form', new Array('curso'), new Array('inicio','fim'));
			<#else>
				return validaFormulario('form', new Array('curso'), null);
			</#if>
		}
		
		$(document).ready(function($)
		{
			var empresaValue = $('#empresaId').val();

			DWREngine.setAsync(false);
			
			populaArea(empresaValue);
			populaEstabelecimento(empresaValue);
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
	
	<@ww.form name="form" action="${formAction}" onsubmit="return validar()" method="POST">
		<@ww.select name="curso.id" id="curso" list="cursos" listKey="id" required="true" listValue="nome" label="Curso" headerKey="" headerValue="Selecione..."/>
		
		<#if comTreinamento>
			<label>Período de realização da turma:</label><br />
			<@ww.datepicker value="${inicio}" name="dataIni" id="inicio" cssClass="mascaraData validaDataIni" after="a" liClass="liLeft"/>
			<@ww.datepicker value="${fim}" name="dataFim" id="fim" cssClass="mascaraData validaDataFim"/>
		<#else>
			<@ww.textfield id="meses" label="Colaboradores sem treinamentos há mais de" name="qtdMesesSemCurso" onkeypress="return(somenteNumeros(event,''));" maxLength="3" after="meses" cssStyle="width:30px; text-align:right;" />			
		</#if>
		
		<@ww.select label="Empresa" name="empresaId" id="empresaId" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="-1"  onchange="populaEstabelecimento(this.value);populaArea(this.value);" disabled="!compartilharColaboradores"/>		
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList"/>
	
		<#if comTreinamento>
			<@ww.select label="Aprovado" name="aprovado" list=r"#{'T':'Todos','S':'Sim','N':'Não'}" />
		</#if>
		<@ww.hidden name="comTreinamento"/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button class="btnRelatorio"  onclick="$('form[name=form]').attr('action', '${formAction}');return validar();"></button>
		<#if comTreinamento>
			<button class="btnRelatorioExportar" onclick="$('form[name=form]').attr('action', 'relatorioColaboradorComTreinamentoXls.action');return validar();"></button>
		</#if>
	</div>
</body>
</html>