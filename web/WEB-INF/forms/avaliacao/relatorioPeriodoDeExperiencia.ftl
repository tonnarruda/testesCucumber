<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>

<head><@ww.head/>
	<title>Acompanhamento do Período de Experiência</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>

	<#include "../ftl/mascarasImports.ftl" />

	<script type='text/javascript'>
		$(function() 
		{
			$('#wwctrl_periodoCheck * span').eq(0).removeAttr('onclick').css('color', '#6E7B8B').css('cursor', 'default');
		});
		function validaQtd()
		{
		    if($("input[name='periodoCheck']:checked").size() >= 4)
		        $("input[name='periodoCheck']").not(':checked').attr('disabled','disabled').parent().css('color', '#DEDEDE');
		    else
		        $("input[name='periodoCheck']").removeAttr('disabled').parent().css('color', '#5C5C5A');
		}
		function submeterAction(action)
		{
			$('form[name=form]').attr('action', action);
			return validaFormulario('form', new Array('periodoIni', 'periodoFim','@periodoCheck'), new Array('periodoIni', 'periodoFim'));
		}
	</script> 
	<#assign periodoIniFormatado = "${periodoIni?date}" />
	<#assign periodoFimFormatado = "${periodoFim?date}" />
</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="imprimeRelatorioPeriodoDeAcompanhamentoDeExperiencia.action" method="POST">

		<@ww.datepicker label="Período" required="true" name="periodoIni" id="periodoIni" cssClass="mascaraData validaDataIni" liClass="liLeft" after="a" value="${periodoIniFormatado}"/>
		<@ww.datepicker label="" name="periodoFim" id="periodoFim" cssClass="mascaraData validaDataFim" value="${periodoFimFormatado}"/><br/>
		<@frt.checkListBox label="Período de Acompanhamento (máx. 4 opções) *" name="periodoCheck" id="periodoCheck" list="periodoCheckList" onClick="validaQtd();" filtro="true"/>						
		<@frt.checkListBox label="Estabelecimento" name="estabelecimentoCheck" id="estabelecimentoCheck" list="estabelecimentoCheckList" filtro="true"/>						
		<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
			
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="return submeterAction('imprimeRelatorioPeriodoDeAcompanhamentoDeExperiencia.action');" class="btnRelatorio" ></button>
		<button onclick="return submeterAction('imprimeRelatorioPeriodoDeAcompanhamentoDeExperienciaXls.action');" class="btnRelatorioExportar"></button>
	</div>
</body>

</html>