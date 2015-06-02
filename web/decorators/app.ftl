<#assign ww=JspTaglibs["/WEB-INF/tlds/webwork.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<link rel="SHORTCUT ICON" href='<@ww.url includeParams="none" value="/imgs/inf.ico"/>'>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="Content-Language" content="pt-br" />
	<meta http-equiv="Cache-Control" content="no-cache, no-store" />
	<meta http-equiv="Pragma" content="no-cache, no-store" />
	<meta http-equiv="Expires" content="0" />
	<title>${title}</title>

	<script>
		var baseUrl = '<@ww.url includeParams="none" value="/"/>';
		var sessionMaxInactiveInterval = ${session.maxInactiveInterval * 1000};
		
		var ultimasNoticias = null;
		<#if ultimasNoticias?exists>
			ultimasNoticias = ${ultimasNoticias};
		</#if>
		
		var pgInicial = false;
		<#if pgInicial?exists && pgInicial>
			pgInicial = true;
		</#if>
	</script>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-1.4.4.min.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.alerts.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.numberformatter-1.1.0.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.dateFormat-1.0.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/functions.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/fortes.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/init.js"/>'></script>

	<style type="text/css">
		<#if pgInicial?exists && pgInicial>
			@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
		</#if>

		@import url('<@ww.url value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/jquery.alerts.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/default.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/fortes.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/botoes.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/menu.css"/>');
		
		.buttonGroup { width: 970px; }
		.buttonGroupRelative { position: relative; }
		.buttonGroupFixed { position: fixed; bottom: 0px; background: url('<@ww.url includeParams="none" value="/imgs/branco_70.png"/>'); }
		.buttonGroupFixed button { margin: 5px 0px; }
	</style>
	
	${head}

</head>
<body>
	<div id="topDiv">
		<div id="userDiv">
			<span class="saudacao">
				Bem-vindo(a)&nbsp;
			</span>
			<span class="nomeUsuario"><@authz.authentication operation="nome"/>&nbsp;</span>
			<span class="nomeEmpresa">(<@authz.authentication operation="empresaNome"/>)&nbsp;&nbsp;</span>
			<br clear="all"/>
			<span class="nomeEmpresa"><@authz.authentication operation="ultimoLoginFormatado"/>&nbsp;&nbsp;-&nbsp;&nbsp; Expira em <span class="expira">00:00</span> &nbsp;&nbsp;</span>
		</div>
			
		<div id="userDiv1">
			<img src='<@ww.url includeParams="none" value="/imgs/topo_img_right.png"/>' border='0' align='absMiddle' />
		</div>
		<div id="logoDiv"><a href="<@ww.url value='/'/>"><img src='<@ww.url includeParams="none" value="/imgs/topo_ico.png"/>' border='0'/></a></div>
		
		<div id="news">
			<a id="newsIcon" href="javascript:;" title="Últimas notícias" style="display:none">
				<img src='<@ww.url includeParams="none" value="/imgs/news.png"/>' border='0'/>
			</a>
			<div id="newsCount" style="display:none"></div>
			
			<div id="newsList" style="display:none">
				<ul></ul>
			</div>
			
			<div id="newsDetails" style="display:none">
				<iframe width="960" height="480"></iframe>
			</div>
		</div>
		
		<#if REG_MSG?exists && REG_MSG != "">
			<span id="msgAutenticacao">&nbsp &nbsp &nbsp ${REG_MSG}</span>
		</#if>
	</div>
	
	<@authz.authentication operation="menuFormatado"/>
	
	<div style="clear: both"></div>
	<div id="waDiv">
		<br>
		<#if !pgInicial?exists || !pgInicial>
			<#if title != "">
				<div id="waDivTitulo">
					${title}
					<#if videoAjuda?exists>
						<span>
							<img title="Vídeo de ajuda" onclick="javascript:popupVideo(${videoAjuda}, '${calculoHash}');" src="<@ww.url includeParams="none" value="/imgs/video.png"/>" style="cursor:pointer;"/>
						</span>
					</#if>
				</div>
			</#if>
			<div class="waDivFormulario">
		</#if>
			${body}
		<#if !pgInicial?exists>
			</div>
		</#if>
		
		<br /><br />
	</div>
</body>
</html>