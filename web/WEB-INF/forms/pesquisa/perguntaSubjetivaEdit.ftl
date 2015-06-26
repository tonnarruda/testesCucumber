<html>
<head>
<@ww.head/>
<title>Perguntas</title>
<#assign formAction="insertSubjetiva.action?pesquisa.tipoAvaliacao=${pesquisa.tipoAvaliacao}"/>
<#assign accessKey="G"/>
<#assign voltarAction="../pesquisa/list.action?tipoAvaliacao=${pesquisa.tipoAvaliacao}"/>

<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
<script type="text/javascript">

	function createInputText() {
		var campo = "";
		campo += 	"<div id=\"wwlbl_insert_textoRespostas\" class=\"wwlbl\">";
		campo += 		"<label for=\"insert_textoRespostas\" class=\"desc\">Nova Opção de Resposta:</label>";
		campo += 	"</div>";
		campo += 	"<div id=\"wwctrl_insert_textoRespostas\" class=\"wwctrl\">";
		campo += 		"<input type=\"text\" name=\"textoRespostas\" id=\"insert_textoRespostas\" size=\"50\"/>";
		campo += 	"</div>";
		campo += "<br/>";

		valorDiv = document.getElementById('novaResposta').innerHTML;

		DWRUtil.setValue("novaResposta",valorDiv+campo);
	}

</script>
</head>
<body>
<@ww.actionerror />
<@ww.form name="form" action="${formAction}" validate="true" method="POST">
		<#if (qtdPerguntas>0) >
			<#list 1..qtdPerguntas as qtde>
				<@ww.textarea label="Pergunta ${qtde}" name="textoPerguntas"/><br/>
			</#list>
		</#if>
		<#--<div id="novaResposta"></div>
		<a href="#" onclick="createInputText();">Incluir nova opção de resposta</a>

		<#list 1..qtdOpcoes as qtde>
			<@ww.textfield label="Opção de Resposta ${qtde}" name="textoRespostas" cssStyle="width:445px;" /><br/>
		</#list>-->


	<@ww.hidden name="pesquisa.id" />
	<@ww.hidden name="qtdRespostas" />

</@ww.form>


<div class="buttonGroup">
	<button onclick="document.form.submit();" class="btnGravar" accesskey="${accessKey}">
	</button>
	<button onclick="window.location='${voltarAction}'" class="btnCancelar" accesskey="C">
	</button>
</div>
</body>
</html>