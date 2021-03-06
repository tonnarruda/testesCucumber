<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Planejamento de Realinhamentos</title>
	<#assign formAction="gerarRelatorio.action"/>
	<#assign accessKey="I"/>
	<#include "../ftl/mascarasImports.ftl" />
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ReajusteDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

<script type="text/javascript">
	
	$(function() {
		<#assign visualizarBotaoCancelar=false/>
		<#if tabelaReajusteColaborador?exists  && tabelaReajusteColaborador.tipoReajuste?exists>
			selecionaTipoReajuste('${tabelaReajusteColaborador.tipoReajuste}');
			<#assign visualizarBotaoCancelar=true/>
		<#else>
			carregaTipoReajuste();
		</#if>
		<#if !verTodasAreas>
			$('#divOptFiltro').hide();
		</#if>
		
		$('#help').qtip({
			content: '<div style="text-align:justify">Ao preencher os campos serão apresentados apenas os planejamentos de realinhamento que estejam dentro do período informado.</div>',
			style: { width: 400 }
		});
		
		setDatas();
	});
	
	function setDatas()
	{
		var newDate = new Date();
		newDate.setMonth(newDate.getMonth() - 6);
		
		var seisMesesAtras = newDate.getDate() + "/"; 
		seisMesesAtras += ((newDate.getMonth() > 9) ? "" : "0") + (newDate.getMonth()+1) + "/"; 
		seisMesesAtras += newDate.getFullYear();
		
		$('#inicio').val(seisMesesAtras);
		$('#fim').val($.datepicker.formatDate('dd/mm/yy',new Date()));
		
		findRealinhamentosByPeriodo();
	}
	
	function createListIndices(data)
	{
		addChecksByCollection('indicesCheck', data);
	}
	
	function populaFaixas()
	{
		var cargosIds = getArrayCheckeds(document.forms[0],'cargosCheck');
		if (cargosIds.length == 0)
			cargosIds = [-1];
		ReajusteDWR.getFaixasByCargosDesabilitandoPorIndiceSemPendencia(createListFaixas, cargosIds);
	}
	
	function createListFaixas(data)
	{
		addChecksByCollection('faixaSalarialsCheck', data);
	}

	function filtrarOpt()
	{
		value = document.getElementById('optFiltro').value;
		if(value == "1")
		{
			document.getElementById('areaOrganizacional').style.display = "";
			document.getElementById('grupoOcupacional').style.display = "none";
		}
		else if(value == "2")
		{
			document.getElementById('areaOrganizacional').style.display = "none";
			document.getElementById('grupoOcupacional').style.display = "";
		}
	}

	function validarCampos()
	{
		if($('#optReajuste').val() == 0)
		{
			$('#optReajuste').css('background', 'rgb(255, 238, 194)');
			jAlert("Preencha os campos indicados.");
			return false;
		}
	
		return validaFormulario('form', new Array('optReajuste','optFiltro'), new Array());
	}

	function imprimir()
	{
		var qtdSelectE = qtdeChecksSelected(document.forms[0], 'estabelecimentosCheck');
		if(qtdSelectE == 0)
		{
			jAlert("Nenhum Estabelecimento selecionado.");
			return false;
		}
		else
			return validarCampos();
	}
	
	function selecionaTipoReajuste(tipoReajuste) 
	{
		$('#colaboradorReajuste').hide();
		$('#indiceReajuste').hide();
		$('#faixaReajuste').hide();
	
		if(tipoReajuste == 'C')
		{
			$('#colaboradorReajuste').show();
			filtrarOpt();
		}
		else if(tipoReajuste == 'F')
		{
			$('#faixaReajuste').show();
		}
		else if(tipoReajuste == 'I')
		{
			$('#indiceReajuste').show();
			ReajusteDWR.getIndicesDesabilitando(createListIndices);
		}
	}
	
	function carregaTipoReajuste() 
	{
		if ($('#optReajuste').val() != '') {
			ReajusteDWR.getTipoReajuste(selecionaTipoReajuste, $('#optReajuste').val());
		} else {
			selecionaTipoReajuste();
		}
	}
	
	function desablitaBotaoXls(valor)
	{
		if(valor=="true")
			$('.btnRelatorioExportar').attr('disabled', true).css('opacity', '0.2');
		else
			$('.btnRelatorioExportar').removeAttr('disabled').css('opacity', '1');
	}
	
	function findRealinhamentosByPeriodo()
	{
		ReajusteDWR.findRealinhamentosByPeriodo(repopulaListaRealinhamento, ${empresaSistema.id}, $('#inicio').val(), $('#fim').val() );
	}
	
	function repopulaListaRealinhamento(data)
	{
		<#if !tabelaReajusteColaborador?exists>
			DWRUtil.removeAllOptions("optReajuste");
			addOptionsByCollection('optReajuste', data, 'Selecione...');
		</#if>
	}

