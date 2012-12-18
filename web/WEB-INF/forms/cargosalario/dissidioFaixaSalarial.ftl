<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Reajuste Coletivo/Dissídio para Cargos/Faixas Salariais</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formataValores.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/FaixaSalarialDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript'>
		function populaFaixas()
		{
			var cargosIds = getArrayCheckeds(document.forms[0],'cargosCheck');
			FaixaSalarialDWR.getByCargos(createListFaixas, cargosIds);
		}
		
		function createListFaixas(data)
		{
			addChecks('faixasCheck', data)
		}
	</script>
</head>

<body>

	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" action="aplicarDissidio.action" method="POST">
		<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos*" list="cargosCheckList" onClick="populaFaixas();"/>
		<@frt.checkListBox name="faixasCheck" id="faixasCheck" label="Faixas Salariais" list="faixasCheckList" />
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="" class="btnGravar"></button>
	</div>
</body>
</html>