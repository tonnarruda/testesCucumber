<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>

<@ww.head/>
	
	<#if comTreinamento>
		<title>Colaboradores com treinamento</title>
	<#else>
		<title>Colaboradores sem treinamento</title>
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
				if($("#status").val() != "NRT")
					formValido = validaFormulario('form', new Array('@cursosCheck'), null);
				else
					formValido = validaFormulario('form', null, null);
			</#if>

			if (formValido)
				processando('${urlImgs}');

			return formValido;
		}
		
		$(document).ready(function($)
		{
			toggleNuncaRealizaraTreinamento();
			var empresaValue = $('#empresaId').val();

			DWREngine.setAsync(false);
			
			populaCurso(empresaValue);
			populaArea(empresaValue);
			populaEstabelecimento(empresaValue);
			
			$('#statusHelp').qtip({
				content: '<div style="text-align:justify"><b>Nunca realizou nenhum curso na empresa:</b> Listagem de colaboradores que nunca realizaram nenhum curso na empresa selecionada.' + 
														 '</p><b>Nunca realizaram os cursos selecionados:</b> Listagem por curso, informando os colaboradores que não possuem este treinamento.'+
														 '</p><b>Aprovados e Reprovados:</b> Listagem por curso, informando os colaboradores aprovados e reprovados que já realizaram o treinamento e estão sem o mesmo a há mais de "X" meses.'+
														 '</p><b>Aprovados:</b> Listagem por curso, informando os colaboradores aprovados que já realizaram o treinamento e estão sem o mesmo a há mais de "X" meses.</p>'+
														 '<b>Reprovados:</b> Listagem por curso, informando os colaboradores reprovados que já realizaram o treinamento e estão sem o mesmo a há mais de "X" meses. </div>',
				style: { width: 400 }
			});			
			$('#cursosCheckToolTipHelp').qtip({
				content: 'Será gerado um relatório individual para cada curso selecionado em um único arquivo.'
			});
			
		});
		
		function toggleNuncaRealizaraTreinamento(){
			if($("#status").val() == "ST") {
				$("#mesesSemTreinamento").hide();
				$("#divSituacao").hide();
				$("#wwgrp_cursosCheck").show();
			} else if($("#status").val() == "NRT"){
					$("#mesesSemTreinamento").hide();
					$("#divSituacao").hide();
					$("#wwgrp_cursosCheck").hide();
			}else{
				$("#mesesSemTreinamento").show();
				$("#divSituacao").show();
				$("#wwgrp_cursosCheck").show();
			}
		}
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
		<#list empresas as empresa>
			<input type="hidden" name="empresasPermitidas" value="${empresa.id}" />
		</#list>
		<#if comTreinamento>
			<label>Período de realização da turma:</label><br />
			<@ww.datepicker value="${inicio}" name="dataIni" id="inicio" cssClass="mascaraData validaDataIni" after="a" liClass="liLeft"/>
			<@ww.datepicker value="${fim}" name="dataFim" id="fim" cssClass="mascaraData validaDataFim"/>
		</#if>
		
		<@ww.select label="Empresa" name="empresaId" id="empresaId" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="-1"  onchange="populaCurso(this.value);populaEstabelecimento(this.value);populaArea(this.value);" disabled="!compartilharColaboradores" cssStyle="width: 500px;"/>
				
		<#if !comTreinamento>
			<label>Considerar colaboradores:</label>
			<img id="statusHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin: -3px 0 -2px -3px;" />
			<br />
			<@ww.select name="status" id="status" list="statusTreinamento" cssStyle="width: 500px;" onchange="toggleNuncaRealizaraTreinamento();"/>
			
			<@ww.div cssClass="div" id="mesesSemTreinamento">
				Considerar colaboradores sem treinamentos há mais de
				<@ww.textfield id="meses" theme="simple" name="qtdMesesSemCurso" onkeypress="return(somenteNumeros(event,''));" maxLength="3" cssStyle="width:30px; text-align:right;" />
				meses.
			</@ww.div>
		</#if>
		<@frt.checkListBox name="cursosCheck" id="cursosCheck" label="Cursos*" list="cursosCheckList" filtro="true" tooltipHelp="true"/>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" filtro="true"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
		<div id="divSituacao">
			<@ww.select label="Situação do colaborador" name="situacao" id="situacao" list="situacoes" cssStyle="width: 500px;"/>
		</div>
		<#if comTreinamento>
			<@ww.select name="aprovado" label="Considerar colaboradores" list="statusAprovacao" cssStyle="width: 500px;"/>
		</#if>
		
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