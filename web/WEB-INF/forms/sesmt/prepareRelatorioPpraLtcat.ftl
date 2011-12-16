<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>

	<#include "../ftl/mascarasImports.ftl" />
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AmbienteDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<script type="text/javascript">
		function populaAmbientes()
	    {
	      var estabelecimentoId = document.getElementById("estabelecimento").value;

	      DWRUtil.useLoadingMessage('Carregando...');
	      AmbienteDWR.getAmbienteChecks(createListAmbientes, estabelecimentoId);
	    }

	    function createListAmbientes(data)
	    {
	      addChecks("ambienteCheck", data);
	    }
	    
	    function valida()
	    {
	    	gerarPpra = document.getElementById('ppra');
	    	gerarLtcat = document.getElementById('ltcat');
	    	 
	    	if (gerarPpra.checked == false && gerarLtcat.checked == false)
	    	{
	    		jAlert('Selecione o relatório a ser gerado.');
	    		return false;
	    	}
	    	else
	    	{
	    	 	return validaFormulario('form', new Array('data','estabelecimento'), new Array('data'));
	    	 }
	    }
	</script>

	<title>PPRA e LTCAT</title>
	<#assign formAction="gerarRelatorio.action"/>
	
	<#assign date = "" />
    <#if data?exists>
		<#assign date = data?date/>
	</#if>

</head>
<body>
	<@ww.actionerror />
	
	<#assign validarCampos="return valida();"/>
	
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
		<@ww.datepicker label="Data" id="data" name="data" required="true" cssClass="mascaraData" value="${date}" />
		<@ww.select label="Estabelecimento" id="estabelecimento" name="estabelecimento.id" required="true" list="estabelecimentos" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="" onchange="javascript:populaAmbientes();" cssStyle="width:240px;"/>
		<@frt.checkListBox label="Ambientes" id="ambiente" name="ambienteCheck" list="ambienteCheckList" />
		<@ww.checkbox label="Exibir Composição do SESMT" id="exibirComposicaoSesmt" name="exibirComposicaoSesmt" labelPosition="left"/>
		Gerar:
		<@ww.checkbox label="PPRA" id="ppra" name="gerarPpra" labelPosition="left"/>
		<@ww.checkbox label="LTCAT" id="ltcat" name="gerarLtcat" labelPosition="left"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnRelatorio"></button>
	</div>
</body>
</html>