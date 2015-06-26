<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign ww=JspTaglibs["/WEB-INF/tlds/webwork.tld"] />
<html>
<head>
<@ww.head/>
<title>${pesquisa.titulo}</title>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/preview.css?version=${versao}"/>');
</style>
<script type="text/javascript">
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


<div id="respostaColaborador">
	<br>
	<h3>${pesquisa.titulo}</h3>
	<fieldset>
		<legend>Colaborador</legend>
		<p>Colaborador: ${colaborador.nomeComercial}</p>
		<p>Area: ${colaborador.areaOrganizacional.nome}</p>
	</fieldset>

	<div class="perguntas">
		<#list pesquisa.perguntas as pergunta>
			<p><b>${pergunta.ordem} -</b> ${pergunta.texto}</p>

			<ul class="resposta">
				<#list respostas as resposta>
					<#if resposta.pergunta.id == pergunta.id>
						<#assign marcaResposta = false />
						<#list colaboradorRespostas as respostaColaborador>
							<#if resposta.id == respostaColaborador.resposta.id>
								<#assign marcaResposta = true />
							</#if>
						</#list>
						<li>
							<#if marcaResposta>
								<img border="0" src="<@ww.url includeParams="none" value="/imgs/check.gif"/>">
							<#else>
								<img border="0" src="<@ww.url includeParams="none" value="/imgs/no_check.gif"/>">
							</#if>
							${resposta.texto}
						</li>
					</#if>
				</#list>

				<#if pergunta.comentario>
					<div class="comentario">
						<li>
							<b>${pergunta.textoComentario}</b>
						</li>
						<li>
							<#list colaboradorRespostas as respostaColaborador>
								<#if pergunta.id == respostaColaborador.pergunta.id && respostaColaborador.comentario?exists>
									${respostaColaborador.comentario}
								</#if>
							</#list>
						</li>
					</div>
				</#if>
			</ul>

		</#list>
	</div>
</div>

	<br>
	<br>
	<div class="buttonGroup">
		<button onclick="CallPrint('respostaColaborador')" class="btnImprimir" accesskey="I">
		</button>
		<button onclick="javascript:window.close();" class="btnFechar" accesskey="F">
		</button>
	</div>
</body>
</html>