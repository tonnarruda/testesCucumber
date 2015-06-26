	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/formataValores.js?version=${versao}"/>"></script>
	
	<#assign data = ""/>
	<#if indiceHistorico?exists>
		<#if indiceHistorico.data?exists>
			<#assign data = indiceHistorico.data?date/>
		</#if>
	</#if>

	<#include "../ftl/mascarasImports.ftl" />