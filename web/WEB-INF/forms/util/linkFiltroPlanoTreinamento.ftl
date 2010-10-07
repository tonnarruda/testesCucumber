<#assign linkFiltro=""/>
<#if filtroPlanoTreinamento?exists && filtroPlanoTreinamento.cursoId?exists>
	<#assign linkFiltro="${linkFiltro}&filtroPlanoTreinamento.cursoId=${filtroPlanoTreinamento.cursoId}"/>
</#if>

<#if filtroPlanoTreinamento?exists && filtroPlanoTreinamento.dataIni?exists>
	<#assign linkFiltro="${linkFiltro}&filtroPlanoTreinamento.dataIni=${filtroPlanoTreinamento.dataIni?date}"/>
</#if>

<#if filtroPlanoTreinamento?exists && filtroPlanoTreinamento.dataFim?exists>
	<#assign linkFiltro="${linkFiltro}&filtroPlanoTreinamento.dataFim=${filtroPlanoTreinamento.dataFim?date}"/>
</#if>

<#if filtroPlanoTreinamento?exists && filtroPlanoTreinamento.realizada?exists>
	<#assign linkFiltro="${linkFiltro}&filtroPlanoTreinamento.realizada=${filtroPlanoTreinamento.realizada}"/>
</#if>

<#if filtroPlanoTreinamento?exists && filtroPlanoTreinamento.page?exists>
	<#assign linkFiltro="${linkFiltro}&filtroPlanoTreinamento.page=${filtroPlanoTreinamento.page}"/>
</#if>