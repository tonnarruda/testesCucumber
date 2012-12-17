<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>

	<title>Organograma de Áreas Organizacionais</title>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/lib_gg_orgchart/raphael-min.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/lib_gg_orgchart/lib_gg_orgchart_v041.js"/>'></script>
	<script type='text/javascript' src='http://canvg.googlecode.com/svn/trunk/canvg.js'></script>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
    <script type='text/javascript'>
		$(function() {
			DWRUtil.useLoadingMessage('Carregando...');
			var areaId = ${areaId};
			AreaOrganizacionalDWR.getByEmpresaJson2(montaOrganograma, <@authz.authentication operation="empresaId"/>, (areaId == "" ? null : areaId));
		});
		
		var oc_data, oc_style, OC_DEBUG;
		
		function montaOrganograma(dados)
		{
			try {
				console.log(dados);
			
				oc_data = {
					"title" : "",
					"root" : {
						"id" : "1",
						"title" : "<@authz.authentication operation="empresaNome"/>",
						"children" : eval(dados)
					}
				};
				
				oc_style = {
					container          : 'oc_container',         // name of the DIV where the chart will be drawn
					vline              : 10,                     // size of the smallest vertical line of connectors
					hline              : 10,                     // size of the smallest horizontal line of connectors
					inner_padding      : 10,                     // space from text to box border
					box_color          : '#aaf',                 // fill color of boxes
					box_color_hover    : '#faa',                 // fill color of boxes when mouse is over them
					box_border_color   : '#008',                 // stroke color of boxes
					line_color         : '#f44',                 // color of connectors
					title_color        : '#000',                 // color of titles
					subtitle_color     : '#707',                 // color of subtitles
					title_font_size    : 12,                     // size of font used for displaying titles inside boxes
					subtitle_font_size : 10,                     // size of font used for displaying subtitles inside boxes
					title_char_size    : [ 6, 12 ],              // size (x, y) of a char of the font used for displaying titles
					subtitle_char_size : [ 5, 10 ],              // size (x, y) of a char of the font used for displaying subtitles
					max_text_width     : 15,                     // max width (in chars) of each line of text ('0' for no limit) 
					text_font          : 'monospaced'            // font family to use (should be monospaced)
				};
				
				OC_DEBUG = false;
				
				oc_render();
			
			} catch (e) { console.log(e); }
		}
		
		function getImgData() 
		{
			var chartArea = document.getElementById('oc_container');
			var svg = chartArea.innerHTML;
			var canvas = document.createElement('canvas');
			canvas.setAttribute('width', chartArea.offsetWidth);
			canvas.setAttribute('height', chartArea.offsetHeight);
			
			canvas.setAttribute(
			    'style',
			    'position: absolute; ' +
			    'top: ' + (-chartArea.offsetHeight * 2) + 'px;' +
			    'left: ' + (-chartArea.offsetWidth * 2) + 'px;');
			document.body.appendChild(canvas);
			canvg(canvas, svg);
			var imgData = canvas.toDataURL("image/png");
			canvas.parentNode.removeChild(canvas);
			return imgData;
		}
    
		function saveAsImg() 
		{
			var imgData = getImgData();
			window.location = imgData.replace("image/png", "image/octet-stream");
		}
    
		function toImg() 
		{ 
			var imgContainer = document.getElementById('img_container');
			var img = document.createElement('img');
			img.src = getImgData();
			
			while (imgContainer.firstChild) {
				imgContainer.removeChild(imgContainer.firstChild);
			}
			
			imgContainer.appendChild(img);
		}
    </script>
</head>

<body>
	<div id="oc_container"></div>
	<button onclick="saveAsImg();">Download</button>
</body>
</html>