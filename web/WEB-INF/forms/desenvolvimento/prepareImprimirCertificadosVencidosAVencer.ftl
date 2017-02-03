<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Colaboradores x Certificações</title>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CertificacaoDWR.js?version=${versao}"/>'></script>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	
	<script type="text/javascript">
		$(function() {
			$('#wwctrl_certificacoesCheck * span').eq(0).removeAttr('onclick').css('color', '#6E7B8B').css('cursor', 'default');

			$('#tooltipHelpPeriodo').qtip({
				content: 'Os campos "Período Certificado" e "Com certificação a vencer em até" só serão habilitados ao marcar a opção "Certificados".'
			});

			habilitaCampos();
			populaColaborador();
			
			$('#listCheckBoxcolaboradoresCheck tbody').remove();
			$('#listCheckBoxcolaboradoresCheck').append('<tbody> <tr> <td colspan="7"> <div class="info">  <ul> <li>Utilize os filtros acima para popular os colaboradores. </br> Filtro obrigatório: "Certificações".</li> </ul> </div> </tr></td> </tbody>');
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
			$('#wwgrp_colaboradorCertificado,#wwgrp_colaboradorNaoCertificado').css("background-color", "#FFF");
			var submit = validaFormulario('form', new Array('@certificacoesCheck'), new Array('dataIni', 'dataFim'), true);
			
			if(!$('#colaboradorCertificado').is(':checked') && !$('#colaboradorNaoCertificado').is(':checked')){
				$('#wwgrp_colaboradorCertificado,#wwgrp_colaboradorNaoCertificado').css("background-color", "rgb(255, 238, 194)");
				jAlert("Preencha os campos indicados.");
			}else if(submit){
				processando('${urlImgs}');
				document.form.submit();
			}
		}
		
		function populaColaborador()
		{
			$('#listCheckBoxcolaboradoresCheck tbody').remove();
			$('#listCheckBoxcolaboradoresCheck label').remove();
			dwr.util.useLoadingMessage('Carregando...');
			var areasIds = getArrayCheckeds(document.forms[0], 'areasCheck');
			var estabelecimentosIds = getArrayCheckeds(document.forms[0], 'estabelecimentosCheck');
			var certificacoesIds = getArrayCheckeds(document.forms[0], 'certificacoesCheck');
			
			if(certificacoesIds.length > 0)
				CertificacaoDWR.getColaboradores($('#dataIni').val(), $('#dataFim').val(),   
												$('#colaboradorCertificado').is(':checked'), $('#colaboradorNaoCertificado').is(':checked'),  
												$('#meses').val(), areasIds, estabelecimentosIds, certificacoesIds, $('#situacao').val(), createListColaborador);
		}
		
		function createListColaborador(data)
		{
			$('#listCheckBoxcolaboradoresCheck tbody').remove();
			$('#listCheckBoxcolaboradoresCheck label').remove();
			if(data.length > 0)
				addChecksByCollection("colaboradoresCheck", data);
			else
				$('#listCheckBoxcolaboradoresCheck').append('<tbody> <tr> <td colspan="7"> <div class="info">  <ul> <li>Não existem colaboradores para o filtro informado acima.</li> </ul> </div> </tr></td> </tbody>');
		}
		
		function validaQtd()
		{
		    if($("input[name='certificacoesCheck']:checked").size() >= 3)
		        $("input[name='certificacoesCheck']").not(':checked').attr('disabled','disabled').parent().css('color', '#DEDEDE');
		    else
		        $("input[name='certificacoesCheck']").removeAttr('disabled').parent().css('color', '#5C5C5A');
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
		<#assign dateIni = "  /  /    "/>
	</#if>
	<#if dataFim?exists>
		<#assign dateFim = dataFim?date/>
	<#else>
		<#assign dateFim = "  /  /    "/>
	</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('@certificacoesCheck'), new Array('dataIni', 'dataFim'))"/>
	
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@ww.form name="form" action="imprimirCertificadosVencidosAVencer.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<fieldset style="padding: 5px 0px 5px 5px; width: 495px;">
			<legend>
				Considerar colaboradores:*  
				<img id="tooltipHelpPeriodo" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -5px" />
			</legend>
			<@ww.checkbox label="Não certificados" id="colaboradorNaoCertificado" name="colaboradorNaoCertificado" liClass="liLeft" labelPosition="left" cssStyle="margin-left: 15px;"  onchange="populaColaborador()"/>
			<@ww.checkbox label="Certificados" id="colaboradorCertificado" name="colaboradorCertificado" liClass="liLeft" labelPosition="left" cssStyle="margin-left: 15px;" onclick="habilitaCampos()" onchange="populaColaborador()"/>
			
			<@ww.div cssStyle="margin-left: 50px;">
				Período em que os colaboradores foram certificados:<br>
				<@ww.datepicker name="dataIni" id="dataIni" value="${dateIni}" liClass="liLeft" cssClass="mascaraData validaDataIni" onchange="populaColaborador();"/>
				<@ww.label value="a" liClass="liLeft" />
				<@ww.datepicker name="dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" onchange="populaColaborador();"/>
				Com certificação a vencer em até 
				<@ww.textfield id="meses" theme="simple" name="mesesCertificacoesAVencer" onkeypress="somenteNumeros(event,'');" maxLength="3" cssStyle="width:30px; text-align:right; margin-top: 8px;" onchange="populaColaborador();"/>
				meses.
			</@ww.div>
		</fieldset>
		</br>
		<@frt.checkListBox name="certificacoesCheck" label="Certificações (máx. 3 opções)" id="certificacoesCheck" list="certificacoesCheckList" filtro="true" onClick="populaColaborador();validaQtd();" required="true"/>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" filtro="true" onClick="populaColaborador();"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true" selectAtivoInativo="true" onClick="populaColaborador();"/>
		<@frt.checkListBox name="colaboradoresCheck" id="colaboradoresCheck" label="Colaboradores" list="colaboradoresCheckList" filtro="true"/>
		<@ww.select label="Situação do colaborador" name="situacao" id="situacao" list="situacaos" onchange="populaColaborador();" cssStyle="width: 500px;"/>
		<@ww.select label="Agrupar por" name="agruparPor" id="agruparPor" list=r"#{'C':'Colaborador','T':'Certificação'}" cssStyle="width: 500px;"/>
	</@ww.form>
	<div class="buttonGroup">
		<button onclick="$('form[name=form]').attr('action', 'imprimirCertificadosVencidosAVencer.action');submit();" class="btnRelatorio" ></button>
		<button onclick="$('form[name=form]').attr('action', 'imprimirCertificadosVencidosAVencerXlS.action');submit();" class="btnRelatorioExportar" ></button>
	</div>
</body>
</html>