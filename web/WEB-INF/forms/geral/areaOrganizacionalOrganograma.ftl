<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>

	<title>Organograma de Áreas Organizacionais</title>
	
	<style type="text/css" media="all">
		#organogramaAreas { overflow: auto; margin-top: 10px; padding: 0px 10px; }
		#organogramaAreas table { margin: 25px auto; }
	</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-1.4.4.min.js"/>'></script>
	<script type='text/javascript' src='https://www.google.com/jsapi'></script>
	
    <script type='text/javascript'>
		google.load('visualization', '1', {packages:['orgchart']});
		
		$(function() {
			var areaId;
			<#if areaId?exists>
				areaId = ${areaId};
			</#if>
		
			AreaOrganizacionalDWR.getByEmpresaJson(montaOrganograma, <@authz.authentication operation="empresaId"/>, areaId);
		});
		
		var data, chart;
		
		function montaOrganograma(dados)
		{
			try {
				data = new google.visualization.DataTable();
				data.addColumn('string', 'nome');
				data.addColumn('string', 'nomeMae');
				data.addRows(eval(dados));
				
				chart = new google.visualization.OrgChart(document.getElementById('organogramaAreas'));
				chart.draw(data, { allowHtml:true, size:'medium' });
				
				<#if areaOrganizacional?exists && areaOrganizacional.nome?exists>
					$("td").filter(function() { return $.text([this]) == '${areaOrganizacional.nome}'; }).addClass('google-visualization-orgchart-nodesel');
				</#if>
			
			} catch (e) { console.log(e); }
		}
    </script>
</head>

<body>
	<div id="organogramaAreas"></div>
</body>
</html>