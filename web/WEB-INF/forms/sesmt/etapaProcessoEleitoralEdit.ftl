<html>
<head>
<@ww.head/>
<#assign desabilitado="false"/>
<#if etapaProcessoEleitoral.id?exists>
	<title>Editar Etapa do Processo Eleitoral</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
	<#if antesOuDepois == "">
		<#assign desabilitado="true"/>
	</#if>
<#else>
	<title>Inserir Etapa do Processo Eleitoral</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<script>
	function desabilitaAntesOuDepois(prazo)
	{
		elemento = document.getElementById('antesOuDepois');
		if (prazo == '0')
			elemento.disabled = "true";
		else
			elemento.disabled = "";
	}
</script>

<#assign validaForm="return validaFormulario('form',new Array('nome','_prazo'),null);">

</head>
<body>
<@ww.actionerror />
<@ww.form name="form" action="${formAction}" validate="true" method="POST">
	<@ww.textfield label="Nome" id="nome" name="etapaProcessoEleitoral.nome" required="true" maxlength="100" cssStyle="width:400px;" />
	<@ww.textfield label="Prazo Legal" id="prazoLegal" name="etapaProcessoEleitoral.prazoLegal" cssStyle="width:400px;"/>
	Prazo:*
	<br/>
	<@ww.textfield name="etapaProcessoEleitoral.prazo" id="_prazo" required="true" onblur="desabilitaAntesOuDepois(this.value);" maxlength="3" onkeypress="return(somenteNumeros(event,''));" liClass="liLeft" cssStyle="width:25px;text-align:right;"/>
	<li id="dias" class="liLeft">
	<div>dia(s)</div></li> <@ww.select name="antesOuDepois" id="antesOuDepois" disabled="${desabilitado}" list=r"#{'antes':'antes','depois':'depois'}" liClass="liLeft" /> da posse
	<br/><br/>
	<@ww.token/>
	<@ww.hidden name="etapaProcessoEleitoral.id"/>
	<@ww.hidden name="etapaProcessoEleitoral.empresa.id"/>
	<#if etapaProcessoEleitoral.eleicao?exists && etapaProcessoEleitoral.eleicao.id?exists>
		<@ww.hidden name="etapaProcessoEleitoral.eleicao.id"/>
	</#if>
</@ww.form>

<div class="buttonGroup">
	<button onclick="${validaForm}" class="btnGravar" accesskey="${accessKey}">
	</button>
	<button onclick="javascript: executeLink('list.action');" class="btnVoltar" accesskey="V">
	</button>
</div>
</body>
</html>