</script>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="${formAction}" onsubmit="return imprimir();" validate="true" method="POST">
		<#if tabelaReajusteColaborador?exists>
			<@ww.label label="Promoção/Reajuste" name="tabelaReajusteColaborador.nome"/>
			<@ww.hidden id="optReajuste" name="tabelaReajusteColaborador.id" value="${tabelaReajusteColaborador.id}"/>
		<#else>
			<label>Período de aplicação do realinhamento:</label>
			<img id="help" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -5px" />
			<br />
			<@ww.datepicker label="" theme="simple" value="" id="inicio" cssClass="mascaraData validaDataIni" liClass="liLeft" onchange="findRealinhamentosByPeriodo();" onblur="findRealinhamentosByPeriodo();"/>a
			<@ww.datepicker label="" theme="simple" value="" id="fim" cssClass="mascaraData validaDataFim" onchange="findRealinhamentosByPeriodo();" onblur="findRealinhamentosByPeriodo();"/>
			</br></br>
			<@ww.select id="optReajuste" label="Selecione o Planejamento"  name="tabelaReajusteColaborador.id" onchange="carregaTipoReajuste()" required="true"  list='tabelaReajusteColaboradors' headerKey="" headerValue="Selecione..."   listKey="id" listValue="nome"/>
		</#if>
		
		<@ww.div id="colaboradorReajuste">
			<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" width="600" filtro="true"/>
		
			<@ww.div id="divOptFiltro">
				<@ww.select id="optFiltro" label="Filtrar Por" name="filtro" required="true"  list=r"#{'1':'Área Organizacional', '2':'Grupo Ocupacional'}" onchange="filtrarOpt();" headerKey="1" />
			</@ww.div>
	
			<@ww.div id="areaOrganizacional">
				<@frt.checkListBox name="areaOrganizacionalsCheck" id="areaOrganizacionalsCheck" label="Áreas Organizacionais" list="areaOrganizacionalsCheckList" width="600" filtro="true" selectAtivoInativo="true"/>
			</@ww.div>
	
			<@ww.div id="grupoOcupacional">
					<@frt.checkListBox name="grupoOcupacionalsCheck" id="grupoOcupacionalsCheck" label="Grupos Ocupacionais" list="grupoOcupacionalsCheckList" width="600" filtro="true"/>
			</@ww.div>
			
			<@ww.select label="Imprimir somente totais" name="total" list=r"#{'false':'Não', 'true':'Sim'}" onchange="desablitaBotaoXls($(this).val());"/>
		
			<@ww.checkbox label="Exibir estabelecimento e área organizacional" id="exibirAreaEstabelecimento" name="exibirAreaEstabelecimento" labelPosition="left"/>
			<@ww.checkbox label="Exibir observação" id="exibirObservacao" name="exibirObservacao" labelPosition="left"/>
		</@ww.div>
		
		<@ww.div id="indiceReajuste">
			<@frt.checkListBox name="indicesCheck" id="indicesCheck" label="Índices" list="indicesCheckList" width="600" />
		</@ww.div>

		<@ww.div id="faixaReajuste">
			<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos" list="cargosCheckList" onClick="populaFaixas();" width="600"/>
			<@frt.checkListBox name="faixaSalarialsCheck" id="faixaSalarialsCheck" label="Faixa Salariais" list="faixaSalarialsCheckList" width="600" />
		</@ww.div>
	

	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio"  onclick="$('form[name=form]').attr('action', '${formAction}');validarCampos();"></button>
		<button class="btnRelatorioExportar" onclick="$('form[name=form]').attr('action', 'gerarRelatorioXls.action');validarCampos();"></button>
	
		<#if visualizarBotaoCancelar>
			<button onclick="window.location='<@ww.url includeParams="none" value="/cargosalario/tabelaReajusteColaborador/list.action"/>'" class="btnCancelar" accesskey="V">
			</button>
		</#if>
	</div>
</body>
</html>