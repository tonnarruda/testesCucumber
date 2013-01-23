<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Relatório de Planejamento de Realinhamentos</title>
	<#assign formAction="gerarRelatorio.action"/>
	<#assign accessKey="I"/>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ReajusteDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

<script type="text/javascript">
	
	$(function() {
		<#assign visualizarBotaoCancelar=false/>
		<#if tabelaReajusteColaborador?exists  && tabelaReajusteColaborador.tipoReajuste?exists>
			selecionaTipoReajuste('${tabelaReajusteColaborador.tipoReajuste}');
			<#assign visualizarBotaoCancelar=true/>
		<#else>
			selecionaTipoReajuste();
		</#if>
	});
	
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

	function validarCampos2()
	{
		<#if tabelaReajusteColaborador?exists && tabelaReajusteColaborador.tipoReajuste == 'C'>
			<#if tabelaReajusteColaborador?exists>
				return validaFormulario('form', new Array('optFiltro'), null)
			<#else>
				return validaFormulario('form', new Array('optReajuste','optFiltro'), null)
			</#if>
		<#else>	
			return validaFormulario('form', null, null)
		</#if>
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
			return validarCampos2();
	}
	
	function selecionaTipoReajuste(tipoReajuste) 
	{
		$('#colaboradorReajuste').hide();
		$('#indiceReajuste').hide();
		$('#faixaReajuste').hide();
	
		if(tipoReajuste == 'C')
		{
			$('#colaboradorReajuste').show();
			document.getElementById('grupoOcupacional').style.display = "none";
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

</script>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="${formAction}" onsubmit="return imprimir();" validate="true" method="POST">
		<#if tabelaReajusteColaborador?exists>
			<@ww.label label="Promoção/Reajuste" name="tabelaReajusteColaborador.nome"/>
			<@ww.hidden name="tabelaReajusteColaborador.id"/>
		<#else>
			<@ww.select id="optReajuste" label="Selecione o Planejamento"  name="tabelaReajusteColaborador.id" onchange="carregaTipoReajuste()" required="true"  list='tabelaReajusteColaboradors' headerKey="" headerValue="Selecione..."   listKey="id" listValue="nome"/>
		</#if>
		
		<@ww.div id="colaboradorReajuste">
			<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" width="600" />
		
			<@ww.select id="optFiltro" label="Filtrar Por" name="filtro" required="true"  list=r"#{'1':'Área Organizacional', '2':'Grupo Ocupacional'}" onchange="filtrarOpt();" headerKey="1"/>
	
			<@ww.div id="areaOrganizacional">
				<@frt.checkListBox name="areaOrganizacionalsCheck" id="areaOrganizacionalsCheck" label="Áreas Organizacionais" list="areaOrganizacionalsCheckList" width="600" />
			</@ww.div>
	
			<@ww.div id="grupoOcupacional">
				<@frt.checkListBox name="grupoOcupacionalsCheck" id="grupoOcupacionalsCheck" label="Grupos Ocupacionais" list="grupoOcupacionalsCheckList" width="600" />
			</@ww.div>
			
			<@ww.select label="Imprimir somente totais" name="total" list=r"#{'false':'Não', 'true':'Sim'}" />
		
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
		<button class="btnRelatorio" onclick="return validarCampos2();document.form.submit();" accesskey="${accessKey}">
		</button>
	
		<#if visualizarBotaoCancelar>
			<button onclick="window.location='<@ww.url includeParams="none" value="/cargosalario/tabelaReajusteColaborador/list.action"/>'" class="btnCancelar" accesskey="V">
			</button>
		</#if>
	</div>
</body>
</html>