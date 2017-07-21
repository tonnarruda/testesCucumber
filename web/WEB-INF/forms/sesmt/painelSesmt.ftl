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
			.icoImprimir { float: right; cursor: pointer; }
			#indicadores li { padding: 7px; }
		</style>
	
		<!--[if lte IE 8]><script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/grafico.js?version=${versao}"/>'></script>
		
		<title>Painel de Indicadores de SESMT</title>

		<script type="text/javascript">
			$(function () {
				
				var afastamentoPorMotivo = ${grfQtdAfastamentosPorMotivo};
				var catsPorDiaSemana = ${grfQtdCatsPorDiaSemana};
				var catsPorHorario = ${grfQtdCatsPorHorario};
				
				montaGrafico("#catsDiaSemana",catsPorDiaSemana,'CATs por Dia da Semana', 0.05);
				montaGrafico("#catsHorario",catsPorHorario,'CATs por Horário', 0.05);
				montaGrafico("#afastamentosMotivo",afastamentoPorMotivo,'Total de Afastamentos por Motivo', 0.05);
				
				$('#totalAfastamentoMotivo').text(afastamentoPorMotivo.length);
				$('#totalCatsPorDiaSemana').text(catsPorDiaSemana.length);
				$('#totalCatsPorHorario').text(catsPorHorario.length);
			
			});
			
			
			function montaGrafico(obj, dados, titulo, combinePercMin)
			{
		
				montaPie(dados, obj, { combinePercentMin: combinePercMin, percentMin: 0.03, noColumns: 2, container: obj + "Legenda" });
				
				$(obj + "Imprimir")
					.unbind()
					.bind('click', 
						function(event) 
						{ 
							popup = window.open("<@ww.url includeParams="none" value="/grafico.jsp"/>");
							popup.window.onload = function() 
							{
								popup.focus();
								popup.document.getElementById('popupTitulo').innerHTML = titulo;
								
								popup.window.opener.montaPie(dados, popup.document.getElementById('popupGrafico'), { container: popup.document.getElementById('popupGraficoLegenda'), combinePercentMin: combinePercMin, percentMin: 0.03} );
								
								popup.window.print();
								popup.window.close();
							}
						}
					);
			}
			
			
			function validaForm()
			{
				return validaFormularioEPeriodo('formBusca', new Array('dataDe','dataAte'), new Array('dataDe','dataAte'));
			}
			
			function imprimirDados(titulo, conteudo)
			{
			
				popup = window.open("<@ww.url includeParams="none" value="/conteudo.jsp"/>");
				popup.window.onload = function() 
				{
					popup.focus();
					popup.document.getElementById('popupTitulo').innerHTML = titulo;
					popup.document.getElementById('popupConteudo').innerHTML = conteudo;
					popup.window.print();
					popup.window.close();
				}
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

		<table class="grid" cellspacing="5">
			<tr>
				<td class="grid-cell">
					<div class="cell-title">
						CATs por Dia da Semana (total: <span id="totalCatsPorDiaSemana"></span>)
						<img id="catsDiaSemanaImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</div>
					<div id="catsDiaSemana" class="graph"></div>
			    	<div style="clear:both"></div>
				</td>
				<td class="grid-cell">
					<div class="cell-title">
						CATs por Horário (total: <span id="totalCatsPorHorario"></span>)
						<img id="catsHorarioImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</div>
					<div id="catsHorario" class="graph"></div>
			    	<div style="clear:both"></div>
				</td>
				
			</tr>
			<tr>
				<td class="grid-cell">
					<div class="cell-title">
						Total de Afastamentos por Motivo (total: <span id="totalAfastamentoMotivo"></span>)
						<img id="afastamentosMotivoImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</div>
			    	<div id="afastamentosMotivo" class="graph"></div>
			    	<div style="clear:both"></div>
				</td>
				<td class="grid-cell">
					<div class="cell-title">
						Estatísticas do SESMT
						<img onclick="imprimirDados('Estatísticas do SESMT', $('#estatisticaSESMT').html())" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</div>
				<div id="estatisticaSESMT">
		    		<div id="indicadores">
			    		<ul>
			    			<li>Nº de Exames Realizados: ${qtdExamesRealizados}</li>
			    			<li>Nº de Registros de Prontuários:	${qtdProntuarios}</li>
			    			<li>Nº de Afastamentos pelo INSS: ${qtdAfastamentosInss}</li>
			    			<li>Nº de Afastamentos (não afastados pelo INSS): ${qtdAfastamentosNaoInss}</li>
			    		</ul>
		    		</div>
	    		</div>
			    	<div style="clear:both"></div>
				</td>
			</tr>
			
		</table>

		<div class="fieldGraph bigger">
			
			<h1>Estatísticas de Resultados de Exames <img onclick="imprimirDados('Estatísticas de Resultados de Exames', $('#estatisticaResultado').html())" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/></h1>
				
	   		<div id="estatisticaResultado" style="height:300px; overflow:scroll; overflow-x: hidden;">
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