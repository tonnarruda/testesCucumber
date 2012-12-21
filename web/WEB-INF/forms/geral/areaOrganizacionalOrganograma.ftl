<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>

	<title>Organograma de Áreas Organizacionais</title>
	
	<style type="text/css" media="all">
		#waDiv { position: relative; width: 98%; margin: 0px auto; left: 0; }
	
		#organogramaAreas { overflow: auto; margin-top: 10px; padding: 10px; border: 1px solid #7E9DB9; display: none; }
		#btnImprimir { display: none }
	</style>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/lib_gg_orgchart/raphael-min.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/lib_gg_orgchart/lib_gg_orgchart_v041.js"/>'></script>
	<script type='text/javascript' src='http://canvg.googlecode.com/svn/trunk/canvg.js'></script>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
    <script type='text/javascript'>
		$(function() {
			$('#areaOrganizacional').change(function() {
				$('#organogramaAreas').empty();
			
				DWRUtil.useLoadingMessage('Carregando...');
				AreaOrganizacionalDWR.getOrganogramaByEmpresaJson(montaOrganograma, <@authz.authentication operation="empresaId"/>, ($(this).val() == "" ? null : new Number($(this).val())));
			});
		});
		
		var oc_data, oc_style, OC_DEBUG;
		
		function montaOrganograma(dados)
		{
			try {
				oc_data = {
					"title" : "",
					"root" : {
						"id" : "1",
						"title" : "<@authz.authentication operation="empresaNome"/>",
						"children" : eval(dados)
					}
				};
				
				oc_style = {
					container          : 'organogramaAreas', 	 // name of the DIV where the chart will be drawn
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
				
				$('#organogramaAreas').show('fast', function() { oc_render(); });

			} catch (e) { console.log(e); }
		}
		
		function imprimir()
		{
			var chartArea = document.getElementById('organogramaAreas');
		
			var configuracaoPadrao = 'left=20,top=20,width=' + chartArea.offsetWidth + ',height=' + chartArea.offsetHeight + ',toolbar=0,scrollbars=1,status=0';
			var WinPrint = window.open('','', configuracaoPadrao);
		
			WinPrint.document.write("<img src='" + getImgData() + "'/>");
		
			WinPrint.document.close();
			WinPrint.focus();
			WinPrint.print();
			WinPrint.setTimeout(function() { WinPrint.close(); }, 3000);
		}
		
		function download()
		{
			var a = document.createElement('a');
			a.download = 'organograma.png';
			a.type = 'image/png';
			a.href = getImgData();
			a.click();
		}
		
		function getImgData() 
		{
			var chartArea = document.getElementById('organogramaAreas');
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
			
			return imgData;
		}
    </script>
</head>

<body>
	<label for="areaOrganizacional">Área Organizacional:</label><br />
	<@ww.select theme="simple" id="areaOrganizacional" name="areaOrganizacional" list="areaOrganizacionals" listKey="id" listValue="descricao" headerKey="" headerValue="Todas" multiple="false" size="10" cssStyle="width: 500px;"/>

	<div id="organogramaAreas"></div>
	
	<a href="javascript:;" onclick="download()" download="organograma.svg" type=""
	
	<div class="buttonGroup">
		<button class="btnImprimir" onclick="imprimir();"></button>
		<button class="btnGravar" onclick="download();"></button>
	</div>
</body>
</html>