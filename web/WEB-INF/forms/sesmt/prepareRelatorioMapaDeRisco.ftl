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
	        var dataReferencia = $.datepicker.formatDate('dd/mm/yy', new Date());
        	if($('#localAmbiente').val() == 1){
	        	$('#wwgrp_estabelecimentoCheck').show();
	        	var estabelecimentoIds = getArrayCheckeds(document.forms[0],'estabelecimentoCheck');        	
				DWRUtil.useLoadingMessage('Carregando...');
		      	AmbienteDWR.getAmbientesByEstabelecimentos(estabelecimentoIds, dataReferencia, createListAmbientes);
	      	}
	      	else{
	      		marcarDesmarcarListCheckBox(document.forms[0], 'estabelecimentoCheck',false)
	      		$('#wwgrp_estabelecimentoCheck').hide();
	      		DWRUtil.useLoadingMessage('Carregando...');
	      		AmbienteDWR.getAmbienteChecks(${empresaSistema.id}, null, $('#localAmbiente').val(), dataReferencia, createListAmbientes);
	      	}
	    }
		
	    function createListAmbientes(data)
	    {
	      addChecksByMap("ambienteCheck", data);
	    }
	    
	    function submit(){
	    	if($('#localAmbiente').val() == 1)
	    		return validaFormulario('form', new Array('@estabelecimentoCheck','@ambienteCheck'), null)
	    	else
	    		return validaFormulario('form', new Array('@ambienteCheck'), null)
	    }
	</script>

	<title>Mapa de Risco</title>
	<#assign formAction="imprimirRelatorioMapaDeRisco.action"/>
	
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form name="form" action="${formAction}" method="POST">
		<@ww.select label="Local do Ambiente" name="localAmbiente" id="localAmbiente" list="locaisAmbiente" headerKey="" required="true" cssStyle="width: 501px;" onchange="populaAmbientes();"/>
		<@frt.checkListBox label="Estabelecimentos" required="true" id="estabelecimento" name="estabelecimentoCheck" onClick="populaAmbientes()" list="estabelecimentoCheckList" filtro="true"/>
		<@frt.checkListBox label="Ambientes" required="true" id="ambiente" name="ambienteCheck" list="ambienteCheckList" filtro="true"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="submit();" class="btnRelatorio"></button>
	</div>
</body>
</html>