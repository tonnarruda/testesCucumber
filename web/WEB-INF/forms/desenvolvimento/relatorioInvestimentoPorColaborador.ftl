<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>

<@ww.head/>

	<title>Investimentos de T&D por Colaborador</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/autoCompleteFortes.js"/>'></script>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
	
	<style type="text/css">
	    @import url('<@ww.url includeParams="none" value="/css/fortes.css"/>');
	    @import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
	    @import url('<@ww.url includeParams="none" value="/css/jquery-ui/redmond.css"/>');
    </style>
	
	<script type='text/javascript'>
		var urlFind = "<@ww.url includeParams="none" value="/geral/colaborador/find.action"/>";
		var ajaxData = ajaxData(urlFind);
		var renderData = renderData();
		
		$(function() {
			$("#colaborador").autocomplete({
				source: ajaxData ,				 
				minLength: 3,
				select: function( event, ui ) { 
					$("#colaboradorId").val(ui.item.id); 
				}
			}).data( "autocomplete" )._renderItem = renderData;
		});
	</script>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign validarCampos="return validaFormulario('form', new Array('inicio','fim'), new Array('inicio','fim'))"/>
	
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
		<@ww.select label="Empresa" name="empresaId" id="empresaId" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="-1" onchange="populaAreas(this.value);"/>

		<@ww.datepicker label="Período" required="true" value="${inicio}" name="dataIni" id="inicio" cssClass="mascaraData validaDataIni" after="a" liClass="liLeft"/>
		<@ww.datepicker label="" value="${fim}" name="dataFim" id="fim" cssClass="mascaraData validaDataFim"/>

		<@ww.select id="realizada" label="Turmas Realizadas" name="realizada" list=r"#{'T':'Todas','S':'Sim','N':'Não'}" />
		
		<@ww.textfield label="Colaborador (Nome, Nome Comercial, Matrícula ou CPF)" name="" id="colaborador" cssStyle="width: 650px;"/>
		<@ww.hidden name="colaboradorId" id="colaboradorId" />
		
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio"></button>
	</div>
</body>
</html>