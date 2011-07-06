<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<title>Acompanhamento do Período de Experiência</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript'>
		$(function() {
			$('#wwctrl_periodoCheck * span').eq(0).removeAttr('onclick').css('color', '#6E7B8B').css('cursor', 'default');
		});
		function validaQtd()
		{
		    if($("input[name='periodoCheck']:checked").size() >= 4)
		        $("input[name='periodoCheck']").not(':checked').attr('disabled','disabled').parent().css('color', '#DEDEDE');
		    else
		        $("input[name='periodoCheck']").removeAttr('disabled').parent().css('color', '#5C5C5A');
		}
	</script> 

	<#assign DataAtual = "${dataDoDia}"/>
	<#assign validarCampos="return validaFormulario('form', new Array('data','tempoDeEmpresa','@periodoCheck'), new Array('data'))"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<#include "../ftl/mascarasImports.ftl" />

		<@ww.form name="form" action="imprimeRelatorioPeriodoDeAcompanhamentoDeExperiencia.action" onsubmit="${validarCampos}" method="POST">
		
			<@ww.datepicker label="Data de Referência" id="data" name="dataReferencia" required="true" value="${DataAtual}" cssClass="mascaraData"/>

			<br>
			Considerar colaboradores com até  
			<@ww.textfield theme="simple" name="tempoDeEmpresa" id="tempoDeEmpresa" cssStyle="width:60px; text-align:right;" maxLength="8" onkeypress = "return(somenteNumeros(event,''));"/> 
			dias de empresa.*
			<br><br>
			
			<@frt.checkListBox label="Período de Acompanhamento (máx. 4 opções) *" name="periodoCheck" id="periodoCheck" list="periodoCheckList" onClick="validaQtd();"/>						
			<@frt.checkListBox label="Estabelecimento" name="estabelecimentoCheck" id="estabelecimentoCheck" list="estabelecimentoCheckList"/>						
			<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areasCheckList"/>
			
		</@ww.form>

		<div class="buttonGroup">
			<button class="btnRelatorio" onclick="${validarCampos};" ></button>
		</div>
</body>
</html>