<#assign ww=JspTaglibs["/WEB-INF/tlds/webwork.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="Content-Language" content="pt-br" />
	<meta http-equiv="Cache-Control" content="no-cache, no-store" />
	<meta http-equiv="Pragma" content="no-cache, no-store" />
	<meta http-equiv="Expires" content="0" />

	<link rel="SHORTCUT ICON" href="../imgs/favicoExterno.ico">
	<title>${title}</title>

	<#-- os seguintes são as chamadas ao Servlet que serve as imagens
		 do módulo externo: imagens customizáveis do topo do layout
		 (sobrescreve as imagens indicadas no default.css)
	-->
	<#assign topDivImg = "${request.contextPath}/externo/layout?tipo=menu1" />
	<#assign userDivImg = "${request.contextPath}/externo/layout?tipo=menu2" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-1.4.4.min.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.numberformatter-1.1.0.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/functions.js?version=${versao}">'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/fortes.js?version=${versao}"/>'></script>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/trafego.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/botoes.css?version=${versao}"/>');

		#topDiv {
			width: 100%;
			height: 43px;
			background-image: url(${topDivImg});
		}

		#userDiv1 {
			display: inline;
			float: right;
			background-image: url(${userDivImg});
			background-repeat: repeat-x;
		}

		#userDiv {
			height: 43px;
			display: inline;
			float: right;
			background-image: url(${userDivImg});
			background-repeat: repeat-x;
		}
	</style>

	<!--[if IE]>
	<style type="text/css" media="screen">
	body
	{
		behavior: url(<@ww.url includeParams="none" value="/css/csshover2.htc"/>);
	}
	.smenuDiv
	{
		width: 100%;
	}
	#mainDiv
	{

	}

	.liLeft
	{
		margin-right:0px;
	}
	</style>
	<![endif]-->
	
	${head}

	</head>
	<body>
		<div id="topDiv">
			<div id="userDiv">
				<span class="saudacao">
					<#if SESSION_CANDIDATO_NOME?exists && SESSION_EMPRESA?exists>
						Bem-vindo(a)&nbsp;
						${SESSION_CANDIDATO_NOME}&nbsp;
					<#else>
						Identifique-se&nbsp;&nbsp;&nbsp;&nbsp;
					</#if>
				</span>
			</div>
			<div id="userDiv1">
				<img src='${request.contextPath}/externo/layout?tipo=menu3' border='0' align='absMiddle' />
			</div>
		</div>
		<br/><br/>
		<div id="mainDiv">
			${body}
		</div>
	</body>
</html>