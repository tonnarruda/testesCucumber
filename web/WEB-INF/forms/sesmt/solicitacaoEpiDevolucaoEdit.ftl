<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	
	<#if solicitacaoEpiItemDevolucao?exists && solicitacaoEpiItemDevolucao.id?exists>
		<title>Editar Devolução de EPI</title>
		<#assign formAction="updateDevolucao.action"/>
	<#else>
		<title>Inserir Devolução de EPI</title>
		<#assign formAction="insertDevolucao.action"/>
	</#if>
	
	<script type="text/javascript" src='<@ww.url includeParams="none" value="/dwr/interface/SolicitacaoEpiDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<#if solicitacaoEpiItemDevolucao?exists && solicitacaoEpiItemDevolucao.dataDevolucao?exists>
			<#assign data = solicitacaoEpiItemDevolucao.dataDevolucao?string('dd/MM/yyyy')>
		<#else>
			<#assign data = "">
	</#if>
	
	<#include "../ftl/mascarasImports.ftl" />
	
	<#-- Não apague, da um erro que nem o babau achou, e ainda mais nem sempre acontece, logo não apague (erro no stack.setValue("#datepicker_js_included", true)) -->
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/webwork/jscalendar/" encode='false'/>calendar.js"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/webwork/jscalendar/lang/" encode='false'/>calendar-${parameters.language?default("en")}.js"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/webwork/jscalendar/" encode='false'/>calendar-setup.js"></script>
	<#-- Não apague, da um erro que nem o babau achou, e ainda mais nem sempre acontece, logo não apague (erro no stack.setValue("#datepicker_js_included", true)) -->
	
	<script type="text/javascript">
		function validaDataDevolucao(submeter){
			if($('#dataDevolucao').val()){
				DWRUtil.useLoadingMessage('Carregando...');
				SolicitacaoEpiDWR.validaDataDevolucao($('#dataDevolucao').val(), $('#solicitacaoEpiItemId').val(), $('#solicitacaoEpiItemDevolucaoId').val(), $('#qtdDevolvida').val(), $('#solicitacaoEpiId').val(),
				function(data){	
					if(data){
						$('.warning').remove();
						$('#alerta').append('<div class="warning"><div style="float:right;"><a title="Ocultar" href="javascript:;" onclick="$(this).parent().parent().remove();">x</a></div><div style="margin: 20px 20px 20px 0px;" >' + data + '</div></div>');
						$('.btnGravar').css('opacity', '0.2').attr('disabled','disabled');
					}else if(submeter){
						return validaFormulario('form', new Array('dataDevolucao','qtdDevolvida'), new Array('dataDevolucao'));
					}else{
	  					$('.warning').remove();
						$('.btnGravar').css('opacity', '1').removeAttr('disabled');
					}						
				});
			}
		}
	</script>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	<div id="alerta"></div>

	<@ww.form name="form" action="${formAction}" method="POST">
		<@ww.datepicker label="Data da Devolução" name="solicitacaoEpiItemDevolucao.dataDevolucao" id="dataDevolucao" required="true" value="${data}" cssClass="mascaraData" onchange="validaDataDevolucao(false)" onblur="validaDataDevolucao(false)"/>
		<@ww.textfield label="Qtd. Devolvida" name="solicitacaoEpiItemDevolucao.qtdDevolvida" id="qtdDevolvida" required="true"  onkeypress="return(somenteNumeros(event,''));" cssStyle="width: 25px;" maxLength="3" onchange="validaDataDevolucao(false)" onblur="validaDataDevolucao(false)" />
		<@ww.textarea label="Observação" name="solicitacaoEpiItemDevolucao.observacao" maxLength="100"/>
		<@ww.hidden name="solicitacaoEpi.id" id="solicitacaoEpiId" />
		<@ww.hidden name="solicitacaoEpiItem.id" id="solicitacaoEpiItemId"/>
		<@ww.hidden name="solicitacaoEpiItemDevolucao.id" id="solicitacaoEpiItemDevolucaoId" />
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="validaDataDevolucao(true);" class="btnGravar"></button>
		<button onclick="window.location='prepareEntrega.action?solicitacaoEpi.id=${solicitacaoEpi.id}'" class="btnVoltar"></button>
	</div>
</body>
</html>