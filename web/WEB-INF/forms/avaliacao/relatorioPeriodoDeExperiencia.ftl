<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>

<head><@ww.head/>
	<title>Acompanhamento do Período de Experiência</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>

	<#include "../ftl/mascarasImports.ftl" />

	<script type='text/javascript'>
		$(function() 
		{
			$('#wwctrl_periodoCheck * span').eq(0).removeAttr('onclick').css('color', '#6E7B8B').css('cursor', 'default');
			
			$('#tooltipHelp').qtip({content: 'Caso queira visualizar todos os acompanhamentos atrasados,basta não informar a período inicial(deixá-lo em branco).'});
		});
		function validaQtd()
		{
		    if($("input[name='periodoCheck']:checked").size() >= 4)
		        $("input[name='periodoCheck']").not(':checked').attr('disabled','disabled').parent().css('color', '#DEDEDE');
		    else
		        $("input[name='periodoCheck']").removeAttr('disabled').parent().css('color', '#5C5C5A');
		}
	</script> 
	<#assign dataIni = "" />
	<#assign dataFim = "${periodoFim?date}" />
	<#assign validarCampos="return validaFormulario('form', new Array('periodoFim','@periodoCheck'), new Array('periodoIni', 'periodoFim'))"/>
</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="imprimeRelatorioPeriodoDeAcompanhamentoDeExperiencia.action" onsubmit="${validarCampos}" method="POST">

		<div>
			Período:
			<img id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -4px" />
		</div>
		<@ww.datepicker  id="periodoIni" name="periodoIni" required="true" value="${dataIni}" cssClass="mascaraData" liClass="liLeft"/>
		<@ww.label value="a" liClass="liLeft"/>
		<@ww.datepicker  id="periodoFim" name="periodoFim" required="true" value="${dataFim}" cssClass="mascaraData"  liClass="liLeft"/>
		<@ww.label value="*"/><br/>

		<@frt.checkListBox label="Período de Acompanhamento (máx. 4 opções) *" name="periodoCheck" id="periodoCheck" list="periodoCheckList" onClick="validaQtd();"/>						
		<@frt.checkListBox label="Estabelecimento" name="estabelecimentoCheck" id="estabelecimentoCheck" list="estabelecimentoCheckList"/>						
		<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areasCheckList"/>
			
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio" onclick="${validarCampos};" ></button>
	</div>
</body>

</html>