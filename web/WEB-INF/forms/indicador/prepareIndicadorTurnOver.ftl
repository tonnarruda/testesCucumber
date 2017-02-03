<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>

<title>Turnover (rotatividade de colaboradores)</title>
<#include "../ftl/mascarasImports.ftl" />

<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>

<script type='text/javascript'>
	$(document).ready(function($)
	{
		var empresasFormulas = [];
		<#list empresasFormulas?keys as key>
			empresasFormulas[${key}] = '${empresasFormulas.get(key)}';
		</#list>
	
		dwr.engine.setAsync(false);
		
		$('#agruparPorTempoServico').change(function() {
			var marcado = $(this).is(":checked");
			$('#periodosServico').toggle( marcado );

			if (marcado && $('#periodos li').size() == 0)
				addPeriodo();
		});
		
		$('#agruparPorTempoServico').change();
		
		$('#empresa').change(function() {
			var empresaId = $(this).val();
			
			populaArea(empresaId);
			populaEstabelecimento(empresaId);
			populaCargo(empresaId);
			
			$('#formula').text(empresasFormulas[empresaId]);
		});
		
		$('#empresa').change();
		
		$('#formulaHelp').qtip({
			content: '<div>A fórmula adotada pela empresa é configurada no cadastro de empresas.</div>',
			style: { width: 280 }
		});
	});

	function populaEstabelecimento(empresaId){
		dwr.util.useLoadingMessage('Carregando...');
		EstabelecimentoDWR.getByEmpresa(empresaId, createListEstabelecimento);
	}

	function createListEstabelecimento(data){
		addChecks('estabelecimentosCheck',data);
	}
	
	function populaCargo(empresaId){
		dwr.util.useLoadingMessage('Carregando...');
		CargoDWR.getByEmpresa(empresaId, createListCargo);
	}

	function createListCargo(data){
		addChecks('cargosCheck',data);
	}
	
	function populaArea(empresaId){
		dwr.util.useLoadingMessage('Carregando...');
		AreaOrganizacionalDWR.getByEmpresa(empresaId, createListArea);
	}

	function createListArea(data){
		addChecks('areasCheck',data);
	}
	
	function filtrarOpt(){
		value =	document.getElementById('optFiltro').value;
		if(value == "1") {
			document.getElementById('divAreas').style.display = "";
			document.getElementById('divCargos').style.display = "none";
		} else if(value == "2") {
			document.getElementById('divAreas').style.display = "none";
			document.getElementById('divCargos').style.display = "";
		}
	}
	
	function delPeriodo(item)
	{
		$(item).parent().parent().remove();
	}
	
	function addPeriodo()
	{
		var periodo = '<li><span>';
		periodo += '<img title="Remover período" onclick="delPeriodo(this)" src="<@ww.url includeParams="none" value="/imgs/remove.png"/>" border="0" align="absMiddle" style="cursor:pointer;" />&nbsp;';
		periodo += '<input type="text" name="tempoServicoIni" id="tempoServicoIni" style="width:30px; text-align:right;" maxlength="4" onkeypress="return somenteNumeros(event,\'\');"/>';
		periodo += '&nbsp;a&nbsp;';
		periodo += '<input type="text" name="tempoServicoFim" id="tempoServicoFim" style="width:30px; text-align:right;" maxlength="4" onkeypress="return somenteNumeros(event,\'\');"/>';
		periodo += '&nbsp;meses</span></li>';
	
		$('#periodos').append(periodo);
		$('#tempoIni, #tempoFim').val('');
	}
	
	function enviarForm()
	{
		if ( $('#agruparPorTempoServico').is(":checked") )
		{
			var valida = true;
			var foco;
			$("input[name='tempoServicoIni'],input[name='tempoServicoFim']").each(function(i, item) {
				if ( !$(this).val() ) {
					valida = false;
					$(this).css('background-color', '#FFEEC2');
				} else
					$(this).css('background-color', '#FFFFFF');
			});
			
			if (!valida) {
				jAlert("Preencha os períodos corretamente");
				return false;
			}
		}
	
		return validaFormularioEPeriodoMesAno('form', new Array('dataDe','dataAte'), new Array('dataDe','dataAte'));
	}
</script>
</head>
<body>

<@ww.actionerror />
<@ww.actionmessage />

<@ww.form name="form" action="turnOver.action" validate="true" method="POST">
	
	<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" />
	Fórmula adotada <img id="formulaHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -5px" />: <span id="formula"></span><br /><br />

	<div>Período (Mês/Ano)*:</div>
	<@ww.textfield name="dataDe" id="dataDe" required="true"  cssClass="mascaraMesAnoData validaDataIni" liClass="liLeft"/>
	<@ww.label value="a" liClass="liLeft"/>
	<@ww.textfield name="dataAte" id="dataAte" required="true" cssClass="mascaraMesAnoData validaDataFim"/>
	<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" filtro="true"/>

	<@ww.select id="optFiltro" label="Filtrar Por" name="filtrarPor" list=r"#{'1':'Área Organizacional', '2':'Cargo'}" onchange="filtrarOpt();"/>
	
	<div id="divAreas">
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
	</div>
	<div id="divCargos" style="display:none;">
		<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos" list="cargosCheckList" filtro="true" selectAtivoInativo="true"/>
	</div>
	
	<@frt.checkListBox name="vinculosCheck" id="vinculosCheck" label="Colocação" list="vinculosCheckList" height="105" filtro="true"/>
	
	<@ww.checkbox label="Exibir colaboradores agrupados por tempo de serviço" name="agruparPorTempoServico" id="agruparPorTempoServico" labelPosition="left"/>
	<div id="periodosServico" style="display:none;">
		<ul id="periodos"></ul>
		
		<a title="Adicionar período" href="javascript:;" onclick="addPeriodo();" style="text-decoration:none;">
			<img src="<@ww.url includeParams="none" value="/imgs/add.png"/>" border="0" align="absMiddle" /> 
			Adicionar período
		</a>
	</div>
	<br />
</@ww.form>

<div class="buttonGroup">
	<button class="btnRelatorio"  onclick="$('form[name=form]').attr('action', 'turnOver.action');enviarForm();"></button>
	<button class="btnRelatorioExportar" onclick="$('form[name=form]').attr('action', 'turnOverXls.action');enviarForm();"></button>
</div>

</body>
</html>