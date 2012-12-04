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

	<style type="text/css" media="print">
		body * {
			visibility: hidden;
		}
		
		#organogramaAreas, #organogramaAreas * {
			visibility: visible;
		}
		
		#organogramaAreas {
			overflow: visible;
			border: none;
			left:0;
			top:0;
			-webkit-transform:rotate(90deg);
			-moz-transform:rotate(90deg);
			-o-transform:rotate(90deg);
			-ms-transform:rotate(90deg);
			filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=1);
		}
	</style>
		
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<script type='text/javascript' src='https://www.google.com/jsapi'></script>
    <script type='text/javascript'>
		google.load('visualization', '1', {packages:['orgchart']});
		
		$(function() {
			$('#areaOrganizacional').change(function() {
				DWRUtil.useLoadingMessage('Carregando...');
				AreaOrganizacionalDWR.getByEmpresaJson(montaOrganograma, <@authz.authentication operation="empresaId"/>, ($(this).val() == "" ? null : new Number($(this).val())));
			});
		});
		
		var data, chart;
		
		function montaOrganograma(dados)
		{
			try {
				$('#organogramaAreas, #btnImprimir').show();
			
				data = new google.visualization.DataTable();
				data.addColumn('string', 'nome');
				data.addColumn('string', 'nomeMae');
				data.addRows(eval(dados));
				
				chart = new google.visualization.OrgChart(document.getElementById('organogramaAreas'));
				chart.draw(data, { allowHtml:true, size:'medium' });
				
				$("td").filter(function() { return $.text([this]) == $("#areaOrganizacional option:selected").text().split('>').pop().trim(); }).addClass('google-visualization-orgchart-nodesel');
			
			} catch (e) { console.log(e); }
		}
    </script>
</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<label for="areaOrganizacional">Área Organizacional:</label><br />
	<@ww.select theme="simple" id="areaOrganizacional" name="areaOrganizacional" list="areaOrganizacionals" listKey="id" listValue="descricao" headerKey="" headerValue="Todas" multiple="false" size="10" cssStyle="width: 500px;"/>

	<div id="organogramaAreas"></div>
	
	<div class="buttonGroup">
		<button id="btnImprimir" class="btnImprimir" onclick="window.print();"></button>
	</div>
</body>
</html>