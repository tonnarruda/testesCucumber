<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>

<@ww.head/>

	<title>Investimentos de T&D por Colaborador</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/autoCompleteFortes.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
	
	<style type="text/css">
	    @import url('<@ww.url includeParams="none" value="/css/fortes.css"/>');
	    @import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
	    @import url('<@ww.url includeParams="none" value="/css/jquery-ui/redmond.css"/>');
    </style>
	
	<script type='text/javascript'>
		var urlFind = "<@ww.url includeParams="none" value="/geral/colaborador/findJson.action"/>";
		
		$(function() {
			
			$("#colaborador").autocomplete({
				source: ajaxData(urlFind),				 
				minLength: 3,
				select: function( event, ui ) { 
					$("#colaboradorId").val(ui.item.id); 
				}
			}).data( "autocomplete" )._renderItem = renderData;
			
			$('#colaborador').focus(function() {
			    $(this).select();
			});	
		});
	</script>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign validarCampos="return validaFormulario('form', new Array('inicio','fim','colaborador'), new Array('inicio','fim'))"/>
	
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
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" action="imprimirRelatorioInvestimentoPorColaborador.action" onsubmit="${validarCampos}" method="POST">

		<@ww.datepicker label="Turma iniciada entre" required="true" value="${inicio}" name="dataIni" id="inicio" cssClass="mascaraData validaDataIni" after="e" liClass="liLeft"/>
		<@ww.datepicker label="" value="${fim}" name="dataFim" id="fim" cssClass="mascaraData validaDataFim"/>

		<@ww.select id="realizada" label="Turmas Realizadas" name="realizada" list=r"#{'T':'Todas','S':'Sim','N':'Não'}" />
		
		<@ww.textfield label="Colaborador (Nome, Nome Comercial, Matrícula ou CPF)" name="" id="colaborador" cssStyle="width: 650px;"/>
		<@ww.hidden name="colaborador.id" id="colaboradorId" />
		
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio"></button>
	</div>
</body>
</html>