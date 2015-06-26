<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<title>${pesquisa.titulo}</title>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/preview.css?version=${versao}"/>');
</style>
<script src='<@ww.url includeParams="none" value="/js/functions.js?version=${versao}"/>'></script>
<script type='text/javascript'>
	function CallPrint(strid)
	{
		var browserName=navigator.appName;

		var prtContent = document.getElementById(strid);
		var WinPrint = window.open('','','letf=20,top=20,width=680,height=368,toolbar=0,scrollbars=1,status=0');

		var cssLink = "";
		cssLink	+=	"<style type='text/css'>";
		cssLink	+=	"@import url('<@ww.url includeParams="none" value="/css/preview.css?version=${versao}"/>');";
		cssLink	+=	"ul{list-style-type:none;}";
		cssLink	+=	"</style>";

		WinPrint.document.write(cssLink + prtContent.innerHTML);

		WinPrint.document.close();
		WinPrint.focus();
		WinPrint.print();
		WinPrint.close();
	}
</script>

</head>
<body>

<div name="respostaColaborador">
	<br>
	<h3>${pesquisa.titulo}</h3>
	<fieldset>
		<legend>Colaborador</legend>
		<p>Colaborador: ${colaborador.nomeComercial}</p>
		<p>Area: ${colaborador.areaOrganizacional.nome}</p>
	</fieldset>

	<#assign zebrar=true/>

	<div class="perguntas">
		<table class="tblSoma" width="100%" cellpadding="0" cellspacing="2">
		<tr>
			<td width="95%"><b>Pergunta</b></td>
			<td width="5%"><b>Nota</b></td>
		</tr>
		<#list pesquisa.perguntas as pergunta>
		<#if zebrar>
			<tr class="linhaPintada">
			<#assign zebrar=false/>
		<#else>
			<tr>
			<#assign zebrar=true/>
		</#if>
			<td><b>${pergunta.ordem} -</b> ${pergunta.texto}</td>
			<td>
		<#list colaboradorRespostas as respostaColaborador>
			<#if pergunta.id == respostaColaborador.pergunta.id>
				${respostaColaborador.valor}
			</#if>
		</#list>
			</td>
		</#list>
		</tr>
		<table>
	</div>
</div>
	<br>
	<br>
	<div class="buttonGroup">
		<button onclick="CallPrint('respostaColaborador')" class="btnImprimir" accesskey="I">
		</button>
		<button onclick="javascript:window.close();" class="btnFechar" accesskey="F">
		</button>
	</div></body>
</html>