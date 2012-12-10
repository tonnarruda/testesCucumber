<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>

	<title>Organograma de Áreas Organizacionais</title>
	
	<style type="text/css" media="all">
		#waDiv { position: relative; width: 98%; margin: 0px auto; left: 0; }
	
		#organogramaAreas { overflow: auto; margin-top: 10px; padding: 0px 10px; border: 1px solid #7E9DB9; display: none; }
		#organogramaAreas table { margin: 25px auto; }
		#btnImprimir { display: none }
	</style>
		
	<script type='text/javascript' src='https://www.google.com/jsapi'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-1.4.4.min.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.alerts.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.numberformatter-1.1.0.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.dateFormat-1.0.js"/>'></script>
	
    <script type='text/javascript'>
		google.load('visualization', '1', {packages:['orgchart']});
		google.setOnLoadCallback(montaOrganograma);
		
		var data, chart;
		
		function montaOrganograma()
		{
			var dados = ${areasOrganizacionaisJson};
		
			try {
				$('#organogramaAreas, #btnImprimir').show();
			
				data = new google.visualization.DataTable();
				data.addColumn('string', 'nome');
				data.addColumn('string', 'nomeMae');
				data.addRows(eval(dados));
				
				chart = new google.visualization.OrgChart(document.getElementById('organogramaAreas'));
				chart.draw(data, { allowHtml:true, size:'medium' });
				
				<#if areaOrganizacional?exists && areaOrganizacional.nome?exists>
					$("td").filter(function() { return $.text([this]) == '${areaOrganizacional.nome}' }).addClass('google-visualization-orgchart-nodesel');
				</#if>
			
			} catch (e) { console.log(e); }
		}
    </script>
</head>

<body>
	<div id="organogramaAreas"></div>
</body>
</html>