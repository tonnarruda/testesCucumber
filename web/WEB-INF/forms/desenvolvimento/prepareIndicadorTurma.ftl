<html>
<head>

<@ww.head/>
	<title>Indicador - Qtd. de Pessoas Treinadas</title>

	<#include "../ftl/mascarasImports.ftl" />
</head>
<body>
<@ww.actionmessage />

<@ww.form name="form" action="imprimirRelatorio.action" validate="true" method="POST">
	<@ww.label name="Período"/>
	<@ww.datepicker name="dataIni" id="dataIni" liClass="liLeft" cssClass="mascaraData"/>
	<@ww.label name="até "value="a" liClass="liLeft"/>
	<@ww.datepicker name="dataFim" id="dataFim" cssClass="mascaraData"/>
</@ww.form>


<div class="buttonGroup">
	<button onclick="document.form.submit();" class="btnRelatorio" accesskey="I">
	</button>
</div>
</body>
</html>