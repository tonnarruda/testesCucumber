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
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>
	
	<script type='text/javascript'>
		function popupVideo(caminho) 
		{
			path =caminho;
			var remote = null;
			remote = window.open(path,'Janela1','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=auto,resizable=no,menubar=no,width=720,height=631')
		}
		
		function popupDetalhes(caminho) 
		{
			path =caminho;
			var remote = null;
			remote = window.open(path,'Janela1','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=auto,resizable=no,menubar=no,width=380,height=550')
		}
	</script>
</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@display.table name="listaVideos" id="video" class="dados">
		<@display.column title="Ações" media="html" style="text-align:center; width:80px;" >
			<img title="Assistir" onclick="javascript:popupVideo('http://www.fortesinformatica.com.br/videos/portal_videoteca_ver_new.php?id=${video.id}&hash=${calculoHash}');" src="<@ww.url includeParams="none" value="/imgs/video.png"/>" style="cursor:pointer;"/>
			<img title="Download" onclick="location.href='http://www.fortesinformatica.com.br/videos/portal_videoteca_download_new.php?id=${video.exe}'" src="<@ww.url includeParams="none" value="/imgs/icon_download.gif"/>" style="cursor:pointer;"/>
			<img title="Detalhes" onclick="javascript:popupDetalhes('http://www.fortesinformatica.com.br/portal_videoteca_detalhes.php?id=${video.id}');" width="18" src="<@ww.url includeParams="none" value="/imgs/info.gif"/>" style="cursor:pointer;"/>
		</@display.column>
		<@display.column title="Título">
			<a href="javascript:;" onclick="javascript:popupVideo('http://www.fortesinformatica.com.br/videos/portal_videoteca_ver_new.php?id=${video.id}&hash=${calculoHash}');">
				${video.titulo}
			</a>
		</@display.column>
	</@display.table>
</body>
</html>