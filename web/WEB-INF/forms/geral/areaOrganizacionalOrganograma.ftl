<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>

	<title>Organograma de Áreas Organizacionais</title>
	
	<style type="text/css" media="all">
		#waDiv { position: relative; width: 98%; margin: 0px auto; left: 0; }
	
		#organogramaAreas { overflow: auto; margin-top: 10px; padding: 10px; border: 1px solid #BEBEBE; display: none; }
		.buttonGroup { display: none }
	</style>

	<!--[if IE]>
		<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/raphael/flashcanvas.js"/>"></script>
	<![endif]-->
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/raphael/raphael-min.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/raphael/lib_gg_orgchart_v041.js"/>'></script>
	
	<script type="text/javascript" src="http://canvg.googlecode.com/svn/trunk/rgbcolor.js"></script>
	<script type='text/javascript' src='http://canvg.googlecode.com/svn/trunk/canvg.js'></script>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
    <script type='text/javascript'>
		$(function() {
			$('#ativa').change(function() {
				AreaOrganizacionalDWR.findByEmpresa(montaAreas, <@authz.authentication operation="empresaId"/>, ($(this).val() == "" ? null : $(this).val()));
			});
		
			$('#ativa').change();
		
			$('#areaOrganizacional').change(function() {
				$('#organogramaAreas').empty();
			
				DWRUtil.useLoadingMessage('Carregando...');
				AreaOrganizacionalDWR.getOrganogramaByEmpresaJson(montaOrganograma, <@authz.authentication operation="empresaId"/>, ($(this).val() == "" ? null : new Number($(this).val())), ($('#ativa').val() == "" ? null : $('#ativa').val()));
			});
		});
		
		var oc_data, oc_style, OC_DEBUG;
		
		function montaAreas(dados)
		{
			addOptionsByCollection('areaOrganizacional', dados, ($('#ativa').val() == "" ? "Todas" : null), 'descricao');
		}
		
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
					box_color          : '#EFEFEF',              // fill color of boxes
					box_color_hover    : '#ffb',                 // fill color of boxes when mouse is over them
					box_border_color   : '#BEBEBE',              // stroke color of boxes
					line_color         : '#BEBEBE',              // color of connectors
					title_color        : '#333333',              // color of titles
					subtitle_color     : '#006600',                 // color of subtitles
					subtitle2_color     : '#006600',                 // color of subtitles
					title_font_size    : 12,                     // size of font used for displaying titles inside boxes
					subtitle_font_size : 10,                     // size of font used for displaying subtitles inside boxes
					subtitle2_font_size : 10,                     // size of font used for displaying subtitles inside boxes
					title_char_size    : [ 6, 12 ],              // size (x, y) of a char of the font used for displaying titles
					subtitle_char_size : [ 5, 10 ],              // size (x, y) of a char of the font used for displaying subtitles
					subtitle2_char_size : [ 5, 10 ],              // size (x, y) of a char of the font used for displaying subtitles
					max_text_width     : 20,                     // max width (in chars) of each line of text ('0' for no limit) 
					text_font          : 'Arial'            	 // font family to use (should be monospaced)
				};
				
				OC_DEBUG = false;
				
				$('#organogramaAreas').show('fast', 
											function() { 
												oc_render();
												$('text').filter(function() { return $.text([this]) == $('#areaOrganizacional option:selected').text().split('>').pop().trim(); }).css('font-weight','bold'); 
												if (!$.browser.msie)
													$('.buttonGroup').show();
											});

			} catch (e) { console.log(e); }
		}
		
		function imprimir()
		{
			var chartArea = document.getElementById('organogramaAreas');
		
			var configuracaoPadrao = 'left=20,top=20,width=' + chartArea.offsetWidth + ',height=' + chartArea.offsetHeight + ',toolbar=0,scrollbars=1,status=0';
			var winPrint = window.open('','', configuracaoPadrao);
		
			winPrint.document.write("<img src='" + getImgData() + "' style='max-width:800px'/>");
		
			winPrint.document.close();
			winPrint.focus();
			winPrint.print();
			winPrint.setTimeout(function() { WinPrint.close(); }, 3000);
		}
		
		function download()
		{
			$('#organograma').val(getImgData());
			$('#downloadForm').submit();
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
			
			
			//setTimeout(function() { }, 0);
		}
    </script>
</head>

<body>
	<label for="ativa">Listar áreas organizacionais </label>
	<@ww.select theme="simple" id="ativa" name="ativa" list=r"#{true:'ativas'}" cssStyle="width: 315px;" headerKey="" headerValue="ativas e inativas"/><br />
	<label for="areaOrganizacional">Área Organizacional:</label><br />
	<@ww.select theme="simple" id="areaOrganizacional" name="areaOrganizacional" list="areaOrganizacionals" listKey="id" listValue="descricao" headerKey="" headerValue="Todas" multiple="false" size="10" cssStyle="width: 500px;"/>

	<div id="organogramaAreas"></div>
	
	<form name="downloadForm" id="downloadForm" action="downloadOrganograma.action" method="post">
		<input type="hidden" name="organograma" id="organograma"/>
	</form>
	
	<div class="buttonGroup">
		<button class="btnImprimir" onclick="imprimir();"></button>
		<button class="btnDownload" onclick="download();"></button>
	</div>
</body>
</html>