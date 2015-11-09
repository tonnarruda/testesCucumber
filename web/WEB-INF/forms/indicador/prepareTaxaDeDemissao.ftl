<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>

<title>Taxa de Demissão</title>
<#include "../ftl/mascarasImports.ftl" />

<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>

<script type='text/javascript'>
	$(document).ready(function($)
	{
		DWREngine.setAsync(false);
		
		$('#empresa').change(function() {
			var empresaId = $(this).val();
			
			populaArea(empresaId);
			populaCargo(empresaId);
			populaEstabelecimento(empresaId);
		});
		
		$('#empresa').change();
	});

	function populaEstabelecimento(empresaId){
		DWRUtil.useLoadingMessage('Carregando...');
		EstabelecimentoDWR.getByEmpresa(createListEstabelecimento, empresaId);
	}

	function createListEstabelecimento(data){
		addChecks('estabelecimentosCheck',data);
	}
	
	function populaCargo(empresaId){
		DWRUtil.useLoadingMessage('Carregando...');
		CargoDWR.getByEmpresa(createListCargo, empresaId);
	}

	function createListCargo(data){
		addChecks('cargosCheck',data);
	}
	
	function populaArea(empresaId){
		DWRUtil.useLoadingMessage('Carregando...');
		AreaOrganizacionalDWR.getByEmpresa(createListArea, empresaId);
	}

	function createListArea(data){
		addChecks('areasCheck',data);
	}
	
	function enviarForm()
	{
		return validaFormularioEPeriodoMesAno('form', new Array('dataDe','dataAte'), new Array('dataDe','dataAte'));
	}
	
	function filtrarOpt()
	{
		value =	document.getElementById('optFiltro').value;
		if(value == "1") {
			document.getElementById('divAreas').style.display = "";
			document.getElementById('divCargos').style.display = "none";
		} else if(value == "2") {
			document.getElementById('divAreas').style.display = "none";
			document.getElementById('divCargos').style.display = "";
		}
	}
</script>
</head>
<body>

<@ww.actionerror />
<@ww.actionmessage />

<@ww.form name="form" action="taxaDeDemissao.action" validate="true" method="POST">
	
	<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" />

	<div>Período (Mês/Ano)*:</div>
	<@ww.textfield name="dataDe" id="dataDe" required="true"  cssClass="mascaraMesAnoData validaDataIni" liClass="liLeft"/>
	<@ww.label value="a" liClass="liLeft"/>
	<@ww.textfield name="dataAte" id="dataAte" required="true" cssClass="mascaraMesAnoData validaDataFim"/>

	<br/>Fórmula: ${formulaTaxaDemissao}<br/><br/>

	<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" filtro="true"/>
	
	<@ww.select id="optFiltro" label="Filtrar Por" name="filtrarPor" list=r"#{'1':'Área Organizacional', '2':'Cargo'}" onchange="filtrarOpt();"/>
	
	<div id="divAreas">
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
	</div>
	<div id="divCargos" style="display:none;">
		<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos" list="cargosCheckList" filtro="true" selectAtivoInativo="true"/>
	</div>
	
	<@frt.checkListBox name="vinculosCheck" id="vinculosCheck" label="Colocação" list="vinculosCheckList" height="105" filtro="true"/>
	
</@ww.form>

<div class="buttonGroup">
	<button class="btnRelatorio" onclick="$('form[name=form]').attr('action','taxaDeDemissao.action');enviarForm();"></button>
	<button class="btnRelatorioExportar" onclick="$('form[name=form]').attr('action','taxaDeDemissaoXls.action');enviarForm();"></button>
</div>

</body>
</html>