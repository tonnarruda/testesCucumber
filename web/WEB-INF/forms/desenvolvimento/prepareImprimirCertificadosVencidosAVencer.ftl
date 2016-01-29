<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Certificações vencidas e a vencer</title>
	
	<script type="text/javascript">
		$(function() {
			$('#tooltipHelp').qtip({
				content: 'Algumas Certificações estão desabilitadas por não possuirem periodicidade.'
			});
			
			$('#tooltipHelpMeses').qtip({
				content: 'O Campo só será habilitado quando o select "Considerar colaboradores" for por "Certificados".'
			});

			habilitaCampos();
		});
		
		function habilitaCampos()
		{
			if($('#colaboradorCertificado').is(':checked')){
				$('#meses,#dataIni,#dataFim').removeAttr('disabled').css("background-color", "#FFF");
				$('#dataIni_button,#dataFim_button').show();
			}else{		
				$('#meses').val('').attr('disabled', 'disabled').css("background-color", "#EFEFEF");
				$('#dataIni,#dataFim').val('  /  /    ').attr('disabled', 'disabled').css("background-color", "#EFEFEF");
				$('#dataIni_button,#dataFim_button').hide();
			}
		}
		
		function submit()
		{
			var submit = validaFormulario('form', new Array('@certificacoesCheck'), new Array('dataIni', 'dataFim'), true);
			
			if(!$('#colaboradorCertificado').is(':checked') && !$('#colaboradorNaoCertificado').is(':checked')){
				$('#wwgrp_colaboradorCertificado,#wwgrp_colaboradorNaoCertificado').css("background-color", "rgb(255, 238, 194)");
				jAlert("Preencha pelo menos um dos campos indicados.");
			}else if(submit)
				document.form.submit();
		}
	</script>
	
	<style type="text/css">
		.div{
			padding-top: 5px;
			padding-bottom: 5px;
		}
	</style>
	
	<#include "../ftl/mascarasImports.ftl" />
	
	<#if dataIni?exists>
		<#assign dateIni = dataIni?date/>
	<#else>
		<#assign dateIni = ""/>
	</#if>
	<#if dataFim?exists>
		<#assign dateFim = dataFim?date/>
	<#else>
		<#assign dateFim = ""/>
	</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('@certificacoesCheck'), new Array('dataIni', 'dataFim'))"/>
	
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@ww.form name="form" action="imprimirCertificadosVencidosAVencer.action" onsubmit="${validarCampos}" validate="true" method="POST">
	
		Certificações*:
		<img id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16"  />
		<@frt.checkListBox name="certificacoesCheck" id="certificacoesCheck" list="certificacoesCheckList" filtro="true"/>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" filtro="true"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>

		Considerar colaboradores:*
		<@ww.checkbox label="Não certificados" id="colaboradorNaoCertificado" name="colaboradorNaoCertificado" liClass="liLeft" labelPosition="left" cssStyle="margin-left: 15px;"/>
		<@ww.checkbox label="Certificados" id="colaboradorCertificado" name="colaboradorCertificado" liClass="liLeft" labelPosition="left" cssStyle="margin-left: 15px;" onclick="habilitaCampos()"/>
		
		<@ww.div cssStyle="margin-left: 50px;">
			Período Certificado:<br>
			<@ww.datepicker name="dataIni" id="dataIni" value="${dateIni}" liClass="liLeft" cssClass="mascaraData validaDataIni "/>
			<@ww.label value="a" liClass="liLeft" />
			<@ww.datepicker name="dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" liClass="liLeft" />
			<img id="tooltipHelpMeses" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: 1px" />
			</br>
			Colaboradores certificados com a certificação a vencer em 
			<@ww.textfield id="meses" theme="simple" name="meses" onkeypress="return somenteNumeros(event,'');" maxLength="3" cssStyle="width:30px; text-align:right; margin-top: 8px;"/>
			meses.
			<img id="tooltipHelpMeses" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -5px" />
		</@ww.div>
		
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="$('form[name=form]').attr('action', 'imprimirCertificadosVencidosAVencer.action');submit();" class="btnRelatorio" ></button>
		<button onclick="$('form[name=form]').attr('action', 'imprimirCertificadosVencidosAVencerXlS.action');submit();" class="btnRelatorioExportar" ></button>
	</div>
	
</body>
</html>