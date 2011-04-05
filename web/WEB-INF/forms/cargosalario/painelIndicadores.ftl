<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/painelIndicadoresTreinamentos.css"/>');
	</style>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
	<title>Painel de Indicadores de Info. Funcionais</title>

	<style type="text/css">
		div.graph
		{
			width: 400px;
			height: 180px;
			float: left;
		}
		.label
		{
			font-size:8pt;
			text-align:center;
			padding:1px;
			color:#000;
		}
	</style>
<script type="text/javascript">
$(function () {

	montaPie(${grfFormacaoEscolars}, "#formacaoEscolar", 0);
	montaPie(${grfFaixaEtarias}, "#faixaEtaria", 0);
	montaPie(${grfSexo}, "#sexo", 0);
	montaPie(${grfEstadoCivil}, "#estadoCivil", 0);
	montaPie(${grfDeficiencia}, "#deficiencia", 0.05);
	
	
	//$("#interactive").bind("plotclick", pieClick);
});

	function montaPie(data, class, qtdOutros)
	{
	    $.plot($(class), data, 
		{
	        series: {
	            pie: {
	                show: true,
	                radius: 0.9,
		                combine: {
		                    color: '#999',
		                    threshold: qtdOutros,
		                    label: 'Outros'
		                },
	                label: {
	                    show: true,
	                    radius: 0.7,
	                    formatter: function(label, series){
	                        return '<div class="label">'+series.percent.toFixed(2)+'%</div>';
	                    }
	                }
	            }
	        },
	        grid: {
				hoverable: true
				//clickable: true
			},
			colors: ["#edc240", "#afd8f8", "#cb4b4b", "#4da74d", "#9440ed"],//"#D7ECFC","#DECD99","#226FA5","#8080FF"
			legend: {
	    		labelFormatter: function(label, series) {
					return label + ' (' + series.datapoints.points[1] + ')';
				}
	  		}
		});
		
		$(class).bind("plothover", pieHover);
	}

	function pieHover(event, pos, obj) 
	{
		if (!obj)
			return;
		percent = parseFloat(obj.series.percent).toFixed(2);
		qtd = parseFloat(obj.series.datapoints.points[1]);
		$("#hover").html('<span style="font-weight: bold; color: '+obj.series.color+'">'+obj.series.label+' (Qtd.: ' + qtd + ', ' + percent + '%)</span>');
	}
	
	function pieClick(event, pos, obj) 
	{
		if (!obj)
			return;
			
		percent = parseFloat(obj.series.percent).toFixed(2);
		alert(''+obj.series.label+': '+percent+'%');
		
	}
</script>

	<#include "../ftl/mascarasImports.ftl" />

	<#assign dateIni = ""/>
	<#assign dateFim = ""/>
</head>
<body>
	<@ww.form name="formBusca" id="formBusca" action="list.action" method="POST">
		Período:<br>
		<@ww.datepicker name="indicadorTreinamento.dataIni" id="dataIni" value="${dateIni}" cssClass="mascaraData validaDataIni" liClass="liLeft"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="indicadorTreinamento.dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" />

		<button onclick="return enviaForm(1);" class="btnPesquisar"></button>
		<button onclick="return enviaForm(2);" class="btnImprimirPdf"></button>

		<br><br>
	    <div id="hover"></div>
		<br><br>
	
	
		<div class="gogDivFormularioX">
	    <div id="formacaoEscolar" class="graph" ></div>
	    <div id="faixaEtaria" class="graph"></div>
	    </div>
   		<div class="gogDivFormularioX">
	    <div id="sexo" class="graph"></div>
	    <div id="estadoCivil" class="graph"></div>
	    </div>
	    <div class="gogDivFormularioX">
	    <div id="deficiencia" class="graph"></div>
	    </div>

	</@ww.form>
</body>
</html>