	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/formataValores.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/indice.js"/>"></script>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ReajusteDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<script type="text/javascript">
		function calculaValor()
		{
			document.getElementById("salarioCalculado").value = "0.00"
			tipoSalarioProposto = document.getElementById("tipo").value;
			IndiceId = document.getElementById("indice").value;
			quantidade = document.getElementById("quantidade").value;

			ReajusteDWR.calculaSalario(setSalarioCalculado, tipoSalarioProposto, "naoPodeSerVazia", IndiceId, quantidade, "");
		}
		
		function setSalarioCalculado(data)
		{
			document.getElementById("salarioCalculado").value = data;
		}
	</script>
	
	<#assign data = ""/>
	<#if faixaSalarialHistorico?exists>
		<#if faixaSalarialHistorico.data?exists>
			<#assign data = faixaSalarialHistorico.data?date/>
		</#if>
	</#if>

	<#include "../ftl/mascarasImports.ftl" />