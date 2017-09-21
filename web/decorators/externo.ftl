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
	<#if empresaId?exists>
		<#assign urlServlet = "${request.contextPath}/externo/layout?empresaId=${empresaId}&tipo=" />
	<#else>
		<#assign urlServlet = "${request.contextPath}/externo/layout?tipo=" />
	</#if>
	
	<#assign topDivImg = "${urlServlet}menu1" />
	<#assign userDivImg = "${urlServlet}menu2" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-1.4.4.min.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.alerts.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.numberformatter-1.1.0.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/functions.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/fortes.js"/>'></script>


	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/jquery.alerts.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/externo.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/fortes.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/default.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/botoes.css"/>');

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
		
		#topContent {
			width: 800px;
			margin: 0 auto;
		}
		
		#topContent .saudacao {
			font-size: 11px;
			margin: 3px 0;
			color: #ffF;
		}
		
		#topContent ul {
			float: right;
			margin: 12px 0;
		}
		
		#topContent ul li {
			display: inline;
			padding: 5px 7px;
			background-color: #069;
		}
		
		#topContent ul li a {
			text-decoration: none;
			color: #fff;
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
			<div id="topContent">
				<#if SESSION_CANDIDATO_ID?exists>
					<ul>
						<li><a href="prepareListAnuncio.action">Vagas Abertas</a></li>
						<li><a href="prepareUpdate.action?moduloExterno=true&empresaId=${SESSION_EMPRESA}&candidato.id=${SESSION_CANDIDATO_ID}">Editar Currículo</a></li>
						<li><a href="listDocumentosAnexos.action?documentoAnexo.origem=E&documentoAnexo.origemId=${SESSION_CANDIDATO_ID}">Anexar Documentos</a></li>
						<li><a href="prepareUpdateSenha.action?moduloExterno=true&empresaId=${SESSION_EMPRESA}&candidato.id=${SESSION_CANDIDATO_ID}">Alterar Senha</a></li>
						<li><a href="logoutExterno.action?empresaId=${SESSION_EMPRESA}">Sair</a></li>
					</ul>
					<div class="saudacao">
						Bem vindo(a)<br />
						${SESSION_CANDIDATO_NOME_RESUMIDO}<br />
						CPF ${SESSION_CANDIDATO_CPF}
					</div>
				</#if>
			</div>
		</div>
		<br clear="all"/><br/>
		<div id="mainDiv">
			${body}
		</div>
	</body>
</html>