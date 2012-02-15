<#assign ww=JspTaglibs["/WEB-INF/tlds/webwork.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<meta http-equiv="Content-Language" content="pt-br" />
	<meta http-equiv="Cache-Control" content="no-cache, no-store" />
	<meta http-equiv="Pragma" content="no-cache, no-store" />
	<meta http-equiv="Expires" content="0" />
	
	<title>Videoteca</title>
	
	<style>
		
	</style>
	
	
	<script type='text/javascript'>
		$(function(){
			var url = 'http://www.fortesinformatica.com.br/portal_videoteca_rh.php';
			
			var imgExibir = '<@ww.url includeParams="none" value="/imgs/video.png"/>';
			var imgDownload = '<@ww.url includeParams="none" value="/imgs/icon_download.gif"/>';
			var imgDetalhe = '<@ww.url includeParams="none" value="/imgs/log.png"/>';
			
			$.getJSON(url, {},function(response){
				var html = "";
				$.each(response, function(indice, valor) {
					var urlVideo = 'http://www.fortesinformatica.com.br/videos/portal_videoteca_ver_new.php?id='+valor.id;
					var urlDownload = 'http://www.fortesinformatica.com.br/videos/portal_videoteca_download_new.php?id='+valor.exe;
					var urlDetalhe = 'http://www.fortesinformatica.com.br/portal_videoteca_detalhes.php?id='+valor.id;
					var classe = 'even';
					if (indice % 2 != 0)
						classe = 'odd';
					html += '<tr class="' + classe + '">';
					html += '<td width="100" align="center">';
					html += '<a href="javascript:popupVideo(\''+urlVideo+'\');"><img src="' + imgExibir + '"/></a> ';	
					html += '<a href="' +urlDownload+ '"><img src="' + imgDownload + '"/></a> ';	
					html += '<a href="javascript:popupDetalhes(\''+urlDetalhe+'\');"><img src="' + imgDetalhe + '"/></a>';	
					html += '</td>';
					html += '<td><a href="javascript:popupVideo(\''+urlVideo+'\');">'+valor.titulo+'</a></td>';
					html += '</tr>'; 
					
	  			});
	  			
	  			$('#listaVideos').html(html);
			});
		});
		
		function popupVideo(caminho) {
			path =caminho;
			var remote = null;
			remote = window.open(path,'Janela1','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=auto,resizable=no,menubar=no,width=720,height=631')
		}
		
		function popupDetalhes(caminho) {
			path =caminho;
			var remote = null;
			remote = window.open(path,'Janela1','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=auto,resizable=no,menubar=no,width=380,height=550')
		}
		
	</script>
</head>

<body>
	<table class="dados">
		<thead>
			<tr>
				<th>Ações</th>
				<th>Título</th>
			</tr>
		</thead>
		<tbody id="listaVideos">
			
		</tbody>
	</table>
</body>
</html>