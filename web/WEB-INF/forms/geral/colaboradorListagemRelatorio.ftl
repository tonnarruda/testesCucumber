<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Listagem de Colaboradores</title>

	<#assign validaDataCamposExtras = ""/>
	<#if habilitaCampoExtra>
		<#list configuracaoCampoExtras as configuracaoCampoExtra>		

			<#if configuracaoCampoExtra.nome?exists && configuracaoCampoExtra.nome == "data1">
				<#assign validaDataCamposExtras = validaDataCamposExtras + ", 'data1', 'data1Fim'"/>
			</#if>
			<#if configuracaoCampoExtra.nome?exists && configuracaoCampoExtra.nome == "data2">
				<#assign validaDataCamposExtras = validaDataCamposExtras + ", 'data2', 'data2Fim'"/>
			</#if>
			<#if configuracaoCampoExtra.nome?exists && configuracaoCampoExtra.nome == "data3">
				<#assign validaDataCamposExtras = validaDataCamposExtras + ", 'data3', 'data3Fim'"/>
			</#if>

		</#list>
	</#if>
	
	<#include "../ftl/mascarasImports.ftl" />
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EmpresaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.picklists.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.emulatedisabled.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		.grade
		{
			width: 100%;
		}
		.grade .grade_field {
			float: left;
			width: 32%;
			padding: 2px 3px;
		}
		#from_colunas,
		#colunas {
			float: left;
			width: 245px;
			height: 302px;
		}

		.pickListButtons,
		.ordenador {
			float: left;
			padding: 125px 10px;
		}
		.ordenador {
			padding-right: 0;
		}
		.ordenador img {
			display: block;
			margin-bottom: 10px;
			cursor: pointer;
		}
		.pickListButtons button {
			display: block;
			margin: 5px 0;
			border: 0;
			background-color: #FFF;
			cursor: pointer;
		}
		.pickListTo {
			margin-top: -16px;
			margin-left: 300px;
		}
		
		.saveLayout {
			float: right;
			margin-top: 10px;
			border: 0;
			cursor: pointer;
		}
		
		option[disabled] {
			background-color:#FFF;
		 	color:#AAA;
		 }
		 
		 ul#periodos { margin: 10px 0px; }
		 ul#periodos li { margin-bottom: 2px; }
		 ul#periodos li span { padding: 3px 5px; }
	</style>

	
	<script type="text/javascript">
		var ieColor = '#AAA';
		var ieBg = '#FFF';
		var msgLimiteLargura = "Os campos selecionados não podem ser adicionados ao relatório.\nUtilize o relatório em Excel.";
		var colunasInfo = ${colunasJson};

		$(document).ready(function($)
		{
			$('#tooltipHelp').qtip({
				content: 'Este filtro está relacionado ao campo <strong>"Não enviar este colaborador para o Fortes Pessoal"</strong> contido na tela de cadastro dos colaboradores.'
			});
			
			DWREngine.setAsync(false);
			$('#aviso').hide();
		
			var empresa = $('#empresa').val();
			
			mostraFiltroEnviadosParaAC();
			populaArea(empresa);
			populaEstabelecimento(empresa);
			populaCargo();
			
			$('#cargosVinculadosAreas').click(function() {
				populaCargo();
			});
			
			$('#cargosVinculadosAreas').attr('checked', true);
			
			$("#colunas").pickList({
				buttons: true,
				addText: '',
				addImage: '<@ww.url value="/imgs/proxima.gif"/>', 
				removeText: '', 
				removeImage: '<@ww.url value="/imgs/anterior.gif"/>',
				ieColor: ieColor, 
				ieBg: ieBg
			});
			
			$("#colunas, #from_colunas").dblclick(sizeOk).find("option");
			$("#b_to_colunas, #b_from_colunas").click(sizeOk);
						
			var configuracaoRelatorioDinamico = '${configuracaoRelatorioDinamico.campos}';
			
			$(configuracaoRelatorioDinamico.split(',')).each(function (){
				$("#from_colunas option[value=" + this +  "]").attr('selected', true).dblclick();
			});
			
			$('#agruparPorTempoServico').change(function() {
				var marcado = $(this).is(":checked");

				$('#periodosServico').toggle( marcado );
				$('#periodoAdmissao').toggle( !marcado );
				
				if (marcado && $('#periodos li').size() == 0)
					addPeriodo();
			});
			
			$('#agruparPorTempoServico').change();
		});


		var empresaIds = new Array();
		var colunasSizes = new Array();
		<#if empresaIds?exists>
			<#list empresaIds as empresaId>
				empresaIds.push(${empresaId});
			</#list>
		</#if>
		
		<#list colunas as coluna>
			colunasSizes.push(${coluna.size});
		</#list>
		
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
			addChecks('areaOrganizacionalsCheck',data, 'populaCargo()');
		}
	
		function populaCargo()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var empresaId = $('#empresa').val();
			
			var areasIds   = getArrayCheckeds(document.form, 'areaOrganizacionalsCheck');
			
			if ($('#cargosVinculadosAreas').is(":checked"))
			{
				if(areasIds.length == 0)
					CargoDWR.getByEmpresas(createListCargo, empresaId, empresaIds);
				else
					CargoDWR.getCargoByArea(createListCargo, areasIds, "getNomeMercadoComEmpresa", empresaId);
			}
			else 
				CargoDWR.getByEmpresas(createListCargo, empresaId, empresaIds);
		}

		function createListCargo(data)
		{
			addChecks('cargosCheck',data);
		}
		function validarCampos(action)
		{
			$('form[name=form]').attr('action', action);
		
			$("#periodos :text").css('background-color', '#FFF');
		
			if ( $('#agruparPorTempoServico').is(":checked") && ($("#periodos :text").size() < 1 || $("#periodos :text[value='']").size() > 0) )
			{
				jAlert('Informe os períodos de tempo de serviço corretamente');
				$("#periodos :text[value='']").css('background-color', '#FFEEC2');
				return false;
			}
		
			if($('#colunas option').length < 1)
			{
				alterBackground('#FFEEC2');
				jAlert("Por favor selecione os campos para impressão.");
				return false;
			}

			if ( $('#agruparPorTempoServico').is(":checked") )
				$('#dataIni, #dataFim').val('');
			else
				$('#periodos').empty();

			var firstOption = $('#colunas option:first');
			var fromOption = $('#from_colunas option[value=' + firstOption.val() + ']');
			var index = $('#from_colunas option').index(fromOption)
			var order = colunasInfo[index].orderField;
			$('#orderField').val(order);
			
			$('#colunas option').attr('selected', true);
			return validaFormulario('form', new Array(), new Array('dataIni','dataFim','naoApague' ${validaDataCamposExtras}));
		}

		var maxSize = 780;
		var espace = 4;
		function sizeOk()
		{	
			totalSize = 0;
			$("#from_colunas option:selected").each(function() 
			{
			    totalSize += colunasSizes[$('#from_colunas option').index($(this))] + espace;
			});
			
			$("#colunas option").each(function() 
			{
			    var option = $('#from_colunas option[value=' + $(this).val() + ']');
			    totalSize += colunasSizes[$('#from_colunas option').index(option)] + espace;
			});

			$("#aviso").toggle(totalSize > maxSize);
			
			if (totalSize > maxSize)
				$("#btnRelatorio").attr('class', 'btnRelatorioDesabilitado').attr('onclick','').unbind('click');
			else
				$("#btnRelatorio").attr('class', 'btnRelatorio').click(function() { return validarCampos('relatorioDinamico.action'); });
		}
		
		function next()
		{
			var sels = $("#colunas option:selected");
			var next = $(sels[sels.length-1]).next('option');
			sels.insertAfter(next);
		}
		
		function prev()	
		{
			var sels = $("#colunas option:selected");
			var prev = $(sels[0]).prev('option');
			sels.insertBefore(prev);
		}
		
		function alterBackground(cor)	
		{
			$('#colunas').css({background: cor});
			$('#from_colunas').css({background: cor});
		}
		
		function salvarLayout()	
		{
			var values = $('#colunas option').map(function () {
    			return $(this).val();
			});
			var campos = $.makeArray(values).join(',');
			
			DWRUtil.useLoadingMessage('Carregando...');
			ColaboradorDWR.updateConfiguracaoRelatorioDinamico(resultadoConfiguracaoRelatorioDinamico, campos, $('#titulo').val(), ${usuarioLogado.id});
		}
		
		function resultadoConfiguracaoRelatorioDinamico(data) 
		{
			if(data == "OK")
				jAlert("Layout do relatório salvo com sucesso.");
			else
				jAlert("Erro ao salvar layout.");
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
		
		function mostraFiltroEnviadosParaAC(){
			EmpresaDWR.isAcintegra($('#empresa').val(), function(acIntegra) {
				if(acIntegra) {
					$('#enviadosAC').show();
				} else {
					$('#enviadoParaAC').val('1');	
					$('#enviadosAC').hide();	
				}
				 
			});
		}
		
	</script>
	<#if dataIni?exists>
		<#assign valueDataIni = dataIni?date/>
	<#else>
		<#assign valueDataIni = ""/>
	</#if>

	<#if dataFim?exists>
		<#assign valueDataFim = dataFim?date/>
	<#else>
		<#assign valueDataFim = ""/>
	</#if>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="relatorioDinamico.action" onsubmit="return validarCampos();" validate="true" method="POST">
	
		<#if compartilharColaboradores>
			<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="" onchange="populaEstabelecimento(this.value);populaArea(this.value);populaCargo();mostraFiltroEnviadosParaAC()"/>
		<#else>
			<@ww.hidden id="empresa" name="empresa.id"/>
			<li class="wwgrp">
				<label>Empresa:</label><br />
				<strong><@authz.authentication operation="empresaNome"/></strong>
			</li>
		</#if>
		
		<@ww.select label="Situação" name="situacao" id="situacao" list="situacaos" cssStyle="width: 160px;"/>
		<@ww.select label="Sexo" id="sexo" name="sexo" list="sexos" cssStyle="width: 160px;" />
		<@ww.select label="Deficiência" id="deficiencia" name="deficiencia" list=r"#{'1':'Todas', '2':'Somente Deficientes', '3':'Sem Deficiência'}" cssStyle="width: 160px;"/>
		
		<div id="enviadosAC"> 
			Considerar colaboradores:<br />
			<@ww.select id="enviadoParaAC" name="enviadoParaAC" list=r"#{'1':'Enviados e Não Enviados para o Fortes Pessoal', '2':'Não Enviados para o Fortes Pessoal', '3':'Enviados para o Fortes Pessoal'}"  theme="simple"/>
			<img id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16"  /><br />
			
			<br clear="all"/>
		</div>
		
		<fieldset class="fieldsetPadrao" style="width:578px; margin-bottom: 10px;">
			<legend>Tempo de Serviço</legend>
			
			<@ww.checkbox label="Agrupar colaboradores em períodos de tempo de serviço" name="agruparPorTempoServico" id="agruparPorTempoServico" labelPosition="left"/>

			<div id="periodoAdmissao">
				<div>Período de Admissão:</div>
				<@ww.datepicker name="dataIni" id="dataIni" liClass="liLeft" value="${valueDataIni}"  cssClass="mascaraData validaDataIni"/>
				<@ww.label value="a" liClass="liLeft"/>
				<@ww.datepicker name="dataFim" id="dataFim"  value="${valueDataFim}" cssClass="mascaraData validaDataFim"/>
			</div>

			<div id="periodosServico" style="display:none;">
				<ul id="periodos"></ul>
				
				<a title="Adicionar período" href="javascript:;" onclick="addPeriodo();">
					<img src="<@ww.url includeParams="none" value="/imgs/add.png"/>" border="0" align="absMiddle" /> 
					Adicionar período
				</a>
			</div>
		</fieldset>
		
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" width="600" filtro="true"/>

		<@frt.checkListBox name="areaOrganizacionalsCheck" id="areaOrganizacionalsCheck" label="Áreas Organizacionais" list="areaOrganizacionalsCheckList" width="600" onClick="populaCargo();" filtro="true" selectAtivoInativo="true"/>
		
		<@ww.checkbox label="Exibir somente os cargos vinculados às áreas organizacionais acima." id="cargosVinculadosAreas" name="" labelPosition="left"/>
		
		<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos" list="cargosCheckList" width="600" filtro="true" selectAtivoInativo="true"/>
		
		<br />
		
		<fieldset class="fieldsetPadrao" style="width:578px; padding: 10px;">
			<legend>Configurações de impressão</legend>
			
			<@ww.textfield label="Título" id="titulo" name="configuracaoRelatorioDinamico.titulo" maxLength="100" cssStyle="width:542px;"/>
			<div class="pickListFrom">Campos disponíveis</div>
			<div class="pickListTo">Campos selecionados</div>
			
			<@ww.select theme="simple" label="" multiple="true" name="colunasMarcadas" id="colunas" list="colunas" listKey="property" listValue="name" />

			<div class="ordenador">
				<img border="0" onClick="prev();" title="Subir campo(s) selecionado(s)" src="<@ww.url value="/imgs/up.gif"/>">
				<img border="0" onClick="next();" title="Baixar campo(s) selecionado(s)" src="<@ww.url value="/imgs/down.gif"/>">
			</div>

			<img border="0" class="saveLayout" onClick="salvarLayout();" title="Salvar campo do relatório" src="<@ww.url value="/imgs/saveLayout.gif"/>">
			<div style="clear: both"></div>
			<div class="actionMessage" id="aviso">Limite de campos para o relatório em PDF foi excedido.<br>Utilize o relatório em Excel.</div>
		</fieldset>
	
		<@ww.hidden name="habilitaCampoExtra" />
		<@ww.hidden id="orderField" name="orderField" />
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio" id="btnRelatorio" onclick="return validarCampos('relatorioDinamico.action');"></button>
		<button class="btnRelatorioExportar" onclick="return validarCampos('relatorioDinamicoXLS.action');"></button>
	</div>
</body>
</html>