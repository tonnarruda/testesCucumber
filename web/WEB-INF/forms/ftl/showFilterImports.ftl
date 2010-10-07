<#if showFilter?exists && showFilter>
	<#assign labelFiltro="Ocultar Filtro"/>
	<#assign imagemFiltro="/imgs/arrow_up.gif"/>
	<#assign classHidden=""/>
<#else>
	<#assign labelFiltro="Exibir Filtro"/>
	<#assign imagemFiltro="/imgs/arrow_down.gif"/>
	<#assign classHidden="hidden"/>
</#if>