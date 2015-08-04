<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>

	<#include "../ftl/mascarasImports.ftl" />
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AmbienteDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<script type="text/javascript">
		function populaAmbientes()
	    {
	      var estabelecimentoIds = getArrayCheckeds(document.forms[0],'estabelecimentoCheck');

		  if(estabelecimentoIds.length == 0 ) {
		  	createListAmbientes(null);
		  	return;
		  }
		  
	      DWRUtil.useLoadingMessage('Carregando...');
	      AmbienteDWR.getAmbientesByEstabelecimentos(createListAmbientes, estabelecimentoIds);
	    }

	    function createListAmbientes(data)
	    {
	      addChecksByMap("ambienteCheck", data);
	    }
	    
	</script>

	<title>Mapa de Risco</title>
	<#assign formAction="imprimirRelatorioMapaDeRisco.action"/>
	<#assign validarCampos="return validaFormulario('form', new Array('@estabelecimentoCheck','@ambienteCheck'), null)"/>
	
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
		<@frt.checkListBox label="Estabelecimentos" required="true" id="estabelecimento" name="estabelecimentoCheck" onClick="populaAmbientes()" list="estabelecimentoCheckList" filtro="true"/>
		<@frt.checkListBox label="Ambientes" required="true" id="ambiente" name="ambienteCheck" list="ambienteCheckList" filtro="true"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnRelatorio"></button>
	</div>
</body>
</html>