<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>

<title>Turnover (rotatividade de colaboradores)</title>
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
		var empresasFormulas = [];
		<#list empresasFormulas?keys as key>
			empresasFormulas[${key}] = '${empresasFormulas.get(key)}';
		</#list>
	
		DWREngine.setAsync(false);
		
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
			populaCargo(empresaId);
			populaEstabelecimento(empresaId);
			
			$('#formula').text(empresasFormulas[empresaId]);
		});
		
		$('#empresa').change();
		
		$('#formulaHelp').qtip({
			content: '<div>A fórmula adotada pela empresa é configurada no cadastro de empresas.</div>',
			style: { width: 280 }
		});
		
		filtrarOpt();
		agruparOpt();
	});
	
	var areasMarcadasIds = new Array();
		<#if areasCheckList?exists>
			<#list areasCheckList as areasChecked>
				<#if areasChecked.selecionado>
					areasMarcadasIds.push(${areasChecked.id});
				</#if>
			</#list>
		</#if>

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
	
	function populaArea(empresaId)
	{
		DWRUtil.useLoadingMessage('Carregando...');
	
		if(empresaId && empresaId!='-1' ){
			AreaOrganizacionalDWR.getPermitidasCheckboxByEmpresas(createListArea,false, empresaId, null);
		}
		else{
			AreaOrganizacionalDWR.getPermitidasCheckboxByEmpresas(createListArea,false, null, empresaIds);
		}
	}
	
	function createListArea(data)
	{
		addChecksCheckBox('areasCheck', data, areasMarcadasIds);
		eventoArea();
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
	function agruparOpt(){
		
		var tipoAgrupamento = $('#optAgrupa').val();
		
		$('#optFiltro').hide();
		$("label[for='optFiltro']").hide()
		
		if(tipoAgrupamento === 'A') {
			$('#divAreas').show();
			$('#divCargos').hide();
			$('#listCheckBoxcargosCheck').css('background-color','#fff');
			$('#wwlbl_areasCheck').find('label').text('Áreas Organizacionais:*');
		} else if(tipoAgrupamento === 'C') {
			$('#divAreas').hide();
			$('#divCargos').show();
			$('#listCheckBoxareasCheck').css('background-color','#fff');
			$('#wwlbl_cargosCheck').find('label').text('Cargos:*');
		}
		else{
			$('#optFiltro').show();
			$("label[for='optFiltro']").show();
			$('#divAreas').show();
			$('#divCargos').hide();
			$('#wwlbl_areasCheck').find('label').text('Áreas Organizacionais:');
			$('#wwlbl_cargosCheck').find('label').text('Cargos:');
			filtrarOpt();
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
		
		if($('#optAgrupa').val()==='A'){
			return validaFormularioEPeriodoMesAno('form', new Array('@areasCheck','dataDe','dataAte'), new Array('dataDe','dataAte'));
		}
		else if($('#optAgrupa').val()==='C'){
			return validaFormularioEPeriodoMesAno('form', new Array('@cargosCheck','dataDe','dataAte'), new Array('dataDe','dataAte'));
		}
		else{
			return validaFormularioEPeriodoMesAno('form', new Array('dataDe','dataAte'), new Array('dataDe','dataAte'));
		}
	}
		
	function eventoArea()
	{
		$("input[name=areasCheck]").change(function(){
			addAreasMarcadasIds(parseInt(this.value));
			if($(this).is(":checked")){
				checarFilhos(this, true);
			}else{
				checarFilhos(this, false);
				removeAreasMarcadasIds(parseInt(this.value));
			}
			
			if($(this).attr("idareamae") != undefined)
				checarMae(this);
		});
	}
		
	function checarFilhos(areaMae, check)
	{
		var idAreaMae = $(areaMae).val();
		$("input[idareamae="+idAreaMae+"]").each(function(){
			if(check)
				$(this).attr("checked", "checked");
			else
				$(this).removeAttr("checked");
			
		}).change();
	}
	
	function checarMae(areaFilha)
	{
		var idAreaMae = $(areaFilha).attr("idareamae");
		if(idAreaMae != undefined){
			if($("input[idareamae="+idAreaMae+"]").size() == $("input[idareamae="+idAreaMae+"]:checked").size()){
				$("#checkGroupareasCheck"+idAreaMae).attr("checked", "checked");
				checarMae($("#checkGroupareasCheck"+idAreaMae));
			}else{
				$("#checkGroupareasCheck"+idAreaMae).removeAttr("checked");
				removeAreasMarcadasIds(parseInt(idAreaMae));
				checarMae($("#checkGroupareasCheck"+idAreaMae));
			}
		}
	}
	
	function addAreasMarcadasIds(valor)
	{
		if(areasMarcadasIds && areasMarcadasIds.indexOf(valor) == -1) 
			areasMarcadasIds.push(valor);
	}

	function removeAreasMarcadasIds(valor)
	{
		for(var i = 0; i < areasMarcadasIds.length ; i++) {
		    if(areasMarcadasIds[i] === valor) {
		       areasMarcadasIds.splice(i, 1);
		    }
		}
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

	<@ww.select id="optAgrupa" label="Agrupar Por" name="agruparPor" list=r"#{'S':'Sem Agrupamento','A':'Área Organizacional', 'C':'Cargo'}" onchange="agruparOpt();" />
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