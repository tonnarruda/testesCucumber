<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>

	<#include "../ftl/mascarasImports.ftl" />
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/moment.min.2.18.1.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AmbienteDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<script type="text/javascript">
		function toggleEstabecimento(){
			$('#ambiente').val('');
			if($('#localAmbiente').val() == 2){
				$('#estabelecimento').val('');
				$('#estabelecimento').parent().parent().hide();
			}
			else
				$('#estabelecimento').parent().parent().show();
		}
	
		function populaAmbientes()
	    {
        	var estabelecimentoId = document.getElementById("estabelecimento").value;
        	if(moment($("#data").val(),'DD/MM/YYYY',true).locale('pt-BR').isValid())
 				var dataReferencia = $("#data").val();
 			else
 				var dataReferencia = $.datepicker.formatDate('dd/mm/yy', new Date());        	
        	
			DWRUtil.useLoadingMessage('Carregando...');
			AmbienteDWR.getAmbienteChecks(${empresaSistema.id}, estabelecimentoId, $('#localAmbiente').val(), dataReferencia, createListAmbientes);
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
	    		if($('#localAmbiente').val() == 1)
	    	 		return validaFormulario('form', new Array('data','estabelecimento'), new Array('data'));
	    	 	else
	    	 		return validaFormulario('form', new Array('data'), new Array('data'));
	    	}
	    }
	    $(function(){
			$('#ppra').change(function() {
				if (this.checked)
					$('#exibirComposicaoSesmt').removeAttr('disabled');
				else
					$('#exibirComposicaoSesmt').attr("disabled", true);
					
				if (this.checked && $(ltcat).attr("checked"))
					$('#quebrarPagina').removeAttr('disabled');
				else
					$('#quebrarPagina').attr("disabled", true);
			});
			
			$('#ppra').change();
			toggleEstabecimento();
		});
	
	   $(function(){
			$('#ltcat').change(function() {
				if (this.checked && $(ppra).attr("checked"))
					$('#quebrarPagina').removeAttr('disabled');
				else
					$('#quebrarPagina').attr("disabled", true);
			});
		});
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
	<@ww.actionmessage />
	
	<#assign validarCampos="return valida();"/>
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
		<@ww.datepicker label="Data" id="data" name="data" required="true" cssClass="mascaraData" value="${date}" onchange="populaAmbientes();" onblur="populaAmbientes();" />
		<@ww.select label="Local do Ambiente" name="localAmbiente" id="localAmbiente" list="locaisAmbiente" headerKey="" required="true" cssStyle="width: 501px;" onchange="toggleEstabecimento();populaAmbientes();"/>
		<@ww.select label="Estabelecimento" id="estabelecimento" name="estabelecimento.id" required="true" list="estabelecimentos" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="" onchange="javascript:populaAmbientes();" cssStyle="width:501px;"/>
		<@frt.checkListBox label="Ambientes" id="ambiente" name="ambienteCheck" list="ambienteCheckList" filtro="true"/>
		Gerar:*
		<@ww.checkbox label="PPRA" id="ppra" name="gerarPpra" labelPosition="left"/>
		<@ww.checkbox label="Exibir Composição do SESMT" id="exibirComposicaoSesmt" name="exibirComposicaoSesmt" labelPosition="right"  cssStyle="float:left; margin-left: 15px;"/>
		<@ww.checkbox label="LTCAT" id="ltcat" name="gerarLtcat" labelPosition="left"/>
		<@ww.checkbox label="Imprimir PPRA e LTCAT em páginas separadas" id="quebrarPagina" name="quebrarPagina" labelPosition="left"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnRelatorio"></button>
	</div>
</body>
</html>