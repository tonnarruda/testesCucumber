<html>
<head>
	<@ww.head/>
	<#if extintor.id?exists>
		<title>Editar Extintor</title>
		<#assign formAction="update.action"/>
	<#else>
		<title>Inserir Extintor</title>
		<#assign formAction="insert.action"/>
	</#if>

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>

	<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');</style>

	<script type="text/javascript">
		var fabribantes = [${fabribantes}];
		var localizacoes = [${localizacoes}];

		$(function() {
			$("#fabricante").autocomplete(fabribantes);
		});
		$(function() {
			$("#localizacao").autocomplete(localizacoes);
		});
	</script>

	<#assign validarCampos="return validaFormulario('form', new Array('estabelecimento','localizacao','tipo'), null)"/>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" method="POST">
		<@ww.select label="Estabelecimento" name="extintor.estabelecimento.id" id="estabelecimento" required="true" list="estabelecimentos" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..."/>
		<@ww.select label="Tipo" id="tipo" required="true" name="extintor.tipo" list="tipos" headerKey="" headerValue="Selecione..."/>
		<@ww.textfield label="Localização" name="extintor.localizacao" id="localizacao" required="true" maxLength="50"/>
		<@ww.textfield label="Fabricante" name="extintor.fabricante" id="fabricante" maxLength="50"/>
		<@ww.textfield label="Número do Cilindro" name="extintor.numeroCilindro" cssStyle="width:70px;text-align:right;" maxLength="10" onkeypress="return(somenteNumeros(event,''));"/>
		<@ww.textfield label="Capacidade (ex.: 12kg)" cssStyle="width:70px;text-align:right;" name="extintor.capacidade" maxLength="10"/>
		
		<@ww.select label="Ativo" name="extintor.ativo" id="ativo" list=r"#{true:'Sim',false:'Não'}" cssStyle="width: 50px;" />

		<li>
			<fieldset>
				<ul>
				<legend>Periodicidade máxima (em meses) para:</legend>
	
				<@ww.textfield label="Recarga" name="extintor.periodoMaxRecarga" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:35px;text-align:right;" maxLength="5"/>
				<@ww.textfield label="Inspeção" name="extintor.periodoMaxInspecao" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:35px;text-align:right;" maxLength="5"/>
				<@ww.textfield label="Teste hidrostático" name="extintor.periodoMaxHidrostatico" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:35px;text-align:right;" maxLength="5"/>
				</ul>
			</fieldset>
		</li>

		<@ww.token/>
		<@ww.hidden name="extintor.id" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnGravar"></button>
		<button onclick="window.location='list.action'" class="btnVoltar"></button>
	</div>
</body>
</html>