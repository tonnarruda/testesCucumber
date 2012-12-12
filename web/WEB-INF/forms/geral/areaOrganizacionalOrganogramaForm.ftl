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
				$('#organogramaAreas, #btnSalvar').show();
			
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
    
        <script type="text/javascript" src="http://canvg.googlecode.com/svn/trunk/rgbcolor.js"></script> 
    <script type="text/javascript" src="http://canvg.googlecode.com/svn/trunk/canvg.js"></script>
    <script>
      function getImgData(chartContainer) {
        var chartArea = chartContainer.getElementsByTagName('iframe')[0].contentDocument.getElementById('chartArea');
        var svg = chartArea.innerHTML;
        var doc = chartContainer.ownerDocument;
        var canvas = doc.createElement('canvas');
        canvas.setAttribute('width', chartArea.offsetWidth);
        canvas.setAttribute('height', chartArea.offsetHeight);
        
        
        canvas.setAttribute(
            'style',
            'position: absolute; ' +
            'top: ' + (-chartArea.offsetHeight * 2) + 'px;' +
            'left: ' + (-chartArea.offsetWidth * 2) + 'px;');
        doc.body.appendChild(canvas);
        canvg(canvas, svg);
        var imgData = canvas.toDataURL("image/png");
        canvas.parentNode.removeChild(canvas);
        return imgData;
      }
      
      function saveAsImg(chartContainer) {
        var imgData = getImgData(chartContainer);
        
        // Replacing the mime-type will force the browser to trigger a download
        // rather than displaying the image in the browser window.
        window.location = imgData.replace("image/png", "image/octet-stream");
      }
      
      function toImg(chartContainer, imgContainer) { 
        var doc = chartContainer.ownerDocument;
        var img = doc.createElement('img');
        img.src = getImgData(chartContainer);
        
        while (imgContainer.firstChild) {
          imgContainer.removeChild(imgContainer.firstChild);
        }
        imgContainer.appendChild(img);
      }
    </script>
</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<label for="areaOrganizacional">Área Organizacional:</label><br />
	<@ww.select theme="simple" id="areaOrganizacional" name="areaOrganizacional" list="areaOrganizacionals" listKey="id" listValue="descricao" headerKey="" headerValue="Todas" multiple="false" size="10" cssStyle="width: 500px;"/>

	<div id="organogramaAreas"></div>
	
	<#assign urlImprimir><@ww.url includeParams="none" value="/geral/areaOrganizacional/imprimirOrganograma.action"/></#assign>
	<#if areaOrganizacional?exists && areaOrganizacional.id?exists>
		<#assign urlImprimir><@ww.url includeParams="none" value="/geral/areaOrganizacional/imprimirOrganograma.action?areaId=${areaOrganizacional.id}"/></#assign>
	</#if>
	
	<div class="buttonGroup">
		<button id="btnSalvar" class="btnGravar" onclick="location.href='${urlImprimir}'"></button>
	</div>
</body>
</html>