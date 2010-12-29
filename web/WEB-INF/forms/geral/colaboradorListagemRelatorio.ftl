<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
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
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.picklists.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.emulatedisabled.js"/>'></script>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
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
	</style>

	
	<script type="text/javascript">
		var ieColor = '#AAA';
		var ieBg = '#FFF';
		var msgLimiteLargura = "Os campos selecionados não podem ser adicionados ao relatório.\nMotivo: Largura máxima excedida.";
		var colunasInfo = ${colunasJson};

		jQuery(document).ready(function($)
		{
			var empresa = jQuery('#empresa').val();
			
			populaArea(empresa);
			populaEstabelecimento(empresa);
			

			$("#colunas").pickList({
				buttons: true,
				addText: '',
				addImage: '<@ww.url value="/imgs/proxima.gif"/>', 
				removeText: '', 
				removeImage: '<@ww.url value="/imgs/anterior.gif"/>',
				ieColor: ieColor, 
				ieBg: ieBg
			});
			
			$("#from_colunas option").dblclick(function(e) {
				alterBackground('#FFF');

				if($(this).attr("disabled"))
					e.stopPropagation();
				
				if(!sizeOk())
				{
					e.stopPropagation();
					alert(msgLimiteLargura);
				}
			});

			$("#b_to_colunas").unbind('click');
			$("#b_to_colunas").click(function(e) {
				e.preventDefault(); 
				alterBackground('#FFF');
				selecionaCampos();
				return false;
			});
			
			var configuracaoRelatorioDinamico = '${configuracaoRelatorioDinamico.campos}';
			
			$(configuracaoRelatorioDinamico.split(',')).each(function (){
				$("#from_colunas option[value=" + this +  "]").attr('selected', true);
				selecionaCampos();
			});
			
		});

		function selecionaCampos()
		{			
			if(sizeOk())
			{
				var from = 'from_colunas';
				var to = 'colunas';
				
				var dest = jQuery("#"+to)[0];

				jQuery("#"+from+" option:selected").clone().each(function() {
					if (this.disabled == true) return
					jQuery(this)
					.appendTo(dest)
					.attr("selected", false);
				});
				
				jQuery("#"+from+" option:selected")
					.attr("selected", false)
					.attr("disabled", true)
				
				if (jQuery.fn.obviouslyDisabled)
		     		jQuery("#"+from).obviouslyDisabled({textColor: ieColor, bgColor: ieBg});
			}
			else
			{				 
				alert(msgLimiteLargura);
			}
		}

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
			AreaOrganizacionalDWR.getByEmpresas(createListArea, empresaId, empresaIds);
		}

		function createListArea(data)
		{
			addChecks('areaOrganizacionalsCheck',data);
		}
	
		function validarCampos()
		{
			if(jQuery('#colunas option').length < 1)
			{
				alterBackground('#FFEEC2');
				alert("Por favor selecione os campos para impressão.");
			}
			else
			{
				var firstOption = jQuery('#colunas option:first');
				var fromOption = jQuery('#from_colunas option[value=' + firstOption.val() + ']');
				var index = jQuery('#from_colunas option').index(fromOption)
				var order = colunasInfo[index].orderField;
				jQuery('#orderField').val(order);
				
				jQuery('#colunas option').attr('selected', true);
				return validaFormulario('form', new Array(), new Array('naoApague' ${validaDataCamposExtras}));
			}
		}

		var maxSize = 780;
		var espace = 4;
		function sizeOk()
		{	
			var totalSize = 0;
			jQuery("#from_colunas option:selected").each(function() 
			{
			    totalSize += colunasSizes[jQuery('#from_colunas option').index(jQuery(this))] + espace;
			});
			
			jQuery("#colunas option").each(function() 
			{
			    var option = jQuery('#from_colunas option[value=' + jQuery(this).val() + ']');
			    totalSize += colunasSizes[jQuery('#from_colunas option').index(option)] + espace;
			});

			return (totalSize <= maxSize);
		}
		
		function next()
		{
			var sels = jQuery("#colunas option:selected");
			var next = jQuery(sels[sels.length-1]).next('option');
			sels.insertAfter(next);
		}
		
		function prev()	
		{
			var sels = jQuery("#colunas option:selected");
			var prev = jQuery(sels[0]).prev('option');
			sels.insertBefore(prev);
		}
		
		function alterBackground(cor)	
		{
			jQuery('#colunas').css({background: cor});
			jQuery('#from_colunas').css({background: cor});
		}
		
		function salvarLayout()	
		{
			var values = jQuery('#colunas option').map(function () {
    			return jQuery(this).val();
			});
			
			var campos = jQuery.makeArray(values).join(',');
			jQuery.get('<@ww.url value="/geral/configuracaoRelatorioDinamico/update.action?campos="/>' + campos + '&titulo=' + jQuery('#titulo').val(), function(data) {
				if(data == "OK")
					alert("Layout do relatório salvo com sucesso.");
				else
					alert("Erro ao salvar layout.");
			});
		}
		
	</script>


</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="relatorioDinamico.action" onsubmit="return validarCampos();" validate="true" method="POST">
	
		<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="" onchange="populaEstabelecimento(this.value);populaArea(this.value);"/>
		
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" width="600" />

		<@frt.checkListBox name="areaOrganizacionalsCheck" id="areaOrganizacionalsCheck" label="Áreas Organizacionais" list="areaOrganizacionalsCheckList" width="600" />
		
		<fieldset class="fieldsetPadrao" style="width:578px; padding: 10px; padding-top: 0">
			<ul>
				<legend>Configurações de impressão</legend><br>

				<@ww.textfield label="Título" id="titulo" name="configuracaoRelatorioDinamico.titulo" maxLength="100" cssStyle="width:542px;"/>
				<div class="pickListFrom">Campos disponíveis</div>
				<div class="pickListTo">Campos selecionados</div>
				
				<@ww.select theme="simple" label="" multiple="true" name="colunasMarcadas" id="colunas" list="colunas" listKey="property" listValue="name" />

				<div class="ordenador">
					<img border="0" onClick="prev();" title="Subir campo(s) selecionado(s)" src="<@ww.url value="/imgs/up.gif"/>">
					<img border="0" onClick="next();" title="Baixar campo(s) selecionado(s)" src="<@ww.url value="/imgs/down.gif"/>">
				</div>
				
				<img border="0" class="saveLayout" onClick="salvarLayout();" title="Salvar layout do relatório" src="<@ww.url value="/imgs/saveLayout.gif"/>">
				<div style="clear: both"></div>
			</ul>
		</fieldset>
	
		<@ww.hidden name="habilitaCampoExtra" />
		<@ww.hidden id="orderField" name="orderField" />
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio" onclick="return validarCampos();"></button>
	</div>
</body>
</html>