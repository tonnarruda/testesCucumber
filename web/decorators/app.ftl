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

	<script type="text/javascript">
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
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/functions.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/fortes.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/init.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	

	<style type="text/css">
		<#if pgInicial?exists && pgInicial>
			@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		</#if>

		@import url('<@ww.url value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/jquery.alerts.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/default.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/fortes.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/botoes.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/menu.css?version=${versao}"/>');
		
		.buttonGroup { width: 970px; }
		.buttonGroupRelative { position: relative; }
		.buttonGroupFixed { position: fixed; bottom: 0px; background: url('<@ww.url includeParams="none" value="/imgs/branco_70.png"/>'); }
		.buttonGroupFixed button { margin: 5px 0px; }
		#topDiv, #menuDropDown { min-width: 1070px; }
		
		@media (max-width: 1150px) {
		    .box_icones {
		    	margin-left: 1005px;
		    	position: absolute !important;
		    }
		}
		@media (min-width: 1150px) {
		    .box_icones {
		    	margin-left: calc((1000px + (100% - 1000px)/2) - 60px);
		    }
		}
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
		</div>
		
		<#if REG_MSG?exists && REG_MSG != "">
			<span id="msgAutenticacao">&nbsp &nbsp &nbsp ${REG_MSG}</span>
		</#if>
	</div>
	
	<@authz.authentication operation="menuFormatado"/>
	
	<div style="clear: both"></div>
	<div id="waDiv">
	 	<div style="position:fixed;" class="box_icones">	
			<div id="icones" style="top: 30px; margin-top:-16px; width: 33px; background: white; border-radius: 5px;padding: 10px;position: absolute;" class="icones">
		        <#if parametrosDoSistemaSession.codEmpresaSuporte?exists && parametrosDoSistemaSession.codClienteSuporte?exists && parametrosDoSistemaSession.codClienteSuporte != "" && parametrosDoSistemaSession.codClienteSuporte != "">
		        	<a href="http://chatonline.grupofortes.com.br/cliente/MATRIZ/${parametrosDoSistemaSession.codClienteSuporte}/${parametrosDoSistemaSession.codEmpresaSuporte}" target="_blank" title="Fortes Chat"><img src="<@ww.url includeParams="none" value="/imgs/chat_fortes.png"/>" class="icon" onmouseover="this.style='opacity: 0.6;display: block; padding: 4px;'" onmouseout="this.style='opacity: 1;display: block; padding: 4px;'" style="display: block; padding: 4px;"/></a>
		        </#if>
		        
		        <a href='http://www.logmein123.com' target='_blank' title='LogMeIn'><img src="<@ww.url includeParams="none" value="/imgs/logmeinrescue.png"/>" class="icon" onmouseover="this.style='opacity: 0.6;display: block; padding: 4px;'" onmouseout="this.style='opacity: 1;display: block; padding: 4px;'" style="display: block; padding: 4px;" /></a>
		        <a href='videoteca.action' title='Videoteca'><img src="<@ww.url includeParams="none" value="/imgs/video.png"/>" class="icon" onmouseover="this.style='opacity: 0.6;display: block; padding: 4px;'" onmouseout="this.style='opacity: 1;display: block; padding: 4px;'" style="display: block; padding: 4px;" /></a>
		        <a href="contatos.action" title="Contatos"><img src="<@ww.url includeParams="none" value="/imgs/telefone.gif"/>" class="icon" onmouseover="this.style='opacity: 0.6;display: block; padding: 4px;'" onmouseout="this.style='opacity: 1;display: block; padding: 4px;'" style="display: block; padding: 4px;" /></a>
		        <a href='http://blog.fortesinformatica.com.br/categoria/ente-rh/?utm_source=sistema&utm_medium=icone-barra-lateral&utm_content=ente-rh&utm_campaign=clique-blog' target='_blank' title='Blog'><img src="<@ww.url includeParams="none" value="/imgs/blog.png"/>" class="icon" onmouseover="this.style='opacity: 0.6;display: block; padding: 4px;'" onmouseout="this.style='opacity: 1;display: block; padding: 4px;'" height="32" width="32" style="display: block; padding: 4px;" /></a>
		        <a href='https://twitter.com/fortestec' target='_blank' title='Twitter'><img src="<@ww.url includeParams="none" value="/imgs/twitter.png"/>" class="icon" onmouseover="this.style='opacity: 0.6;display: block; padding: 4px;'" onmouseout="this.style='opacity: 1;display: block; padding: 4px;'" style="display: block; padding: 4px;" /></a>
		        <a href="https://www.facebook.com/fortestecnologia" target='_blank' title="Facebook"><img src="<@ww.url includeParams="none" value="/imgs/facebook.png"/>" class="icon" onmouseover="this.style='opacity: 0.6;display: block; padding: 4px;'" onmouseout="this.style='opacity: 1;display: block; padding: 4px;'" style="display: block; padding: 4px;" /></a>
		        <a href="https://www.linkedin.com/company/fortes-informatica" target='_blank' title="Linkedin" id="teste"><img src="<@ww.url includeParams="none" value="/imgs/linkedin.png"/>" class="icon" onmouseover="this.style='opacity: 0.6;display: block; padding: 4px;'" onmouseout="this.style='opacity: 1;display: block; padding: 4px;'" style="display: block; padding: 4px;" /></a>
		    </div>
	    </div>
		
		<br>
		<#if !pgInicial?exists || !pgInicial>
			<#if title != "">
				<div id="waDivTitulo">
					${title}
					<#if videosAjuda?exists>
						<#list videosAjuda as videoAjuda> 					
							<span style="margin-top: -9px;">
								<img title="Vídeo de ajuda" onclick="javascript:popupVideo(${videoAjuda}, '${calculoHash}');" src="<@ww.url includeParams="none" value="/imgs/video.png"/>" style="cursor:pointer;" />
							</span>
						</#list>
					</#if>
					<#if msgHelp?exists>
						<img  id="tooltipHelp" height="17" width="17" align="right" src="<@ww.url includeParams="none" value="/imgs/infoHelp.png"/>" style="cursor:pointer;"/>
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
<#if msgHelp?exists>
	<script>
		$(document).ready(function($){
			$('#tooltipHelp').qtip({
				content: '${msgHelp}',
				position: {
					corner: {
						target: 'bottomLeft',
						tooltip: 'topRight'
					}
				},
				style: { 
					width: 400,
					textAlign: 'justify',
					border: {
						radius: 4,
						color: '#e7e7e7'
					},
					background: '#F9F9F9',
					tip: 'topRight',
					name: 'light'
				}
			});
		});
	</script>
</#if>	
</html>