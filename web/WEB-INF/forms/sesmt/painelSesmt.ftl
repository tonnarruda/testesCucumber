<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<@ww.head/>
		
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
			@import url('<@ww.url value="/css/indicadores.css?version=${versao}"/>');
			
			.semAcidentes { text-align: center; color: #060; border: 2px solid #060; background-color: #EFE; }
			.semAcidentes p { font-size: 14px; font-weight: bold; }
			.semAcidentes span { font-size: 22px; color: #333; }
			.graph li { padding: 13px; }
			.legendLabel { width: 210px; }
		</style>
	
		<!--[if lte IE 8]><script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/grafico.js?version=${versao}"/>'></script>
		
		<title>Painel de Indicadores de SESMT</title>

		<script type="text/javascript">
			$(function () {
				montaPie(${grfQtdCatsPorDiaSemana}, "#catsDiaSemana", {combinePercentMin: 0.05, percentMin: 0.05});
				montaPie(${grfQtdCatsPorHorario}, "#catsHorario", {combinePercentMin: 0.05, percentMin: 0.05});
				montaPie(${grfQtdAfastamentosPorMotivo}, "#afastamentosMotivo", {combinePercentMin: 0.05, percentMin: 0.05});
			});
			
			function validaForm()
			{
				return validaFormularioEPeriodo('formBusca', new Array('dataDe','dataAte'), new Array('dataDe','dataAte'));
			}
		</script>
	
		
		<#if dataDe?exists>
			<#assign dateIni = dataDe?date/>
		<#else>
			<#assign dateIni = ""/>
		</#if>
		<#if dataAte?exists>
			<#assign dateFim = dataAte?date/>
		<#else>
			<#assign dateFim = ""/>
		</#if>
		
		<#include "../ftl/mascarasImports.ftl" />
		<#include "../ftl/showFilterImports.ftl" />
	</head>
	
	<body>
		<div class="semAcidentes">
	   		<p>Estamos trabalhando há <span>${qtdDiasSemAcidentes}</span> dias sem acidentes.</p>
	   	</div>
	   	<br clear="all"/>
	   	
		<#include "../util/topFiltro.ftl" />
			<@ww.form name="formBusca" id="formBusca" action="painel.action" method="POST">
				<@ww.select label="Empresa" name="empresa.id" id="empresa" cssClass="empresa" listKey="id" listValue="nome" list="empresas" />
			
				Período:*<br>
				<@ww.datepicker name="dataDe" id="dataDe" value="${dateIni}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
				<@ww.label value="a" liClass="liLeft" />
				<@ww.datepicker name="dataAte" id="dataAte" value="${dateFim}" cssClass="mascaraData validaDataFim" />
				
				<button onclick="return validaForm();" class="btnPesquisar grayBGE"></button>
			</@ww.form>
		<#include "../util/bottomFiltro.ftl" />

		<div class="fieldGraph">
			<h1>CATs por Dia da Semana</h1>
		    <div id="catsDiaSemana" class="graph"></div>
	    </div>
	    
		<div class="fieldGraph">
			<h1>CATs por Horário</h1>
	    	<div id="catsHorario" class="graph"></div>
	    </div>
	    
	    <div class="fieldGraph">
			<h1>Total de Afastamentos por Motivo</h1>
	    	<div id="afastamentosMotivo" class="graph"></div>
	    </div>
	    
	    <div class="fieldGraph">
			<h1>Estatísticas do SESMT</h1>
	    	<div class="graph">
	    		<ul>
	    			<li>Nº de Exames Realizados: ${qtdExamesRealizados}</li>
	    			<li>Nº de Registros de Prontuários:	${qtdProntuarios}</li>
	    			<li>Nº de Afastamentos pelo INSS: ${qtdAfastamentosInss}</li>
	    			<li>Nº de Afastamentos (não afastados pelo INSS): ${qtdAfastamentosNaoInss}</li>
	    		</ul>
	    	</div>
	    </div>
	    <div style="clear: both"></div>
	    	    
		<div class="fieldGraph bigger">
			<h1>Estatísticas de Resultados de Exames</h1>
	   		<div style="height:300px; overflow:scroll; overflow-x: hidden;">
		   		<@display.table name="exames" id="exame" class="dados" style="width: 945px;">
					<@display.column property="nome" title="Exame"/>
					<@display.column property="qtdNormal" title="Qtd. Normal" style="width: 100px; text-align: right;"/>
					<@display.column property="qtdAnormal" title="Qtd. Alterado" style="width: 100px; text-align: right;"/>
				</@display.table>
			</div>
	   	</div>
	    <div style="clear: both"></div>
	    
		<a name="pagebottom"></a>
	</body>
</html>