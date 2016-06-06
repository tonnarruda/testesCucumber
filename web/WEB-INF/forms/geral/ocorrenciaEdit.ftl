<html>
<head>
<@ww.head/>
<#if ocorrencia.id?exists>
	<title>Editar Ocorrência</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Ocorrência</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
<script type="text/javascript">
	$(function() {
		$('#ajuda').qtip({
			content: '<div style="text-align:justify">Não é possível integrar ou desintegrar esse tipo de ocorrência com o Fortes Pessoal, pois essa alteração pode gerar inconsistência.</div>',
			style: { width: 400 }
		});
	});
</script>
<#assign validarCampos="return validaFormulario('form', new Array('descricao','pontuacao'), null)"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}"  validate="true" method="POST">	
		<@ww.textfield label="Descrição" name="ocorrencia.descricao" id="descricao" cssStyle="width: 282px;" maxLength="40" required="true"/>
		<@ww.textfield label="Pontuação" name="ocorrencia.pontuacao" id="pontuacao" maxLength="10" onkeypress="return(somenteNumeros(event,'-'));" cssStyle="width:70px;"  required="true" cssClass="pontuacao"/>

		<#if empresaIntegradaComAC>
			<#if ocorrencia.id?exists>
				<@ww.hidden name="ocorrencia.integraAC"/>
				<#if ocorrencia.integraAC>
					Esta ocorrência está integrada com o Fortes Pessoal
				<#else>
					Esta ocorrência não está integrada com o Fortes Pessoal
				</#if>		
				<img id="ajuda" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -5px" /></h2>
			<#else>
				<@ww.checkbox label="Enviar para o Fortes Pessoal" id="integraAC" name="ocorrencia.integraAC" labelPosition="left"/>
			</#if>
		</#if>
		
		<@ww.checkbox label="Considerar como absenteísmo" name="ocorrencia.absenteismo"  labelPosition="left"/>
		<@ww.checkbox label="Exibir em performance profissional" name="ocorrencia.performance"  labelPosition="left"/>

		<@ww.hidden label="Id" name="ocorrencia.id" />
		<@ww.hidden name="ocorrencia.codigoAC" />
	</@ww.form>
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}"></button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V"></button>
	</div>
</body>
</html>