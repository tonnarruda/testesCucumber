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
		
		function exibeOcultaFiltro() 
		{
			$('#divFiltroForm').toggle();
			
			if ( $('#divFiltroForm').is(':visible') )
			{
				$('#linkFiltro img').attr('src','<@ww.url includeParams="none" value="/imgs/"/>arrow_up.gif');
				$('#labelLink').text('Ocultar filtro');
			}
			else
			{
				$('#linkFiltro img').attr('src','<@ww.url includeParams="none" value="/imgs/"/>arrow_down.gif');
				$('#labelLink').text('Exibir filtro');
			}
		}
		
		$(function() {
			<#if modulo?exists && modulo != ''>
				exibeOcultaFiltro();
			</#if>
		});
	</script>
</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<div class="divFiltro">
		<div class="divFiltroLink">
			<a href="javascript:exibeOcultaFiltro();" id="linkFiltro">
				<img alt="Ocultar\Exibir Filtro" src="<@ww.url includeParams="none" value="/imgs/"/>arrow_down.gif"> 
				<span id="labelLink" class="labelLink">Exibir Filtro</span>
			</a>
		</div>
		<div id="divFiltroForm" class="divFiltroForm hidden">
			<@ww.form name="formBusca" action="videoteca.action" method="POST" id="formBusca">
				<@ww.select label="Módulo" name="modulo" list=r"#{'R&S':'R&S', 'C&S':'C&S', 'Pesquisas':'Pesquisas', 'Aval. de Desempenho':'Aval. de Desempenho', 'T&D':'T&D', 'Info. Funcionais':'Info. Funcionais', 'SESMT':'SESMT', 'Utilitários':'Utilitários'}" headerKey="" headerValue="Todos"/>
				<button type="submit" class="btnPesquisar grayBGE"></button>
			</@ww.form>
		</div>
	</div>
	<br />

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